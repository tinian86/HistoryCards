package de.daisy.navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	
	Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btn = (Button) findViewById(R.id.haupt_btn);
        btn.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View v) {
		
		startActivity(new Intent(this, Seite2.class));
		
	}
}
