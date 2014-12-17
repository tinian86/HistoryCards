package de.historyalpha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MenueActivity extends Activity implements OnClickListener {
	
	//GUI_Elemente
	Button btn_single;
	Button btn_multi;
	Button btn_tut;
	Button btn_score;
	
	//German/English_Button on First_Menue
	ImageButton img_btn_de;
	ImageButton img_btn_en;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startseite);
		
		//GUI_Elemente
		btn_single = (Button) findViewById(R.id.btn_single);
		btn_multi = (Button) findViewById(R.id.btn_multi);
		btn_tut = (Button) findViewById(R.id.btn_tutorial);
		btn_score = (Button) findViewById(R.id.btn_score);
			
		//German_Button on First_Menue
		img_btn_de = (ImageButton) findViewById(R.id.img_de);
		img_btn_de.setOnClickListener(this);
		
		//English_Button on First_Menue
		img_btn_en = (ImageButton) findViewById(R.id.img_en);
		img_btn_en.setOnClickListener(this);
		
		
//		Button singlePlayerButton = (Button) findViewById(R.id.btn_single);
//		singlePlayerButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				Intent nextScreen = new Intent(getApplicationContext(),
//						GameActivity.class);
//				startActivity(nextScreen);
//			}
//		});
	}
	
	//Click on German
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.img_en:
			img_btn_en.setVisibility(v.INVISIBLE);
			img_btn_de.setVisibility(v.VISIBLE);
			btn_single.setText("Single Player");
			btn_multi.setText("Multi Player");
			btn_tut.setText("Tutorial");
			btn_score.setText("Highscore");
		case R.id.img_de:
			img_btn_de.setVisibility(v.INVISIBLE);
			img_btn_en.setVisibility(v.VISIBLE);
			//btn_single.setText("Einzelspieler");
			//btn_multi.setText("Mehrspieler");
			//btn_tut.setText("Anleitung");
			//btn_score.setText("Punktestand");
		}
		
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
	
	
	public void startGame(View view){
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
		//Hier koennen ueber getRandomCards() neue Karten aus dem Stapel-Array geholt werden
		Log.d("MenueActivity", "Stapel" + CardQuestion.getRandomCards().toString());
	}
	
	public void showScore(View view){
		Intent intent = new Intent(this, HighscoreAcitvity.class);
		startActivity(intent);
	}
	
	public void closeApp(View view){
		super.onStop();
	}

	
	

}
