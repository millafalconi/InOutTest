package com.thoughtworks.inout.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Date;

import android.test.InstrumentationTestCase;

import com.thoughtworks.inout.Punch;
import com.thoughtworks.inout.PunchType;
import com.thoughtworks.inout.TimeCard;
import com.thoughtworks.inout.clock.TimeMachine;
import com.thoughtworks.inout.db.TimeCardDAO;

public class InOutTest extends InstrumentationTestCase {
	
	
	public void testShouldRegisterInTimeCard(){

		TimeCardDAO mockTimeCardDAO = mock(TimeCardDAO.class);
		TimeCard timeCard = new TimeCard(mockTimeCardDAO);
		Date now = TimeMachine.freeze();
			
		PunchType punchIn = PunchType.IN;
		timeCard.punch(punchIn);
	
		verify(mockTimeCardDAO).insertPunch(new Punch(punchIn, now));
	}
	
}
