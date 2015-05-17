package com.example.networktest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
	private Button sendRequestForXML;
	private Button sendRequestForXMLWithSAX;
	private Button sendRequestForJSON;
	private Button sendRequestForJSONWithGSON;
		
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

		sendRequestForXML = (Button) findViewById(R.id.send_request_for_xml);
		sendRequestForXML.setOnClickListener(this);

		sendRequestForXMLWithSAX = (Button) findViewById(R.id.send_request_for_xml_with_sax);
		sendRequestForXMLWithSAX.setOnClickListener(this);
		
		sendRequestForJSON = (Button) findViewById(R.id.send_request_for_json);
		sendRequestForJSONWithGSON = (Button) findViewById(R.id.send_request_for_json_with_gson);
		sendRequestForJSON.setOnClickListener(this);
		sendRequestForJSONWithGSON.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send_request:
			sendRequestWithHttpURLConnection();
			break;
		case R.id.send_request_http_client:
			sendRequestWithHttpClient();
			break;
		case R.id.send_request_for_xml:
			sendRequestForXML(false);
			break;
		case R.id.send_request_for_xml_with_sax:
			sendRequestForXML(true);
			break;
		case R.id.send_request_for_json:
			sendRequestForJSON(false);
			break;
		case R.id.send_request_for_json_with_gson:
			sendRequestForJSON(true);
			break;
		default:
			break;
		}
	}

	private void sendRequestWithHttpURLConnection() {
		HttpUtil.sendHttpRequest("http://www.bing.com", new HttpCallbackListener() {

			@Override
			public void onFinish(String response) {
				Message msg = new Message();
				msg.what = SHOW_RESPONSE;
				msg.obj = response;
				handler.sendMessage(msg);
			}

			@Override
			public void onError(Exception e) {
				e.printStackTrace();
			}
			
		});
		
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				HttpURLConnection conn = null;
//				try {
//					URL url = new URL("http://www.bing.com");
//					conn = (HttpURLConnection) url.openConnection();
//					conn.setRequestMethod("GET");
//					conn.setConnectTimeout(8000);
//					InputStream in = conn.getInputStream();
//					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//					StringBuilder resp = new StringBuilder();
//					String line;
//					while ((line = reader.readLine()) != null) {
//						resp.append(line);
//					}
//					Message msg = new Message();
//					msg.what = SHOW_RESPONSE;
//					msg.obj = resp.toString();
//					handler.sendMessage(msg);
//				} catch (Exception e) {
//					e.printStackTrace();
//				} finally {
//					if (conn != null) {
//						conn.disconnect();
//					}
//				}
//				
//			}
//			
//		}).start();
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
	
	private void sendRequestForXML(final boolean useSax) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet("http://192.168.1.93/get_data.xml");
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = httpResponse.getEntity();
						String resp = EntityUtils.toString(entity, "utf-8");
						
						Message msg = new Message();
						msg.what = SHOW_RESPONSE;
						
						if (useSax)
							msg.obj = parseXMLwithSAX(resp).toString();
						else
							msg.obj = parseXML(resp).toString();
						
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}).start();
	}
	
	private String parseXML(String xmlData) {
		StringBuilder builder = new StringBuilder();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xmlData));
			int eventType = parser.getEventType();
			String id = "";
			String name = "";
			String version = "";
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String nodeName = parser.getName();
				switch (eventType) {
				case XmlPullParser.START_TAG: {
					if ("id".equals(nodeName)) {
						id = parser.nextText();
					} else if ("name".equals(nodeName)) {
						name = parser.nextText();
					} else if ("version".equals(nodeName)) {
						version = parser.nextText();
					}
					break;
				}
				case XmlPullParser.END_TAG: {
					if ("app".equals(nodeName)) {
						builder.append("id is " + id + "\n");
						builder.append("name is " + name + "\n");
						builder.append("version is " + version + "\n\n");
					}
					break;
				}
				default:
					break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	private String parseXMLwithSAX(String xmlData) {
		String result = "error!";
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			XMLReader reader = factory.newSAXParser().getXMLReader();
			ContentHandler handler = new ContentHandler();
			reader.setContentHandler(handler);
			reader.parse(new InputSource(new StringReader(xmlData)));
			result = handler.getResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private void sendRequestForJSON(final boolean useGSON) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet("http://192.168.1.93/get_data.json");
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = httpResponse.getEntity();
						String resp = EntityUtils.toString(entity, "utf-8");
						
						Message msg = new Message();
						msg.what = SHOW_RESPONSE;
						
						if (useGSON)
							msg.obj = parseJSONwithGSON(resp).toString();
						else
							msg.obj = parseJSON(resp).toString();
						
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}).start();
	}

	private String parseJSON(String jsonData) {
		StringBuilder builder = new StringBuilder();
		try {
			JSONArray array = new JSONArray(jsonData);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				builder.append("id is " + obj.getString("id") + "\n");
				builder.append("name is " + obj.getString("name") + "\n");
				builder.append("version is " + obj.getString("version") + "\n\n");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	private String parseJSONwithGSON(String jsonData) {
		StringBuilder builder = new StringBuilder();
		try {
			Gson gson = new Gson();
			List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>(){}.getType());
			for (App app : appList) {
				builder.append("id is " + app.getId() + "\n");
				builder.append("name is " + app.getName() + "\n");
				builder.append("version is " + app.getVersion() + "\n\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
}
