package de.historyalpha;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.ClipData;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class GameActivity extends Activity {
	public String NAMETAG = "Alpha - GameActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// findViewById(R.id.button1).setOnTouchListener(new MyTouchListener());
		// findViewById(R.id.button2).setOnTouchListener(new MyTouchListener());
		// findViewById(R.id.button3).setOnTouchListener(new MyTouchListener());
		// findViewById(R.id.button4).setOnTouchListener(new MyTouchListener());
		// findViewById(R.id.button5).setOnTouchListener(new MyTouchListener());

		findViewById(R.id.bottomButton1).setOnTouchListener(
				new MyTouchListener());
		findViewById(R.id.bottomButton2).setOnTouchListener(
				new MyTouchListener());
		findViewById(R.id.bottomButton3).setOnTouchListener(
				new MyTouchListener());

		// findViewById(R.id.grid1).setOnDragListener(new MyDragListener());
		// findViewById(R.id.grid2).setOnDragListener(new MyDragListener());
		// findViewById(R.id.grid3).setOnDragListener(new MyDragListener());
		// findViewById(R.id.grid4).setOnDragListener(new MyDragListener());
		// findViewById(R.id.grid5).setOnDragListener(new MyDragListener());
		// findViewById(R.id.grid6).setOnDragListener(new MyDragListener());
		// findViewById(R.id.grid7).setOnDragListener(new MyDragListener());
		// findViewById(R.id.grid8).setOnDragListener(new MyDragListener());

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		int bottomheight = height / 6; // Höhe der unteren Leiste
		int bottomwidth = width / 6; // Breite der unteren Leiste

		// Setze Größe der Karten in der unteren Leiste:
		findViewById(R.id.bottomButton1).setMinimumHeight(bottomheight);
		findViewById(R.id.bottomButton1).setMinimumWidth(bottomwidth);
		findViewById(R.id.bottomButton2).setMinimumHeight(bottomheight);
		findViewById(R.id.bottomButton2).setMinimumWidth(bottomwidth);
		findViewById(R.id.bottomButton3).setMinimumHeight(bottomheight);
		findViewById(R.id.bottomButton3).setMinimumWidth(bottomwidth);

		int topheight = height * 3 / 6; // Höhe des Spielfensters (ScrollView)
		int topwidth = width; // ganze Breite des Spielfensters (ScrollView)

		HorizontalScrollView hv = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
		hv.setMinimumHeight(topheight);

		// Höhe und Breite eines Kästchens in der ScrollView
		int cardheight = topheight / 2;
		int cardwidth = topwidth / 4; // nicht Quadratisch

		GridLayout gl = (GridLayout) findViewById(R.id.gridlayout);

		// Füge 3 LinearLayouts hinzu für Kartenplätze
		LinearLayout l1 = new LinearLayout(getApplicationContext());
		l1.setMinimumHeight(cardheight);
		l1.setMinimumWidth(cardwidth);
		l1.setGravity(Gravity.CENTER);
		l1.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape));
		gl.addView(l1);
		l1.setOnDragListener(new MyDragListener());

		LinearLayout l2 = new LinearLayout(getApplicationContext());
		l2.setMinimumHeight(cardheight);
		l2.setMinimumWidth(cardwidth);
		l2.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape));
		l2.setGravity(Gravity.CENTER);
		gl.addView(l2);
		l2.setOnDragListener(new MyDragListener());

		LinearLayout l3 = new LinearLayout(getApplicationContext());
		l3.setMinimumHeight(cardheight);
		l3.setMinimumWidth(cardwidth);
		l3.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape));
		l3.setGravity(Gravity.CENTER);
		gl.addView(l3);
		l3.setOnDragListener(new MyDragListener());

		// Dem mittleren Layout die Startkarte hinzufügen
		Button b1 = new Button(getBaseContext());
		b1.setText("Startkarte");
		b1.setMinimumHeight((int) (cardheight * 0.9));
		b1.setMinimumWidth((int) (cardwidth * 0.9));

		l2.addView(b1);

		// Log.d("bla", String.valueOf(gl.getHeight()));

		// findViewById(R.id.button1).setMinimumHeight(height/3);
		// findViewById(R.id.button2).setMinimumHeight(height/3);
		// findViewById(R.id.button3).setMinimumHeight(height/3);
		// findViewById(R.id.grid1).setMinimumHeight(height/2);
		// findViewById(R.id.grid2).setLayoutParams(params);
		// findViewById(R.id.grid3).setLayoutParams(params);
		// findViewById(R.id.grid4).setLayoutParams(params);
		// findViewById(R.id.grid5).setLayoutParams(params);
		// findViewById(R.id.grid6).setLayoutParams(params);
		// findViewById(R.id.grid7).setLayoutParams(params);
		// findViewById(R.id.grid8).setLayoutParams(params);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menue, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
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
				R.drawable.shape_droptarget);
		Drawable normalShape = getResources().getDrawable(R.drawable.shape);

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
				View view = (View) event.getLocalState();
				ViewGroup owner = (ViewGroup) view.getParent();
				owner.removeView(view);

				// Größe der Karten bekommen über Displaygröße
				Display display = getWindowManager().getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				int width = size.x;
				int height = size.y;

				int topheight = height * 3 / 6; // Höhe des Spielfensters
												// (ScrollView)
				int topwidth = width; // ganze Breite des Spielfensters
										// (ScrollView)

				// Höhe und Breite eines Kästchens in der ScrollView
				int cardheight = topheight / 2;
				int cardwidth = topwidth / 4; // nicht Quadratisch

				// Ändere Größe der Karte
				LayoutParams lp = new LayoutParams((int) (cardwidth * 0.9),
						(int) (cardheight * 0.9));
				view.setLayoutParams(lp);
				LinearLayout container = (LinearLayout) v;

				// Wenn angelegtes Feld leer (also erstes oder letztes Element:
				if (container.getChildCount() == 0) {
					// Entferne alle Views aus dem Container und füge die Karte
					// hinzu
					container.removeAllViews();
					container.addView(view);
					view.setVisibility(View.VISIBLE);
					view.setOnTouchListener(null); // Gelegte Karten können
													// nicht mehr bewegt werden

					// Füge neues LinearLayout als Container hinzu
					GridLayout gl = (GridLayout) findViewById(R.id.gridlayout);

					LinearLayout l1 = new LinearLayout(getApplicationContext());
					l1.setMinimumHeight(cardheight);
					l1.setMinimumWidth(cardwidth);
					l1.setGravity(Gravity.CENTER);
					l1.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.shape));

					if (gl.getChildAt(0).equals(container)) {
						gl.addView(l1, 0);
					} else {
						gl.addView(l1);
					}
					l1.setOnDragListener(new MyDragListener());

				}
				//Wenn im Container schon eine Karte drin ist:
				else{
					//TODO Alte Karte aus Container rausnehmen und in nächstes Feld packen, neue karte rein (rekursiv!)
				}

				// if (container.getChildCount() > 0) {
				//
				// int number = Integer
				// .parseInt(container.getTag().toString());
				// /**
				// * Hier entsteht der Fehler: Verschiebt man einen Button in
				// * ein leeres Feld, so crashed das Programm Grund unklar.
				// * container.getChildCount liefer 1, also hat der Container
				// * nur 1 Child an Position 0?
				// **/
				// View b = container.getChildAt(1);
				// Log.d(NAMETAG, String.valueOf(container.getChildCount()));
				// number++;
				// // Log.d(NAMETAG,String.valueOf(number));
				// LinearLayout nextContainer = (LinearLayout) ((View) container
				// .getParent()).findViewWithTag(String
				// .valueOf(number));
				// // Log.d(NAMETAG, "container.getParent()" + ((View)
				// //
				// container.getParent()).findViewWithTag(String.valueOf(number)).toString());
				// nextContainer.removeAllViews();
				// // Log.d(NAMETAG,b.toString());
				// nextContainer.addView(b);
				//
				// }

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
