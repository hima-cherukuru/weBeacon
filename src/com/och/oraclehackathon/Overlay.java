package com.och.oraclehackathon;

import java.util.Random;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Overlay extends Activity {
	public static final String OP600_101A = "OP600-CONF-101";
	public static final String OP600_130 = "OP600-CONF-130";
	public static final String OP600_127 = "OP600-FLEX-127";
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	PointF startPoint = new PointF();
	PointF midPoint = new PointF();
	float oldDist = 1f;
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;
	ImageView floorImage;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String itemName = bundle.getString("name");
        Log.e("","ItemName from overlay is "+itemName);
        
        int num = new Random().nextInt(3);
        
        if(num==0)
        {
        	setContentView(R.layout.overlay_101a);
         	floorImage = (ImageView) findViewById(R.id.map101a);
        }
        else if(num==1)
        {
        	setContentView(R.layout.overlay_130);
        	floorImage = (ImageView) findViewById(R.id.map130);
        }
        else if(num==2)
        {
        	setContentView(R.layout.overlay_127);
        	floorImage = (ImageView) findViewById(R.id.map127);
        }
  
	floorImage.setOnTouchListener(new View.OnTouchListener() {
		
	@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			ImageView view = (ImageView) v;
			switch (event.getAction() & MotionEvent.ACTION_MASK)
			{
				case MotionEvent.ACTION_DOWN:
					savedMatrix.set(matrix);
					startPoint.set(event.getX(), event.getY());
					mode = DRAG;
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					oldDist = spacing(event);
					if (oldDist > 10f)
					{
						savedMatrix.set(matrix);
						midPoint(midPoint, event);
						mode = ZOOM;
					} 
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
					mode = NONE;
					break;
				case MotionEvent.ACTION_MOVE:
					if (mode == DRAG)
					{
						matrix.set(savedMatrix);
						matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
					} else if (mode == ZOOM) 
						{
							float newDist = spacing(event);
							if (newDist > 10f)
							{
								matrix.set(savedMatrix);
								float scale = newDist / oldDist;
								matrix.postScale(scale, scale, midPoint.x, midPoint.y);
							}
						}
					break;
			}
			
			view.setImageMatrix(matrix);
			ImageView pinView = (ImageView) findViewById(R.id.pin);
			
			return true;
		}
		
		private float spacing(MotionEvent event) 
		{
			float x = event.getX(0) - event.getX(1);
			float y = event.getY(0) - event.getY(1);
			return FloatMath.sqrt(x * x + y * y);
		}
		
		private void midPoint(PointF point, MotionEvent event) 
		{
			float x = event.getX(0) + event.getX(1);
			float y = event.getY(0) + event.getY(1);
			point.set(x / 2, y / 2); 
		}
	});
}
}
