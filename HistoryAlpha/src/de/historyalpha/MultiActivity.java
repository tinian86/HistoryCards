package de.historyalpha;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MultiActivity extends Activity implements OnClickListener {

	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_multi_player);
		
		setupShowMulti();
	}
	
	
	public void setupShowMulti() {
		
		LinearLayout startLayout = (LinearLayout) findViewById(R.id.LinearLayout1);
		startLayout.setOrientation(LinearLayout.VERTICAL);
		
		@SuppressWarnings("deprecation")
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
			     LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		layoutParams.setMargins(20, 10, 20, 60);
		
		/*
		LinearLayout startLayout2 = (LinearLayout) findViewById(R.id.LinearLayout2);
		startLayout.setOrientation(LinearLayout.HORIZONTAL);
		*/
		
		@SuppressWarnings("deprecation")
		LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
			     LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		layoutParams2.setMargins(20, 10, 20, 15);
		
		
		TextView view = new TextView(getBaseContext());
		view.setTextColor(Color.BLACK);
		view.setText("Anzahl der Spieler?");
		view.setGravity(Gravity.CENTER);
		view.setTextSize(70);
		
		startLayout.addView(view, layoutParams);
		
		
		Button button1 = new Button(getBaseContext());
		Button button2 = new Button(getBaseContext());
		Button button3 = new Button(getBaseContext());
		
		button1.setTextColor(Color.BLACK);
		button2.setTextColor(Color.BLACK);
		button3.setTextColor(Color.BLACK);
		
		button1.setText("2");
		button2.setText("3");
		button3.setText("4");
		
		button1.setGravity(Gravity.CENTER);
		button2.setGravity(Gravity.CENTER);
		button3.setGravity(Gravity.CENTER);
		
		button1.setTextSize(50);
		button2.setTextSize(50);
		button3.setTextSize(50);
		
		button1.setId(1);
		button2.setId(2);
		button3.setId(3);
		
		button1.setBackgroundResource(R.drawable.gradientgruen);
		button2.setBackgroundResource(R.drawable.gradientgruen);
		button3.setBackgroundResource(R.drawable.gradientgruen);
		
		startLayout.addView(button1, layoutParams2);
		startLayout.addView(button2, layoutParams2);
		startLayout.addView(button3, layoutParams);
		
		
		Button button4 = new Button(getBaseContext());
		button4.setTextColor(Color.BLACK);
		button4.setText("Zurück");
		button4.setGravity(Gravity.CENTER);
		button4.setTextSize(40);
		
		//button1.setOnClickListener(this);
		//button2.setOnClickListener(this);
		//button3.setOnClickListener(this);
		
		button4.setOnClickListener(this);
		button4.setId(4);
		button4.setBackgroundResource(R.drawable.gradientrot);
		
		startLayout.addView(button4, layoutParams2);
		
	}
	
	@Override
	public void onClick(View v) {
		
		startActivity(new Intent(this, MenueActivity.class));
		
	}	

}
