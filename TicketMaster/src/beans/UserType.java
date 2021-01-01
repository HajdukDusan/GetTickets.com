package beans;

public class UserType {
	
	enum Type{
		GOLD,
		SILVER,
		BRONZE
	}
	private Type type;
	private String discount;
	private String requiredPoints;
	
	public UserType() {
		
	}

}
