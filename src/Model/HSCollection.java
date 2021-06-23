package Model;

import java.util.HashMap;

public class HSCollection {
	/**
	 * Stores each card that a person owns within a map to be easily retrieved by its name
	 */
	private HashMap<String, Card> myCardCollection;
	/**
	 * Constructor for the HSCollection.
	 */
	public HSCollection() {
		myCardCollection = new HashMap<>();
		
	}
	/** 
	 * Adds a card to the collection if its missing or iterates the count of a card if 
	 * it already exists in the collection.
	 * @param newCard is the Card being added to the collection.
	 */
	public void addCard(Card newCard) {
		if(myCardCollection.containsKey(newCard.getName())) {
			newCard.iterateCount();
			myCardCollection.put(newCard.getName(), newCard);
		}else {
			myCardCollection.put(newCard.getName(), newCard);
		}
	}
	/**
	 * Retrieves the map that stores each card.
	 * @return the map that acts as the collection.
	 */
	public HashMap<String, Card> getCardCollection() {
		return myCardCollection;
	}


}
