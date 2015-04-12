package com.example.databasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private MyDatabaseHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 3);
		
		Button btn_create = (Button) findViewById(R.id.create_database);
		btn_create.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dbHelper.getWritableDatabase();
			}
			
		});
		
		Button btn_add = (Button) findViewById(R.id.add_data);
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				
				ContentValues values = new ContentValues();
				
				values.put("name",  "The Da Vinci Code");
				values.put("author", "Dan Brown");
				values.put("pages", 454);
				values.put("price", 16.96);
				db.insert("Book", null, values);
				
				values.clear();
				values.put("name",  "The Lost Symbol");
				values.put("author", "Dan Brown");
				values.put("pages", 510);
				values.put("price", 19.95);
				db.insert("Book", null, values);				
			}
			
		});
		
		Button btn_update = (Button) findViewById(R.id.update_data);
		btn_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				
				ContentValues values = new ContentValues();
				values.put("price", 9.99);
				
				db.update("Book", values, "name = ?", new String[] { "The Da Vinci Code"} );
			}
			
		});
		
		Button btn_delete = (Button) findViewById(R.id.delete_data);
		btn_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				db.delete("Book", "pages > ?", new String[] { "500"} );
			}
			
		});
		
		Button btn_query = (Button) findViewById(R.id.query_data);
		btn_query.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				
				Cursor cursor = db.query("Book", null, null, null, null, null, null);
				
				if (cursor.moveToFirst()) {
					do {
						Log.d("MainActivity", "book name is " + 
							cursor.getString(cursor.getColumnIndex("name")));
						Log.d("MainActivity", "book author is " +
							cursor.getString(cursor.getColumnIndex("author")));
						Log.d("MainActivity", "book pages is " +
							cursor.getInt(cursor.getColumnIndex("pages")));
						Log.d("MainActivity", "book price is " +
							cursor.getDouble(cursor.getColumnIndex("price")));
					} while (cursor.moveToNext());
				}
				
				cursor.close();
			}
			
		});
		
		Button btn_replace = (Button) findViewById(R.id.replace_data);
		btn_replace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				
				Cursor cursor = db.query("Book", null, null, null, null, null, null);
				
				db.beginTransaction();
				
				try {
					db.delete("Book", null, null);
					
					if (true) {
						throw new NullPointerException();
					}
					
					ContentValues values = new ContentValues();
					
					values.put("name",  "Game of Thrones");
					values.put("author", "George Martin");
					values.put("pages", 720);
					values.put("price", 20.85);
					db.insert("Book", null, values);
					
					db.setTransactionSuccessful();

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					db.endTransaction();
				}
			}
			
		});
	}

}
