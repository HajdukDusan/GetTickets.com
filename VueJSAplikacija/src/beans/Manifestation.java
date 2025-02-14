package beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Base64;  


public class Manifestation {
	public enum ManifestationType{
		CONCERT,FESTIVAL
	}
	
	public enum ManifestationStatus{
		PENDING, DENIED, APPROVED, FINISHED, SOLD_OUT
	}
	
	private String name;
	private ManifestationType manifestationType;
	private String numberOfSeats;
	@JsonFormat(pattern = "YYYY-MM-DD")
	private Date dateTime;
	private Integer regularPrice;
	private ManifestationStatus status;
	private Double prosecnaOcena;
	private Location location;
	// treba promenuti
	private String eventPoster;
	private boolean isDeleted;
	private String cookie;
	private ArrayList<String> comments;
	
	
	public Manifestation(String name, ManifestationType manifestationType, String numberOfSeats, Date dateTime,
			Integer regularPrice, ManifestationStatus status, Location location, boolean isDeleted, String eventPoster) {
		super();
		this.name = name;
		this.manifestationType = manifestationType;
		this.numberOfSeats = numberOfSeats;
		this.dateTime = dateTime;
		this.regularPrice = regularPrice;
		this.status = status;
		this.location = location;
		this.eventPoster = eventPoster;
		this.isDeleted = isDeleted;
		this.prosecnaOcena = 0.0;
	}
	
	public Double getProsecnaOcena() {
		return prosecnaOcena;
	}

	public void setProsecnaOcena(Double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getEventPoster() {
		return eventPoster;
	}

	public void setEventPoster(String eventPoster) {
		this.eventPoster = eventPoster;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ManifestationType getManifestationType() {
		return manifestationType;
	}

	public void setManifestationType(ManifestationType manifestationType) {
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

	public Integer getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(Integer regularPrice) {
		this.regularPrice = regularPrice;
	}

	public ManifestationStatus getStatus() {
		return status;
	}

	public void setStatus(ManifestationStatus status) {
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

	@Override
	public String toString() {
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyy hh:mm");
		
		return name + "," + manifestationType + ","
				+ numberOfSeats + "," + dt.format(dateTime) + "," + regularPrice + "," + status
				+ "," + location + "," + isDeleted + "," + eventPoster;
	}

	
}
