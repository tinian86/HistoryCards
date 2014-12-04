package de.daisy.kleinesspiel;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class App extends Activity implements OnClickListener {
	
	Button btnClick;
	ProgressBar prog;
	int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        
        btnClick = (Button) findViewById(R.id.button1);
        btnClick.setOnClickListener(this);
        
        prog = (ProgressBar) findViewById(R.id.progressBar1);
        prog.setMax(100);
        
        
       btnClick.setOnClickListener(this);
       
    }

	@Override
	public void onClick(View v) {
		
		if(v == btnClick){
			i = i + 4;
			if(i >= 100){
				AlertDialog alert = new AlertDialog.Builder(this).create();
				alert.setMessage("Du hast die Leiste voll gekriegt!");
				alert.show();
				
				i = 0;
			}
			
			prog.setProgress(i);
		}
		
	}
}
