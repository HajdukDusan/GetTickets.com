package beans;

import java.util.Date;

import org.apache.tomcat.jni.File;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.util.Base64;  


public class Manifestation {
	private String name;
	private String manifestationType;
	private String numberOfSeats;
	private Date dateTime;
	private String regularPrice;
	private boolean status;
	private Location location;
	// treba promenuti
	private String eventPoster;
	
	

	public Manifestation(String name, String manifestationType, String numberOfSeats, Date dateTime,
			String regularPrice, boolean status, Location location, String eventPoster) {
		super();
		this.name = name;
		this.manifestationType = manifestationType;
		this.numberOfSeats = numberOfSeats;
		this.dateTime = dateTime;
		this.regularPrice = regularPrice;
		this.status = status;
		this.location = location;
		this.eventPoster = eventPoster;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManifestationType() {
		return manifestationType;
	}

	public void setManifestationType(String manifestationType) {
		this.manifestationType = manifestationType;
	}

	public String getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(String numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(String regularPrice) {
		this.regularPrice = regularPrice;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Manifestation() {
		// TODO Auto-generated constructor stub
	}

	
}
