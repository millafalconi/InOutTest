package com.thoughtworks.inout.test;

import java.util.Date;

import android.test.InstrumentationTestCase;

import com.thoughtworks.inout.PunchType;
import com.thoughtworks.inout.TimeCard;
import com.thoughtworks.inout.clock.TimeMachine;
import com.thoughtworks.inout.db.TimeCardDAO;

import static org.mockito.Mockito.*;

public class InOutTest extends InstrumentationTestCase {
	
	
	public void testShouldRegisterInTimeCard(){

		TimeCardDAO mockTimeCard = mock(TimeCardDAO.class);
		TimeCard timeCard = new TimeCard(mockTimeCard);
		Date now = TimeMachine.freeze();
			
		PunchType punchType = PunchType.IN;
		timeCard.punch(punchType);
	
		verify(mockTimeCard).insertPunch(now, punchType);
	}
	
//	public void testShouldHaveInButton(){
//		InOutActivity inOutActivity = getActivity();
//		Button inOutButton = (Button) inOutActivity.findViewById(R.id.button_in);
//		assertNotNull(inOutButton);
//	}
	
//	public void testShouldRegisterWhenClickOnButton(){
//		
//		InOutActivity mockInOut = mock(InOutActivity.class);
//		
//		View realView = getActivity().findViewById(R.id.button_in);
//		when(mockInOut.findViewById(R.id.button_in)).thenReturn(realView);
//		Button inOutButton = (Button) mockInOut.findViewById(R.id.button_in);
//		inOutButton.performClick();
//	    
//	}

}
