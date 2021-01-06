package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import beans.Card;


public class CardDAO {

	public CardDAO(String path,ManifestationDAO mDAO) {
		loadCards(path,mDAO);
	}
	
	


	private HashMap<String,Card> cards = new HashMap<String,Card>();
	private ArrayList<Card> cardsList = new ArrayList<Card>();
	
	public void loadCards(String path, ManifestationDAO mDAO) {
		BufferedReader in = null;
		try {
			File file = new File(path);
			in = new BufferedReader(new FileReader(file));
			String line;
			String[] params;
			line = in.readLine();
			while ((line = in.readLine()) != null) {
				params = line.split(",");
				Boolean status = Boolean.parseBoolean(params[5]);
				Card c = new Card(params[0],mDAO.getManifestations().get(params[1]), mDAO.getManifestations().get(params[1]).getDateTime(), params[2], params[3],params[4],status,params[6]);
				
				cards.put(params[0],c);
				cardsList.add(c);
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
	public HashMap<String, Card> getCards() {
		return cards;
	}

	public void setCards(HashMap<String, Card> cards) {
		this.cards = cards;
	}

	public ArrayList<Card> getCardsList() {
		return cardsList;
	}

	public void setCardsList(ArrayList<Card> cardsList) {
		this.cardsList = cardsList;
	}

	
}