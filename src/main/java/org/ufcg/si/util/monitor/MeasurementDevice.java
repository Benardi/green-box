package org.ufcg.si.util.monitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ufcg.si.util.monitor.logging.LoggingDevice;

public class MeasurementDevice {
	private MonitorFactory mf;
	private List<IMonitor<?>> toolbox;
	private LoggingDevice logger;
	private String filePath;

	public MeasurementDevice(String filePath) {
		this.filePath = filePath;
		this.mf = new MonitorFactory();
		this.logger = new LoggingDevice();
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

		Iterator itr = toolbox.iterator();
		while (itr.hasNext()) {
			IMonitor<?> monitor = (IMonitor<?>) itr.next();
			this.logger.createLogger(filePath + monitor.toString());
			this.logger.logData(monitor.getElapse()+ "");
			this.logger.closeLogger();
		}

	}

}
