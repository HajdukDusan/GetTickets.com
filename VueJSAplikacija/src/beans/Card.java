package beans;

import java.util.Date;

public class Card {
	enum CardType{
		VIP,REGULAR,FAN_PIT
	}
	
	private String id;
	private String manifestation;
	private Date manifestationDate;
	private String price;
	private String buyersName;
	private String buyersSurname;
	private boolean status;
	private String cardType;
	
	public Card(String id, String manifestation, Date manifestationDate, String price, String buyersName,
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getManifestation() {
		return manifestation;
	}
	public void setManifestation(String manifestation) {
		this.manifestation = manifestation;
	}
	public Date getManifestationDate() {
		return manifestationDate;
	}
	public void setManifestationDate(Date manifestationDate) {
		this.manifestationDate = manifestationDate;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getBuyersName() {
		return buyersName;
	}
	public void setBuyersName(String buyersName) {
		this.buyersName = buyersName;
	}
	public String getBuyersSurname() {
		return buyersSurname;
	}
	public void setBuyersSurname(String buyersSurname) {
		this.buyersSurname = buyersSurname;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
}
