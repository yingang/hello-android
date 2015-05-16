package com.example.servicebestpractice;

import java.util.Date;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;


public class LongRunningService extends Service {
	
	private PendingIntent pi;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				logAndToast("executed at " + new Date().toString());
			}
			
		}).start();
		
		AlarmManager mgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		int tenSeconds = 10 * 1000;
		long triggerAtTime = SystemClock.elapsedRealtime() + tenSeconds;
		Intent i = new Intent(this, AlarmReceiver.class);
		pi = PendingIntent.getBroadcast(this,  0, i, 0);
		mgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

		return super.onStartCommand(i, flags, startId);
	}

	private void logAndToast(String msg) {
		toast(msg);
		Log.d("LongRunningService", msg);
	}
	
    private void toast(final String msg) {  
        Handler handler = new Handler(Looper.getMainLooper());  
        handler.post(new Runnable() {  
            @Override  
            public void run() {  
                Toast.makeText(LongRunningService.this, msg, Toast.LENGTH_SHORT).show();  
            }  
        });  
    }

	@Override
	public void onDestroy() {
		AlarmManager mgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		mgr.cancel(pi);
		
		super.onDestroy();
	}
    
}
