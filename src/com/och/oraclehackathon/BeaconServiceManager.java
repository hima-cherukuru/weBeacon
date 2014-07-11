package com.och.oraclehackathon;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.BeaconManager.MonitoringListener;
import com.google.gson.Gson;
import com.och.bean.LocationListing;
import com.och.bean.MapImageLocation;
import com.och.network.RestClient;
import com.och.network.RestClient.RequestMethod;


public class BeaconServiceManager {
	private static String prevmac="";
	private static final int NOTIFICATION_ID = 123;
	 private static BeaconManager beaconManager;
	 private static NotificationManager notificationManager;
	 public static final String EXTRAS_BEACON = "extrasBeacon";
	 private static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
	 private static Beacon beacon;
	 private static final String PARKING_LOT_FEATURES = "FIND PARKING";
	 private static final String PARKING_FEATURES = "BOOK SPOT";
	 private static final String BUILDING_FEATURES = "FIND FLEX OFFICE, FIND ZIP CUBE, FIND CONFERENCE ROOM";
	 private static final String FLEX_OFFICE_FEATURES = "BOOK FLEX OFFICE";
	 private static final String ZIP_CUBE_FEATURES = "BOOK ZIP CUBE";
	 private static final String CONFERENCE_ROOM_FEATURES = "BOOK ZIP CUBE";
	 
	 
//	 private static final Region ALL_ESTIMOTE_BEACONS = new Region("regionId",
//	   ESTIMOTE_PROXIMITY_UUID, null, null);
	 private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);


	 private static Context currentContext;

	 // Create everything we need to monitor the beacons
	 public static void Create(NotificationManager notificationMngr,
	   final Context context, final Intent i) {
	  try {
	   notificationManager = notificationMngr;
	   currentContext = context;

	// Configure BeaconManager.
	    beaconManager = new BeaconManager(currentContext);
	    beaconManager.setRangingListener(new BeaconManager.RangingListener() {
	      @Override
	      public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
	    	  if (beacons!=null && beacons.size()>0){
		    	  beacon = beacons.get(0);
		    	  if (!prevmac.equalsIgnoreCase(beacon.getMacAddress())){
		    		  try {
				            new BeaconDataLoadTask().execute("http://rws66277fwks:7101/Client/resources/apps/getBeaconInfo/"+beacon.getMacAddress());
				            Log.e("GetBeaconData", "Received:"+beacon.getMacAddress());
			    		} catch(Exception e) {
				        	Log.e("GetBeaconData", e.getMessage());
				        }
		    		  prevmac = beacon.getMacAddress();
		    	  }
	      }
	    	  //for (Beacon beacon: beacons) {
		    		// send details to server and get type etc
		    		
	    	  //}
	      }
	    });

//	    
//	    
//	    
//	   
//	   
//	   // Create a beacon manager
//	   beaconManager = new BeaconManager(currentContext);
//
//	   // We want the beacons heartbeat to be set at one second.
//	   //beaconManager.setBackgroundScanPeriod(TimeUnit.MILLISECONDS.toMillis(1),
//	   //  0);
//
//	   // Method called when a beacon gets...
//	   beaconManager.setMonitoringListener(new MonitoringListener() {
//	    // ... close to us.
//	    @Override
//	    public void onEnteredRegion(Region region, List<Beacon> beacons) {
//	    	
//	    	for (Beacon beacon: beacons) {
//	    		// send details to server and get type etc
//	    		try {
//		            new BeaconDataLoadTask().execute("http://rws66277fwks:7101/Client/resources/apps/getBeaconInfo/"+beacon.getMacAddress());
//		        } catch(Exception e) {
//		        	Log.e("GetBeaconData", e.getMessage());
//		        }
//	    		
//	    		
//	    	}
//	    	
//	    	
//	    	
//	    }
//
//	    // ... far away from us.
//	    @Override
//	    public void onExitedRegion(Region region) {
//	     postNotificationIntent("Estimote testing",
//	       "I have lost my estimote !!!", "");
//	    }
//	   });
	   
	   // Connect to the beacon manager...
	   beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
	    @Override
	    public void onServiceReady() {
	     try {
	      // ... and start the monitoring
	      //beaconManager.startMonitoring(ALL_ESTIMOTE_BEACONS);
	      beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
	     } catch (RemoteException e) {
	          Toast.makeText(currentContext, "Cannot start ranging, something terrible happened",
	              Toast.LENGTH_LONG).show();
	          Log.e("BeaconServiceManager", "Cannot start ranging", e);
	        }
	    }
	   });
	  } catch (Exception e) {
		  Log.e("", e.getMessage());
	  }
	 }

	 // Pops a notification in the task bar
	 public static void postNotificationIntent(MapImageLocation location) {
	  //i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		 String features = getFeatures(location.getType());
		 
	
		 Intent notificationIntent = new Intent(currentContext, SplashScreen.class);
		 notificationIntent.putExtra("available", location.getavailable());
		 notificationIntent.putExtra("buildingNum", location.getBuildingNum());
		 notificationIntent.putExtra("description", location.getDescription());
		 notificationIntent.putExtra("floorNum", location.getFloorNum());
		 notificationIntent.putExtra("latLang", location.getLatlng());
		 notificationIntent.putExtra("name", location.getName());
		 notificationIntent.putExtra("roomNum", location.getRoomNum());
		 notificationIntent.putExtra("type", location.getType());
		 notificationIntent.putExtra("mac", beacon.getMacAddress());
		 
		 
		 notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 PendingIntent pendingNotificationIntent = PendingIntent.getActivity(currentContext,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
	
		 Notification notification = new Notification.Builder(currentContext)
		    .setSmallIcon(R.drawable.ic_launcher).setContentTitle(location.getDescription())
		    .setContentText(features).setAutoCancel(true)
		    .setContentIntent(pendingNotificationIntent).build();
		  notification.defaults |= Notification.DEFAULT_SOUND;
		  notification.defaults |= Notification.DEFAULT_LIGHTS;
		  notification.flags |= Notification.FLAG_AUTO_CANCEL;
		  //notification.setLatestEventInfo(getApplicationContext(), notificationTitle, notificationMessage, pendingNotificationIntent);
		        
		  notificationManager.notify(NOTIFICATION_ID, notification);
	  
	 }

	 // Stop beacons monitoring, and closes the service
	 public static void stop() {
	  try {
	   beaconManager.stopMonitoring(ALL_ESTIMOTE_BEACONS_REGION);
	   beaconManager.disconnect();
	  } catch (Exception e) {
		  
	  }
	 }

	 private static class BeaconDataLoadTask extends AsyncTask<String, Integer, MapImageLocation> {
		 
		    protected MapImageLocation doInBackground(String... urls) {
		    	MapImageLocation list = null;
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
		                Log.i("json",json );
		                list = new Gson().fromJson(json, MapImageLocation.class);

		            } catch(Exception e) {
		            	Toast.makeText(currentContext, "Cant connect to server", Toast.LENGTH_LONG).show();
		            	System.out.println("something wrong");
		            }
		        }
		        return list;
		    }

		    protected void onProgressUpdate(Integer... progress) {

		    }

		    protected void onPostExecute(MapImageLocation location) {

		        if (location!=null){
		        	handleTaskOutput(location);
		        	Log.i("",location.getType());
		        }
		        	
		    }

		}

	public static void handleTaskOutput(MapImageLocation location) {
		postNotificationIntent(location);
		//postNotificationIntent(location.getDescription(),features, features);
	}
	
	
	private static String getFeatures (String type) {
			if (type!=null&&type.length()>0){
			
				if (type.equalsIgnoreCase("BUILDING")){
					return BUILDING_FEATURES;
				} else if (type.equalsIgnoreCase("CONF_ROOM")){
					return CONFERENCE_ROOM_FEATURES;
				} else if (type.equalsIgnoreCase("FLEX_OFFICE")){
					return FLEX_OFFICE_FEATURES;
				} else if (type.equalsIgnoreCase("ZIP_CUBE")){
					return ZIP_CUBE_FEATURES;
				} else if (type.equalsIgnoreCase("PARKING_LOT")){
					return PARKING_LOT_FEATURES;
				} else if (type.equalsIgnoreCase("PARKING_SLOT")){
					return PARKING_FEATURES;
				}
			}
			return "";
				
		}
	}
	
	
	



