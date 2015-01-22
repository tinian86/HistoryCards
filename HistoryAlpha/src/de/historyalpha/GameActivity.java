package de.historyalpha;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity implements OnClickListener {

	public String NAMETAG = "Alpha - GameActivity";

	private List<Card> cardList = new ArrayList<Card>();
	private List<Card> playableCards = new ArrayList<Card>();
	private int width;
	private int height;
	private int lifes = 3;
	ArrayList<Card> cardStack;
	boolean wrongCardOnTable;
	
	private Toast toast;

	public static String PREFS_NAME = "game";
	private static String CURRENT_SCORE_KEY = "current_score";
	public static String CARD_TEMP = "leer";

	private int score = 0;
	private String name = "";

	float touchX = 0;
	float touchY = 0;
	float currentTouchX = 0;
	float currentTouchY = 0;

	private long pressStartTime;

	boolean reveal = false;

	int dragIndex = 0;
	boolean dragEntered = false;

	ArrayList<LinearLayout> gridList = new ArrayList<LinearLayout>();

	boolean descriptionShown = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		cardList.clear();
		playableCards.clear();

		cardStack = CardQuestion.getCardStack();
		
		wrongCardOnTable = false;

		try {

			setupBottomBar();

			// Temporär startkarte manuell:
			Card startCard = cardStack.remove(0);

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

			setupCardArea();

		} catch (Exception e) {
			Log.d("GameActivity", e.getMessage());
		}

	}

	@SuppressWarnings("deprecation")
	public void setupCardArea() {
	
		GridLayout gl = (GridLayout) findViewById(R.id.gridlayout);
	
		List<Card> cardDrawList = new ArrayList<Card>();
		
		cardDrawList.clear();
	
		gridList.clear();
	
		for (Card card : cardList) {
			cardDrawList.add(card);
		}
	
		gl.removeAllViews();
	
		int topheight = height * 3 / 5; // Höhe des Spielfensters (ScrollView)
		int topwidth = width; // ganze Breite des Spielfensters (ScrollView)
	
		// Höhe und Breite eines Kästchens in der ScrollView
		int cardheight = topheight / 2;
		int cardwidth = topwidth / 4; // nicht Quadratisch
	
		// Log.d("height", String.valueOf(cardheight));
		// Log.d("width", String.valueOf(cardwidth));
	
		int margin = (int) (cardwidth * 0.1);
	
		// Log.d("margin", String.valueOf(margin));
	
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
				gridList.add(lastElement);
	
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
	
			container.setMinimumHeight(cardheight);
			container.setMinimumWidth(cardwidth);
	
			container.setOnDragListener(new MyDragListener());
	
			// Buttons hinzufügen wenn nicht das erste oder letzte Element
			if (card.getCardId() != 0) {
				Button b1 = new Button(getBaseContext());
				b1.setText(card.getSchlagwort());
				b1.setMinimumHeight(cardheight - margin);
				b1.setMinimumWidth(cardwidth - margin);
				b1.setMaxHeight(cardheight - margin);
				b1.setMaxWidth(cardwidth - margin);
	
				if (!reveal) {
	
					showDetails(b1);
	
					if (!card.isCorrect()) {
						b1.setText(String.valueOf(card.getSchlagwort() + "\n"
								+ card.getJahr()));
						b1.setBackgroundColor(Color.RED);
						Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
						b1.startAnimation(shake);
						// b1.setTextSize(40f);
						b1.setOnClickListener(new OnClickListener() {
	
							@Override
							public void onClick(View v) {
	
								Card wrongCard = new Card(0, "dummy",
										"du dummy", 2042);
	
								for (Card card2 : cardList) {
	
									if (!card2.isCorrect()) {
										wrongCard = card2;
									}
								}
	
								if (wrongCard.getCardId() != 0) {
									cardList.remove(wrongCard);
									wrongCardOnTable = false;
									setupCardArea();
								}
								toast.cancel();
	
							}
						});
					}
	
				} else {
	
					showDetails(b1);
					b1.setText(String.valueOf(card.getSchlagwort() + "\n"
							+ card.getJahr()));
	
					if (card.isCorrect()) {
						b1.setBackgroundColor(Color.GREEN);
						b1.setTextColor(Color.BLACK);
					} else {
						b1.setBackgroundColor(Color.RED);
					}
					
					LinearLayout bottomBar = (LinearLayout) findViewById(R.id.playableCardLayout);
					
					//funktioniert nicht...
					for (int i=0; i<playableCards.size(); i++) {
						Button bottomButton = (Button) bottomBar.getChildAt(i);
						bottomButton.setText(playableCards.get(i).getSchlagwort() +"\n" +playableCards.get(i).getJahr());
					}
					
					wrongCardOnTable = false;
	
				}
	
				container.addView(b1);
			}
	
			gl.addView(container);
			gridList.add(container);
	
			counter++;
	
			if (counter > 3) {
				counter = 0;
			}
	
		}
	
		// Sortiere Elemente der Liste für Layout neu (vertauschen von 3 und 4,
		// 7 und 8, etc.)
		for (int i = 0; i < gridList.size(); i++) {
			// Pos i+1 durch 4 teilbar heißt, dass i und i-1 vertauscht werden
			// müssen
			if ((i + 1) % 4 == 0) {
				// Plätze tauschen
				LinearLayout temp = gridList.get(i);
				gridList.set(i, gridList.get(i - 1));
				gridList.set(i - 1, temp);
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

	public void setupBottomBar() {
	
		playableCards.clear();
	
		Card card1 = cardStack.remove(0);
		Card card2 = cardStack.remove(0);
		Card card3 = cardStack.remove(0);
	
		playableCards.add(card1);
		playableCards.add(card2);
		playableCards.add(card3);
	
		LinearLayout bottomBar = (LinearLayout) findViewById(R.id.playableCardLayout);
		
		bottomBar.setGravity(Gravity.CENTER);
	
		bottomBar.removeAllViews();
	
		Button button1 = new Button(getBaseContext());
		Button button2 = new Button(getBaseContext());
		Button button3 = new Button(getBaseContext());
	
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
		button1.setMaxHeight(height / 4);
		button1.setMinimumWidth(bottomwidth);
		button1.setMaxWidth(height / 4);
	
		button2.setMinimumHeight(bottomheight);
		button2.setMaxHeight(height / 4);
		button2.setMinimumWidth(bottomwidth);
		button2.setMaxWidth(height / 4);
	
		button3.setMinimumHeight(bottomheight);
		button3.setMaxHeight(height / 4);
		button3.setMinimumWidth(bottomwidth);
		button3.setMaxWidth(height / 4);
	
		bottomBar.addView(button1);
		bottomBar.addView(button2);
		bottomBar.addView(button3);
	
		TextView txtScore = (TextView) findViewById(R.id.score_id);
		TextView txtLife = (TextView) findViewById(R.id.life_id);
		txtScore.setTextColor(android.graphics.Color.YELLOW);
		txtLife.setTextColor(android.graphics.Color.GREEN);
	
		// TODO Schriftgröße dynamisch -> fuer Tablet 40f
		txtScore.setTextSize(25f);
		txtLife.setTextSize(25f);
	
	}

	// Methode, um die Karten aufzudecken und ihre Jahreszahl anzuzeigen
	public void revealCards(View view) {
	
		// Zwei counter, um die Punkte aufzusummieren die man fuer Karten
		// bekommt
		int counter = 0;
		int sum = 0;
	
		boolean wrongCard = false;
	
		for (int j = 1; j < cardList.size() - 2; j++) {
	
			int checkYear1 = cardList.get(j).jahr;
			int checkYear2 = cardList.get(j + 1).jahr;
	
			if (checkYear1 < checkYear2) {
				counter = counter + 1;
				sum = sum + counter;
			} else if (cardList.size() > 3) {
	
				Button button1 = (Button) view;
	
				if (button1.getText().equals(cardList.get(j).getSchlagwort())) {
					wrongCard = true;
					wrongCardOnTable = true;
					cardList.get(j).setCorrect(false);
				} else {
					wrongCard = true;
					wrongCardOnTable = true;
					cardList.get(j + 1).setCorrect(false);
				}
	
			}
	
		}
	
		score = sum;
	
		// Wenn eine Karte falsch gelegt wird, erscheint eine Meldung, die Liste
		// wird geleert und das Spielfeld neu initialisiert
		if (wrongCard) {
			
	
			if (getLifes() > 0) {
				Context context = getApplicationContext();
				CharSequence text = "Sie haben eine Karte falsch angelegt und verlieren ein Leben! Rote Karte antippen zum Weiterspielen!";
				int duration = Toast.LENGTH_LONG;
		
				toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.BOTTOM, 0, 0);
				toast.show();
				
				decreaseLifes();
			} else {
				
				Context context = getApplicationContext();
				CharSequence text = "Sie haben keine Leben mehr. Game over! :-( \"Zum Highscore\" antippen um zum Highscore zu gelangen!";
				int duration = Toast.LENGTH_LONG;
		
				toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				
				wrongCardOnTable = false;
				revealAll(view);
				return;
			}
	
		}
	
		setupCardArea();
		// Scorefeld setzen
		TextView scoreField = (TextView) findViewById(R.id.score_id);
		scoreField.setText("Punkte: " + String.valueOf(score));
		// scoreField.setHeight(height / 5);
		// scoreField.setWidth(width / 6);
		// scoreField.setTextSize(1.0f);
		// Verbleibende Leben setzen
		TextView actualLife = (TextView) findViewById(R.id.life_id);
		actualLife.setText("Leben: " + String.valueOf(lifes));
	}

	private void revealAll(final View view) {
	
		this.reveal = true;
		setupCardArea();
	
		LinearLayout menu = (LinearLayout) findViewById(R.id.menuLayout);
	
		menu.removeAllViews();
	
		Button endButton = new Button(getBaseContext());
		endButton.setMinimumHeight(height/5);
		endButton.setText("Zum HighScore");
		endButton.setBackgroundColor(Color.GREEN);
		endButton.setTextColor(Color.BLACK);
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		endButton.startAnimation(shake);
	
		menu.addView(endButton);
	
		endButton.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				popup(view);
	
			}
		});
	
	}

	public void popup(View v) {
	
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.popup, null);
		final PopupWindow popupWindow = new PopupWindow(popupView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.update();
	
		TextView tex = (TextView) popupView.findViewById(R.id.text);
		popupView.findViewById(R.id.userName);
		final EditText nameInput = (EditText) popupView
				.findViewById(R.id.userName);
	
		tex.setText("Das Spiel ist vorbei! \nSie haben " + score
				+ " Punkte erreicht.");
		tex.setTextSize(25f);
	
		Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
		btnDismiss.setOnClickListener(new Button.OnClickListener() {
	
			@Override
			public void onClick(View v) {
	
				/*
				 * SharedPreferences prefs =
				 * this.getSharedPreferences(PREFS_NAME, 0);
				 * 
				 * Editor editor = prefs.edit();
				 * editor.putInt(CURRENT_SCORE_KEY, score); editor.commit();
				 */
	
				name = nameInput.getText().toString();
	
				// popupWindow.dismiss();
				gotoHighscore();
			}
		});
	
		// popupWindow.showAsDropDown(mapView, -50, +30);
		popupWindow.showAtLocation(v, 1, 50, 50);
	}

	public void getNewCards(View view) {
		setupBottomBar();

	}

	private void showDetails(Button b1) {
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				View popupView = layoutInflater.inflate(
						R.layout.popupcarddescription, null);
				final PopupWindow popupWindow2 = new PopupWindow(popupView,
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				Button button = (Button) v;

				String schlagwort = "";
				String beschreibung = "";

				for (Card card2 : cardList) {
					if (((String) button.getText()).contains(card2
							.getSchlagwort())) {
						schlagwort = card2.getSchlagwort();
						beschreibung = card2.getBeschreibung();
					}
				}

				Button bigButton = (Button) popupView
						.findViewById(R.id.cardDescriptionButton);
				bigButton.setText(schlagwort + "\n\n" + beschreibung);
				// bigButton.setTextSize(20f);
				bigButton.setMinimumHeight(height * 3 / 5);
				bigButton.setMinimumWidth(width / 2);

				bigButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						popupWindow2.dismiss();

					}
				});

				popupWindow2.showAtLocation(v, 1, 50, 50);

			}
		});

	}

	// Hier wird nachdem alle leben aufgebraucht sind, die Highscore Aktivity
	// aufgerufen und der score
	// dem intent uebergeben und für das naechste spiel auf 0 gesetzt(in dem
	// globaen Speicher)
	private void gotoHighscore() {
		SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME, 0);
	
		Editor editor = prefs.edit();
		editor.putInt(CURRENT_SCORE_KEY, score);
	
		editor.commit();
	
		Intent intent = new Intent(this, HighscoreAcitvity.class);
		intent.putExtra(
				getResources().getString(R.string.new_highscore_extra_key),
				score);
	
		intent.putExtra("Name", name);
	
		editor.putInt(CURRENT_SCORE_KEY, 0);
		editor.commit();
	
		startActivity(intent);
	}

	private void decreaseLifes() {
		if (lifes > 0) {
			lifes--;
		}
	}

	private int getLifes() {
		return lifes;
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
	
	public void onBackPressed(){
		final Intent intent = new Intent(this, MenuActivity.class);
		new AlertDialog.Builder(this)
        .setTitle("Spiel beenden?")
        .setMessage("Wollen sie das Spiel wirklich beenden? Der bisherige Spielstand wird nicht gespeichert.")
        .setNegativeButton(android.R.string.no, null)
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				startActivity(intent);
				
			}

            
        }).create().show();
	}

	private void setScore(int i) {
		score = i;
	}

	private int getScore() {
		return score;
	}

	private final class MyTouchListener implements OnTouchListener {

		public boolean onTouch(View view, MotionEvent motionEvent) {

			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN
					&& !wrongCardOnTable && !reveal) {
				pressStartTime = System.currentTimeMillis();
				descriptionShown = false;

				ClipData data = ClipData.newPlainText("", "");
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
						view);
				view.startDrag(data, shadowBuilder, view, 0);

				return true;
			}

			else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE
					&& !wrongCardOnTable && !reveal) {

				ClipData data = ClipData.newPlainText("", "");
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
						view);
				view.startDrag(data, shadowBuilder, view, 0);
				// view.setVisibility(View.INVISIBLE);

				return true;

			} else {
				return false;
			}
		}
	}

	class MyDragListener implements OnDragListener {
		// Drawable enterShape = getResources().getDrawable(
		// R.drawable.background01);
		// Drawable normalShape = getResources().getDrawable(
		// R.drawable.background01);

		@SuppressWarnings("deprecation")
		@Override
		public boolean onDrag(View v, DragEvent event) {

			List<Card> cL = cardList;
			LinearLayout container = (LinearLayout) v;

			// int action = event.getAction();
			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				touchX = event.getX();
				touchY = event.getY();
				break;
			case DragEvent.ACTION_DRAG_ENTERED:

				if (container.getChildCount() != 0) {
					for (int i = 1; i < cL.size() - 1; i++) {
						Button but = (Button) container.getChildAt(0);
						if (but.getText().equals(cL.get(i).getSchlagwort())) {
							dragIndex = i;
							break;
						}
					}
					moveCards(dragIndex);
					dragEntered = true;
				}

				break;
			case DragEvent.ACTION_DRAG_EXITED:

				if (dragEntered) {
					moveCardsBack(dragIndex);
				}

				dragEntered = false;
				break;
			case DragEvent.ACTION_DROP:
				// Dropped, reassign View to ViewGroup

				GridLayout gl = (GridLayout) findViewById(R.id.gridlayout);

				View view = (View) event.getLocalState();
				ViewGroup owner = (ViewGroup) view.getParent();

				if (v.getParent().equals(gl)) {

					if (dragEntered) {
						moveCardsBack(dragIndex);
					}

					dragEntered = false;

					owner.removeView(view);

					Button b = (Button) view;

					Card playedCard = new Card(0, "dummy", "du dummy", 2042);

					if (b.getText() == playableCards.get(0).getSchlagwort()) {
						playedCard = playableCards.get(0);
						playableCards.remove(0);
					} else if (b.getText() == playableCards.get(1)
							.getSchlagwort()) {
						playedCard = playableCards.get(1);
						playableCards.remove(1);
					} else if (b.getText() == playableCards.get(2)
							.getSchlagwort()) {
						playedCard = playableCards.get(2);
						playableCards.remove(2);
					}

					playableCards.add(cardStack.remove(0));

					LinearLayout bottomBar = (LinearLayout) findViewById(R.id.playableCardLayout);

					Button button1 = new Button(getBaseContext());

					button1.setTextColor(Color.WHITE);

					button1.setOnTouchListener(new MyTouchListener());

					button1.setText(playableCards.get(2).getSchlagwort());

					int bottomheight = height / 5; // Höhe der unteren Leiste
					int bottomwidth = width / 5; // Breite der unteren Leiste

					button1.setMinimumHeight(bottomheight);
					button1.setMaxHeight(height / 4);
					button1.setMinimumWidth(bottomwidth);
					button1.setMaxWidth(height / 4);

					bottomBar.addView(button1);

					// Log.d("liste", cL.toString());

					container = (LinearLayout) v;
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
						for (int i = 1; i < cL.size() - 1; i++) {
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
					// setupCardArea(cL);
					revealCards(view);

				}

				else {

				}

				break;
			case DragEvent.ACTION_DRAG_ENDED:

				long pressDuration = System.currentTimeMillis()
						- pressStartTime;

				if (pressDuration < 200 && !descriptionShown) {

					descriptionShown = true;

					LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
							.getSystemService(LAYOUT_INFLATER_SERVICE);
					View popupView = layoutInflater.inflate(
							R.layout.popupcarddescription, null);
					final PopupWindow popupWindow2 = new PopupWindow(popupView,
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);

					View view2 = (View) event.getLocalState();

					Button button = (Button) view2;

					String schlagwort = "";
					String beschreibung = "";

					for (Card card2 : playableCards) {
						if (button.getText().equals(card2.getSchlagwort())) {
							schlagwort = card2.getSchlagwort();
							beschreibung = card2.getBeschreibung();
						}
					}

					Button bigButton = (Button) popupView
							.findViewById(R.id.cardDescriptionButton);
					bigButton.setText(schlagwort + "\n" + beschreibung);
					bigButton.setMinimumHeight(height * 3 / 5);
					bigButton.setMinimumWidth(width / 2);

					bigButton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							popupWindow2.dismiss();

						}
					});

					popupWindow2.showAtLocation(v, 1, 50, 50);
				}
			default:
				break;
			}
			return true;
		}

		private void moveCards(int dragIndex) {

			for (int j = gridList.size() - 1; j > dragIndex; j--) {
				LinearLayout elem1 = gridList.get(j - 1);
				LinearLayout elem2 = gridList.get(j);
				if (elem1.getChildCount() != 0 && elem2.getChildCount() == 0) {
					Button button = (Button) elem1.getChildAt(0);
					elem1.removeAllViews();
					elem2.addView(button);

				}
			}
		}

	}

	private void moveCardsBack(int dragIndex) {

		for (int j = dragIndex; j < gridList.size() - 1; j++) {
			LinearLayout elem1 = gridList.get(j);
			LinearLayout elem2 = gridList.get(j + 1);
			if (elem1.getChildCount() == 0 && elem2.getChildCount() != 0) {
				Button button = (Button) elem2.getChildAt(0);
				elem2.removeAllViews();
				elem1.addView(button);

			}
		}

	}

	public void onClick(View e) {

	}
}
