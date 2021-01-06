package beans;

public class Location {
	// geo. duzina
	private String longitude;
	//geo. sirina
	private String latitude;
	//adresa formata ulica broj, grad, postanski broj
	private String address;
	
	
	public Location() {

	}


	public Location(String longitude, String latitude, String address) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.address = address;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}

}
