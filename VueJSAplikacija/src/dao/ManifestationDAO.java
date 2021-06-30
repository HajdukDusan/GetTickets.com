package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.Location;
import beans.Manifestation;
import beans.Manifestation.ManifestationStatus;
import beans.Manifestation.ManifestationType;
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
			System.out.println("KITA");
			System.out.println(path);
			in = new BufferedReader(new FileReader(file));
			String line;
			String[] params;
			line = in.readLine();
			while ((line = in.readLine()) != null) {
				params = line.split(",");
				ManifestationStatus status = ManifestationStatus.valueOf(params[5]);
				Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(params[3]);
				Location l = new Location(params[6], params[7], params[8], params[9], params[10]);
				Manifestation.ManifestationType manifestationType =ManifestationType.valueOf(params[1]);
				Integer price = Integer.parseInt(params[4]);
				boolean isDeleted = Boolean.parseBoolean(params[11]);
				Manifestation m = new Manifestation(params[0],manifestationType,params[2],date,price,status,l,isDeleted,params[12]);
				System.out.print(m + "\n");
				System.out.print(l + "\n");
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
	public void writeManifestation(String path) {
		BufferedWriter out = null;
		try {
			File file = new File(path);
			
			out = new BufferedWriter(new FileWriter(file));
			out.write("name,manifestation type,number of seats, date and time, regular price, status, location, isDeleted,event poster\n");
			for(Manifestation m:manifestationsList) {
				out.write(m + "\n");
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ( out != null ) {
				try {
					out.close();
				}
				catch (Exception e) { }
			}
		}
		
		
	}
	public boolean saveManifestation(Manifestation manifestation) {
		
		if(manifestation.getEventPoster() == null) {
			manifestation.setEventPoster("");
		}
		for(Manifestation m: manifestationsList) {
			if(m.getLocation().equals(manifestation.getLocation()) && m.getDateTime().equals(manifestation.getDateTime())){
				return false;
			}
		}

		manifestationsList.add(manifestation);
		manifestations.put(manifestation.getName(), manifestation);
		//writeManifestation("D:\\faks\\3godina\\Web\\VueJSAplikacija\\WebContent\\data\\manifestations.txt");
		return true;
	}
	public boolean updateManifestation(Manifestation manifestation) {
		
		if(manifestation.getEventPoster() == null) {
			manifestation.setEventPoster("");
		}
		for(Manifestation m: manifestationsList) {
			if(m.getName().equals(manifestation.getName())){
				manifestationsList.remove(m);
				manifestationsList.add(manifestation);
				manifestations.put(manifestation.getName(), manifestation);
			}
		}


		//writeManifestation("D:\\faks\\3godina\\Web\\VueJSAplikacija\\WebContent\\data\\manifestations.txt");
		return true;
	}
	
	public Manifestation getManifestationByName(String name) {
		for(Manifestation m: manifestationsList) {
			if(m.getName().equals(name)){
				return m;
			}
		}
		return null;
	}
	
	public HashMap<String, Manifestation> getManifestations() {
		return manifestations;
	}

	public void setManifestations(HashMap<String, Manifestation> manifestations) {
		this.manifestations = manifestations;
	}

	public ArrayList<Manifestation> getManifestationsList() {
	
		Collections.sort(manifestationsList, new Comparator<Manifestation>() {
			  public int compare(Manifestation o1, Manifestation o2) {
			      return o1.getDateTime().compareTo(o2.getDateTime());
			  }
		});
		Collections.reverse(manifestationsList);
		return manifestationsList;
	}
	public ArrayList<Manifestation> getPendingManifestationsList() {
		
		ArrayList<Manifestation> tmp = new ArrayList<Manifestation>();
		
		for(Manifestation m : manifestationsList) {
			System.out.println(m.getStatus());
			if(m.getStatus() == ManifestationStatus.PENDING) {
				tmp.add(m);
			}
		}
		
		return tmp;
	}
	public ArrayList<Manifestation> getManifestationsSearched(String naziv,String grad,Integer min,Integer max) {
		ArrayList<Manifestation> manifestationsSearched = new ArrayList<Manifestation>();
		for(Manifestation m:manifestationsList) {
			if((m.getName().toLowerCase().contains(naziv.toLowerCase()) || naziv.equals("")) && (m.getLocation().getCity().toLowerCase().contains(grad.toLowerCase()) || grad.equals(""))
					&& (m.getRegularPrice() > min) && (m.getRegularPrice() < max)) {
				manifestationsSearched.add(m);
				}
		}
		return manifestationsSearched;
	}
	public ArrayList<Manifestation> getManifestationsWorker(List<String> ids){
		ArrayList<Manifestation> manifs = new ArrayList<Manifestation>();
		for(String id:ids) {
			for(Manifestation m: manifestationsList) {
				if(m.getName().equals(id)) {
					manifs.add(m);
				}
			}

		}
		return manifs;
	}
	public void setManifestationsList(ArrayList<Manifestation> manifestationsList) {
		this.manifestationsList = manifestationsList;
	}
}