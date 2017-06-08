package org.ufcg.si.util.monitor;

public class MonitorFactory {

	
	public IMonitor<?> getTimeMonitor(){
		IMonitor<?> monitor = new TimeMonitor();
		return monitor;
	}
	
	public IMonitor<?> getMemMonitor(){
		IMonitor<?> monitor = new MemMonitor();
		return monitor;
	}
	
	public IMonitor<?> getCPUMonitor(){
		IMonitor<?> monitor = new CPUMonitor();
		return monitor;
	}
	
	
}
