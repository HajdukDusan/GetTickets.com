package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import beans.Location;
import beans.Manifestation;
public class ManifestationDAO {

	public ManifestationDAO(String path) { 
		loadManifestation(path);
	}
	
	
	private HashMap<String,Manifestation> manifestations = new HashMap<String,Manifestation>();
	private ArrayList<Manifestation> manifestationsList = new ArrayList<Manifestation>();
	
	public void loadManifestation(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path);
			in = new BufferedReader(new FileReader(file));
			String line;
			String[] params;
			line = in.readLine();
			while ((line = in.readLine()) != null) {
				params = line.split(",");
				boolean status = Boolean.parseBoolean(params[5]);
				Date date = new SimpleDateFormat("dd-mm-yyyy HH:mm").parse(params[3]);
				Location l = new Location(params[6],params[7],(params[8] + params[9]  + params[10]));
				Manifestation m = new Manifestation(params[0],params[1],params[2],date,params[4],status,l,params[11]);
				
				manifestations.put(m.getName(),m);
				manifestationsList.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ( in != null ) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}

	public HashMap<String, Manifestation> getManifestations() {
		return manifestations;
	}

	public void setManifestations(HashMap<String, Manifestation> manifestations) {
		this.manifestations = manifestations;
	}

	public ArrayList<Manifestation> getManifestationsList() {
		return manifestationsList;
	}

	public void setManifestationsList(ArrayList<Manifestation> manifestationsList) {
		this.manifestationsList = manifestationsList;
	}
}