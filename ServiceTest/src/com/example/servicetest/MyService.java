package com.example.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
	
	DownloadBinder binder = new DownloadBinder();
	
	class DownloadBinder extends Binder {
		public void startDownload() {
			logAndToast("startDownload executed");
		}
		
		public void stopDownload() {
			logAndToast("stopDownload executed");
		}

		public void getProgress() {
			logAndToast("getProgress executed");
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		super.onCreate();
		
		Notification notification = new Notification(R.drawable.ic_launcher,
				"Notification comes", System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this,  0, notificationIntent, 0);
		notification.setLatestEventInfo(this, "This is title", "This is content", pendingIntent);
		startForeground(1, notification);
		
		logAndToast("onCreate executed");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		logAndToast("onStartCommand executed");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		logAndToast("onDestroy executed");
	}
	
	private void logAndToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
		Log.d("MyService", msg);
	}

}
