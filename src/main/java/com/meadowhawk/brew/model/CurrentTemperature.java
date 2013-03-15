package com.meadowhawk.brew.model;

import java.util.Observable;

import com.meadowhawk.brew.TemperatureObserver;

/**
 * Managers the sensor reading from the temp102.
 * @author lee
 */
public class CurrentTemperature extends Observable {
	private static final String THE_CURRENT_TEMPERATURE_READING_IS = "The current Temperature reading is ";
	public enum TempScale {
		CELSIUS("C"), FAHRENHEIT("F");
		
		private String code;
		TempScale(String code){
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
	};
	
	int tempSum = 0;
	private TempScale prefScale = TempScale.FAHRENHEIT;
	
	/**
	 * 
	 */
	public CurrentTemperature(){
	}
	
	/**
	 * @param tempertureSum
	 */
	public CurrentTemperature(int tempertureSum){
		this.tempSum = tempertureSum;
	}
	
	/**
	 * @param tempertureSum
	 * @param tempObserver
	 */
	public CurrentTemperature(int tempertureSum, TemperatureObserver tempObserver) {
		this(tempertureSum);
		addObserver(tempObserver);
	}

	/**
	 * @return - raw int value read from the temperature sensor.
	 */
	public int getTempSum() {
		return tempSum;
	}
	
	public void setTempSum(int tempSum) {
		this.tempSum = tempSum;
		setChanged();
		notifyObservers(getPreferedTempValue());
	}
	
	/**
	 * Based on configuration settings, this method will return the temperature
	 * in the users preferred flavor C|F, by default it returns F because that's
	 * what I'm sure to using.
	 * 
	 * @return temperature value in preferred scale.
	 */
	public Double getPreferedTempValue() {
		if(this.prefScale == TempScale.CELSIUS){
			return this.getCurrentCTemp();
		} else{
			return this.getCurrentFTemp();
		}
		
	}

	public Double getCurrentFTemp() {
		return (this.tempSum * 0.1125) + 32;
	}
	
	public Double getCurrentCTemp() {
		return this.tempSum*0.0625;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(THE_CURRENT_TEMPERATURE_READING_IS);
		sb.append(this.getCurrentCTemp()).append(" C or ");
		sb.append(this.getCurrentFTemp()).append(" F.");
		return sb.toString();
	}

	public TempScale getPrefScale() {
		return prefScale;
	}

	public void setPrefScale(TempScale prefScale) {
		this.prefScale = prefScale;
	}
}
