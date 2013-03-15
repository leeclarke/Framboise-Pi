package com.meadowhawk.brew;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;

import com.meadowhawk.brew.model.BrewState;
import com.meadowhawk.brew.model.CurrentTemperature;
import com.meadowhawk.brew.tasks.LogTempTask;
import com.meadowhawk.brew.util.AdaGPIOPin;
import com.meadowhawk.brew.util.BrewScheduledExecutor;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;

/**
 * Main executable for BrewPi
 * @author lee
 */
public class FramboisePi {
	static GpioPinDigitalOutput relayPower = null;
	static BrewState brewState = new BrewState();
	static TemperatureObserver temperatureObserver = new TemperatureObserver();
	static long blinkDelay = 1000;
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		System.out.println("<--Framboise Pi--> Brewing computer ... started.");
		ScheduledExecutorService scheduler = new BrewScheduledExecutor(1);
		brewState.setCurrentTemp(new CurrentTemperature(0, temperatureObserver));  //add Observer to the temp value so it will see data changes.
		
		Double minTestTemp = new Double(63.0);
		Double maxTestTemp = new Double(63.0);
		brewState.setMinimumTemp(minTestTemp);
		brewState.setMaximumTemp(maxTestTemp);
		
		// create gpio controller
		GpioController gpio = GpioFactory.getInstance();

		// provision gpio pin #01 & #03 as an output pins and blink
		GpioPinDigitalOutput ledButton = gpio.provisionDigitalOutputPin(AdaGPIOPin.GPIO_24, "ButtonLED");
		GpioPinDigitalOutput ledStartUp = gpio.provisionDigitalOutputPin(AdaGPIOPin.GPIO_17, "StartUpFlashLED");
		relayPower = gpio.provisionDigitalOutputPin(AdaGPIOPin.GPIO_23, "PowerRelayOnOff");
		
		
		// continuously blink the led every second
		ledStartUp.blink(blinkDelay);

		// provision gpio pin #02 as an input pin with its internal pull down
		// resistor enabled.  This is TEMP until the 5button controller has a driver ported to Java.
		GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(AdaGPIOPin.GPIO_21, "MyButton", PinPullResistance.PULL_DOWN);
        
        // create a gpio callback trigger on gpio pin#4; when #4 changes state, perform a callback
        // invocation on the user defined 'Callable' class instance
        myButton.addTrigger(new GpioCallbackTrigger(PinState.HIGH ,new Callable<Void>()
        {
            public Void call() throws Exception
            {
                return null;
            }
        }),new GpioCallbackTrigger(PinState.LOW ,new Callable<Void>()
        {
            public Void call() throws Exception
            {
                System.out.println("Toggle State of Relay power");
                relayPower.toggle();
                return null;
            }
        }));

        //printTempAltCalc();
		System.out.println(" ... the LED will continue blinking until the program is terminated.");
		System.out.println(" ... PRESS <CTRL-C> TO STOP THE PROGRAM.");

		//Begin monitoring Temperature.
		scheduler.scheduleAtFixedRate(new LogTempTask(brewState), 10, 60, SECONDS); //delay 10 then run every min.
	
		// keep program running until user aborts (CTRL-C)
		for (;;) {
			Thread.sleep(500);
		}
	}

}
