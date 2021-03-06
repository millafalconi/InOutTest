package com.thoughtworks.inout.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.jayway.android.robotium.solo.Solo;
import com.thoughtworks.inout.Punch;
import com.thoughtworks.inout.PunchActivity;
import com.thoughtworks.inout.R;
import com.thoughtworks.inout.clock.TimeMachine;
import com.thoughtworks.inout.db.SQLLiteTimeCardDAO;
import com.thoughtworks.inout.db.TimeCardDAO;
import com.thoughtworks.inout.exception.DataRetrieveException;

public class PunchTest extends ActivityInstrumentationTestCase2<PunchActivity> {

	private Solo solo;
	
	public PunchTest() {
		super(PunchActivity.class);
	}
	
	@Override
	public void setUp() {
		TimeCardDAO dao = new SQLLiteTimeCardDAO(getActivity());
		dao.clearDatabase();
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
	
	public void testDatePickerInitialValue() {
		Button punchButton = (Button)solo.getView(R.id.punch_button);
		String punchText = punchButton.getText().toString();
		Date now = TimeMachine.freeze();
		solo.clickOnButton(punchText);
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(now);
		
		DatePicker datePicker = (DatePicker)solo.getView(PunchActivity.DATE_PICKER_ID);
		assertEquals(datePicker.getYear(), cal.get(Calendar.YEAR));
		assertEquals(datePicker.getMonth(), cal.get(Calendar.MONTH));
		assertEquals(datePicker.getDayOfMonth(), cal.get(Calendar.DAY_OF_MONTH));
		
		TimePicker timePicker = (TimePicker)solo.getView(PunchActivity.TIME_PICKER_ID);
		assertEquals((Integer)cal.get(Calendar.HOUR_OF_DAY), timePicker.getCurrentHour());
		assertEquals((Integer)cal.get(Calendar.MINUTE), timePicker.getCurrentMinute());
	}
	
	public void testDateInsert() {
		Button punchButton = (Button)solo.getView(R.id.punch_button);
		String punchText = punchButton.getText().toString();
		Date now = TimeMachine.freeze();
		solo.clickOnButton(punchText);
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, 1);

		DatePicker datePicker = (DatePicker)solo.getView(PunchActivity.DATE_PICKER_ID);		
		TimePicker timePicker = (TimePicker)solo.getView(PunchActivity.TIME_PICKER_ID);
		
		solo.setDatePicker(datePicker, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
		solo.setTimePicker(timePicker, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
		String okButton = getActivity().getString(R.string.ok_button);
		solo.clickOnButton(okButton);
		
		TimeCardDAO tcDao = new SQLLiteTimeCardDAO(getActivity());
		try {
			Punch punch = tcDao.getLastPunch();
			assertEquals(cal.getTime(), punch.getDate());
		} catch (DataRetrieveException e) {
			fail(e.getMessage());
		}
	}
	
	@Override
	public void tearDown() {
		TimeMachine.backToPresent();
		solo.finishOpenedActivities();
	}

}
