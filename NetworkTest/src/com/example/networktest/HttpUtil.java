package com.example.networktest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	
	public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpURLConnection conn = null;
				URL url;
				try {
					url = new URL(address);
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(8000);
					conn.setReadTimeout(8000);
					conn.setDoInput(true);
					conn.setDoOutput(true);
					InputStream in = conn.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder resp = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						resp.append(line);
					}
					if (listener != null) {
						listener.onFinish(resp.toString());
					}
				} catch (Exception e) {
					if (listener != null) {
						listener.onError(e);
					}
				} finally {
					if (conn != null) {
						conn.disconnect();
					}
				}
			}
			
		}).start();
	}
}
