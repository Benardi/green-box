package org.ufcg.si.util.monitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ufcg.si.util.monitor.logging.LoggingDevice;

public class MeasurementDevice {
	private MonitorFactory mf;
	private List<IMonitor<?>> toolbox;
	private LoggingDevice logger;

	public MeasurementDevice(String filePath) {
		this.mf = new MonitorFactory();
		initializeDevices();
		this.logger = new LoggingDevice(filePath);
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
		while (itr.hasNext()) {
			IMonitor<?> monitor = (IMonitor<?>) itr.next();
			monitor.setStartingPoint();
		}
	}

	public void endMeasurement() {
		Iterator itr = toolbox.iterator();
		while (itr.hasNext()) {
			IMonitor<?> monitor = (IMonitor<?>) itr.next();
			monitor.setEndingPoint();
		}
	}

	public void logMeasurement(String operation) {
		this.logger.createLogger();
		Iterator itr = toolbox.iterator();
		while (itr.hasNext()) {
			IMonitor<?> monitor = (IMonitor<?>) itr.next();
			this.logger.logData(monitor.getElapse() + "," + monitor.toString() + "," + operation);
		}
		this.logger.closeLogger();

	}

}
