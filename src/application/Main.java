package application;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import Model.Card;
import Model.HSCollection;
import Model.HSSet;
import Model.PackSim;

public class Main {
	private static  HSCollection myCollection;
	private static  HSSet myTestSet;

	public static void main(String[] args) {
		
		myCollection = new HSCollection();
		createTestSet();
		for(String n : myCollection.getCardCollection().keySet()) { 
			System.out.println(n + "before");
		}
		PackSim packs = new PackSim(myTestSet, myCollection);
     	//PriorityQueue<Card> newPack = packs.genOnePacks(0);
		//HSSet set = packs.testPoss();
//		ArrayList<Card> leggos = set.getRaritySubSet(3);
//		for(Card c : leggos) {
//			System.out.println(c.getName());
//		}

     	//PriorityQueue<Card> newPack4 = packs.genTwoPacks(40, myCollection);
    	PriorityQueue<Card> newPack4 = packs.genFourPacks(40, 10, myCollection);
    	packs.genFourPacks(0, 11, myCollection);
		while(!newPack4.isEmpty()) {
			Card c = newPack4.poll();
			myCollection.addCard(c);
			System.out.println("the card name is" + c.getName() 
							+ " the rarity is" + c.getRarity());
		}
		
	}
	private static void createTestSet() {
		ArrayList<Card> c = new ArrayList<>();
		Card c1 = new Card(0, "boar");
		c.add(c1);
		Card c2 = new Card(0, "murloc");
		c.add(c2);
		Card c3 = new Card(0, "dragon");
		c.add(c3);
		Card c4 = new Card(0, "mech");
		c.add(c4);
		Card r1 = new Card(1, "annoyo-tron");
		c.add(r1);
		Card r2 = new Card(1, "phantom");
		c.add(r2);
		Card r3 = new Card(1, "beast");
		c.add(r3);
		Card e1 = new Card(2, "big game hunter");
		c.add(e1);
		Card e2 = new Card(2, "doomsayer");
		c.add(e2);
		Card e3 = new Card(2, "gorehowl");
		c.add(e3);
		Card l1 = new Card(3, "bob");
		c.add(l1);
		Card l2 = new Card(3, "leeory");
		c.add(l2);
		//myCollection.addCard(l1);
		//myCollection.addCard(l2);
		myTestSet = new HSSet(c);
		
	}
}
