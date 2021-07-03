package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import beans.Card;
import beans.Comment;
import beans.Comment.CommentStatus;
import beans.User;

public class CommentDAO {
	
	String pathcmt;
	
	private HashMap<String, Comment> comments= new HashMap<String,Comment>();
	private ArrayList<Comment> commentsList = new ArrayList<Comment>();
	public CommentDAO() {
		// TODO Auto-generated constructor stub
	}

	
	public CommentDAO(String path, ManifestationDAO mDAO, CardDAO cDAO, UserDAO uDAO) {
		loadComments(path);
		pathcmt = path;
	}
	public Comment save(Comment c) {
		commentsList.add(c);
		BufferedWriter out = null;
		try {
			File file = new File(pathcmt);
			System.out.println(pathcmt);
			out = new BufferedWriter(new FileWriter(file, true));
			out.write(c.toString());
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ( out != null ) {
				try {
					out.close();
				}
				catch (Exception e) { }
			}
		}
		return c;
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
	public List<Comment> findByManifestation(String manifestation,CommentStatus status){
		ArrayList<Comment> commentsManif = new ArrayList<Comment>();
		for(Comment c: commentsList) {
			if(c.getManifestation().equals(manifestation) && c.getStatus().equals(status)) {
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
	public Comment updateStatus(String user,String manifestation, String text, String grade,CommentStatus status) {
		for(Comment c: commentsList) {
			if(c.getText().equals(text) && c.getManifestation().equals(manifestation) && c.getGrade().equals(grade) && c.getStatus().equals(CommentStatus.PENDING)) {
				c.setStatus(status);
				return c;
			}
		}
		return null;
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
	public List<Comment> findByManifestationApproved(String manifestation) {
		ArrayList<Comment> commentsManif = new ArrayList<Comment>();
		for(Comment c: commentsList) {
			if(c.getManifestation().equals(manifestation)) {
				commentsManif.add(c);
				}
		}
		return commentsManif;
	}

	public Double calculateAverage(String manifestation) {
		List<Comment> comments = findByManifestation(manifestation,CommentStatus.APPROVED);
		Double score = 0.0;
		for(Comment c: comments) {
			score += Double.parseDouble(c.getGrade());
		}
		return score/comments.size();
	}
}
