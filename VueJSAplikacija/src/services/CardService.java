package services;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Card;
import beans.Card.CardType;
import beans.Manifestation;
import beans.User;
import beans.UserOtkazivanje;
import dao.CardDAO;
import dao.ManifestationDAO;
import dao.UserDAO;
import beans.Manifestation;
@Path("/card")
public class CardService {

	@Context
	ServletContext ctx;
	
	public CardService() {
	}
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("cardDAO") == null) {
			String path = ctx.getRealPath("/") + "data\\manifestations.txt";
			String path1 = ctx.getRealPath("/") + "data\\cards.txt";
			
			if(ctx.getAttribute("manifestationDAO")== null) {
				ManifestationDAO mDAO = new ManifestationDAO(path);
				ctx.setAttribute("manifestationDAO", mDAO);
			}
			ctx.setAttribute("cardDAO", new CardDAO(path1, (ManifestationDAO) ctx.getAttribute("manifestationDAO")));
		}
	}
	@GET
	@Path("/cards")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Card> getCards(){
		CardDAO dao = (CardDAO) ctx.getAttribute("cardDAO");
		return dao.getCardsList();
	}
	@GET
	@Path("/userCards/cookie={cookie}")
	public List<Card> getUserCards(@PathParam(value = "cookie") String cookie){
		
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		CardDAO dao = (CardDAO) ctx.getAttribute("cardDAO");
		User u = userDAO.findUser(cookie.split("-")[0]);
		
		return dao.getUserCards(u.getpCardsIds());
	}
	@GET
	@Path("/manifestationReservedCardsCount/{manifestation}")
	public Integer getNumberOfCardsManif(@PathParam(value = "manifestation") String manifestation) {
		ManifestationDAO mDAO = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		CardDAO dao = (CardDAO) ctx.getAttribute("cardDAO");
		int seats = Integer.parseInt(mDAO.getManifestationByName(manifestation).getNumberOfSeats());
		seats = seats - dao.getManifestationCardsReserved(manifestation).size();
		System.out.println(seats);
		return seats;
	}
	@GET
	@Path("/cancelCard/cookie={cookie}/id={id}")
	public void cancelCard(@PathParam(value="cookie") String cookie,@PathParam(value="id") String id) {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		CardDAO dao = (CardDAO) ctx.getAttribute("cardDAO");
		ManifestationDAO mDAO = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		//pronadjemo korisnika
		User u = userDAO.findUserCookie(cookie);		
		
		Double points = u.getCollectedPoints();
		Card c = dao.findCard(id);
		c.setStatus(false);
		dao.save(c);
		
		
		UserOtkazivanje uot = new UserOtkazivanje(u.getUsername(), c.getId(), LocalDate.now());
		userDAO.saveUserOtkazivanjetoFile(uot);
		
	}
	@GET
	@Path("/buyCards/type={type}/number={number}/user={user}/Manifestation={manifestation}")
	public Response buyCards(@PathParam(value = "type") CardType type,@PathParam(value = "number") Integer number,@PathParam(value = "user") String user,@PathParam(value="manifestation") String manifestation) {
		CardDAO dao = (CardDAO) ctx.getAttribute("cardDAO");
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		ManifestationDAO mDAO = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		
		//pronadjemo manifestaciju za koju se kupuje karte
		Manifestation m = mDAO.getManifestationByName(manifestation);
		
		//pronadjemo korisnika koji kupuje karte
		User u = userDAO.findUser(user.split("-")[0]);
		//racunanje cene
		Double price = (double)number * (double) m.getRegularPrice();
		if(type.equals(CardType.VIP)) {
			price = price*4;
		}
		else if(type.equals(CardType.FAN_PIT)) {
			price = price*2;
		}
		//provera da li ima dovoljno karti na raspolaganju
		if(number > getNumberOfCardsManif(manifestation)) {
			
			return Response.status(Response.Status.NOT_FOUND).entity("Nema dovoljno karti na lageru").build();
		}
		
		//popust
		price = price - price*u.getUserType().getDiscount();
		Double points =  (double)(m.getRegularPrice())/1000 * 133;
		System.out.println(price);
		Random rand = new Random();
		for(int i=0;i<number;i++) {
			String id = "";
	        for(int j = 0;j<10;j++) {
	        	id = id + rand.nextInt(9) + 9;
	        }
			Card c = new Card(id,manifestation, m.getDateTime(), price.toString(), u.getUsername(), u.getSurname(), true, type);
			
			//saving
			dao.save(c);
			u.setCollectedPoints(u.getCollectedPoints() + points);
			u.getUserType().checkPoints(u.getCollectedPoints());
			System.out.println(u.getCollectedPoints());
			u.getpCardsIds().add(c.getId());
			
			userDAO.save(u);
		}
		return Response.ok("Uspesno ste kupili karte", MediaType.APPLICATION_JSON).build();
	}
}
