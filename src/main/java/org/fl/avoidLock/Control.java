/*
 * MIT License

Copyright (c) 2017, 2026 Frederic Lefevre

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

	private static final Logger avoidLockLog = Logger.getLogger(Control.class.getName());

	private static Control controlInstance;
	
	// delay between each step
	private long timing;

	// Avoid lock duration in milliseconds
	private long avoidLockDuration;

	// number of pixel to move back and forth
	private int nbPixels;

	private RunningContext runningContext;

	private Control() {	
	}
	
	private Control(RunningContext runningContext) {
		
		this.runningContext = runningContext;
		
		AdvancedProperties swingWkSampleProperties = runningContext.getProps();

		// get maximum duration (property is in minutes)
		avoidLockDuration = swingWkSampleProperties.getLong("avoidLock.maximumDuration", Long.MAX_VALUE/60000) * 60000;

		// get timing
		timing = swingWkSampleProperties.getLong("avoidLock.timing", 10000);
		if (timing < 10) {
			timing = 10;
			avoidLockLog.warning(() -> "Set initial timing from property file too low, default to " + timing);
		}

		// get number of pixel to move back and forth
		nbPixels = swingWkSampleProperties.getInt("avoidLock.nbPixels", 1);
	}

	private static Control getInstance() {
		if (controlInstance == null) {
			controlInstance = new Control(AvoidLockGui.getRunningContext());
		}
		return controlInstance;
	}
	
	public static RunningContext getRunningContext() {
		return getInstance().runningContext;
	}
	
	public static long getTiming() {
		return getInstance().timing;
	}

	public static void setTiming(long t) {

		avoidLockLog.fine(() -> "Set timing to " + t + "; previous=" + getInstance().timing);
		if (t < 10) {
			getInstance().timing = t;
		} else {
			getInstance().timing = 10;
			avoidLockLog.fine(() -> "Set timing too low, default to " + getInstance().timing);
		}	
	}
	
	public static int getNbPixels() {
		return getInstance().nbPixels;
	}

	public static long getAvoidLockDuration() {
		return getInstance().avoidLockDuration;
	}
	
	public static long setAvoidLockDuration(long t) {
		avoidLockLog.fine(() -> "Set remaing time to " + t + "; previous=" + getInstance().avoidLockDuration);		
		getInstance().avoidLockDuration = t;
		return getInstance().avoidLockDuration;
	}
}
