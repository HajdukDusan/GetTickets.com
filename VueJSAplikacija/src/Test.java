package src;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import src.dao.CardDAO;
import src.dao.ManifestationDAO;
import src.dao.UserDAO;

public class Test {

	public static void main(String[] args) {
	    /*ClientConfig config = new ClientConfig();  
	    Client client = ClientBuilder.newClient(config);  
	    WebTarget target = client.target(getBaseURI());  
		
	    //System.out.println(target.path("rest").path("hello").request().accept(MediaType.TEXT_PLAIN).get(String.class));  
		String path = "C:\\Users\\Nikola\\eclipse-workspace\\TicketMasterTest\\WebContent\\data";
		ManifestationDAO mDAO = new ManifestationDAO(path + "\\manifestations.txt");
		CardDAO cDAO = new CardDAO(path + "\\cards.txt",mDAO);
		System.out.println(cDAO.getCardsList());
		UserDAO uDAO = new UserDAO(path + "\\users.txt",mDAO,cDAO);
		*/
	}
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/TicketMasterTest/").build();
	}

}
