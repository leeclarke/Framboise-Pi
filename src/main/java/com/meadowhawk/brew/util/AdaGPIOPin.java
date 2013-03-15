package com.meadowhawk.brew.util;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Maps GPIO pins back to the Board pins for the benefit of those who are working with an AdaFruit board 
 * extension marked with PI GPIO values.
 * @author lee
 */
public class AdaGPIOPin {

	public static final Pin GPIO_17 = RaspiPin.GPIO_00;
	public static final Pin GPIO_18 = RaspiPin.GPIO_01;
	public static final Pin GPIO_21 = RaspiPin.GPIO_02;
	public static final Pin GPIO_22 = RaspiPin.GPIO_03;
	public static final Pin GPIO_23 = RaspiPin.GPIO_04;
	public static final Pin GPIO_24 = RaspiPin.GPIO_05;
}
