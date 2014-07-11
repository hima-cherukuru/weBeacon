package com.och.oraclehackathon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.och.network.RestClient;
import com.och.network.RestClient.RequestMethod;

public class Dialog extends Activity {
	private Context currentContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentContext = getApplicationContext();
		setContentView(R.layout.dialog);
		Bundle bundle = getIntent().getExtras();
        
		String type = bundle.getString("type");
		final String mac = bundle.getString("mac");
		String buildingNum = bundle.getString("buildingNum");
		String description = bundle.getString("description");
		String floorNum = bundle.getString("floorNum");
		String roomNum = bundle.getString("roomNum");
		
		new AlertDialog.Builder(this)
		.setTitle("Confirm Booking")
		.setMessage("Would you like to book room: "+description+"?")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

		    public void onClick(DialogInterface dialog, int whichButton) {
		    	new BookResourceTask().execute("http://rws66277fwks:7101/Client/resources/apps/markUsed/"+mac);
	            
		    }})
		 .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

			    public void onClick(DialogInterface dialog, int whichButton) {
			    	finish();
			    }})
			 .show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dialog, menu);
		return true;
	}
	
	 private class BookResourceTask extends AsyncTask<String, Integer, String> {
		 
		    protected String doInBackground(String... urls) {
		    	String response = null;
		        int count = urls.length;

		        for (int i = 0; i < count; i++) {
		            try {

		                RestClient client = new RestClient(urls[i]);

		                try {
		                    client.Execute(RequestMethod.GET);
		                } catch (Exception e) {
		                    e.printStackTrace();
		                }

		                response = client.getResponse();
		                Log.i("response",response );
		                
		            } catch(Exception e) {
		            	Toast.makeText(currentContext, "Cant connect to server", Toast.LENGTH_LONG).show();
		            	System.out.println("something wrong");
		            }
		        }
		        return response;
		    }

		    protected void onProgressUpdate(Integer... progress) {

		    }

		    protected void onPostExecute(String response) {

		        if (response!=null){
		        	handleTaskOutput(response);
		        	
		        }
		        	
		    }

			

		}
	 
	 private void handleTaskOutput(String response) {
		 Toast.makeText(Dialog.this, response, Toast.LENGTH_SHORT).show();
		 final Handler handler = new Handler();
		 handler.postDelayed(new Runnable() {
		     @Override
		     public void run() {
		    	 finish();
		     }
		 }, 1000);
		}

}
