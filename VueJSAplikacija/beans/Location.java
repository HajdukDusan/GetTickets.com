package beans;

public class Location {
	// geo. duzina
	private String longitude;
	//geo. sirina
	private String latitude;
	//adresa formata ulica broj, grad, postanski broj
	private String address;
	
	private String city;
	
	private String country;
	
	
	public Location() {

	}


	public Location(String longitude, String latitude, String address,String city,String country) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.address = address;
		this.city = city;
		this.country = country;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
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


	@Override
	public boolean equals(Object obj) {
		   if (obj == null)
		   {
		      return false;
		   }

		   if (this.getClass() != obj.getClass())
		   {
		      return false;
		   }
		Location temp = (Location) obj;
		if(this.getAddress().equals(temp.getAddress()) && this.getCity().equals(temp.getCity()) && this.getCountry().equals(temp.getCountry())) {
			return true;
		}
		return false;
	}


	@Override
	public String toString() {
		return longitude + "," + latitude + "," + address + "," + city
				+ "," + country;
	}

}
