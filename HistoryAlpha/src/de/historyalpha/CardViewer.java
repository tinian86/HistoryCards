package de.historyalpha;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

//TODO: Kann bzw. hat noch Fehler! nach der Einbindung die genauen Größen einsetzen. Sollte aber grob stimmen.
	
public class CardViewer extends Activity {
	
	private int width;
	private int height;
	
	//die Buttons auf der Seite definieren
	public Button card1;
	public Button card2;
	public Button card3;
	
	//die Layouts auf der Seite definieren
	public RelativeLayout relative;
	public LinearLayout linear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_card_viewer);
		
		//die Buttons mit der Seite verbinden
		card1 = (Button) findViewById(R.id.card1);
		card2 = (Button) findViewById(R.id.card2);
		card3 = (Button) findViewById(R.id.card3);
		
		//die Layouts mit der Seite verbinden
		relative = (RelativeLayout) findViewById(R.id.relativeViewer);
		linear = (LinearLayout) findViewById(R.id.linearViewer);
		
	}
	
	//erstellt die seite mit den 3 Karten, szs. den CardViewer
	public void setupCardViewer(){
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		
		//die Breite durch 3 teilen
		int bottomwidth = width / 3;
		//die Höhe
		int bottomheight = height;
		
		card1.setMinimumWidth(bottomwidth);
		card1.setMaxWidth(bottomwidth);
		card1.setMinimumHeight(bottomheight);
		card1.setMaxHeight(bottomheight);
		
		card2.setMinimumWidth(bottomwidth);
		card2.setMaxWidth(bottomwidth);
		card2.setMinimumHeight(bottomheight);
		card2.setMaxHeight(bottomheight);
		
		card3.setMinimumWidth(bottomwidth);
		card3.setMaxWidth(bottomwidth);
		card3.setMinimumHeight(bottomheight);
		card3.setMaxHeight(bottomheight);
		
		linear.addView(card1);
		linear.addView(card2);
		linear.addView(card3);		
		
	}
	
}
