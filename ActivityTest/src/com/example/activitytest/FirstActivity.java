package com.example.activitytest;

import android.app.Activity;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import android.widget.Button;
import android.widget.Toast;



public class FirstActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first_layout);
		
		Button btn1 = (Button)findViewById(R.id.button_1);
		
		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(FirstActivity.this, "You clicked button 1", Toast.LENGTH_SHORT).show();
			}
		});
		
		Button btn_next = (Button)findViewById(R.id.button_next);
		
		btn_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
				Intent intent = new Intent("com.example.activitytest.ACTION_START");
				intent.addCategory("com.example.activitytest.MY_CATEGORY");
				startActivity(intent);
			}
		});

		Button btn_douban = (Button)findViewById(R.id.button_douban);
		
		btn_douban.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://www.douban.com"));
				startActivity(intent);
			}
		});
}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_item:
			Toast.makeText(FirstActivity.this, "You clicked Add", Toast.LENGTH_SHORT).show();
			break;
		case R.id.remove_item:
			Toast.makeText(FirstActivity.this, "You clicked Remove", Toast.LENGTH_SHORT).show();
			break;
		case R.id.exit_item:
			finish();
			break;
		default:				
		}
		return true;
	}
}
