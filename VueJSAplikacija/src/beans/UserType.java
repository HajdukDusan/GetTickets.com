package beans;

public class UserType {
	
//	enum Type{
	//	GOLD,
		//SILVER,
	//	BRONZE
//	}
	private String type;
	private String requiredPoints;
	private Double discount;
	
	public UserType() {
		
	}
	public UserType(String type, Double discount, String requiredPoints) {
		super();
		this.type = type;
		this.discount = discount;
		this.requiredPoints = requiredPoints;
	}
	public void checkPoints(Double points) {
		if(points < 3000) {
			this.type = "BRONZE";
			this.discount = 0.0;
		}
		else if(points< 4000) {
			this.type = "SILVER";
			this.discount = 0.03;
		}
		else {
			this.type ="GOLD";
			this.discount = 0.05;
		}
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

	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public String getRequiredPoints() {
		return requiredPoints;
	}

	public void setRequiredPoints(String requiredPoints) {
		this.requiredPoints = requiredPoints;
	}
	
	

}
