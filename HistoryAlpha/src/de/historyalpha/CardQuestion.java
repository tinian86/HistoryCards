package de.historyalpha;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	// Ein Stapel wird erstellt, der alle Karten beinhaltet
	// TODO: Die Karten werden zur Zeit noch per Hand in den Stapel getan, automatisieren?
	
	
	static Card stapel[] = new Card[] { card1, card2, card3, card4, card5, card6, card7, card8, card9, card10 };
	static Card auswahlKarten[] = new Card[10];

	/**
	 * Liefert drei zufaellige Karten. Dazu wird eine ArrayListe erstellt und geshuffelt (gemischt).
	 * Damit sind die IDs einzigartig und nicht mehr doppelt. 
	 * Aus dieser Liste werden dann die ersten 3 Elemente gewaehlt und uebergeben. 
	 */
	// TODO: Wenn eine Karte bereits auf dem Spielfeld liegt, darf diese nicht erneut aus dem Stapel der vorhandenen Karten geholt werden!
	// Daher Methode finden, um auszuschliessen dass das passiert
	public static Card[] getRandomCards() {
		
//		Random rand = new Random();
//		int max = 9;
//		int min = 0;

		// int randomNum1 = rand.nextInt((max - min) + 1) + min;
		// int randomNum2 = rand.nextInt((max - min) + 1) + min;
		// int randomNum3 = rand.nextInt((max - min) + 1) + min;

		// auswahlKarten[0] = stapel[randomNum1];
		// auswahlKarten[1] = stapel[randomNum2];
		// auswahlKarten[2] = stapel[randomNum3];

		final List<Integer> sack = new ArrayList<Integer>(10);
		for (int i = 0; i < 10; i++) {
			sack.add(i);
		}
		Collections.shuffle(sack);
//		Log.d("CardQuestion Test", "Sack0 = " + String.valueOf(sack.get(0)));
//		Log.d("CardQuestion Test", "Sack1 = " + String.valueOf(sack.get(1)));
//		Log.d("CardQuestion Test", "Sack2 = " + String.valueOf(sack.get(2)));
		auswahlKarten[0] = stapel[sack.get(0)];
		auswahlKarten[1] = stapel[sack.get(1)];
		auswahlKarten[2] = stapel[sack.get(2)];
//		Log.d("CardQuestion Test", sack.toString());
//		Log.d("CardQuestion0", auswahlKarten[0].toString());
//		Log.d("CardQuestion1", auswahlKarten[1].toString());
//		Log.d("CardQuestion2", auswahlKarten[2].toString());

		return auswahlKarten;
	}

}
