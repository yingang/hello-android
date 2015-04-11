package com.example.broadcasttest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private IntentFilter intentFilter;
	
	private NetworkChangeReceiver networkChangeReceiver;
	
	private LocalReceiver localReceiver;
	
	private LocalBroadcastManager localBroadcastManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		intentFilter = new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

		networkChangeReceiver = new NetworkChangeReceiver();
		registerReceiver(networkChangeReceiver, intentFilter);
		
		Button btn = (Button) findViewById(R.id.send);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
				sendBroadcast(intent);
			}
			
		});

		Button btn2 = (Button) findViewById(R.id.send_order);
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
				sendOrderedBroadcast(intent, null);
			}
			
		});	
		
		localBroadcastManager = LocalBroadcastManager.getInstance(this);

		Button btn3 = (Button) findViewById(R.id.send_local);
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
				localBroadcastManager.sendBroadcast(intent);
			}
			
		});
		
		intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
		localReceiver = new LocalReceiver();
		localBroadcastManager.registerReceiver(localReceiver, intentFilter);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(networkChangeReceiver);
		super.onDestroy();
	}

	class NetworkChangeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			
			ConnectivityManager conn_mgr = (ConnectivityManager)
				getSystemService(Context.CONNECTIVITY_SERVICE);
			
			NetworkInfo netInfo = conn_mgr.getActiveNetworkInfo();
			
			if (netInfo != null && netInfo.isAvailable()) {
				Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
			}
			
		}
		
	}
	
	class LocalReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(context, "received local broadcast", Toast.LENGTH_SHORT).show();
		}
		
	}
}
