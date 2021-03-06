package org.ufcg.si.util.monitoring.gauge;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

@SuppressWarnings("restriction")
public class CPUGauge implements IGauge<Double> {

	private OperatingSystemMXBean osBean;
	private double startingPoint;
	private double endingPoint;

	public CPUGauge() {
		this.osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
	}

	private double getUsage() {
		double load = -1;

		for (int i = 0; i < 5; i++) {
			load = osBean.getSystemCpuLoad();

			if ((load < 0.0 || load > 1.0) && load != -1.0) {
				throw new RuntimeException(
						"getSystemCpuLoad() returns " + load + " which is not in the [0.0,1.0] interval");
			}
			try {
				Thread.sleep(100);
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
