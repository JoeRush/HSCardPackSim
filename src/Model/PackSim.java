package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.stream.Collectors;

public class PackSim {
	private static final int FIRST_PITY = 10;
	private static final int PITY_TIMER = 40;
	private PriorityQueue<Card> myPackQueue;
	private HSSet myPossibles;
	private HSSet mySet;
	/**
	 * Pack Simulator for each generation of hearthstone pack given the changes to 
	 * how cards a determined in each pack have changed over the years.
	 * Note this is based off of data done by the public as the actual data behind
	 * how hearthstone determines cards is not public and thus might be inaccurate.
	 * @param theSet is the hearthstone set that is being opened.
	 * @param theOpened is the collection of the user.
	 */
	public PackSim(HSSet theSet, HSCollection theOpened) {
		Comparator<Card> rarityComparator = new Comparator<Card>() {
			public int compare(Card c1, Card c2) {
				return c1.getRarity() - c2.getRarity();
			}
		};
		myPackQueue = new PriorityQueue<>(Collections.reverseOrder(rarityComparator));
		
		mySet = theSet;
		setPossibles(theOpened);
	}
	/**
	 * This is for the earliest hearthstone packs with only a bad luck prevention
	 * condition for legendaries after 40 packs.
	 * @param openedCount is the number of packs the user currently has opened for this set.
	 * @return a queue of cards that can then be added to the collection.
	 */
	public PriorityQueue<Card> genOnePacks(int openedCount) {

		int highest = 0;
		for(int i = 0; i < 5; i++) {
			double round = reroll();
			Card card = cardRarity(round);
			while(highest == 0 && i == 4) {
				round = reroll();
				card = cardRarity(round);
				if(highest < card.getRarity()) {
					highest = card.getRarity();
				}

			}
			if(highest < card.getRarity()) {
				highest = card.getRarity();
			}
			if(openedCount == 40 && i == 4 && highest != 3) {
				card  = randomCardRarity(3);
				highest = 3;
			}
			myPackQueue.add(card);
		}
		return myPackQueue;
		
	}
	/**
	 * Determines the rarity of each card generated.
	 * @param round decimal generated to determine rarity.
	 * @return a card to add to the queue.
	 */
	private Card cardRarity(double round) {
		Card card;
		if(round >= 71.65) {
			card  = randomCardRarity(0);
			
		}else if(round >= 22.84) {
			card  = randomCardRarity(1);
		}else if(round >= 4.42) {
			card  = randomCardRarity(2);
		}else {
			card  = randomCardRarity(3);
		}
		return card;
	}
	/**
	 * Determines the card from a subset of cards based off of rarity.
	 * @param theRarity is the subset of the set in question.
	 * @return a card that is to be stored.
	 */
	public Card randomCardRarity(int theRarity) {
		ArrayList<Card> card = mySet.getRaritySubSet(theRarity);
		Random random = new Random();
		int CardCount = card.size();
		int rCards = random.nextInt(CardCount);
		Card c = card.get(rCards);
		return c;
	}
	/**
	 * Used for generation two and beyond packs to prevent duplicate legendaries 
	 * unless they user owns all within a set.

	 * @return a new legendary card
	 */
	private Card rerollLegendary() {
		ArrayList<Card> card = myPossibles.getRaritySubSet(3);
//		for(Card c : cards) {
//			System.out.println(c.getName());
//		}
//		System.out.println("______________________");
		Random random = new Random();
		int CardCount = card.size();
		int rCards = random.nextInt(CardCount);
		Card c = card.get(rCards);
		return c;

		
	}
	/** 
	 * used for testing reasons.
	 * @return
	 */
	public HSSet testPoss() {
		return myPossibles;
	}
	/**
	 * rolls a random double value that represents a % to determine card.
	 * @return a random double value.
	 */
	public double reroll() {
		Double start = 0.0;
		Double end = 100.0;
		double cardVal = (Math.random() * ((end-start)+1)) + start;
		double round = Math.round(cardVal * 100.0) / 100.0;
		return round;
	}
	/**
	 * Generation 2 packs in which duplicate legendary protection was added.
	 * @param openedCount is the number of packs opened by the user since last legendary.
	 * @param openedCards is the collection the user currently owns.
	 * @return a queue of 5 cards to add to the users collection.
	 */
	public PriorityQueue<Card> genTwoPacks(int openedCount, HSCollection openedCards) {

		int highest = 0;
		for(int i = 0; i < 5; i++) {
			double round = reroll();
			Card card = cardRarity(round);
			while(highest == 0 && i == 4) {
				round = reroll();
				card = cardRarity(round);
				if(highest < card.getRarity()) {
					highest = card.getRarity();
				}

			}
			if(highest < card.getRarity()) {
				highest = card.getRarity();
			}
			if(openedCount == 40 && i == 4 && highest != 3) {
				card  = randomCardRarity(3);
				highest = 3;
			}
			if(card.getRarity()== 3) {

				
				if(openedCards.getCardCollection().containsKey(card.getName())) {
					boolean state = false;

					if(myPossibles.getRaritySubSet(3).size() == (mySet.getRaritySubSet(3).size())) {
						state = true;
					}

			
				
					
					while(!state && openedCards.getCardCollection().containsKey(card.getName())) {

						card = rerollLegendary();

					}
					
				}
				updatePossibles(card);
			}
			
			myPackQueue.add(card);
		}


		return myPackQueue;
	}
	/**
	 * Generation 3 packs in which a condition for the first 10 packs was added.
	 * @param openedCount packs of the set the user has opened since the last legendary.
	 * @param totalCount packs of the set that the user has opened total.
	 * @param openedCards the collection of cards the user currently owns.
	 * @return a queue of 5 cards to add to a users collection.
	 */
	public PriorityQueue<Card> genThreePacks(int openedCount,int totalCount, HSCollection openedCards) {

		int highest = 0;

		for(int i = 0; i < 5; i++) {
			double round = reroll();
			Card card = cardRarity(round);
			while(highest == 0 && i == 4) {
				round = reroll();
				card = cardRarity(round);
				if(highest < card.getRarity()) {
					highest = card.getRarity();
				}

			}
			if(highest < card.getRarity()) {
				highest = card.getRarity();
			}
			if((openedCount == 40 || totalCount == 10)  && i == 4 && highest != 3 ) {
				card  = randomCardRarity(3);
				highest = 3;
			}
			if(card.getRarity()== 3) {
				if(openedCards.getCardCollection().containsKey(card.getName())) {
					boolean state = false;

					if(myPossibles.getRaritySubSet(3).size() == (mySet.getRaritySubSet(3).size())) {
						state = true;
					}

			
				
					
					while(!state && openedCards.getCardCollection().containsKey(card.getName())) {

						card = rerollLegendary();

					}
					
				}
				updatePossibles(card);
			}
			myPackQueue.add(card);
		}


		return myPackQueue;
	}
	/**
	 * Duplicate Protection for all types of cards added in Generation 4 packs.
	 * @param openedCount number of packs since last legendary.
	 * @param totalCount number of packs total.
	 * @param openedCards user's collection.
	 * @return queue of 5 new cards to add to the user's collection.
	 */
	public PriorityQueue<Card> genFourPacks(int openedCount, int totalCount, HSCollection openedCards) {
		int highest = 0;
		setPossibles(openedCards);
		for(int i = 0; i < 5; i++) {
			double round = reroll();
			Card card = cardRarityGen4(round);
			while(highest == 0 && i == 4) {
				round = reroll();
				card = cardRarityGen4(round);
				if(highest < card.getRarity()) {
					highest = card.getRarity();
				}

			}
			if(highest < card.getRarity()) {
				highest = card.getRarity();
			}
			if((openedCount == 40 || totalCount == 10)  && i == 4 && highest != 3 ) {
				card  = randomCardRarityGen4(3);
				highest = 3;
			}
			card = gen4Protection(openedCards, card);
			updatePossibles(card);
			fixSubset(card);
			myPackQueue.add(card);
		}
		return myPackQueue;
	}
	/**
	 * Protection against duplicates for all cards.
	 * @param openedCards collection of the user.
	 * @param theCard the card to be checked against.
	 * @return a new card or the original card.
	 */
	private Card gen4Protection(HSCollection openedCards, Card theCard) {
		Card newCard;
		HashMap<String, Card> theOpened = openedCards.getCardCollection();
		
		if(theOpened.containsKey(theCard.getName())) {
			Card fromCollection = theOpened.get(theCard.getName());
			if(fromCollection.getRarity() == 3) {
				newCard = randomCardRarityGen4(3);
				updatePossibles(newCard);
			}else if(fromCollection.getCount()+1 > 2) {
				newCard = randomCardRarityGen4(theCard.getRarity());
			}else {
				newCard = theCard;

			}
			
			
		}else {
			newCard = theCard;
		}
		return newCard;
	}
	/**
	 * updates possible cards for generation 4 packs
	 * @param newCard the card being removed from the list of possibles.
	 */
	private void updatePossibles(Card newCard) {
		int rarity = newCard.getRarity();
		myPossibles.removeCard(newCard, rarity);
		if(myPossibles.getRaritySubSet(rarity).isEmpty()) {
			myPossibles.updateSubset(mySet.getRaritySubSet(rarity), rarity);
		}
		
	}
	/**
	 * generation 4 card generation.
	 * @param round the value used to determine the card's rarity.
	 * @return a card.
	 */
	private Card cardRarityGen4(double round) {
		Card card;
		if(round >= 71.65) {
			card  = randomCardRarityGen4(0);
			
		}else if(round >= 22.84) {
			card  = randomCardRarityGen4(1);
		}else if(round >= 4.42) {
			card  = randomCardRarityGen4(2);
		}else {
			card  = randomCardRarityGen4(3);
		}
		return card;
	}
	/**
	 * Randomly generates a card based off of rarity subset for Gen 4.
	 * @param theRarity rarity subset to generate a card off of.
	 * @return a card to add to the queue.
	 */
	public Card randomCardRarityGen4(int theRarity) {
		ArrayList<Card> cList = myPossibles.getRaritySubSet(theRarity);
		Random random = new Random();
		int CardCount = cList.size();
		
		int rCards = 0;
		if(CardCount != 0) {
			rCards = random.nextInt(CardCount);
		}
		
		Card c = cList.get(rCards);
		return c;
	}
	/**
	 * sets the possibles the user can open up based off their collection for generation 4 packs.
	 * @param openedCards user's collection.
	 */
	private void setPossibles(HSCollection openedCards) {
		ArrayList<Card> theSet = mySet.getRaritySubSet(4);
		ArrayList<Card> possible = new ArrayList<>();
		ArrayList<Card> commons = new ArrayList<>();
		ArrayList<Card> rares = new ArrayList<>();
		ArrayList<Card> epics = new ArrayList<>();
		ArrayList<Card> leggos = new ArrayList<>();
		for(Card c : theSet) {
			int rarity = c.getRarity();
			boolean contains = openedCards.getCardCollection().containsKey(c.getName());
		
			if(rarity == 3 && !contains) {
				
				possible.add(c);
				leggos.add(c);
			}else if(contains && (c.getCount() < 2) && rarity != 3) {
				possible.add(c);
			
				if(c.getRarity() == 0) {
					commons.add(c);
				}else if(c.getRarity() == 1) {
					rares.add(c);
					
				}else {
					epics.add(c);
				}
				
			}
			
		}
	
		if(possible.isEmpty()) {
			myPossibles = new HSSet(mySet.getRaritySubSet(4));

		}else {
			myPossibles = new HSSet(fixEmpties(possible, commons, rares, epics, leggos));


			
		}
	
	}
	/**
	 * helper method for setPossibles.
	 * @param theP full list of possibles.
	 * @param theC the common list.
	 * @param theR the rare list.
	 * @param theE the epic list.
	 * @param theL the legendary list.
	 * @return a full list of possibles.
	 */
	private ArrayList<Card> fixEmpties(ArrayList<Card> theP,
						ArrayList<Card> theC, ArrayList<Card> theR, 
						ArrayList<Card> theE, ArrayList<Card> theL) {
		ArrayList<Card> results = theP;
		if(theC.isEmpty()) {
			 results.addAll(mySet.getRaritySubSet(0));
		}
		if(theR.isEmpty()) {

			results.addAll(mySet.getRaritySubSet(1));
		}
		if(theE.isEmpty()) {
			results.addAll(mySet.getRaritySubSet(2));
		}
		if(theL.isEmpty()) {

			results.addAll(mySet.getRaritySubSet(3));
		}
		
		return results;
		
	}
	private void fixSubset(Card theCard) {
		int rarity = theCard.getRarity();
		ArrayList<Card> subset = myPossibles.getRaritySubSet(rarity);
		if(subset.isEmpty()) {
			myPossibles.updateSubset(mySet.getRaritySubSet(rarity), rarity);
		}
		
	}
}
