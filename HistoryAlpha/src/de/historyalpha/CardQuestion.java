package de.historyalpha;

import java.util.Random;

import android.util.Log;

public class CardQuestion {

	// eine Card besteht aus: Card(int cardId, String schlagwort, String text, int jahr)

	static Card card1 = new Card(1, "Teflon", "Wann wurde das Teflon erfunden?", 1934);
	static Card card2 = new Card(2, "Mondlandung", "Wann betrat der erste Mensch den Mond?", 1969);
	static Card card3 = new Card(3, "Kaugummi", "Wann wurde die erste Kaugummifabrik eröffnet?", 1848);
	static Card card4 = new Card(4, "Tesla", "Wann wurde Nikola Tesla geboren?", 1856);
	static Card card5 = new Card(5, "Guernica", "Wann malte Picasso seine 'Guernica'?", 1937);
	static Card card6 = new Card(6, "Olympische Spiele", "Wann fanden die Olympischen Spiele das erste mal statt?", 1896);
	static Card card7 = new Card(7, "Nobelpreis", "Wann wurde der Nobelpreis erstmalig verliehen?", 1901);
	static Card card8 = new Card(8, "Columbus", "Wann entdeckte Christoph Columbus Amerika?", 1492);
	static Card card9 = new Card(9, "Auto", "Wann wurde das erste Auto entwickelt?", 1886);
	static Card card10 = new Card(10, "Kinder Schokolade", "Wann kam die Kinder Schokolade auf den Markt?", 1967);

	static Card stapel[] = new Card[] { card1, card2, card3, card4, card5, card6, card7, card8, card9, card10 };

	static Card auswahlKarten[] = new Card[10];

	// Achtung! Karten koennen noch gleich sein! Potentiall kann drei mal "Mondlandung" ausgegeben werden!
	// TODO: Fix it - wie kann man dem Array sagen, dass es nur einzigartig rausholen soll?
	public static Card[] getRandomCards() {
		Random rand = new Random();
		int max = 9;
		int min = 0;

		int randomNum1 = rand.nextInt((max - min) + 1) + min;
		int randomNum2 = rand.nextInt((max - min) + 1) + min;
		int randomNum3 = rand.nextInt((max - min) + 1) + min;
		
		auswahlKarten[0] = stapel[randomNum1];
		auswahlKarten[1] = stapel[randomNum2];
		auswahlKarten[2] = stapel[randomNum3];

		// Log.d("CardQuestion0", auswahlKarten[0].toString());
		// Log.d("CardQuestion1", auswahlKarten[1].toString());
		// Log.d("CardQuestion2", auswahlKarten[2].toString());
		return auswahlKarten;
	}

}
