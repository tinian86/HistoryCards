package de.historyalpha;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TutorialActivity extends Activity implements OnClickListener {

	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tutorial);
		
		setupShowTut();
	}
	
	
	public void setupShowTut() {
		
		LinearLayout startLayout = (LinearLayout) findViewById(R.id.LinearLayout1);
		startLayout.setOrientation(LinearLayout.VERTICAL);
		
		@SuppressWarnings("deprecation")
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
			     LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		layoutParams.setMargins(0, 15, 0, 15);  //left, top, right, bottom
		
		
		TextView view = new TextView(getBaseContext());
		view.setTextColor(Color.BLACK);
		view.setText("Anleitung");
		view.setGravity(Gravity.CENTER);
		view.setTextSize(70);
		
		TextView text = new TextView(getBaseContext());
		text.setTextColor(Color.BLACK);
		text.setText("Ziel des Spiels ist es, die unten aufgeführten Karten durch Beantworten der Fragen, die mit einem Klick " +
				"drauf erscheinen, in die richtige historisch-zeitliche Reihenfolge durch Schieben der Karten einzusortieren.\n" +
				"Als Starthilfe ist die erste Karte vorgegeben, welche das Bestimmen der Jahreszahlen unterstützen soll.\n" +
                "Sobald eine Karte falsch einsortiert wird, erscheint die Jahreszahl der gelegten Karte. Eins von den drei "+
				"Leben geht dabei verloren. Durch Anklicken auf die falsche Karte wird eine neue Karte 'in die Hand' gegeben.\n"+
				"Die Anzahl jeder richtig eingesetzten Karte wird summiert und gibt den Punktestand an. Bei jeder falsch " + 
				"gelegten gibt es keinen Punktabzug.");
		//text.setGravity(Gravity.CENTER);
		text.setTextSize(32);
		
		Button button = new Button(getBaseContext());
		button.setTextColor(Color.BLACK);
		button.setText("Zurück");
		button.setTextSize(40);
		button.setOnClickListener(this);
		button.setId(1);
		button.setBackgroundResource(R.drawable.gradientrot);
		button.setGravity(Gravity.CENTER);
		
		startLayout.addView(view, layoutParams);
		startLayout.addView(text);
		startLayout.addView(button, layoutParams);
		
	}
	
	@Override
	public void onClick(View v) {
		
		startActivity(new Intent(this, MenueActivity.class));
		
	}	

}
