package com.remindme;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

public class NotificationIntent extends Activity {

		@SuppressWarnings("deprecation")
		public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int notifID;
		NotificationManager nm;
		// Get the notification ID  
		notifID = getIntent().getExtras().getInt("NotifID");

		// Create the pending intent that will reference the intent to go when clicked 
		Intent i = new Intent(this, MainActivity.class);
		i.putExtra("NotifID", notifID);
		PendingIntent detailsIntent = PendingIntent.getActivity(this, 0, i, 0);

		// Setup notification 
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notif = new Notification(R.drawable.ic_launcher,
				"Food has expired!", System.currentTimeMillis());

		// Hides status bar notification when clicked
		notif.flags |= Notification.FLAG_AUTO_CANCEL;
		
		CharSequence from = "RemindMe";
		CharSequence message = "Food has expired!";
		notif.setLatestEventInfo(this, from, message, detailsIntent);

		// ---100ms delay, vibrate for 250ms, pause for 100 ms and
		// then vibrate for 500ms---
		notif.vibrate = new long[] { 100, 250, 100, 500 };
		nm.notify(notifID, notif);
		
		// Added sound to notification
		try {
	        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
	        //this.setVolumeControlStream(AudioManager.STREAM_NOTIFICATION);
	        r.setStreamType(AudioManager.STREAM_NOTIFICATION);
	        r.play();
	    } catch (Exception e) {}
		
		// Destroy activity 
		finish();
	}
}
