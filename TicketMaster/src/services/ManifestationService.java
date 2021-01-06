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

import beans.Manifestation;
import dao.ManifestationDAO;
@Path("/manifestation")
public class ManifestationService {

	
	@Context
	ServletContext ctx;
	
	public ManifestationService() {
		// TODO Auto-generated constructor stub
	}
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("manifestationDAO") == null) {
			String path = "C:\\Users\\Hajduk\\git\\TicketMaster\\git\\TicketMaster\\WebContent\\data\\manifestations.txt";
			ctx.setAttribute("manifestationDAO", new ManifestationDAO(path));
		}
	}
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() 
	{
        return "nesto";
    }
	@GET
	@Path("/manifestations")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getManifestations(){
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		return dao.getManifestationsList();
	}
}
