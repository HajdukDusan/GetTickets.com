package model;

import java.util.ArrayList;
import java.util.Date;

public class User {
	
	enum Gender{
		MALE,
		FEMALE,
		OTHER
		
	}
	
	private String username;
	private String password;
	private String name;
	private String surname;
	private Gender gender;
	private Date birthDate;
	private UserType userType;
	private ArrayList<Card> pCards;
	private ArrayList<Manifestation> manifestations;
	private String collectedPoints;
	
	public User() {
	}

}
