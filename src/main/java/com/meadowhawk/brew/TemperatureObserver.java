package com.meadowhawk.brew;

import java.util.Observable;
import java.util.Observer;

import com.pi4j.io.gpio.PinState;

/**
 * Watches the Temp sensor and reacts based on upper/lower bounds.
 * @author lee
 */
public class TemperatureObserver implements Observer {
	
	public TemperatureObserver() {
		
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable obs, Object obj) {
		 System.out.println("update(" + obs + "," + obj + ");");
		 System.out.println("Pin State = " + ((FramboisePi.relayPower.getState() == PinState.HIGH)?"HIGH":"LOW"));
		 if(FramboisePi.relayPower != null && obj instanceof Double){
			 Double  curTemp = (Double)obj;
			 //Check temp and see if between bounds.
			 if(curTemp.compareTo(FramboisePi.brewState.getMinimumTemp()) < 0){
				 System.out.println("Too cool");
				 FramboisePi.relayPower.setState(PinState.LOW);
			 } 
			 else if(curTemp.compareTo(FramboisePi.brewState.getMaximumTemp()) > 0){
				 System.out.println("Too hot");
				 FramboisePi.relayPower.setState(PinState.HIGH);
			 }
		 }
	}

}
