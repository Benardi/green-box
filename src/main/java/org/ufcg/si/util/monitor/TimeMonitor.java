package org.ufcg.si.util.monitor;

public class TimeMonitor implements IMonitor<Long>{

	private long startingPoint;
	private long endingPoint;
	
	public void setStartingPoint(){
		this.startingPoint = System.currentTimeMillis();
	}
	
	public void setEndingPoint(){
		this.endingPoint = System.currentTimeMillis();
	}
	
	public Long getElapse(){
		return endingPoint - startingPoint;
		
	}
	
	@Override
	public String toString() {
		return "TIME";
	}

	
}
