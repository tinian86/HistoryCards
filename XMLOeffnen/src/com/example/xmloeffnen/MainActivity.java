package com.example.xmloeffnen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button btn;
	TextView tvXml;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.button1);

	    btn.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            // Load XML for parsing.
	            AssetManager assetManager = getAssets();
	            InputStream inputStream = null;
	            try {
	                inputStream = assetManager.open("temp.xml");
	            } catch (IOException e) {
	                Log.e("tag", e.getMessage());
	            }

	            String s = readTextFile(inputStream);
	            TextView tv = (TextView)findViewById(R.id.textView1);
	            tv.setText(s);
	        }
	    });
	}

	
	private String readTextFile(InputStream inputStream) {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    byte buf[] = new byte[1024];
	    int len;
	    try {
	        while ((len = inputStream.read(buf)) != -1) {
	            outputStream.write(buf, 0, len);
	        }
	        outputStream.close();
	        inputStream.close();
	    } catch (IOException e) {

	    }
	    return outputStream.toString();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
}
