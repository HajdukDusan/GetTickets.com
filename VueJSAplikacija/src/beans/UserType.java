package beans;

public class UserType {
	
//	enum Type{
	//	GOLD,
		//SILVER,
	//	BRONZE
//	}
	private String type;
	private String discount;
	private String requiredPoints;
	
	
	public UserType() {
		
	}
	public UserType(String type, String discount, String requiredPoints) {
		super();
		this.type = type;
		this.discount = discount;
		this.requiredPoints = requiredPoints;
	}

	public UserType(String type) {
		super();
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getRequiredPoints() {
		return requiredPoints;
	}

	public void setRequiredPoints(String requiredPoints) {
		this.requiredPoints = requiredPoints;
	}
	
	

}
