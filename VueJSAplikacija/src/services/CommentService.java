package services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import dao.CardDAO;
import dao.CommentDAO;
import dao.ManifestationDAO;
import dao.UserDAO;
import beans.Comment;
@Path("/comment")
public class CommentService {
	
	@Context
	ServletContext ctx;
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("commentDAO") == null) {
			
			String path = ctx.getRealPath("/") + "data\\manifestations.txt";
			String path1 = ctx.getRealPath("/")+ "data\\cards.txt";
			String path2 = ctx.getRealPath("/")+ "data\\users.txt";
			String path3 = ctx.getRealPath("/")+ "data\\comments.txt";
			if(ctx.getAttribute("manifestationDAO")== null) {
				ManifestationDAO mDAO = new ManifestationDAO(path);
				ctx.setAttribute("manifestationDAO", mDAO);
			}
			if(ctx.getAttribute("cardDAO")== null) {
				CardDAO cDAO = new CardDAO(path1, (ManifestationDAO) ctx.getAttribute("manifestationDAO"));
				ctx.setAttribute("cardDAO", cDAO);
			}
			if(ctx.getAttribute("userDAO") == null) {
				ctx.setAttribute("userDAO", new UserDAO(path2,(ManifestationDAO) ctx.getAttribute("manifestationDAO"),(CardDAO) ctx.getAttribute("cardDAO")));
			}
			ctx.setAttribute("commentDAO", new CommentDAO(path3,(ManifestationDAO) ctx.getAttribute("manifestationDAO"),(CardDAO) ctx.getAttribute("cardDAO"), (UserDAO) ctx.getAttribute("userDAO")));
		}
	}
	@GET
	@Path("/comments")
	  public List<Comment> allComments() {  
		
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
	    return dao.getCommentsList();
	  }  
	@GET
	@Path("/manifestation={manifestation}")
	  public List<Comment> allComments(@PathParam(value = "manifestation") String manifestation) {  
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
	    return dao.findByManifestation(manifestation);
	  } 
}
