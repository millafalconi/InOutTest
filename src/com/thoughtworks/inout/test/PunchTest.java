package com.thoughtworks.inout.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.jayway.android.robotium.solo.Solo;
import com.thoughtworks.inout.PunchActivity;
import com.thoughtworks.inout.R;

public class PunchTest extends ActivityInstrumentationTestCase2<PunchActivity> {

	private Solo solo;
	
	public PunchTest() {
		super(PunchActivity.class);
	}
	
	@Override
	public void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testConfirmDialog() {
		Button punchButton = (Button)solo.getView(R.id.punch_button);
		String punchText = punchButton.getText().toString();
		solo.clickOnButton(punchText);
		String okButton = getActivity().getString(R.string.ok_button);
		assertTrue(solo.searchButton(okButton));
		solo.clickOnButton(okButton);
		assertFalse(punchText.equals(punchButton.getText().toString()));
	}
	
	public void testCancelDialog() {
		Button punchButton = (Button)solo.getView(R.id.punch_button);
		String punchText = punchButton.getText().toString();
		solo.clickOnButton(punchText);
		String cancelButton = getActivity().getString(R.string.cancel_button);
		assertTrue(solo.searchButton(cancelButton));
		solo.clickOnButton(cancelButton);
		assertTrue(punchText.equals(punchButton.getText().toString()));
	}
	
	@Override
	public void tearDown() {
		solo.finishOpenedActivities();
	}

}
