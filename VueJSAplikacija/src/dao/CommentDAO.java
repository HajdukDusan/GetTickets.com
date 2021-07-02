package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import beans.Card;
import beans.Comment;
import beans.Comment.CommentStatus;
import beans.User;

public class CommentDAO {
	
	
	
	private HashMap<String, Comment> comments= new HashMap<String,Comment>();
	private ArrayList<Comment> commentsList = new ArrayList<Comment>();
	public CommentDAO() {
		// TODO Auto-generated constructor stub
	}

	
	public CommentDAO(String path, ManifestationDAO mDAO, CardDAO cDAO, UserDAO uDAO) {
		loadComments(path);
	}
	
	public void loadComments(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path);
			in = new BufferedReader(new FileReader(file));
			String line;
			String[] params;
			line = in.readLine();
			while ((line = in.readLine()) != null) {
				params = line.split(",");
				
				CommentStatus cs = CommentStatus.valueOf(params[4]);
				Comment c = new Comment(params[0],params[1],params[2],params[3],cs);
				commentsList.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ( in != null ) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}

	public List<Comment> findByManifestation(String manifestation){
		ArrayList<Comment> commentsManif = new ArrayList<Comment>();
		for(Comment c: commentsList) {
			if(c.getManifestation().equals(manifestation)) {
				commentsManif.add(c);
				}
		}
		return commentsManif;
	}
	public List<Comment> findByUser(String user){
		ArrayList<Comment> commentsManif = new ArrayList<Comment>();
		for(Comment c: commentsList) {
			if(c.getUser().equals(user)) {
				commentsManif.add(c);
				}
		}
		return commentsManif;
	}
	public HashMap<String, Comment> getComments() {
		return comments;
	}


	public void setComments(HashMap<String, Comment> comments) {
		this.comments = comments;
	}


	public ArrayList<Comment> getCommentsList() {
		return commentsList;
	}


	public void setCommentsList(ArrayList<Comment> commentsList) {
		this.commentsList = commentsList;
	}
	
}
