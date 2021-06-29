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
	
	public User loginUser(String username,String password) {
		System.out.println(username + password);
		if(!users.containsKey(username)) {
			return null;
		}
		System.out.println(username + password);
		User u = users.get(username);
		if(!u.getPassword().equals(password)) {
			return null;
		}
		System.out.println(username + password);
		return u;
	}
	
	public User save(User user) {
		for(User u: usersList) {
			if(u.getUsername().equals(user.getUsername())) {
				usersList.remove(user);
			}
		}
		users.put(user.getUsername(),user);
		usersList.add(user);
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
					users.put(admin.getUsername(),admin);
					usersList.add(admin);
				}
				else if(params[0].equals("worker")) {
					User worker = generateWorker(params,mDAO.getManifestations());
					users.put(worker.getUsername(),worker);
					usersList.add(worker);
				}
				else if(params[0].equals("user")) {
					User user = generateUser(params,cDAO.getCards());
					users.put(user.getUsername(),user);
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
		ArrayList<String> pCardsIds = new ArrayList<String>();
		if(data.length == 11) {
			if(!(data[10].equals(""))) {
				String[] cardIds = data[10].split(";");
				for(String s: cardIds) {
					System.out.println(s);
					Card c = allCards.get(s);
					pCards.add(c);
					pCardsIds.add(c.getId());
				}
			}
		}

		
		User user = new User(data[0],data[1],data[2],data[3],data[4],data[5],date,data[7],userType,pCards);
		user.setpCardsIds(pCardsIds);
		return user;
	}
	
	private User generateWorker(String[] data,HashMap<String, Manifestation> allManifestations) throws ParseException{
		ArrayList<Manifestation> manifestations = new ArrayList<Manifestation>();
		ArrayList<String> manifestationsIds = new ArrayList<String>();
		String[] manifestationNames = data[8].split(";");
		for(String s: manifestationNames) {
			System.out.println(s);
		}
		for(String s:manifestationNames) {
			
			Manifestation m = allManifestations.get(s);
			manifestationsIds.add(m.getName());
		}
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data[6]);
		User worker = new User(data[0],data[1],data[2],data[3],data[4],data[5],date,manifestations);
		worker.setManifestationsIds(manifestationsIds);
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
	public ArrayList<User> getUsersSearched(String ime,String prezime, String korisnickoIme,String tipKorisnika, boolean desc, String sortBy) {
		ArrayList<User> usersSearched = new ArrayList<User>();
		for(User m:usersList) {
			if((m.getName().toLowerCase().contains(ime.toLowerCase()) || ime.equals(""))
				&& (m.getSurname().toLowerCase().contains(prezime.toLowerCase()) || prezime.equals(""))
				&& (m.getUsername().toLowerCase().contains(korisnickoIme.toLowerCase()) || korisnickoIme.equals(""))
				&& (m.getRole().toLowerCase().contains(tipKorisnika.toLowerCase()) || tipKorisnika.equals("")))
				{
					usersSearched.add(m);
				}
		}
		ArrayList<User> sorted = new ArrayList<User>();
		
		while(usersSearched.size() != 0) {
		
			String pik = "";
			User remove = null;
			for(User u : usersSearched) {
				if(sortBy.equals("imenu")) {
						if(pik.compareTo(u.getName()) < 0) {
							pik = u.getName();
							remove = u;
						}
				}
				else if(sortBy.equals("prezimenu")) {
						if(pik.compareTo(u.getSurname()) < 0) {
							pik = u.getSurname();
							remove = u;
						}
				}
				else if(sortBy.equals("korisnickom imenu")) {
						if(pik.compareTo(u.getUsername()) < 0) {
							pik = u.getUsername();
							remove = u;
						}
				}
			}
			
			sorted.add(remove);
			usersSearched.remove(remove);
			
		}
		ArrayList<User> flipped = new ArrayList<User>();
		if(desc) {
			for(int i = sorted.size()-1; i >= 0; i--) {
				flipped.add(sorted.get(i));
			}
			return flipped;
		}
		
		return sorted;
	}
} 
