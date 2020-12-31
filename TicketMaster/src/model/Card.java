package model;

import java.util.Date;

public class Card {
	enum CardType{
		VIP,REGULAR,FAN_PIT
	}
	
	private String id;
	private Manifestation manifestation;
	private Date manifestationDate;
	private String price;
	private User user;
	private boolean status;
	private CardType cardType;
	public Card() {
		// TODO Auto-generated constructor stub
	}

}
