package com.example.guiprototype;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class HighscoreActivity extends Activity {

	public static String PREFS_NAME = "game";
	public static String NEW_HIGHSCORE_EXTRA_KEY = "newHighscore";

	private static String HIGHSCORE_NAME_KEY = "highscoreNamekey";
	private static String HIGHSCORE_VALUE_KEY = "highscoreValuekey";
	private static String PLAYER_NAME = "player";

	
	private class HighscoreEntry {
		public String playerName;
		public int playerScore;
		public HighscoreEntry(String playerName, int score) {
			super();
			this.playerName = playerName;
			this.playerScore = score;
		}
	}

	// SharedPreferences prefs;
	public int score = 0;
	int sc;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_highscore);
		Intent intent = getIntent();
 
		String newHighscoreExtraKey = getResources().getString(R.string.new_highscore_extra_key);

		// if opend from finished game
		if (intent.hasExtra(newHighscoreExtraKey)) {
			// insert new score into highscore list
			// show "enter name" popup
			
//			LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
//					.getSystemService(LAYOUT_INFLATER_SERVICE);
//			View popupView = layoutInflater.inflate(R.layout.enter_name, null);
//			final PopupWindow popupWindow = new PopupWindow(popupView,
//					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//			final EditText enterPlayerName = (EditText) popupView.findViewById(R.id.editText1);
//			Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
//			btnDismiss.setOnClickListener(new Button.OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					
//					setPlayerName(enterPlayerName.getText().toString());
//					
//					
//					popupWindow.dismiss();
//					initiate();
//				}
//			});
////			popupWindow.showAtLocation(findViewById(R.id.textView123), 1, 30, 30);
////			popupWindow.showAtLocation(getCurrentFocus(), 1, 50, 50);
			
			int newHighscore = intent.getIntExtra(newHighscoreExtraKey, -1);
			if (newHighscore > 0) {
				// valid new highscore value received - insert
				setScore(newHighscore);
			}
		}initiate();
	}

	public void initiate(){
		// show highscore list
		List<HighscoreEntry> highscore = getHighscore();
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.highscoreList);
		layout.removeAllViews();
		
		int count = 0;
		for(HighscoreEntry entry : highscore) {
			LinearLayout highscoreLine = new LinearLayout(this);
			highscoreLine.setOrientation(LinearLayout.HORIZONTAL);
			highscoreLine.setGravity(Gravity.FILL_HORIZONTAL);
			
			TextView nameView = new TextView(this);
			TextView scoreView = new TextView(this);
			scoreView.setGravity(Gravity.RIGHT);
			scoreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			
			nameView.setText(++count + ". " + entry.playerName);
			scoreView.setText("" + entry.playerScore);
			
			highscoreLine.addView(nameView);
			highscoreLine.addView(scoreView);
			
			layout.addView(highscoreLine);
		}
	}

	// insert new Highscoore, if any, in the right slot
	private void setScore(int score) {
		List<HighscoreEntry> highscore = getHighscore();
		List<HighscoreEntry> newHighscore = new LinkedList<HighscoreEntry>();
		
		SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME, 0);
		String playerName = prefs.getString(PLAYER_NAME, "abc");
		boolean inserted = false;
				
		for(HighscoreEntry entry : highscore) {
			if(!inserted && score > entry.playerScore) {
				
				
				newHighscore.add(new HighscoreEntry(playerName, score));
				inserted = true;
			}
			
			newHighscore.add(entry);
		}
//		Editor editor = prefs.edit();
//		editor.putString(PLAYER_NAME, "leer");
//		editor.commit();
		
		setHighscore(newHighscore);
	
	}
	
	
	
	private List<HighscoreEntry> getHighscore() {
		List<HighscoreEntry> highscore = new LinkedList<HighscoreEntry>();
		SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME, 0);
		
		for (int i = 0; i < 10; i++) {
			highscore.add(new HighscoreEntry(prefs.getString(HIGHSCORE_NAME_KEY + i, "empty"), prefs.getInt(HIGHSCORE_VALUE_KEY + i, 0)));
		}
		
		return highscore;
	}
	
	// Save the 10 highest entrys 
	private void setHighscore(List<HighscoreEntry> highscore) {
		// assure 10 highscore values
		highscore = highscore.subList(0, 10);
		
		Editor prefEditor = getSharedPreferences(PREFS_NAME, 0).edit();
		int i=0;
		for (HighscoreEntry entry : highscore) {
			prefEditor.putString(HIGHSCORE_NAME_KEY + i, entry.playerName);
			prefEditor.putInt(HIGHSCORE_VALUE_KEY + i, entry.playerScore);
			
			++i;
		}
		
		prefEditor.commit();
	}


//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.highscoore, menu);
//		return true;
//	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, com.example.mindenfinden.MenueActivity.class);
		startActivity(i);
	}

}
