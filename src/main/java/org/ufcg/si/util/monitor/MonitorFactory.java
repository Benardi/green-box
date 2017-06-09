package org.ufcg.si.util.monitor;

public class MonitorFactory {

	
	public IMonitor<Long> createTimeMonitor(){
		IMonitor<Long> monitor = new TimeMonitor();
		return monitor;
	}
	
	public IMonitor<Long> createMemMonitor(){
		IMonitor<Long> monitor = new MemMonitor();
		return monitor;
	}
	
	public IMonitor<Double> createCPUMonitor(){
		IMonitor<Double> monitor = new CPUMonitor();
		return monitor;
	}
	
	
}
