package org.ufcg.si.util.monitor;

public class TimeMonitor {

	private long startingPoint;
	private long endingPoint;
	
	public void setStartingPoint(){
		this.startingPoint = System.currentTimeMillis();
	}
	
	public void setEndingPoint(){
		this.endingPoint = System.currentTimeMillis();
	}
	
	public long getElapsedTime(){
		return endingPoint - startingPoint;
	}
	
}
