package com.example.notificationtest;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

public class NotificationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_layout);
		
		NotificationManager mgr = (NotificationManager)
			getSystemService(NOTIFICATION_SERVICE);
		mgr.cancel(1);
	}	
}
