package org.fl.avoidLock;

import java.util.logging.Logger;

import com.ibm.lge.fl.util.AdvancedProperties;
import com.ibm.lge.fl.util.RunningContext;

public class Control {

	public static Logger avoidLockLog ;
	
	// delay between each step
	private static int timing ;
	
	// maximum duration is minutes
	private static int maxDuration ;
	
	// remaining time in milliseconds
	private static long remainingTime ;
	
	// number of pixel to move back and forth
	private static int nbPixels ;
	
	private static RunningContext runningContext ;
	
	private static final String DEFAULT_PROP_FILE = "avoidLock.properties";

	public static boolean init() {
		
		// access to properties and logger
		runningContext = new RunningContext("AvoidLock", null, DEFAULT_PROP_FILE);
		AdvancedProperties swingWkSampleProperties = runningContext.getProps();
		avoidLockLog = runningContext.getpLog();
        
	     // get maximum duration
		 maxDuration =  swingWkSampleProperties.getInt("avoidLock.maximumDuration", 60) ;
		 remainingTime = maxDuration * 60000 ;
	     
		// get timing
	    timing = swingWkSampleProperties.getInt("avoidLock.timing", 10000) ;
		if (timing < 10) {
			timing = 10 ;
		}
		 
	    // get number of pixel to move back and forth
	    nbPixels =  swingWkSampleProperties.getInt("avoidLock.nbPixels", 1) ;
	    
		return true ;
	}

	public static RunningContext getRunningContext() {
		return runningContext;
	}

	public static int getTiming() {
		return timing;
	}
	
	public static int getMaxDuration() {
		return maxDuration;
	}

	public static void setTiming(int t) {
		timing = t ;
		if (timing < 10) {
			timing = 10 ;
		}	
	}
	
	public static int getNbPixels() {
		return nbPixels;
	}

	public static long getRemainingTime() {
		avoidLockLog.fine("control get remaing time=" + remainingTime);
		return remainingTime;
	}

	public static String getRemainingTimeString() {
		avoidLockLog.fine("control get remaing time=" + remainingTime);
		long minutes = remainingTime/60000 ;
		long seconds = (remainingTime - (remainingTime/60000)*60000)/1000 ;
		return Long.toString(minutes) + ":" + Long.toString(seconds);
	}
	
	public static void setRemainingTime(long t) {
		avoidLockLog.fine("control set remaing time=" + t + "; actual=" + Control.remainingTime);
		Control.remainingTime = t;
	}
}
