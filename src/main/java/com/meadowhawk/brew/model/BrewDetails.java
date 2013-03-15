package com.meadowhawk.brew.model;

import org.joda.time.DateTime;

/**
 * Meta-data objet for storing all the data related to the current brew. This can be persisted to 
 * @author lee
 */
public class BrewDetails {
	DateTime brewDate;
	Double batchSize;
	String honeyVarietal;
	Double SGgoal;
	Double alcaholByVolGoal;

}
