package org.fl.avoidLock;

public class WorkerInformation {

	private String status ;
	private int step ;
	private long remainingTime ;
	
	public WorkerInformation() {
		super();
		step = 0 ;
		status = "Unknown" ;
		remainingTime = Control.getMaxDuration() ;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}


	public long getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(long remainingTime) {
		this.remainingTime = remainingTime;
	}
}
