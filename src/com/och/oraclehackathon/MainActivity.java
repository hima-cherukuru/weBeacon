package com.och.oraclehackathon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


//
//overlay
public class MainActivity extends Activity {

	ListView lv;
	
	
	
	@Override
	public void onNewIntent(Intent intent){
	    Bundle extras = intent.getExtras();
	    if(extras != null){
	    	final String type = extras.getString("type");
	    	final String mac = extras.getString("mac");
	
	    if (type!=null && type.equalsIgnoreCase("CONF_ROOM")){
	    		Intent i = new Intent(MainActivity.this, Dialog.class);
			   i.putExtra("mac", mac);
			   i.putExtra("type", type);
			   i.putExtra("buildingNum", extras.getString("buildingNum"));
			   i.putExtra("description", extras.getString("description"));
			   i.putExtra("floorNum", extras.getString("floorNum"));
			   i.putExtra("roomNum", extras.getString("roomNum"));
			   
			   
			    
			   
			   startActivity(i);
			   
	    	} else if (type!=null && type.equalsIgnoreCase("FLEX_OFFICE")){
	    		Intent i = new Intent(MainActivity.this, Dialog.class);
				   i.putExtra("mac", mac);
				   i.putExtra("type", type);
				   i.putExtra("mac", mac);
				   i.putExtra("type", type);
				   i.putExtra("buildingNum", extras.getString("buildingNum"));
				   i.putExtra("description", extras.getString("description"));
				   i.putExtra("floorNum", extras.getString("floorNum"));
				   i.putExtra("roomNum", extras.getString("roomNum"));
				   
				   startActivity(i);
	    	} else if (type!=null && type.equalsIgnoreCase("ZIP_CUBE")){
	    		Intent i = new Intent(MainActivity.this, Dialog.class);
				   i.putExtra("mac", mac);
				   i.putExtra("type", type);
				   i.putExtra("mac", mac);
				   i.putExtra("type", type);
				   i.putExtra("buildingNum", extras.getString("buildingNum"));
				   i.putExtra("description", extras.getString("description"));
				   i.putExtra("floorNum", extras.getString("floorNum"));
				   i.putExtra("roomNum", extras.getString("roomNum"));
				   
				   startActivity(i);
	    	} else if (type!=null && type.equalsIgnoreCase("PARKING_LOT")){
	    		setContentView(R.layout.activity_main);

	            lv = (ListView) findViewById(R.id.featureList);
	       	        
	            lv.setOnItemClickListener(new OnItemClickListener() {
	    			@Override
	    			public void onItemClick(AdapterView<?> arg0, View arg1,
	    					int arg2, long arg3) {
	    				   
	    				   Intent i = new Intent(MainActivity.this, Listing.class);
	    				   i.putExtra("mac", mac);
	    				   // get type from click
	    				   String data=(String)arg0.getItemAtPosition(arg2);
	    				   String type="";
	    				   if (data.equalsIgnoreCase("Conference room"))
	    					type = "CONF_ROOM";
	    				   else if (data.equalsIgnoreCase("Flex Cube"))
	    				    type = "FLEX_OFFICE";
	    				   else if (data.equalsIgnoreCase("Zip room")) 
	    				   	type = "ZIP_CUBE";
	    				   else if (data.equalsIgnoreCase("Parking"))
	    				    type = "PARKING_SLOT";
	    				   i.putExtra("type", type);
	    				   startActivity(i); 
	    			}

	            });
	    	} else if (type!=null && type.equalsIgnoreCase("PARKING_SLOT")){
	    		Intent i = new Intent(MainActivity.this, Dialog.class);
				   i.putExtra("mac", mac);
				   i.putExtra("type", type);
				   i.putExtra("mac", mac);
				   i.putExtra("type", type);
				   i.putExtra("buildingNum", extras.getString("buildingNum"));
				   i.putExtra("description", extras.getString("description"));
				   i.putExtra("floorNum", extras.getString("floorNum"));
				   i.putExtra("roomNum", extras.getString("roomNum"));
				   
				   startActivity(i);
			
	    	} else {
	    			setContentView(R.layout.activity_main);

		            lv = (ListView) findViewById(R.id.featureList);
		       	        
		            lv.setOnItemClickListener(new OnItemClickListener() {
		    			@Override
		    			public void onItemClick(AdapterView<?> arg0, View arg1,
		    					int arg2, long arg3) {
		    				   
		    				   Intent i = new Intent(MainActivity.this, Listing.class);
		    				   i.putExtra("mac", mac);
		    				   // get type from click
		    				   String data=(String)arg0.getItemAtPosition(arg2);
		    				   String type="";
		    				   if (data.equalsIgnoreCase("Conference room"))
		    					type = "CONF_ROOM";
		    				   else if (data.equalsIgnoreCase("Flex Cube"))
		    				    type = "FLEX_OFFICE";
		    				   else if (data.equalsIgnoreCase("Zip room")) 
		    				   	type = "ZIP_CUBE";
		    				   else if (data.equalsIgnoreCase("Parking"))
		    				    type = "PARKING_SLOT";
		    				   i.putExtra("type", type);
		    				   startActivity(i); 
		    			}

		            });

		    	}
	    	
//	            setContentView(R.layout.activity_main);
	            // extract the extra-data in the Notification
	            //String msg = extras.getString("mac");
	            //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	            //txtView = (TextView) findViewById(R.id.txtMessage);
	            //txtView.setText(msg);
	        
	    }else {
    			setContentView(R.layout.activity_main);

	            lv = (ListView) findViewById(R.id.featureList);
	       	        
	            lv.setOnItemClickListener(new OnItemClickListener() {
	    			@Override
	    			public void onItemClick(AdapterView<?> arg0, View arg1,
	    					int arg2, long arg3) {
	    				   
	    				   Intent i = new Intent(MainActivity.this, Listing.class);
	    				   
	    				   // get type from click
	    				   String data=(String)arg0.getItemAtPosition(arg2);
	    				   String type="";
	    				   if (data.equalsIgnoreCase("Conference room"))
	    					type = "CONF_ROOM";
	    				   else if (data.equalsIgnoreCase("Flex Cube"))
	    				    type = "FLEX_OFFICE";
	    				   else if (data.equalsIgnoreCase("Zip room")) 
	    				   	type = "ZIP_CUBE";
	    				   else if (data.equalsIgnoreCase("Parking"))
	    				    type = "PARKING_SLOT";
	    				   i.putExtra("type", type);
	    				   startActivity(i); 
	    			}

	            });

	    	}

	     
	    
	    


	}
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
