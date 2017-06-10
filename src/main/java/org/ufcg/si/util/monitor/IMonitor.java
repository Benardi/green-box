package org.ufcg.si.util.monitor;

public interface IMonitor<T>{
	
	public void setStartingPoint();
	
	public void setEndingPoint();
	
	public T getElapse();
	
	public T getInitialReading();

	public T getFinalReading();

}
