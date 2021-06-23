package Model;

public class Card {
	/**
	 * The name of the Card.
	 */
	private String myName;
	/**
	 * The rarity of the card.
	 */
	private int myRarity;
	/**
	 * The count in a collection for the card.
	 */
	
	private int myCount;
//	/**
//	 * The state of animation for the card.
//	 */
//	boolean myGolden;
	/**
	 * Used to get Data for Cards used with HSCollection and HSSet.
	 * @param theRarity is the rarity of the card. 
	 * @param theName is the name of the card.
	 */
	public Card(int theRarity, String theName) {
		myName = theName;
		myRarity = theRarity;
		myCount = 1;
	}
	/**
	 * Retrieves the rarity of a card.
	 * @return the rarity of the card.
	 */
	public int getRarity() {
		return myRarity;
	}
	/**
	 * Retrieves the name of the card.
	 * @return stored name of the card.
	 */
	public String getName() {
		return myName;
	}
	/**
	 * Iterates the count of a card within a collection.
	 */
	public void iterateCount() {
		myCount++;
	}
	/**
	 * Retrieves the count of a card within a collection for later generations
	 * of packs.
	 * @return the count of a card within a collection.
	 */
	public int getCount() {
		return myCount;
	}
}
