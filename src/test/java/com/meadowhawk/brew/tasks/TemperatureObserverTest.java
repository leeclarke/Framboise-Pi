package com.meadowhawk.brew.tasks;

import static org.junit.Assert.*;

import java.util.Observable;

import org.junit.Test;

import com.meadowhawk.brew.TemperatureObserver;
import com.meadowhawk.brew.model.BrewState;
import com.meadowhawk.brew.model.CurrentTemperature;

public class TemperatureObserverTest {

//	@Test
	public void testUpdate() {
		TestableTempObserver tempWatch = new TestableTempObserver();
		
		CurrentTemperature curTemp = new CurrentTemperature(300,tempWatch);
		
		//Set a new value
		curTemp.setTempSum(350);
		assertTrue(tempWatch.getTimesUpdated()>0);
	}
	
	class TestableTempObserver extends TemperatureObserver{
		
		public TestableTempObserver() {
			super();
			// TODO Auto-generated constructor stub
		}

		int timesUpdated = 0;
		
		@Override
		public void update(Observable obs, Object obj) {
			super.update(obs, obj);
			timesUpdated++;
		}

		public int getTimesUpdated() {
			return timesUpdated;
		}

		public void setTimesUpdated(int timesUpdated) {
			this.timesUpdated = timesUpdated;
		}
		
	}

}
