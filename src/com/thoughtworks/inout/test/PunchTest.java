package com.thoughtworks.inout.test;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.thoughtworks.inout.PunchActivity;
import com.thoughtworks.inout.PunchType;

public class PunchTest extends ActivityInstrumentationTestCase2<PunchActivity> {

	private Solo solo;

	public PunchTest() {
		super(PunchActivity.class);
	}
	
	@Override
	public void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testFirstTimeUser() {
		assertTrue(solo.searchButton(PunchType.IN.getValue()));
		solo.clickOnButton(PunchType.IN.getValue());
		assertTrue(solo.searchButton(PunchType.OUT.getValue()));
		solo.clickOnButton(PunchType.OUT.getValue());
		assertTrue(solo.searchButton(PunchType.IN.getValue()));
	}
	
	@Override
	public void tearDown() {
		solo.finishOpenedActivities();
	}

}
