package services;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import beans.Card;
import beans.Manifestation;
import beans.User;
import beans.Card.CardType;
import beans.Manifestation.ManifestationStatus;
import beans.Manifestation.ManifestationType;
import dao.CardDAO;
import dao.CommentDAO;
import dao.ManifestationDAO;
import dao.UserDAO;
@Path("/manifestation")
public class ManifestationService {

	@Context
	ServletContext ctx;
	
	
	public static String path_image = "";
	
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
		}
	}
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() 
	{
        String actualPath = ctx.getRealPath("/");
        System.out.println(actualPath);
        return "Hello test";
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
    @POST
    @Path("/upload")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendFile(String value) throws IOException{
        String[] strings = value.split(",");
        String extension;
        switch (strings[0]) {//check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            default://should write cases for more images types
                extension = "jpg";
                break;
        }
        //convert base64 string to binary data
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
        String fileName = "";
        Random rn = new Random();
        for(int i = 0;i<10;i++) {
        	fileName = fileName + rn.nextInt(9) + 9;
        }
        //String path = "C:\\Users\\Hajduk\\Documents\\TicketMaster\\VueJSAplikacija\\WebContent\\images\\" +fileName + "." + extension;
        String path =  ctx.getRealPath("/") + "images\\" +fileName + "." + extension;
        File file = new File(path);
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        path_image = "images\\"+fileName + "." + extension;
        System.out.println(path_image);
        path =  "images\\"+fileName + "." + extension;
        return Response.ok(path, MediaType.APPLICATION_JSON).build();
    }
	@GET
	@Path("/manifestationsWorker/cookie={cookie}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getManifestationsWorker(@PathParam(value = "cookie") String cookie){
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		User worker = userDAO.findUser(cookie.split("-")[0]);
		return dao.getManifestationsWorker(worker.getManifestationsIds());
		
	}
	@GET
	@Path("/manifestations")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getManifestations(){
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		return dao.getManifestationsList();
	}
	@GET
	@Path("/pending-manifestations")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getPendingManifestations(){
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		return dao.getPendingManifestationsList();
	}
	@GET
	@Path("/approve/name={name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ApproveManifestations(@PathParam("name") String name){
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		Manifestation m = dao.getManifestationByName(name);
		m.setStatus(ManifestationStatus.APPROVED);
		dao.updateManifestation(m);
		return Response.ok("",MediaType.APPLICATION_JSON).build();
	}
	@GET
	@Path("/deny/name={name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response DenyManifestations(@PathParam("name") String name){
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		Manifestation m = dao.getManifestationByName(name);
		m.setStatus(ManifestationStatus.DENIED);
		dao.updateManifestation(m);
		return Response.ok("",MediaType.APPLICATION_JSON).build();
	}
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Manifestation findOne(@PathParam("id") String id){
		
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		return dao.getManifestations().get(id);
	}
	@POST
	@Path("/updateManifestation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateManifestation(Manifestation manifestation) throws IOException {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		dao.updateManifestation(manifestation);
		return Response.ok("",MediaType.APPLICATION_JSON).build();
		
       // manifestation.setEventPoster("images\\test" + extension);
	}
	@POST
	@Path("/saveManifestation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveManifestation(Manifestation manifestation) throws IOException {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		System.out.println(path_image);
		manifestation.setEventPoster(path_image);
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
	@GET
	@Path("/checkIfAttended/cookie={cookie}/manifestation={manifestation}")
	public boolean checkIfAttended(@PathParam("cookie") String cookie,@PathParam("manifestation") String manifestation) {
		CardDAO dao = (CardDAO) ctx.getAttribute("cardDAO");
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		ManifestationDAO mDAO = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		
		User u = userDAO.findUserCookie(cookie);
		List<Card> cards = dao.getManifestationCardsReserved(manifestation);
		for(String id: u.getpCardsIds()) {
			for(Card c: cards) {
				if(c.getId().equals(id)) {
					return true;
				}
			}
		}
		return false;
		
	}
	@GET
	@Path("/allBuyers/manifestation={manifestation}")
	public List<User> getAllBuyers(@PathParam("manifestation") String manifestation) {
		CardDAO dao = (CardDAO) ctx.getAttribute("cardDAO");
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		ManifestationDAO mDAO = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		return mDAO.getBuyers(userDAO.getUsersList(), dao.getManifestationCardsReserved(manifestation));
	}
	@GET
	@Path("/searched")
	public List<Manifestation> searched(
			@QueryParam("manifestacija") String manifestacija,
			@QueryParam("cenaOd") Integer cenaOd,
			@QueryParam("cenaDo") Integer cenaDo,
			@QueryParam("datumOd") String datumOd,
			@QueryParam("datumDo") String datumDo,
			@QueryParam("lokacija") String lokacija,
			@QueryParam("statusKarte") String status,
			@QueryParam("sortBy") String sortBy,
			@QueryParam("smer") String smer,
			@QueryParam("tipManifestacije") ManifestationType tipManifestacije,
			@QueryParam("raspolozivost") String raspolozivost) throws ParseException,NumberFormatException{
		List<Manifestation> manifestations = new ArrayList<Manifestation>();
		CommentDAO cDAO = (CommentDAO) ctx.getAttribute("commentDAO");
		ManifestationDAO mDAO = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		CardDAO dao = (CardDAO) ctx.getAttribute("cardDAO");

		final Date dateOdValue = mDAO.manifStringToDate(datumOd);
		final Date dateDoValue = mDAO.manifStringToDate(datumDo);
		if(sortBy.equals("Lokacija")) {
			manifestations =  mDAO.getManifestationsList().stream().filter(manifestation -> {
				return ((manifestacija == null || manifestacija.isEmpty() || manifestation.getName().toLowerCase().contains(manifestacija.toLowerCase()))
						&& (cenaOd == null || (manifestation.getRegularPrice() > cenaOd))
						&& (cenaDo == null || (manifestation.getRegularPrice() < cenaDo))
						&& (dateOdValue == null || datumOd.isEmpty() || manifestation.getDateTime().after(dateOdValue))
						&& (dateDoValue == null || datumDo.isEmpty() || manifestation.getDateTime().before(dateDoValue))
						&& (lokacija == null || lokacija.isEmpty()  || manifestation.getLocation().getCity().toLowerCase().contains(lokacija.toLowerCase()))
						&& (tipManifestacije == null || manifestation.getManifestationType().equals(tipManifestacije))
						&& (raspolozivost == null || raspolozivost.isEmpty() 
						|| (Integer.parseInt(manifestation.getNumberOfSeats())==dao.getManifestationCardsReserved(manifestation.getName()).size()))
						);
				}).sorted(Comparator.comparing(Manifestation::getLocation)).collect(Collectors.toList());
		}
		if(sortBy.equals("Cena Karte")) {
			manifestations =  mDAO.getManifestationsList().stream().filter(manifestation -> {
				return ((manifestacija == null || manifestacija.isEmpty() || manifestation.getName().toLowerCase().contains(manifestacija.toLowerCase()))
						&& (cenaOd == null || (manifestation.getRegularPrice() > cenaOd))
						&& (cenaDo == null || (manifestation.getRegularPrice() < cenaDo))
						&& (dateOdValue == null || datumOd.isEmpty() || manifestation.getDateTime().after(dateOdValue))
						&& (dateDoValue == null || datumDo.isEmpty() || manifestation.getDateTime().before(dateDoValue))
						&& (lokacija == null || lokacija.isEmpty()  || manifestation.getLocation().getCity().toLowerCase().contains(lokacija.toLowerCase()))
						&& (tipManifestacije == null || manifestation.getManifestationType().equals(tipManifestacije))
						&& (raspolozivost == null || raspolozivost.isEmpty() 
						|| (Integer.parseInt(manifestation.getNumberOfSeats())==dao.getManifestationCardsReserved(manifestation.getName()).size()))
						);
				}).sorted(Comparator.comparing(Manifestation::getRegularPrice)).collect(Collectors.toList());
		}
		if(sortBy.equals("Datum odrzavanja") ) {
			manifestations =  mDAO.getManifestationsList().stream().filter(manifestation -> {
				return ((manifestacija == null || manifestacija.isEmpty() || manifestation.getName().toLowerCase().contains(manifestacija.toLowerCase()))
						&& (cenaOd == null || (manifestation.getRegularPrice() > cenaOd))
						&& (cenaDo == null || (manifestation.getRegularPrice() < cenaDo))
						&& (dateOdValue == null || datumOd.isEmpty() || manifestation.getDateTime().after(dateOdValue))
						&& (dateDoValue == null || datumDo.isEmpty() || manifestation.getDateTime().before(dateDoValue))
						&& (lokacija == null || lokacija.isEmpty()  || manifestation.getLocation().getCity().toLowerCase().contains(lokacija.toLowerCase()))
						&& (tipManifestacije == null || manifestation.getManifestationType().equals(tipManifestacije))
						&& (raspolozivost == null || raspolozivost.isEmpty()|| !manifestation.getStatus().equals(ManifestationStatus.SOLD_OUT))
						);
				}).sorted(Comparator.comparing(Manifestation::getDateTime)).collect(Collectors.toList());
		}
		if(smer.equals("Rastuce")) {
			Collections.reverse(manifestations);
		}
//		for(Manifestation m: manifestations) {
//			if(m.getStatus().equals(ManifestationStatus.FINISHED)) {
//				m.setProsecnaOcena(cDAO.calculateAverage(m.getName()));
//			}
//		}
		return manifestations;
	}
}
