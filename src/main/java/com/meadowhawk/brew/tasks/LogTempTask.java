package com.meadowhawk.brew.tasks;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.management.RuntimeErrorException;

import com.meadowhawk.brew.model.BrewState;
import com.meadowhawk.brew.model.CurrentTemperature;
import com.pachube.jpachube.Feed;
import com.pachube.jpachube.Pachube;
import com.pachube.jpachube.PachubeException;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

/**
 * Scheduled task to upload temp data to Cosm datastore.
 * @author lee
 */
public class LogTempTask implements Runnable {

	private static final String API_KEY = "IcVPqS89z7L_2fMh-8ALc-m-tKOSAKx3cmd2VEE2UVp2bz0g";
	private static final Integer FEED_ID = 95749;
	private BrewState brewState ;
	
	public LogTempTask(BrewState brewState) {
		this.brewState = brewState;
	}

	public void run() {
		I2CBus bus;
		try {
//TODO: Start setting the CurrentTemperture object so it 			
			bus = I2CFactory.getInstance(I2CBus.BUS_1);
			I2CDevice tmp102 = bus.getDevice(0x48);
	        int MSB = tmp102.read();
	        int LSB = tmp102.read();
	        int TemperatureSum = ((MSB << 8) | LSB) >> 4;
        	brewState.getCurrentTemp().setTempSum(TemperatureSum);
	        
	        //double celsius = TemperatureSum*0.0625;
	        //double fahrenheit = (TemperatureSum * 0.1125) + 32;
	        //System.out.println(">> ALT Temp reading >> " + fahrenheit + "F ");
	        //double fahrenheit2 = (1.8 * celsius) + 32 ;
	        System.out.println(">> Task Temp reading >> " + brewState.getCurrentTemp());
	        writeDataToCosm(brewState.getCurrentTemp().getPreferedTempValue());
		} catch (IOException ioe) {
			ioe.printStackTrace();
//			throw new RuntimeErrorException(new Error(), "LogTempTask Failed");
			System.out.println("Gonk!  Failed to read the I2C bus for the device at 0x48. [Task] message:" + ioe.getMessage());
		}
         

	}
	
	protected void writeDataToCosm(double temp) {
		Pachube pachubeClient = new Pachube(API_KEY);

		try {
			Feed feed = pachubeClient.getFeed(FEED_ID);
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			feed.updateDatastream("TestTemp", Double.valueOf(twoDForm.format(temp)) + "");
		} catch (PachubeException e) {
			System.err.println(e.getMessage());
		}
	}

}
