package de.historyalpha;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
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
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

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

			// Tempor�r startkarte manuell:
			Card startCard = new Card(1, "startkarte",
					"Wann wurde das Teflon erfunden?", 1934);

			// Dummykarte f�r Anfangs und Endpunkt (nicht sichtbar)

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
			Log.d("bla", e.getMessage());
		}

	}

	public void setupBottomBar() {

		// TODO Hier noch die Namen der Buttons durch 3 random Karten
		// ersetzen. Nur zum testen mit random Zahlen!

		playableCards.clear();

		Card[] test = CardQuestion.getRandomCards();
		for (int i = 0; i < 3; i++) {

//			int rand = (int) (Math.random() * 100);
//			Card c1 = new Card(rand, String.valueOf(rand), "", rand);
			Card c1 = new Card(test[0].getCardId(), test[0].getSchlagwort(), "", test[0].getJahr());
			

//			int rand2 = (int) (Math.random() * 100);
//			Card c2 = new Card(rand2, String.valueOf(rand2), "", rand2);
			Card c2 = new Card(test[1].getCardId(), test[1].getSchlagwort(), "", test[1].getJahr());
			
//			int rand3 = (int) (Math.random() * 100);
//			Card c3 = new Card(rand3, String.valueOf(rand3), "", rand3);
			Card c3 = new Card(test[2].getCardId(), test[2].getSchlagwort(), "", test[2].getJahr());

			playableCards.add(c1);
			playableCards.add(c2);
			playableCards.add(c3);
		}

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

		int bottomheight = height / 5; // H�he der unteren Leiste
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

	public void setupCardArea(List<Card> cL) {

		GridLayout gl = (GridLayout) findViewById(R.id.gridlayout);

		List<Card> cardDrawList = new ArrayList<Card>();

		for (Card card : cL) {
			cardDrawList.add(card);
		}

		gl.removeAllViews();

		int topheight = height * 3 / 5; // H�he des Spielfensters (ScrollView)
		int topwidth = width; // ganze Breite des Spielfensters (ScrollView)

		// H�he und Breite eines K�stchens in der ScrollView
		int cardheight = topheight / 2;
		int cardwidth = topwidth / 4; // nicht Quadratisch

		Log.d("height", String.valueOf(cardheight));
		Log.d("width", String.valueOf(cardwidth));

		int margin = (int) (cardwidth * 0.1);

		Log.d("margin", String.valueOf(margin));

		// Sortiere Elemente der Liste f�r Layout neu (vertauschen von 3 und 4,
		// 7 und 8, etc.)
		for (int i = 0; i < cardDrawList.size(); i++) {
			// Pos i+1 durch 4 teilbar hei�t, dass i und i-1 vertauscht werden
			// m�ssen
			if ((i + 1) % 4 == 0) {
				// Pl�tze tauschen
				Card temp = cardDrawList.get(i);
				cardDrawList.set(i, cardDrawList.get(i - 1));
				cardDrawList.set(i - 1, temp);
			}
		}

		// Erzeuge LinearLayouts mit Buttons f�r jedes Kartenelement in der
		// Liste
		int counter = 0; // Z�hler f�r Bilder mit Pfeilen
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

			// Buttons hinzuf�gen wenn nicht das erste oder letzte Element
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

		@Override
		public boolean onDrag(View v, DragEvent event) {
			int action = event.getAction();
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
						// Container leer, pr�fe ob Anfang oder Ende
						if (gl.getChildAt(0).equals(container)) {
							cL.add(1, playedCard); // Am Anfang einf�gen
						} else {
							cL.add(cL.size() - 1, playedCard); // Am Ende
																// einf�gen
						}
					} else {
						// Im Container befindet sich etwas, suche belegte
						// Container
						// in Grid ab
						for (int i = 0; i < cL.size(); i++) {
							Button but = (Button) container.getChildAt(0);
							if (but.getText().equals(cL.get(i).getSchlagwort())) {
								// F�ge zu legende Karte vor der Karte des
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

		public void replaceItem() {
		}

	}
}
