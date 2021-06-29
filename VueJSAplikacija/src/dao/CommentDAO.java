package dao;

import java.util.ArrayList;
import java.util.HashMap;

import beans.Comment;
import beans.User;

public class CommentDAO {

	public CommentDAO() {
		// TODO Auto-generated constructor stub
	}
	private HashMap<String, Comment> comments= new HashMap<String,Comment>();
	private ArrayList<Comment> commentsList = new ArrayList<Comment>();
}
