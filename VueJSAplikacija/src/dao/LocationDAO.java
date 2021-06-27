package dao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import beans.Location;
public class LocationDAO {

	public LocationDAO() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<Location> locations = new ArrayList<Location>();
	
	
	public void loadLocation(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path);
			in = new BufferedReader(new FileReader(file));
			String line;
			String[] params;
			line = in.readLine();
			while ((line = in.readLine()) != null) {
				params = line.split(",");
				Location location = new Location(params[0],params[1],params[2], line, line);
				locations.add(location);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
} 
