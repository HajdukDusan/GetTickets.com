package beans;

import java.util.Date;

public class Card {
	enum CardType{
		VIP,REGULAR,FAN_PIT
	}
	
	private String id;
	private Manifestation manifestation;
	private Date manifestationDate;
	private String price;
	private String buyersName;
	private String buyersSurname;
	private boolean status;
	private String cardType;
	public Card() {
		// TODO Auto-generated constructor stub
	}
	public Card(String id, Manifestation manifestation, Date manifestationDate, String price, String buyersName,
			String buyersSurname, boolean status, String cardType) {
		super();
		this.id = id;
		this.manifestation = manifestation;
		this.manifestationDate = manifestationDate;
		this.price = price;
		this.buyersName = buyersName;
		this.buyersSurname = buyersSurname;
		this.status = status;
		this.cardType = cardType;
	}
}
