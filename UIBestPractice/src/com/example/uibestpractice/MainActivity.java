package com.example.uibestpractice;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private List<Msg> msgList = new ArrayList<Msg>();
	
	private ListView msgListView;
	private TextView inputText;
	private MsgAdapter msgAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initMsgs();
		
		msgAdapter = new MsgAdapter(MainActivity.this, R.layout.msg_item, msgList);
		
		msgListView = (ListView) findViewById(R.id.msg_list_view);
		msgListView.setAdapter(msgAdapter);
		
		inputText = (TextView) findViewById(R.id.input_text);
		
		Button btn = (Button) findViewById(R.id.send);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				String content = inputText.getText().toString();
				if (content.length() == 0)
					return;
				
				Msg msg = new Msg(content, Msg.TYPE_SENT);
				msgList.add(msg);
				msgAdapter.notifyDataSetChanged();
				msgListView.setSelection(msgList.size());
				inputText.setText("");
			}
			
		});
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
	
	private void initMsgs() {
		Msg msg1 = new Msg("Hello guy.", Msg.TYPE_RECEIVED);
		msgList.add(msg1);
		Msg msg2 = new Msg("Hello. Who is that?", Msg.TYPE_SENT);
		msgList.add(msg2);
		Msg msg3 = new Msg("This is Tom. Nice talking to you. ", Msg.TYPE_RECEIVED);
		msgList.add(msg3);
	}
}
