package services;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.User;
import dao.CardDAO;
import dao.ManifestationDAO;
import dao.UserDAO;
@Path("/user")  
public class UserService {  
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("userDAO") == null) {
			String path = "C:\\Users\\Nikola\\eclipse-workspace\\TicketMasterTest\\WebContent\\data\\manifestations.txt";
			String path1 = "C:\\Users\\Nikola\\eclipse-workspace\\TicketMasterTest\\WebContent\\data\\cards.txt";
			String path2 = "C:\\Users\\Nikola\\eclipse-workspace\\TicketMasterTest\\WebContent\\data\\users.txt";
			ManifestationDAO mDAO = new ManifestationDAO(path);
			CardDAO cDAO = new CardDAO(path1,mDAO);
			ctx.setAttribute("userDAO", new UserDAO(path2,mDAO,cDAO));
		}
	}
	
	
	
	@GET  
	@Produces(MediaType.TEXT_PLAIN)  
	  public String sayPlainTextHello() {  
	    return "Hello Jersey Plain";  
	  }  
	
	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getUsers(){
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.getUsersList();
	}
	
}   