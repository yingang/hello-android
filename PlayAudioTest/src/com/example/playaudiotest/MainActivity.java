package com.example.playaudiotest;

import java.io.File;

import com.example.playaudiotest.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	private Button play;
	private Button pause;
	private Button stop;
	
	private MediaPlayer mediaPlayer = new MediaPlayer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		play = (Button) findViewById(R.id.play);
		pause = (Button) findViewById(R.id.pause);
		stop = (Button) findViewById(R.id.stop);
		
		play.setOnClickListener(this);
		pause.setOnClickListener(this);
		stop.setOnClickListener(this);
		
		initMediaPlayer();
	}

	private void initMediaPlayer() {
		Log.i("MainActivity", Environment.getExternalStorageState().toString());
		Log.i("MainActivity", Environment.getExternalStorageDirectory().toString());
		
		try {
			File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");

			if (file.exists()) {
				Log.i("MainActivity", file.getAbsolutePath());
				Log.i("MainActivity", file.getCanonicalPath());
				Log.i("MainActivity", file.getPath());
			}
			
			mediaPlayer.setDataSource(file.getPath());
			mediaPlayer.prepare();
		} catch (Exception e) {
			Toast.makeText(this, "Oops...", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play:
			if (!mediaPlayer.isPlaying()) {
				mediaPlayer.start();
			}
			break;
		case R.id.pause:
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
			}
			break;
		case R.id.stop:
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.reset();
				initMediaPlayer();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}
	
	
}
