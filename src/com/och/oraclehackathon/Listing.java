package com.och.oraclehackathon;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.och.bean.LocationListing;
import com.och.bean.MapImageLocation;
import com.och.network.RestClient;
import com.och.network.RestClient.RequestMethod;
import com.och.util.ListAdapter;


public class Listing  extends Activity {

		LocationManager lm;
	 
	    ArrayList locationArray = null;
	    ListAdapter locationAdapter;
	    LocationListing list;
	 
	    ListView lv;
	    TextView loadingText;
	 
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.list);
	        
	        Bundle bundle = getIntent().getExtras();
	        
	        String type = bundle.getString("type");
	        String mac =  bundle.getString("mac");
	        
	        if (mac!=null && type!=null) {
		        lv = (ListView) findViewById(R.id.list_nearme);
		 
		        locationArray = new ArrayList();
		        locationAdapter = new ListAdapter(Listing.this, R.layout.list_item, locationArray);
		 
		        lv.setTextFilterEnabled(true);
		        lv.setAdapter(locationAdapter);
		        
		        lv.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						
							//String itemName = locationAdapter.getItem(arg2).toString();
							
						    MapImageLocation item = (MapImageLocation) arg0.getAdapter().getItem(arg2);
							System.out.println("Item is "+item.getName());
							Log.e("Item name in Listing is ",item.getName());
							//log.c("Item name is "+itemName);
							Intent i = new Intent(Listing.this, Overlay.class);
							i.putExtra("name",item.toString());
						   						   
		           		   startActivity(i); 
					}
	
		        });
		 
		        try {
		            new ListingLoadTask().execute("http://rws66277fwks:7101/Client/resources/apps/getLocationInfo/"+mac+"/"+type);
		        } catch(Exception e) {}
	    } else {
	    	Toast.makeText(getApplicationContext(), "You are not currently close to a beacon. Please enter office campus and try again.", Toast.LENGTH_LONG).show();
	    }
	    
	    
	    }
	 
	    private class ListingLoadTask extends AsyncTask<String, Integer, LocationListing> {
	 
	        protected LocationListing doInBackground(String... urls) {
	            LocationListing list = null;
	            int count = urls.length;
	 
	            for (int i = 0; i < count; i++) {
	                try {
	 
	                    RestClient client = new RestClient(urls[i]);
	 
	                    try {
	                        client.Execute(RequestMethod.GET);
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	 
	                    String json = client.getResponse();
	 
	                    list = new Gson().fromJson(json, LocationListing.class);
	 
	                } catch(Exception e) {
	                	System.out.println("something wrong");
	                }
	            }
	            return list;
	        }
	 
	        protected void onProgressUpdate(Integer... progress) {
	 
	        }
	 
	        protected void onPostExecute(LocationListing loclist) {
	 
	            if (loclist!=null)
		        	for(MapImageLocation lm : loclist.getLocations())
		            {
		                locationArray.add(lm);
		            }
	            locationAdapter.notifyDataSetChanged();
	        }
	 
	    }
	}
