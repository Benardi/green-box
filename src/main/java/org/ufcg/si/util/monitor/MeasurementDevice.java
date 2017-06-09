package org.ufcg.si.util.monitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MeasurementDevice {
	private MonitorFactory mf;
	List<IMonitor<?>> toolbox;

	public MeasurementDevice() {
		this.mf = new MonitorFactory();
		initializeDevices();
	}

	private void initializeDevices() {
		toolbox = new ArrayList<IMonitor<?>>();

		IMonitor<Long> timeMonitor = mf.createTimeMonitor();
		IMonitor<Long> memoryMonitor = mf.createMemMonitor();
		IMonitor<Double> cpuMonitor = mf.createCPUMonitor();

		toolbox.add(timeMonitor);
		toolbox.add(memoryMonitor);
		toolbox.add(cpuMonitor);
	}

	public void startMeasurement() {
		
		Iterator itr = toolbox.iterator();
		while(itr.hasNext()){
			IMonitor<?> monitor = (IMonitor<?>) itr.next();
			monitor.setStartingPoint();
		}

	}
	
	public void endMeasurement() {
		
		Iterator itr = toolbox.iterator();
		while(itr.hasNext()){
			IMonitor<?> monitor = (IMonitor<?>) itr.next();
			monitor.setEndingPoint();
		}

	}
	

}
