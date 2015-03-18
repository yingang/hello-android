package com.example.activitytest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.second_layout);
		
		Button btn1 = (Button)findViewById(R.id.button_2);
		
		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(SecondActivity.this, "You clicked button 2", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
