package de.historyalpha;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
//import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HighscoreAcitvity extends Activity implements OnClickListener {

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

	
	public void screenSize(){
		DisplayMetrics metrics = getResources().getDisplayMetrics(); 
		 
		double ySize = metrics.heightPixels / metrics.ydpi;
		double xSize = metrics.widthPixels / metrics.xdpi;
		 
		// Bildschirmgrösse in Zoll
		double screenSize = Math.sqrt(xSize * xSize + ySize * ySize);
	}
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

			
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.acitvity_highscore);
		Intent intent = getIntent();
	 
		String newHighscoreExtraKey = getResources().getString(R.string.new_highscore_extra_key);

		// if opend from finished game
		if (intent.hasExtra(newHighscoreExtraKey)) {
				
			int newHighscore = intent.getIntExtra(newHighscoreExtraKey, -1);
			String newName = intent.getStringExtra("Name");
			if (newHighscore > 0) {
				// valid new highscore value received - insert
				setScore(newHighscore, newName);
			}
			}initiate();
		}
	

	
	public void initiate(){
		// show highscore list
		List<HighscoreEntry> highscore = getHighscore();
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.highscoreList);
		layout.removeAllViews();
		layout.setOrientation(LinearLayout.VERTICAL);
		
		@SuppressWarnings("deprecation")
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
			     LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		layoutParams.setMargins(0, 20, 0, 15);
		
		TextView view = new TextView(getBaseContext());
		view.setTextColor(Color.BLACK);
		view.setText("Punktestand");
		view.setGravity(Gravity.CENTER);
		view.setTextSize(70);
		
		layout.addView(view, layoutParams);
		
		
		int count = 0;
		for(HighscoreEntry entry : highscore) {
			LinearLayout highscoreLine = new LinearLayout(this);
			highscoreLine.setOrientation(LinearLayout.HORIZONTAL);
			highscoreLine.setGravity(Gravity.FILL_HORIZONTAL);
			
			TextView nameView = new TextView(this);
			TextView scoreView = new TextView(this);
			scoreView.setGravity(Gravity.RIGHT);
			scoreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			
			nameView.setText(++count + ". " + entry.playerName + "....");
			scoreView.setText("...." + entry.playerScore);
			nameView.setTextSize(35);
			scoreView.setTextSize(35);
			nameView.setTextColor(android.graphics.Color.BLACK);
			scoreView.setTextColor(android.graphics.Color.BLACK);
			
			highscoreLine.addView(nameView);
			highscoreLine.addView(scoreView);
			
			layout.addView(highscoreLine);
		}
		
		Button button = new Button(getBaseContext());
		button.setTextColor(Color.BLACK);
		button.setText("Menü");
		button.setTextSize(40);
		button.setOnClickListener(this);
		button.setId(1);
		button.setBackgroundResource(R.drawable.gradientblau);
		//button.setGravity(Gravity.CENTER);
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		int bottomheight = (height / 7) - 25;
		int bottomwidth = width / 3;
		
		button.setMinimumHeight(bottomheight);
		button.setMaxHeight(bottomheight);
		button.setMinimumWidth(bottomwidth);
		button.setMaxWidth(bottomwidth);
		
		layout.addView(button, layoutParams);
	}

	// insert new Highscoore, if any, in the right slot
	private void setScore(int score, String newName) {
		List<HighscoreEntry> highscore = getHighscore();
		List<HighscoreEntry> newHighscore = new LinkedList<HighscoreEntry>();
		
		SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME, 0);
		String playerName = newName;
		boolean inserted = false;
				
		for(HighscoreEntry entry : highscore) {
			if(!inserted && score > entry.playerScore) {
				
				
				newHighscore.add(new HighscoreEntry(playerName, score));
				inserted = true;
			}
			
			newHighscore.add(entry);
		}

		
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
	
	
	
	public void onClick(View v) {
		
		startActivity(new Intent(this, MenueActivity.class));
		
	}	

	
}
