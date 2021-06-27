package services;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import beans.Manifestation;
import beans.User;
import dao.ManifestationDAO;
import dao.UserDAO;
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
			String actualPath = ctx.getRealPath("/");
			System.out.println(actualPath);
			String path = actualPath + "data\\manifestations.txt";
			ctx.setAttribute("manifestationDAO", new ManifestationDAO(path));
	        String userDirectory = new File("").getAbsolutePath();
		}
	}
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() 
	{
        String actualPath = ctx.getRealPath("/");
        System.out.println(actualPath);
        return "Hello Wosdadadrld";
    }
	@GET
	@Path("/manifestationsSeached/naziv={naziv}/grad={grad}/min={min}/max={max}/minDatum={minDatum}/maxDatum={maxDatum}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getManifestationsNaziv(@PathParam("naziv") String naziv, @PathParam("grad") String grad,
			@PathParam("min") Integer min, @PathParam("max") Integer max,
			@PathParam("minDatum") String minDatum, @PathParam("maxDatum") String maxDatum){
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		if(naziv.equals("\"\"")) {
			naziv = "";
		}
		if(grad.equals("\"\"")) {
			grad = "";
		}
		if(min == 0) {
			min = Integer.MIN_VALUE;
		}
		if(max == 0) {
			max = Integer.MAX_VALUE;
		}
		return dao.getManifestationsSearched(naziv,grad,min,max);
	}
	@GET
	@Path("/manifestations")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getManifestations(){
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		return dao.getManifestationsList();
	}
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Manifestation findOne(@PathParam("id") String id){
		
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		return dao.getManifestations().get(id);
	}
	@POST
	@Path("/saveManifestation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveManifestation(Manifestation manifestation) throws IOException {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		System.out.println(manifestation);
		if(!dao.saveManifestation(manifestation)) {
			return Response.status(Response.Status.NOT_FOUND).entity("Vec postoji manifestaciji na ovoj lokaciji u datom vremenu").build();
		}
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		User u = userDAO.findUser(manifestation.getCookie().split("-")[0]);
		u.getManifestationsIds().add(manifestation.getName());
		userDAO.save(u);
		return Response.ok("",MediaType.APPLICATION_JSON).build();
		
       // manifestation.setEventPoster("images\\test" + extension);
	}
	
}
