package Model;

import java.util.ArrayList;
import java.util.HashMap;


public class HSSet {
	/**
	 * Map that stores each card into subsets based off of rarity.
	 */
	private HashMap<Integer, ArrayList<Card>> myCardSet;

	/**
	 * Constructor for HSSet.
	 * Class is used to create list of sets of cards or the possible values
	 * a user can get given the cards within their collection.
	 * @param cardList is the entire list of cards within a give set.
	 */
	public HSSet(ArrayList<Card> cardList) {
		myCardSet = new HashMap<Integer, ArrayList<Card>>();
		ArrayList<Card> commons = new ArrayList<>();
		ArrayList<Card> rares = new ArrayList<>();
		ArrayList<Card> epics = new ArrayList<>();
		ArrayList<Card> legendaries = new ArrayList<>();
		ArrayList<Card> fullSet = new ArrayList<>();
		for(Card c : cardList) {
			int rarity = c.getRarity();
			if(rarity == 0) {
				
				commons.add(c);
			}else if(rarity == 1) {
				rares.add(c);
			}else if(rarity == 2) {
				epics.add(c);
				
			}else {
				legendaries.add(c);
			}
			fullSet.add(c);
		}
		myCardSet.put(0, commons);
		myCardSet.put(1, rares);
		myCardSet.put(2, epics);
		myCardSet.put(3, legendaries);
		myCardSet.put(4, fullSet);
	}
	/**
	 * Gives the user the subset of a set based off of rarity for easier
	 * random pack generation and duplication protection.
	 * 0 = commons.
	 * 1 = rares.
	 * 2 = epics.
	 * 3 = legendaries.
	 * 4 = full set.
	 * defaults to giving the full set if the user does not input a valid value.
	 * @param rarityValue is the value the represents each rarity of card or the full set.
	 * @return the subset that the user desires.
	 */
	public ArrayList<Card> getRaritySubSet(int rarityValue) {
		ArrayList<Card> returnList;
		if(rarityValue < 4) {
			returnList = myCardSet.get(rarityValue);
		}else {
			returnList = myCardSet.get(4);
		}
		return returnList;
	}
	/**
	 * Removes a card from a set if need used mainly for testing possibles.
	 * @param theCard being removed for the set/
	 * @param Rarity is the rarity of the card being remove so 
	 * that it can easily be removed from that position in the map.
	 */
	public void removeCard(Card theCard, int Rarity) {
		ArrayList<Card> fullList = myCardSet.get(4);
		ArrayList<Card> raritySet = myCardSet.get(Rarity);
		fullList.remove(theCard);
		raritySet.remove(theCard);
		myCardSet.put(4, fullList);
		myCardSet.put(Rarity, raritySet);
	}
	/**
	 * Used for updating possible cards for a user based off of their collections.
	 * @param theNewSubset updates the subset for the given rarity.
	 * @param theRarity is the rarity subset to be updated.
	 */
	public void updateSubset(ArrayList<Card> theNewSubset, int theRarity) {
		myCardSet.put(theRarity, theNewSubset);
	}
}
