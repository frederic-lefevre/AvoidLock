package org.fl.avoidLock;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.MouseInfo;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

import javax.swing.SwingWorker;

import org.fl.util.os.Chronometre;

public class AvoidLock  extends SwingWorker<String,WorkerInformation> {

	private final UiControl   startStop ;
	private final ProcessInfo stepsInfo;
	
	private int 			  step ;
	private WorkerInformation wkInfos ;
	private Robot 			  mouseRobot ;

	public AvoidLock(UiControl uc, ProcessInfo pi) {
		super();
		startStop = uc ;
		stepsInfo = pi ;
		step 	  = 0 ;
		try {
			mouseRobot = new Robot();
		} catch (AWTException e) {
			Control.avoidLockLog.log(Level.SEVERE, "AWT exception when creating robot", e) ;
			mouseRobot = null ;
		}
		wkInfos = new WorkerInformation() ;
	}
	

	 @Override 
	 public String doInBackground() {
	
		step = 0 ;
		Chronometre chronos = new Chronometre() ;
		
		while  (Control.getRemainingTime() > 0) {
				
			Control.avoidLockLog.fine("Process step " + step + "; Remaining time=" + Control.getRemainingTime()) ;
			
			if (! startStop.isRunning()) {
				chronos = new Chronometre() ;
				Control.setRemainingTime(Control.getMaxDuration()*60000) ;
				step = 0 ;
				wkInfos.setStep(step) ;
				wkInfos.setStatus("Reset done.") ;
				wkInfos.setRemainingTime(Control.getRemainingTime());
				
				publish(wkInfos) ;
				Control.avoidLockLog.fine("Reset to initial state") ;
				
				while (! startStop.isRunning()) {
					// the worker is not running	
					// sleep some time, 
					
					try {
						Thread.sleep(10) ;
					} catch (InterruptedException e) {
					}
				}
				chronos.start();
				Control.avoidLockLog.fine("Process is restarted") ;
			}
			if ((startStop.isPaused() && startStop.isRunning())) {
				long v = chronos.pause();
				wkInfos.setStep(step) ;
				wkInfos.setStatus("Paused. ") ;
				Control.setRemainingTime(Control.getRemainingTime() - v);
				wkInfos.setRemainingTime(Control.getRemainingTime());
				publish(wkInfos) ;
				Control.avoidLockLog.finest("Process is paused") ;
				
				while (startStop.isPaused() && startStop.isRunning()) {				
					
					// the simulator is paused
					// sleep some time
					try {
						Thread.sleep(10) ;
					} catch (InterruptedException e) {
					}
				}
				chronos.start();
				Control.avoidLockLog.finest("Process is resumed");
				
			}
			
			Control.setRemainingTime(Control.getRemainingTime() - chronos.getDeltaValue());
			wkInfos.setStep(step) ;
			wkInfos.setStatus("Running. ") ;
			wkInfos.setRemainingTime(Control.getRemainingTime());			
			publish(wkInfos) ;			
			
			// Move the mouse to avoid lock
			int x = MouseInfo.getPointerInfo().getLocation().x ;
			int y = MouseInfo.getPointerInfo().getLocation().y ;
			int pas ;
			if (step % 2 == 0) {
				pas = Control.getNbPixels() ;
			} else {
				pas = 0 - Control.getNbPixels() ;
			}
			mouseRobot.mouseMove(x+ pas, y);
			
			// sleep some time
			try {
				Thread.sleep(Control.getTiming()) ;
			} catch (InterruptedException e) {
			}
			
			// go to next step
			step = step + 1 ;
		}
		return "Avoid lock ended" ;
	 }
	 
	 @Override
     protected void process(java.util.List<WorkerInformation> chunks) {
        // Get the latest result from the list
		 WorkerInformation latestResult = chunks.get(chunks.size() - 1);
		 
        stepsInfo.setStepNumber(latestResult.getStep());
        stepsInfo.setSimulStatus(latestResult.getStatus());
        stepsInfo.setRemaingTime(Control.getRemainingTimeString());
        startStop.setRemainingTime(latestResult.getRemainingTime());
     }

	 @Override
     protected void done() {
		 
		 try {
			 stepsInfo.setSimulStatus(get()) ;
			 startStop.deactivate() ;
		} catch (InterruptedException e) {
			Control.avoidLockLog.severe("Exception when getting process result: " + e) ;
			e.printStackTrace();
		} catch (ExecutionException e) {
			Control.avoidLockLog.severe("Exception when getting process result: " + e) ;
			e.printStackTrace();
		}
	 }
}
