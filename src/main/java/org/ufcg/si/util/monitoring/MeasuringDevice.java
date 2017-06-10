package org.ufcg.si.util.monitoring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ufcg.si.util.monitoring.gauge.GaugeFactory;
import org.ufcg.si.util.monitoring.gauge.IGauge;
import org.ufcg.si.util.monitoring.logging.LoggingDevice;

public class MeasuringDevice {
	private GaugeFactory mf;
	private List<IGauge<?>> toolbox;
	private LoggingDevice logger;
	private String filePath;

	public MeasuringDevice(String filePath) {
		this.filePath = filePath;
		this.mf = new GaugeFactory();
		this.logger = new LoggingDevice();
		initializeDevices();

	}

	private void initializeDevices() {
		toolbox = new ArrayList<IGauge<?>>();

		IGauge<Long> timeMonitor = mf.createTimeGauge();
		IGauge<Long> memoryMonitor = mf.createMemGauge();
		IGauge<Double> cpuMonitor = mf.createCPUGauge();

		toolbox.add(timeMonitor);
		toolbox.add(memoryMonitor);
		toolbox.add(cpuMonitor);
	}

	public void startMeasurement() {
		Iterator itr = toolbox.iterator();
		while (itr.hasNext()) {
			IGauge<?> monitor = (IGauge<?>) itr.next();
			monitor.setStartingPoint();
		}
	}

	public void endMeasurement() {
		Iterator itr = toolbox.iterator();
		while (itr.hasNext()) {
			IGauge<?> monitor = (IGauge<?>) itr.next();
			monitor.setEndingPoint();
		}
	}

	public void logMeasurement(String operation) {

		Iterator itr = toolbox.iterator();
		while (itr.hasNext()) {
			IGauge<?> monitor = (IGauge<?>) itr.next();
			this.logger.createLogger(filePath + monitor.toString()+".txt");
			this.logger.logData(monitor.getInitialReading() + "," + monitor.getFinalReading() + "," + monitor.getElapse());
			this.logger.closeLogger();
		}

	}

}
