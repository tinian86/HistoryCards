package de.historyalpha;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {

	public String NAMETAG = "Alpha - GameActivity";

	private List<Card> cardList = new ArrayList<Card>();
	private List<Card> playableCards = new ArrayList<Card>();
	private int width;
	private int height;
	private int lifes = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		try {

			setupBottomBar();

			// Temporär startkarte manuell:
			Card startCard = new Card(1, "startkarte",
					"Wann wurde das Teflon erfunden?", 1934);

			// Dummykarte für Anfangs und Endpunkt (nicht sichtbar)

			Card dummyCardBegin = new Card(0, "begin", "", 0);
			Card dummyCardEnd = new Card(0, "end", "", 0);

			this.cardList.add(dummyCardBegin);
			this.cardList.add(startCard);
			// this.cardList.add(startCard2);
			// this.cardList.add(startCard);
			// this.cardList.add(startCard2);
			// this.cardList.add(startCard);
			// this.cardList.add(startCard2);
			this.cardList.add(dummyCardEnd);

			setupCardArea(cardList);

		} catch (Exception e) {
			Log.d("GameActivity", e.getMessage());
		}

	}

	public void setupBottomBar() {

		playableCards.clear();

		Card[] threeCards = CardQuestion.getRandomCards();
		// TODO: @Dennis war diese for-Schleife noetig? Scheint auch ohne zu
		// funktionieren
		// for (int i = 0; i < 3; i++) {
		Card card1 = new Card(threeCards[0].getCardId(),
				threeCards[0].getSchlagwort(), "", threeCards[0].getJahr());
		Card card2 = new Card(threeCards[1].getCardId(),
				threeCards[1].getSchlagwort(), "", threeCards[1].getJahr());
		Card card3 = new Card(threeCards[2].getCardId(),
				threeCards[2].getSchlagwort(), "", threeCards[2].getJahr());

		playableCards.add(card1);
		playableCards.add(card2);
		playableCards.add(card3);
		// }

		LinearLayout bottomBar = (LinearLayout) findViewById(R.id.playableCardLayout);

		bottomBar.removeAllViews();

		Button button1 = new Button(getBaseContext());
		Button button2 = new Button(getBaseContext());
		Button button3 = new Button(getBaseContext());

		// b1.setId(11);
		// b1.setId(22);
		// b1.setId(33);

		button1.setTextColor(Color.WHITE);
		button2.setTextColor(Color.WHITE);
		button3.setTextColor(Color.WHITE);

		button1.setOnTouchListener(new MyTouchListener());
		button2.setOnTouchListener(new MyTouchListener());
		button3.setOnTouchListener(new MyTouchListener());

		button1.setText(playableCards.get(0).getSchlagwort());
		button2.setText(playableCards.get(1).getSchlagwort());
		button3.setText(playableCards.get(2).getSchlagwort());

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;

		int bottomheight = height / 5; // Höhe der unteren Leiste
		int bottomwidth = width / 5; // Breite der unteren Leiste

		button1.setMinimumHeight(bottomheight);
		button1.setMaxHeight(bottomheight);
		button1.setMinimumWidth(bottomwidth);
		button1.setMaxWidth(bottomwidth);

		button2.setMinimumHeight(bottomheight);
		button2.setMaxHeight(bottomheight);
		button2.setMinimumWidth(bottomwidth);
		button2.setMaxWidth(bottomwidth);

		button3.setMinimumHeight(bottomheight);
		button3.setMaxHeight(bottomheight);
		button3.setMinimumWidth(bottomwidth);
		button3.setMaxWidth(bottomwidth);

		bottomBar.addView(button1);
		bottomBar.addView(button2);
		bottomBar.addView(button3);

	}

	// TODO Ist die Methode so noetig? Sie ruft ja lediglich setupBottomBar()
	// auf, daher besser direkt machen?
	public void getNewCards(View view) {
		setupBottomBar();

	}

	// TODO: Zur Zeit werden mit dem Button "Neue Karten" ALLE der drei Karten
	// neu geholt -> sollten eigentlich nur die fehlenden ersetzt werden

	// Methode, um die Karten aufzudecken und ihre Jahreszahl anzuzeigen
	public void revealCards(View view) {

		// Zwei counter, um die Punkte aufzusummieren die man fuer Karten
		// bekommt
		// TODO Summe enthaelt am Ende noch Werte fuer Start- und Endkarte (6)
		// zuviel!
		// Einfach 6 abziehen geht nicht, da geometrisch aufsummiert wird!
		int counter = 0;
		int sum = 0;

		boolean wrongCard = false;

		// Methode testet, ob Karten in der falschen Reihenfolge angelegt
		// wurden. Falls ja, so wird ein Leben abgezogen
		// TODO: Funktionalitaet fehlt bisher noch.
		for (int j = cardList.size() - 2; j > 0; j--) {

			for (int i = 0; i < cardList.size(); i++) {

//				Log.d("Listeninhalt", cardList.get(i).schlagwort.toString()
//						+ String.valueOf(cardList.get(i).jahr));

				int checkYear = cardList.get(j).jahr;
				
				Log.d("checkYear", "checkYear: " + String.valueOf(checkYear) + " " + cardList.get(j).schlagwort + String.valueOf(cardList.get(j).jahr));
				
				
				// Jahreszahlen vergleichen
				// TODO Glaube das ist noch nicht ganz korrekt?
				if (checkYear < cardList.get(i).jahr) {
//					Log.d("checkYear",
//							"checkYear: " + String.valueOf(checkYear) + " "
//									+ String.valueOf(cardList.get(i).jahr));
					// Wenn eine Karte falsch liegt, Variable auf true setzen
					wrongCard = true;
				}

				cardList.get(i).schlagwort = String
						.valueOf(cardList.get(i).jahr);
				setupCardArea(cardList);

				counter = counter + 1;
				sum = sum + counter;
			}
		}

		// Wenn eine Karte falsch gelegt wird, erscheint eine Meldung, die Liste
		// wird geleert und das Spielfeld neu initialisiert
		if (wrongCard) {
			Context context = getApplicationContext();
			CharSequence text = "Sie haben eine Karte falsch angelegt und verlieren ein Leben!";
			int duration = Toast.LENGTH_LONG;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();

			lifes = lifes - 1;

			// // Alle Karten aus der Liste entfernen
			// // TODO: Buggy, unklar wieso
			// for (int i = 0; i < cardList.size(); i++) {
			// cardList.remove(i);
			// }
			//
			// setupBottomBar();
			//
			// Card startCard = new Card(1, "startkarte",
			// "Wann wurde das Teflon erfunden?", 1934);
			//
			// // Dummykarte für Anfangs und Endpunkt (nicht sichtbar)
			//
			// Card dummyCardBegin = new Card(0, "begin", "", 0);
			// Card dummyCardEnd = new Card(0, "end", "", 0);
			//
			// this.cardList.add(dummyCardBegin);
			// this.cardList.add(startCard);
			// this.cardList.add(dummyCardEnd);
		}

		// Spielfeld neuzeichnen
		// TODO: Ist noch buggy
		setupCardArea(cardList);
		// Scorefeld setzen
		TextView score = (TextView) findViewById(R.id.score_id);
		score.setText(String.valueOf(sum));
		// Verbleibende Leben setzen
		// TODO: Hat noch keine Funktionalitaet
		TextView actualLife = (TextView) findViewById(R.id.life_id);
		actualLife.setText(" " + String.valueOf(lifes));
	}

	@SuppressWarnings("deprecation")
	public void setupCardArea(List<Card> cL) {

		GridLayout gl = (GridLayout) findViewById(R.id.gridlayout);

		List<Card> cardDrawList = new ArrayList<Card>();

		for (Card card : cL) {
			cardDrawList.add(card);
		}

		gl.removeAllViews();

		int topheight = height * 3 / 5; // Höhe des Spielfensters (ScrollView)
		int topwidth = width; // ganze Breite des Spielfensters (ScrollView)

		// Höhe und Breite eines Kästchens in der ScrollView
		int cardheight = topheight / 2;
		int cardwidth = topwidth / 4; // nicht Quadratisch

//		Log.d("height", String.valueOf(cardheight));
//		Log.d("width", String.valueOf(cardwidth));

		int margin = (int) (cardwidth * 0.1);

//		Log.d("margin", String.valueOf(margin));

		// Sortiere Elemente der Liste für Layout neu (vertauschen von 3 und 4,
		// 7 und 8, etc.)
		for (int i = 0; i < cardDrawList.size(); i++) {
			// Pos i+1 durch 4 teilbar heißt, dass i und i-1 vertauscht werden
			// müssen
			if ((i + 1) % 4 == 0) {
				// Plätze tauschen
				Card temp = cardDrawList.get(i);
				cardDrawList.set(i, cardDrawList.get(i - 1));
				cardDrawList.set(i - 1, temp);
			}
		}

		// Erzeuge LinearLayouts mit Buttons für jedes Kartenelement in der
		// Liste
		int counter = 0; // Zähler für Bilder mit Pfeilen
		for (Card card : cardDrawList) {

			// Sonderfall Liste hat 3, 7, 11 Elemente etc. - Leeres Feld
			// zwischenschieben
			if ((card.equals(cardDrawList.get(cardDrawList.size() - 1)) && (cardDrawList
					.size() + 1) % 4 == 0)) {

				// Erzeuge leeres (unsichtbares) Element
				LinearLayout lastElement = new LinearLayout(getBaseContext());
				lastElement.setMinimumHeight(cardheight);
				lastElement.setMinimumWidth(cardwidth);

				lastElement.setGravity(Gravity.CENTER);
				gl.addView(lastElement);

				counter++;
			}

			LinearLayout container = new LinearLayout(getBaseContext());

			if (counter == 0) {
				container.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.background01));
			} else if (counter == 1) {
				container.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.background02));
			} else if (counter == 2) {
				container.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.background03));
			} else if (counter == 3) {
				container.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.background04));
			}

			container.setGravity(Gravity.CENTER);

			container.setOnDragListener(new MyDragListener());

			// Buttons hinzufügen wenn nicht das erste oder letzte Element
			if (card.getCardId() != 0) {
				Button b1 = new Button(getBaseContext());
				b1.setText(card.getSchlagwort());
				b1.setMinimumHeight(cardheight - margin);
				b1.setMinimumWidth(cardwidth - margin);
				b1.setMaxHeight(cardheight - margin);
				b1.setMaxWidth(cardwidth - margin);

				container.addView(b1);
			}

			gl.addView(container);

			counter++;

			if (counter > 3) {
				counter = 0;
			}

		}
		// Zum debuggen
		// for (int i = 0; i < cardDrawList.size(); i++) {
		// Log.d("cardDrawList" , "CDL: " +
		// cardDrawList.get(i).schlagwort.toString());
		// }
		// for (int i = 0; i < cardList.size(); i++) {
		// Log.d("cardList", "CL: " + cardList.get(i).schlagwort.toString());
		// }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.menue, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// int id = item.getItemId();
		// if (id == R.id.action_settings) {
		// return true;
		// }
		return super.onOptionsItemSelected(item);
	}

	private final class MyTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				ClipData data = ClipData.newPlainText("", "");
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
						view);
				view.startDrag(data, shadowBuilder, view, 0);
				view.setVisibility(View.INVISIBLE);
				return true;
			} else {
				return false;
			}
		}
	}

	class MyDragListener implements OnDragListener {
		Drawable enterShape = getResources().getDrawable(
				R.drawable.background01);
		Drawable normalShape = getResources().getDrawable(
				R.drawable.background01);

		@SuppressWarnings("deprecation")
		@Override
		public boolean onDrag(View v, DragEvent event) {
			// int action = event.getAction();
			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				// do nothing
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
				v.setBackgroundDrawable(enterShape);
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				v.setBackgroundDrawable(normalShape);
				break;
			case DragEvent.ACTION_DROP:
				// Dropped, reassign View to ViewGroup

				GridLayout gl = (GridLayout) findViewById(R.id.gridlayout);
				if (v.getParent().equals(gl)) {
					View view = (View) event.getLocalState();
					ViewGroup owner = (ViewGroup) view.getParent();
					owner.removeView(view);

					Button b = (Button) view;

					Card playedCard = new Card(0, "dummy", "du dummy", 2042);

					if (b.getText() == playableCards.get(0).getSchlagwort()) {
						playedCard = playableCards.get(0);
						// playableCards.remove(0);
					} else if (b.getText() == playableCards.get(1)
							.getSchlagwort()) {
						playedCard = playableCards.get(1);
						// playableCards.remove(1);
					} else if (b.getText() == playableCards.get(2)
							.getSchlagwort()) {
						playedCard = playableCards.get(2);
						// playableCards.remove(2);
					}

					List<Card> cL = cardList;

					// Log.d("liste", cL.toString());

					LinearLayout container = (LinearLayout) v;
					if (container.getChildCount() == 0) {
						// Container leer, prüfe ob Anfang oder Ende
						if (gl.getChildAt(0).equals(container)) {
							cL.add(1, playedCard); // Am Anfang einfügen
						} else {
							cL.add(cL.size() - 1, playedCard); // Am Ende
																// einfügen
						}
					} else {
						// Im Container befindet sich etwas, suche belegte
						// Container
						// in Grid ab
						for (int i = 0; i < cL.size(); i++) {
							Button but = (Button) container.getChildAt(0);
							if (but.getText().equals(cL.get(i).getSchlagwort())) {
								// Füge zu legende Karte vor der Karte des
								// Container
								// in den gedroppt werden soll in die Liste
								cL.add(i, playedCard);
								break;
							}
						}
					}

					// Log.d("liste2", cL.toString());
					// Spielfeld neu zeichnen
					setupCardArea(cL);
				}

				break;
			case DragEvent.ACTION_DRAG_ENDED:
				v.setBackgroundDrawable(normalShape);
			default:
				break;
			}
			return true;
		}

	}
}
