package com.example.activitytest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("SecondActivity", this.toString());
		Log.d("SecondActivity", "Task id is " + getTaskId());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.second_layout);
		
		String data = getIntent().getStringExtra("extra_data");
		Log.d("SeconActivity", data);
		
		Button btn1 = (Button)findViewById(R.id.button_2);
		
		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(SecondActivity.this, "You clicked button 2", Toast.LENGTH_SHORT).show();
			}
		});
		
		Button btn_exit = (Button)findViewById(R.id.button_exit);
		
		btn_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ExitActivity();
			}
			
		});

	
		Button btn_back = (Button)findViewById(R.id.button_back);
		
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(SecondActivity.this, FirstActivity.class);
				startActivity(intent);
			}
			
		});

		Button btn_3rd = (Button)findViewById(R.id.button_3rd);
		
		btn_3rd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
				startActivity(intent);
			}
			
		});
	}

	@Override
	public void onBackPressed() {
		ExitActivity();
	}
	
	private void ExitActivity() {
		Intent intent = new Intent();
		intent.putExtra("data_return", "Hello From SecondActivity");
		setResult(RESULT_OK, intent);
		finish();
	}
	
	public static void actionStart(Context context, String data) {
		Intent intent = new Intent(context, SecondActivity.class);
		intent.putExtra("extra_data", data);
		context.startActivity(intent);
	}
}
