package com.example.broadcastbestpractice.test;

import com.example.broadcastbestpractice.ActivityCollector;
import com.example.broadcastbestpractice.LoginActivity;

import android.test.AndroidTestCase;

public class ActivityCollectorTest extends AndroidTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testAddActivity() {
		assertEquals(0, ActivityCollector.activities.size());
		LoginActivity loginActivity = new LoginActivity();
		ActivityCollector.addActivity(loginActivity);
		assertEquals(1, ActivityCollector.activities.size());
		ActivityCollector.addActivity(loginActivity);
		assertEquals(1, ActivityCollector.activities.size());
	}
}
