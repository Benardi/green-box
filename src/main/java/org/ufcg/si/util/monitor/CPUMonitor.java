package org.ufcg.si.util.monitor;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

@SuppressWarnings("restriction")
public class CPUMonitor implements IMonitor<Double> {

	private OperatingSystemMXBean osBean;
	private double startingPoint;
	private double endingPoint;

	public CPUMonitor() {
		this.osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
	}

	private double getUsage() {
		double load = -1;

		for (int i = 0; i < 10; i++) {
			load = osBean.getSystemCpuLoad();
			if ((load < 0.0 || load > 1.0) && load != -1.0) {
				throw new RuntimeException(
						"getSystemCpuLoad() returns " + load + " which is not in the [0.0,1.0] interval");
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return load;
	}

	@Override
	public void setStartingPoint() {
		this.startingPoint = this.getUsage();

	}

	@Override
	public void setEndingPoint() {
		this.endingPoint = this.getUsage();
	}

	@Override
	public Double getElapse() {
		return endingPoint - startingPoint;
	}

	@Override
	public Double getInitialReading() {
		return this.startingPoint;
	}

	@Override
	public Double getFinalReading() {
		return this.endingPoint;
	}

	@Override
	public String toString() {
		return "CPU";
	}

}
