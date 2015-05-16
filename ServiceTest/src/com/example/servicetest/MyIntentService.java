package com.example.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class MyIntentService extends IntentService {

	public MyIntentService() {
		super("MyIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		logAndToast("[IntentService] Thread id is " + Thread.currentThread().getId());
	}

	private void logAndToast(String msg) {
		toast(msg);
		Log.d("MyIntentService", msg);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		logAndToast("[IntentService] onDestroy executed, thread id is "
				+ Thread.currentThread().getId());
	}
	
    private void toast(final String msg) {  
        Handler handler = new Handler(Looper.getMainLooper());  
        handler.post(new Runnable() {  
            @Override  
            public void run() {  
                Toast.makeText(MyIntentService.this, msg, Toast.LENGTH_SHORT).show();  
            }  
        });  
    }  
}
