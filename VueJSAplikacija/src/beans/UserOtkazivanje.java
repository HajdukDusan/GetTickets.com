package beans;

import java.time.LocalDate;
import java.util.Date;

public class UserOtkazivanje {
	
	public String username;
	public String idCard;
	public Date timeOfCancel;

	public UserOtkazivanje(String u, String idc, Date time) {
		username = u;
		idCard = idc;
		timeOfCancel = time;
	}

	@Override
	public String toString() {
		return  username + "," + idCard + "," + timeOfCancel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Date getTimeOfCancel() {
		return timeOfCancel;
	}

	public void setTimeOfCancel(Date timeOfCancel) {
		this.timeOfCancel = timeOfCancel;
	}
	
	
	
}
