package com.example.broadcastbestpractice;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

public class ForceOfflineReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Warning");
		builder.setMessage("You are forced to be offline. please try to login again.");
		builder.setCancelable(false);
		builder.setPositiveButton("OK", 
				new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ActivityCollector.finishAll();
						Intent intent = new Intent(context, LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
					}
				});
		
		AlertDialog dlg = builder.create();
		dlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dlg.show();
	}

}
