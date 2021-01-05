package dao;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import beans.Card;
import beans.Manifestation;
import beans.User;
import beans.UserType;

// Ucitavamo korisnike iz

public class UserDAO {
	
	
	private HashMap<String, User> users= new HashMap<String,User>();
	private ArrayList<User> usersList = new ArrayList<User>();
	
	public UserDAO(String path,ManifestationDAO mDAO,CardDAO cDAO) {
		loadUsers(path,mDAO,cDAO);
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
	
	public void loadUsers(String path,ManifestationDAO mDAO,CardDAO cDAO) {
		BufferedReader in = null;
		try {
			File file = new File(path);
			in = new BufferedReader(new FileReader(file));
			String line;
			String[] params;
			line = in.readLine();
			while ((line = in.readLine()) != null) {
				params = line.split(",");
				if(params[0].equals("admin")) {
					User admin = generateAdmin(params);
					users.put(admin.getRole(),admin);
					usersList.add(admin);
				}
				else if(params[0].equals("worker")) {
					User worker = generateWorker(params,mDAO.getManifestations());
					users.put(worker.getRole(),worker);
					usersList.add(worker);
				}
				else if(params[0].equals("user")) {
					User user = generateUser(params,cDAO.getCards());
					users.put(user.getRole(),user);
					usersList.add(user);
				}
				
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
	
	private User generateUser(String[] data,HashMap<String, Card> allCards) throws ParseException {
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data[6]);
		
		UserType userType = new UserType(data[8]);
		
		ArrayList<Card> pCards = new ArrayList<Card>();
		if(!(data[9].equals(""))) {
			String[] cardIds = data[9].split(";");
			for(int i = 0;i < cardIds.length - 1;i++) {
				System.out.println(cardIds[i]);
				Card c = allCards.get(cardIds[i]);
				pCards.add(c);
			}
		}
		User user = new User(data[0],data[1],data[2],data[3],data[4],data[5],date,data[7],userType,pCards);
		
		return user;
	}
	
	private User generateWorker(String[] data,HashMap<String, Manifestation> allManifestations) throws ParseException{
		ArrayList<Manifestation> manifestations = new ArrayList<Manifestation>();
		String[] manifestationIds = data[7].split(";");
		for(int i = 0;i < manifestationIds.length - 1;i++) {
			Manifestation m = allManifestations.get(manifestationIds[i]);
			manifestations.add(m);
		}
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data[6]);
		User worker = new User(data[0],data[1],data[2],data[3],data[4],data[5],date,manifestations);
		return worker;
	}
	
	private User generateAdmin(String[] data) throws ParseException {
		Date date =new SimpleDateFormat("dd/MM/yyyy").parse(data[6]);
		User admin = new User(data[0],data[1],data[2],data[3],data[4],data[5],date);
		return admin;
	}

	public void saveUsers(ArrayList<User> users, String path) {
		
	}

	public HashMap<String, User> getUsers() {
		return users;
	}

	public void setUsers(HashMap<String, User> users) {
		this.users = users;
	}

	public ArrayList<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(ArrayList<User> usersList) {
		this.usersList = usersList;
	}
} 
