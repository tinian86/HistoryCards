package de.historyalpha;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MenueActivity extends Activity implements OnClickListener {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menue);
		
		setupStartPage();
				
	}
	
	public void setupStartPage() {
		
		LinearLayout startLayout = (LinearLayout) findViewById(R.id.menueStart);
		startLayout.setOrientation(LinearLayout.VERTICAL);
		
		@SuppressWarnings("deprecation")
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
			     LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		layoutParams.setMargins(0, 0, 0, 20);  //left, top, right, bottom
		
		@SuppressWarnings("deprecation")
		LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
			     LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		layoutParams2.setMargins(0, 0, 0, 35);  //left, top, right, bottom
		
		
		TextView view = new TextView(getBaseContext());
		Button button1 = new Button(getBaseContext());
		Button button2 = new Button(getBaseContext());
		Button button3 = new Button(getBaseContext());
		Button button4 = new Button(getBaseContext());
		
		view.setTextColor(Color.BLACK);
		button1.setTextColor(Color.BLACK);
		button2.setTextColor(Color.BLACK);
		button3.setTextColor(Color.BLACK);
		button4.setTextColor(Color.BLACK);
		
		view.setText("History Cards");
		button1.setText("Einzelspieler");
		button2.setText("Mehrspieler");
		button3.setText("Anleitung");
		button4.setText("Punktestand");

		view.setGravity(Gravity.CENTER);
		view.setTextSize(120);
		button1.setTextSize(50);
		button2.setTextSize(50);
		button3.setTextSize(50);
		button4.setTextSize(50);
		
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		
		button1.setId(1);
		button2.setId(2);
		button3.setId(3);
		button4.setId(4);
		
		button1.setBackgroundResource(R.drawable.gradientgelb);
		button2.setBackgroundResource(R.drawable.gradientgruen);
		button3.setBackgroundResource(R.drawable.gradientblau);
		button4.setBackgroundResource(R.drawable.gradientlila);
		
		startLayout.addView(view, layoutParams2);
		startLayout.addView(button1, layoutParams);
		startLayout.addView(button2, layoutParams);
		startLayout.addView(button3, layoutParams);
		startLayout.addView(button4, layoutParams);
		
	}
	
	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		case 1:
			startActivity(new Intent(this, GameActivity.class));
			break;
		case 2:
			startActivity(new Intent(this, MultiActivity.class));
			break;
		case 3:
			startActivity(new Intent(this, TutorialActivity.class));
			break;
		case 4:
			startActivity(new Intent(this, HighscoreAcitvity.class));		
		}
		
	}	

}
