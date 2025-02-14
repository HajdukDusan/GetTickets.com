package beans;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@JsonFormat(pattern = "YYYY-MM-DD")
	private Date birthDate;

	private UserType userType;
	private Double collectedPoints;
	private String role;
	private ArrayList<Card> pCards;
	private ArrayList<String> pCardsIds;
	private ArrayList<Manifestation> manifestations;
	private ArrayList<String> manifestationsIds;
	private ArrayList<String> comments;
	private boolean isDeleted;
	private boolean blocked;
	
	private Integer numOfPenals = 0;
	
	public User() {
	}
	public String getUsername() {
		return username;
	}
	// Konstruktor za obicno korisnika
	public User(String role,String username,String password,String name,String surname,String gender,Date birthDate,Double collectedPoints,UserType userType,ArrayList<Card> pCards,boolean isDeleted,boolean blocked) {
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
		this.pCardsIds = new ArrayList<String>();
		this.userType = userType;
		this.manifestations = null;
		this.blocked = blocked;
		this.isDeleted = isDeleted;
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
		this.manifestationsIds = new ArrayList<String>();
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
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public ArrayList<String> getpCardsIds() {
		return pCardsIds;
	}
	public void setpCardsIds(ArrayList<String> pCardsIds) {
		this.pCardsIds = pCardsIds;
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
	public Double getCollectedPoints() {
		return collectedPoints;
	}
	public void setCollectedPoints(Double collectedPoints) {
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
	public ArrayList<String> getManifestationsIds() {
		return manifestationsIds;
	}
	public void setManifestationsIds(ArrayList<String> manifestationsIds) {
		this.manifestationsIds = manifestationsIds;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String toStringUser() {
		
		String tmp = "";
		for(int i = 0; i < pCardsIds.size(); i++) {
			if(i != 0) {
				tmp += ";" + pCardsIds.get(i);
			}
			else {
				tmp += pCardsIds.get(i);
			}
		}
		
		return  role + "," + username + "," + password + "," + name + "," + surname
				+ "," + gender + "," + birthDate + "," + collectedPoints + "," + userType + ","
				+ isDeleted  + "," + tmp + "\n";
	}
	public String toStringWorker() {
		
		String tmp = "";
		for(int i = 0; i < manifestationsIds.size(); i++) {
			if(i != 0) {
				tmp += ";" + manifestationsIds.get(i);
			}
			else {
				tmp += manifestationsIds.get(i);
			}
		}
		
		return  role + "," + username + "," + password + "," + name + "," + surname
				+ "," + gender + "," + birthDate + "," + isDeleted + "," + tmp + "\n";
	}
	public String toStringAdmin() {
		return  role + "," + username + "," + password + "," + name + "," + surname
				+ "," + gender + "," + birthDate + "," + isDeleted + "\n";
	}
	public Integer getNumOfPenals() {
		return numOfPenals;
	}
	public void setNumOfPenals(Integer numOfPenals) {
		this.numOfPenals = numOfPenals;
	}
}
