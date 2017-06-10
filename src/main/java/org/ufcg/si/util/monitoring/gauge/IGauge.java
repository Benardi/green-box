package org.ufcg.si.util.monitoring.gauge;

public interface IGauge<T>{
	
	public void setStartingPoint();
	
	public void setEndingPoint();
	
	public T getElapse();
	
	public T getInitialReading();

	public T getFinalReading();

}
