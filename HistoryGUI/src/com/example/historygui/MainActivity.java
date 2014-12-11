package com.example.historygui;




import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.ClipData;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.button1).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.button2).setOnTouchListener(new MyTouchListener());
//        findViewById(R.id.button3).setOnTouchListener(new MyTouchListener());
//        findViewById(R.id.button4).setOnTouchListener(new MyTouchListener());
//        findViewById(R.id.button5).setOnTouchListener(new MyTouchListener());
        
        findViewById(R.id.bottomButton1).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.bottomButton2).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.bottomButton3).setOnTouchListener(new MyTouchListener());
        
        findViewById(R.id.grid1).setOnDragListener(new MyDragListener());
        findViewById(R.id.grid2).setOnDragListener(new MyDragListener());
        findViewById(R.id.grid3).setOnDragListener(new MyDragListener());
        findViewById(R.id.grid4).setOnDragListener(new MyDragListener());
        findViewById(R.id.grid5).setOnDragListener(new MyDragListener());
        findViewById(R.id.grid6).setOnDragListener(new MyDragListener());
        findViewById(R.id.grid7).setOnDragListener(new MyDragListener());
        findViewById(R.id.grid8).setOnDragListener(new MyDragListener());
        
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        
       
        
        HorizontalScrollView hv = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
        hv.setMinimumHeight(height/2);
        
        
        
       
        
       // Log.d("bla", String.valueOf(gl.getHeight()));
        
       findViewById(R.id.button1).setMinimumHeight(height/3);
       findViewById(R.id.button2).setMinimumHeight(height/3);
       //findViewById(R.id.button3).setMinimumHeight(height/3);
       //findViewById(R.id.grid1).setMinimumHeight(height/2);
//        findViewById(R.id.grid2).setLayoutParams(params);
//        findViewById(R.id.grid3).setLayoutParams(params);
//        findViewById(R.id.grid4).setLayoutParams(params);
//        findViewById(R.id.grid5).setLayoutParams(params);
//        findViewById(R.id.grid6).setLayoutParams(params);
//        findViewById(R.id.grid7).setLayoutParams(params);
//        findViewById(R.id.grid8).setLayoutParams(params);
        
        
        
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
    
    private final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
          if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
          } else {
            return false;
          }
        }
      }

      class MyDragListener implements OnDragListener {
        Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
          int action = event.getAction();
          switch (event.getAction()) {
          case DragEvent.ACTION_DRAG_STARTED:
            // do nothing
            break;
          case DragEvent.ACTION_DRAG_ENTERED:
            v.setBackgroundDrawable(enterShape);
            break;
          case DragEvent.ACTION_DRAG_EXITED:
            v.setBackgroundDrawable(normalShape);
            break;
          case DragEvent.ACTION_DROP:
            // Dropped, reassign View to ViewGroup
            View view = (View) event.getLocalState();
            ViewGroup owner = (ViewGroup) view.getParent();
            owner.removeView(view);
            LayoutParams lp = new LayoutParams(90, 90);
            view.setLayoutParams(lp);
            LinearLayout container = (LinearLayout) v;
            
            if (container.getChildCount() > 0){
            	
            	int number = Integer.parseInt(container.getTag().toString());
            	View b = container.getChildAt(1);
            	number++;
            	LinearLayout nextContainer = (LinearLayout) ((View) container.getParent()).findViewWithTag(String.valueOf(number));
            	nextContainer.removeAllViews();
        		nextContainer.addView(b);
            	

            	
            }
            container.removeAllViews();
            container.addView(view);
            view.setVisibility(View.VISIBLE);
            break;
          case DragEvent.ACTION_DRAG_ENDED:
            v.setBackgroundDrawable(normalShape);
          default:
            break;
          }
          return true;
        }
        
        public void replaceItem() {
		}
        
        
      }
    
}
