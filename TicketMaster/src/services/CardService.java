package services;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Card;
import beans.Manifestation;
import dao.CardDAO;
import dao.ManifestationDAO;
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
			String path = "C:\\Users\\Nikola\\eclipse-workspace\\TicketMasterTest\\WebContent\\data\\manifestations.txt";
			String path1 = "C:\\Users\\Nikola\\eclipse-workspace\\TicketMasterTest\\WebContent\\data\\cards.txt";
			ManifestationDAO mDAO = new ManifestationDAO(path);
			ctx.setAttribute("cardDAO", new CardDAO(path1,mDAO));
		}
	}
	@GET
	@Path("/cards")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Card> getCards(){
		CardDAO dao = (CardDAO) ctx.getAttribute("cardDAO");
		return dao.getCardsList();
	}
}
