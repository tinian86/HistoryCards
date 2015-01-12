package de.historyalpha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class TutorialActivity extends Activity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tutorial);
	}
	
	
	public void goBack(View view){
		Intent i = new Intent(this, MenueActivity.class);
		startActivity(i);
	}
}
