package com.example.broadcastbestpractice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText accountEdit;
	private EditText passwordEdit;
	private CheckBox rememberPass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		accountEdit = (EditText) findViewById(R.id.account);
		passwordEdit = (EditText) findViewById(R.id.password);
		rememberPass = (CheckBox) findViewById(R.id.remember_pass);
		
		Button login = (Button) findViewById(R.id.login);
		
		final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

		if (pref.getBoolean("remember_password", false)) {
			accountEdit.setText(pref.getString("account", ""));
			passwordEdit.setText(pref.getString("password", ""));
			rememberPass.setChecked(true);
		}
		
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String account = accountEdit.getText().toString();
				String password = passwordEdit.getText().toString();
				if (account.equals("admin") && password.equals("123456")) {
					
					SharedPreferences.Editor editor = pref.edit();
					if (rememberPass.isChecked()) {
						editor.putBoolean("remember_password", true);
						editor.putString("account", account);
						editor.putString("password", password);
					} else {
						editor.clear();
					}
					editor.commit();
					
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(LoginActivity.this, "account or password is invalid",
						Toast.LENGTH_SHORT).show();
				}
			}
			
		});
	}
	
	
}
