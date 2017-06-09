package org.ufcg.si.util.monitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ufcg.si.util.monitor.logging.LoggingDevice;

public class MeasurementDevice {
	private MonitorFactory mf;
	private List<IMonitor<?>> toolbox;
	private LoggingDevice logger;

	public MeasurementDevice() {
		this.mf = new MonitorFactory();
		initializeDevices();
		this.logger = new LoggingDevice("src/main/java/org/ufcg/si/util/monitor/logging/Log1");
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
		this.logger.createLogger();
		Iterator itr = toolbox.iterator();
		while (itr.hasNext()) {
			IMonitor<?> monitor = (IMonitor<?>) itr.next();
			monitor.setStartingPoint();
			this.logger.logData("START");
		}
		this.logger.closeLogger();

	}

	public void endMeasurement() {
		this.logger.createLogger();
		Iterator itr = toolbox.iterator();
		while (itr.hasNext()) {
			IMonitor<?> monitor = (IMonitor<?>) itr.next();
			monitor.setEndingPoint();
			this.logger.logData("END");
		}
		this.logger.closeLogger();

	}

	public static void main(String[] args) {
		MeasurementDevice md = new MeasurementDevice();
		md.startMeasurement();
		md.endMeasurement();
	}

}
