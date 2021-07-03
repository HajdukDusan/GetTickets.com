package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;

import java.io.FileWriter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import beans.Card;
import beans.User;
import beans.Card.CardType;


public class CardDAO {

	String cardPath;
	
	public CardDAO(String path,ManifestationDAO mDAO) {
		loadCards(path,mDAO);
		cardPath = path;
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
				Card c = new Card(params[0], params[1], mDAO.getManifestations().get(params[1]).getDateTime(), params[2], params[3],params[4],status,CardType.valueOf(params[6]));
				
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
	public List<Card> getUserCards(List<String> cardIds){
		ArrayList<Card> userCards = new ArrayList<Card>();
		for(String s: cardIds) {
			System.out.println(cards.get(s).isStatus());
			//if(cards.get(s).isStatus()) {
				userCards.add(cards.get(s));
			//}

		}
		return userCards;
		
	}
	public Card findCard(String id) {
		return cards.get(id);
	}
	public List<Card> getManifestationCards(String manifestation){
		ArrayList<Card> cardsManif = new ArrayList<Card>();
		for(Card c:cardsList) {
			if(c.getManifestation().equals(manifestation)) {
				cardsManif.add(c);
			}
		}
		return cardsManif;
	}
	public List<Card> getManifestationCardsReserved(String manifestation){
		ArrayList<Card> cardsManif = new ArrayList<Card>();
		for(Card c:cardsList) {
			if(c.getManifestation().equals(manifestation) && c.isStatus()) {
				cardsManif.add(c);
			}
		}
		return cardsManif;
	}
	public void save(Card c) {
		cards.put(c.getId(),c);

		Collection<Card> values = cards.values();
		cardsList = new ArrayList<>(values);
		BufferedWriter out = null;
		// try {
		// 	File file = new File(cardPath);
		// 	System.out.println(cardPath);
		// 	out = new BufferedWriter(new FileWriter(file, true));
		// 	out.write(c.toString());
		
		// } catch (Exception e) {
		// 	e.printStackTrace();
		// } finally {
		// 	if ( out != null ) {
		// 		try {
		// 			out.close();
		// 		}
		// 		catch (Exception e) { }
		// 	}
		// }
	}
	public HashMap<String, Card> getCards() {
		return cards;
	}
	public Date cardStringToDate(String date) throws ParseException {
		if(date == null || date.isEmpty()) {
			return null;
		}
		return new SimpleDateFormat("yyyy-MM-dd").parse(date); 
	}
	public void setCards(HashMap<String, Card> cards) {
		this.cards = cards;
	}

	public ArrayList<Card> getCardsList() {
		return cardsList;
	}
	public ArrayList<Card> getCardsListReserved() {
		ArrayList<Card> reservedCards = new ArrayList<Card>();
		for(Card c: cardsList) {
			if(c.isStatus()) {
				reservedCards.add(c);
			}
		}
		return reservedCards;
	}
	public void setCardsList(ArrayList<Card> cardsList) {
		this.cardsList = cardsList;
	}

	
}