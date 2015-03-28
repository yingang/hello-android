package com.example.uiwidgettest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private EditText editText;
	
	private ImageView imageView;
	
	private ProgressBar progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn = (Button) findViewById(R.id.button);
		btn.setOnClickListener(this);
		
		editText = (EditText) findViewById(R.id.edit_text);
		imageView = (ImageView) findViewById(R.id.image_view);
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.button:
//			Toast.makeText(this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
			imageView.setImageResource(R.drawable.jelly_bean);
			
			progressBar.setProgress(progressBar.getProgress() + 10);
	
			AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
			dialog.setTitle("Alert Dialog");
			dialog.setMessage(editText.getText().toString());
			dialog.setCancelable(false);
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								
				@Override
				public void onClick(DialogInterface dialog, int which) {
					popupProgressDialog();
				}
			});
			dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			dialog.show();
			break;
		default:
		}
	}
	
	private void popupProgressDialog() {
		ProgressDialog dialog = new ProgressDialog(MainActivity.this);
		dialog.setTitle("This is a ProgressDialog");
		dialog.setMessage("Loading");
		dialog.setCancelable(true);
		dialog.show();
	}
}
