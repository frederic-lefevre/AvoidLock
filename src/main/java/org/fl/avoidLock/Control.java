/*
 * MIT License

Copyright (c) 2017, 2023 Frederic Lefevre

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package org.fl.avoidLock;

import java.util.logging.Logger;

import org.fl.util.AdvancedProperties;
import org.fl.util.RunningContext;

public class Control {

	public static Logger avoidLockLog;

	// delay between each step
	private static int timing;

	// maximum duration is minutes
	private static int maxDuration;

	// remaining time in milliseconds
	private static long remainingTime;

	// number of pixel to move back and forth
	private static int nbPixels;

	private static RunningContext runningContext;

	private static final String DEFAULT_PROP_FILE = "avoidLock.properties";

	private static boolean initialized = false;

	public static void init() {

		// access to properties and logger
		runningContext = new RunningContext("AvoidLock", null, DEFAULT_PROP_FILE);
		AdvancedProperties swingWkSampleProperties = runningContext.getProps();
		avoidLockLog = runningContext.getpLog();

		// get maximum duration
		maxDuration = swingWkSampleProperties.getInt("avoidLock.maximumDuration", 60);
		remainingTime = maxDuration * 60000;

		// get timing
		timing = swingWkSampleProperties.getInt("avoidLock.timing", 10000);
		if (timing < 10) {
			timing = 10;
		}

		// get number of pixel to move back and forth
		nbPixels = swingWkSampleProperties.getInt("avoidLock.nbPixels", 1);

		initialized = true;
	}

	public static RunningContext getRunningContext() {
		if (!initialized) {
			init();
		}
		return runningContext;
	}

	public static int getTiming() {
		if (!initialized) {
			init();
		}
		return timing;
	}
	
	public static int getMaxDuration() {
		if (!initialized) {
			init();
		}
		return maxDuration;
	}

	public static void setTiming(int t) {
		timing = t ;
		if (timing < 10) {
			timing = 10 ;
		}	
	}
	
	public static int getNbPixels() {
		if (!initialized) {
			init();
		}
		return nbPixels;
	}

	public static long getRemainingTime() {
		if (!initialized) {
			init();
		}
		avoidLockLog.fine("control get remaing time=" + remainingTime);
		return remainingTime;
	}

	public static String getRemainingTimeString() {
		if (!initialized) {
			init();
		}
		avoidLockLog.fine("control get remaing time=" + remainingTime);
		long minutes = remainingTime/60000 ;
		long seconds = (remainingTime - (remainingTime/60000)*60000)/1000 ;
		return Long.toString(minutes) + ":" + Long.toString(seconds);
	}
	
	public static void setRemainingTime(long t) {
		if (!initialized) {
			init();
		}
		avoidLockLog.fine("control set remaing time=" + t + "; actual=" + Control.remainingTime);
		Control.remainingTime = t;
	}
}
