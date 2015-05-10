package com.example.androidthreadtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	public static final int UPDATE_TEXT = 1;
	
	private Button changeText;
	private TextView text;
	
	private Handler handler = new Handler() {
		
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE_TEXT:
				text.setText("Nice to meet you!");
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		changeText = (Button) findViewById(R.id.change_text);
		text = (TextView) findViewById(R.id.text);
		
		changeText.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_text:
			new Thread(new Runnable() {

				@Override
				public void run() {
//					text.setText("Nice to meet you!");
					Message msg = new Message();
					msg.what = UPDATE_TEXT;
					handler.sendMessage(msg);
				}
				
			}).start();
			break;
		default:
			break;
		}
	}
}
