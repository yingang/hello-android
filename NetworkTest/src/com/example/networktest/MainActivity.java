package com.example.networktest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	public static final int SHOW_RESPONSE = 0;
	
	private Button sendRequest;
	private Button sendRequestWithHTTPClient;
	
	private TextView responseText;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
	
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_RESPONSE:
				String response = (String) msg.obj;
				responseText.setText(response);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sendRequest = (Button) findViewById(R.id.send_request);
		responseText = (TextView) findViewById(R.id.response_text);
		
		sendRequest.setOnClickListener(this);

		sendRequestWithHTTPClient = (Button) findViewById(R.id.send_request_http_client);
		sendRequestWithHTTPClient.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.send_request) {
			sendRequestWithHttpURLConnection();
		} else if (v.getId() == R.id.send_request_http_client) {
			sendRequestWithHttpClient();
		}
	}

	private void sendRequestWithHttpURLConnection() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpURLConnection conn = null;
				try {
					URL url = new URL("http://www.bing.com");
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(8000);
					InputStream in = conn.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder resp = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						resp.append(line);
					}
					Message msg = new Message();
					msg.what = SHOW_RESPONSE;
					msg.obj = resp.toString();
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (conn != null) {
						conn.disconnect();
					}
				}
				
			}
			
		}).start();
	}
	
	private void sendRequestWithHttpClient() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet("http://www.bing.com");
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = httpResponse.getEntity();
						String resp = EntityUtils.toString(entity, "utf-8");
						Message msg = new Message();
						msg.what = SHOW_RESPONSE;
						msg.obj = resp.toString();
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}).start();
	}
}
