package com.example.sharedpreferencetest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn = (Button) findViewById(R.id.save_data);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
				editor.putString("name", "YG");
				editor.putInt("age", 36);
				editor.putBoolean("married", true);
				editor.commit();
			}
			
		});
		
		Button btn2 = (Button) findViewById(R.id.restore_data);
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
				Log.d("MainActivity", "name is " + pref.getString("name", ""));
				Log.d("MainActivity", "age is " + pref.getInt("age", 0));
				Log.d("MainActivity", "marries is " + pref.getBoolean("married", false));
			}
			
		});
	}

}
