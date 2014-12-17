package de.historyalpha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MenueActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menue);
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
