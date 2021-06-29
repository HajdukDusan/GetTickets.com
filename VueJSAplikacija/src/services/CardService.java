package services;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Card;
import beans.Manifestation;
import beans.User;
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
}
