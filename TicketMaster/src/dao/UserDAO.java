package dao;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import beans.User;

// Ucitavamo korisnike iz

public class UserDAO {
	
	
	private Map<String, User> users = new HashMap<String,User>();
	
	public UserDAO() {
		// TODO Auto-generated constructor stub
	}

	public UserDAO(String contextPath) {
		
	}

	public Collection<User> findAll() {
		return users.values();
	}

	public User findUser(String username) {
		return users.containsKey(username) ? users.get(username) : null;
	}
	
	public User save(User user) {
		users.put(user.getUsername(),user);
		return user;
		
	}
	
	private void loadUsers(String path) {
		BufferedReader in = null;
		
	}
	
}
