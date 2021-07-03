package beans;

import java.time.LocalDate;
import java.util.Date;

public class UserOtkazivanje {
	
	public String username;
	public String idCard;
	public LocalDate timeOfCancel;

	public UserOtkazivanje(String u, String idc, LocalDate time) {
		username = u;
		idCard = idc;
		timeOfCancel = time;
	}
	
}
