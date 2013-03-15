package com.meadowhawk.brew.model;

import java.util.Observable;

import org.joda.time.DateTime;

/**
 * Keeps current data about the active brewing process.
 * @author lee
 */
public class BrewState extends Observable{
	private CurrentTemperature currentTemp;
	private String batchName;
	private DateTime batchScheduledStartTime;  //DateTime of actual brew start
	private DateTime batchScheduledEndTime;    //DateTime of expectedEnd time.
	private Double maximumTemp;
	private Double minimumTemp;
	private BrewDetails brewDetails;
	
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public DateTime getBatchScheduledStartTime() {
		return batchScheduledStartTime;
	}
	public void setBatchScheduledStartTime(DateTime batchScheduledStartTime) {
		this.batchScheduledStartTime = batchScheduledStartTime;
	}
	public DateTime getBatchScheduledEndTime() {
		return batchScheduledEndTime;
	}
	public void setBatchScheduledEndTime(DateTime batchScheduledEndTime) {
		this.batchScheduledEndTime = batchScheduledEndTime;
	}
	public CurrentTemperature getCurrentTemp() {
		return currentTemp;
	}
	public void setCurrentTemp(CurrentTemperature currentTemp) {
		this.currentTemp = currentTemp;
	}
	public Double getMaximumTemp() {
		return maximumTemp;
	}
	public void setMaximumTemp(Double maximumTemp) {
		this.maximumTemp = maximumTemp;
	}
	public Double getMinimumTemp() {
		return minimumTemp;
	}
	public void setMinimumTemp(Double minimumTemp) {
		this.minimumTemp = minimumTemp;
	}
	
	
}
