package beans;

import java.util.ArrayList;
import java.util.Date;

public class User {
	
	//public enum Gender{
		//MALE,
		//FEMALE
	//}
	
	private String username;
	private String password;
	private String name;
	private String surname;
	private String gender;
	private Date birthDate;
	private UserType userType;
	private String collectedPoints;
	private String role;
	private ArrayList<Card> pCards;
	private ArrayList<Manifestation> manifestations;

	
	
	public User() {
	}
	public String getUsername() {
		return username;
	}
	// Konstruktor za obicno korisnika
	public User(String role,String username,String password,String name,String surname,String gender,Date birthDate,String collectedPoints,UserType userType,ArrayList<Card> pCards) {
		this.role = role;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.birthDate = birthDate;
		this.collectedPoints = collectedPoints;
		this.pCards = new ArrayList<Card>();
		this.pCards = pCards;
		this.userType = userType;
		this.manifestations = null;
		
	}

	// Konstruktor za prodavca
	public User(String role,String username,String password,String name,String surname,String gender,Date birthDate,ArrayList<Manifestation> manifestations) {
		this.role = role;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.birthDate = birthDate;
		this.manifestations = new ArrayList<Manifestation>();
		this.manifestations = manifestations;
		this.collectedPoints = null;
		this.pCards = null;
		this.collectedPoints = null;
		this.userType = null;
	}
	// Konstruktor za admina
	public User(String role,String username,String password,String name,String surname,String gender,Date birthDate) {
		this.role = role;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.birthDate = birthDate;
		this.collectedPoints = null;
		this.pCards = null;
		this.collectedPoints = null;
		this.manifestations = null;
		this.userType = null;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getCollectedPoints() {
		return collectedPoints;
	}
	public void setCollectedPoints(String collectedPoints) {
		this.collectedPoints = collectedPoints;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public ArrayList<Card> getpCards() {
		return pCards;
	}
	public void setpCards(ArrayList<Card> pCards) {
		this.pCards = pCards;
	}
	public ArrayList<Manifestation> getManifestations() {
		return manifestations;
	}
	public void setManifestations(ArrayList<Manifestation> manifestations) {
		this.manifestations = manifestations;
	}
}
