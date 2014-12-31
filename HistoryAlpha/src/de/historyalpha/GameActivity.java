package de.historyalpha;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ClipData;
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

public class GameActivity extends Activity {

	public String NAMETAG = "Alpha - GameActivity";

	private List<Card> cardList = new ArrayList<Card>();
	private List<Card> playableCards = new ArrayList<Card>();
	private int width;
	private int height;

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
		Card c1 = new Card(threeCards[0].getCardId(),
				threeCards[0].getSchlagwort(), "", threeCards[0].getJahr());
		Card c2 = new Card(threeCards[1].getCardId(),
				threeCards[1].getSchlagwort(), "", threeCards[1].getJahr());
		Card c3 = new Card(threeCards[2].getCardId(),
				threeCards[2].getSchlagwort(), "", threeCards[2].getJahr());

		playableCards.add(c1);
		playableCards.add(c2);
		playableCards.add(c3);
		// }

		LinearLayout bottomBar = (LinearLayout) findViewById(R.id.playableCardLayout);

		bottomBar.removeAllViews();

		Button b1 = new Button(getBaseContext());
		Button b2 = new Button(getBaseContext());
		Button b3 = new Button(getBaseContext());

		// b1.setId(11);
		// b1.setId(22);
		// b1.setId(33);

		b1.setTextColor(Color.WHITE);
		b2.setTextColor(Color.WHITE);
		b3.setTextColor(Color.WHITE);

		b1.setOnTouchListener(new MyTouchListener());
		b2.setOnTouchListener(new MyTouchListener());
		b3.setOnTouchListener(new MyTouchListener());

		b1.setText(playableCards.get(0).getSchlagwort());
		b2.setText(playableCards.get(1).getSchlagwort());
		b3.setText(playableCards.get(2).getSchlagwort());

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;

		int bottomheight = height / 5; // Höhe der unteren Leiste
		int bottomwidth = width / 5; // Breite der unteren Leiste

		b1.setMinimumHeight(bottomheight);
		b1.setMaxHeight(bottomheight);
		b1.setMinimumWidth(bottomwidth);
		b1.setMaxWidth(bottomwidth);

		b2.setMinimumHeight(bottomheight);
		b2.setMaxHeight(bottomheight);
		b2.setMinimumWidth(bottomwidth);
		b2.setMaxWidth(bottomwidth);

		b3.setMinimumHeight(bottomheight);
		b3.setMaxHeight(bottomheight);
		b3.setMinimumWidth(bottomwidth);
		b3.setMaxWidth(bottomwidth);

		bottomBar.addView(b1);
		bottomBar.addView(b2);
		bottomBar.addView(b3);

	}

	public void getNewCards(View view) {
		setupBottomBar();

	}

	// TODO: Zur Zeit werden mit dem Button "Neue Karten" ALLE der drei Karten
	// neu geholt -> sollten eigentlich nur die fehlenden ersetzt werden
	
	// Methode, um die Karten aufzudecken und ihre Jahreszahl anzuzeigen
	// TODO: Jahreszahl wird nicht sofort angezeigt, erst wenn weitere Karte
	// gelegt wird! Fix!
	public void revealCards(View view) {

		// Zwei counter, um die Punkte aufzusummieren die man fuer Karten
		// bekommt
		// TODO Summe enthaelt am Ende noch Werte fuer Start- und Endkarte (6) zuviel! 
		int counter = 0;
		int sum = 0;

		for (int i = 0; i < cardList.size(); i++) {
			cardList.get(i).schlagwort = String.valueOf(cardList.get(i).jahr);
			counter = counter + 1;
			sum = sum + counter;
		}
		// Spielfeld neuzeichnen
		setupCardArea(cardList);
		// Scorefeld setzen
		TextView score = (TextView) findViewById(R.id.score_id);
		score.setText(String.valueOf(sum));
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

		Log.d("height", String.valueOf(cardheight));
		Log.d("width", String.valueOf(cardwidth));

		int margin = (int) (cardwidth * 0.1);

		Log.d("margin", String.valueOf(margin));

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
