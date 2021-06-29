package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import dao.CardDAO;
import dao.ManifestationDAO;

@Path("/comment")
public class CommentService {
	
	@Context
	ServletContext ctx;
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("commentDAO") == null) {
		}
	}
}
