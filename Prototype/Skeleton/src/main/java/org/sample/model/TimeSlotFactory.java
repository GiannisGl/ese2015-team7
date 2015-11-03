package org.sample.model;

import org.springframework.util.AutoPopulatingList.ElementFactory;
import org.springframework.util.AutoPopulatingList.ElementInstantiationException;

public class TimeSlotFactory implements ElementFactory<TimeSlot> {
	
		public TimeSlot createElement(int arg0) throws ElementInstantiationException {
			return new TimeSlot();
		}
}