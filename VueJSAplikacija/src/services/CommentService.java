package services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import dao.CardDAO;
import dao.CommentDAO;
import dao.ManifestationDAO;
import dao.UserDAO;
import beans.Comment;
import beans.Comment.CommentStatus;
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
			String path4 = ctx.getRealPath("/")+ "data\\otkazivanja.txt";
			if(ctx.getAttribute("manifestationDAO")== null) {
				ManifestationDAO mDAO = new ManifestationDAO(path);
				ctx.setAttribute("manifestationDAO", mDAO);
			}
			if(ctx.getAttribute("cardDAO")== null) {
				CardDAO cDAO = new CardDAO(path1, (ManifestationDAO) ctx.getAttribute("manifestationDAO"));
				ctx.setAttribute("cardDAO", cDAO);
			}
			if(ctx.getAttribute("userDAO") == null) {
				ctx.setAttribute("userDAO", new UserDAO(path2,path4,(ManifestationDAO) ctx.getAttribute("manifestationDAO"),(CardDAO) ctx.getAttribute("cardDAO")));
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
	    return dao.findByManifestation(manifestation,CommentStatus.APPROVED);
	}
	@GET
	@Path("/manifestationAll/manifestacija={manifestation}")
	  public List<Comment> allCommentsManif(@PathParam(value = "manifestation") String manifestation) {  
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
	    return dao.findByManifestation(manifestation);
	}
	@POST
	@Path("/addComment")
	public void addComment(Comment comment) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		comment.setStatus(CommentStatus.PENDING);
		dao.save(comment);
		System.out.println(dao.getCommentsList());
	}
	@GET
	@Path("/commentsPending/manifestacija={manifestacija}")
	public List<Comment> getCommentsPending(@PathParam("manifestacija") String manifestacija){
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.findByManifestation(manifestacija, CommentStatus.PENDING);
	}
	@GET
	@Path("/commentUpdate/manifestacija={manifestacija}/korisnik={korisnik}/grade={grade}/text={text}/status={status}")
	public void commentUpdate(@PathParam("manifestacija") String manifestacija,@PathParam("korisnik") String korisnik,
			@PathParam("text") String text, @PathParam("grade") String grade,@PathParam("status") CommentStatus status) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		Comment c = dao.updateStatus(korisnik.split("-")[0], manifestacija, text, grade,status);
		System.out.println(c);
	}
	@GET
	@Path("/averageScore/manifestation={manifestation}")
	public Double averageScore(@PathParam("manifestation") String manifestation) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.calculateAverage(manifestation);
	}
}
