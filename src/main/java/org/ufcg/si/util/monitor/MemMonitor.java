package org.ufcg.si.util.monitor;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

@SuppressWarnings("restriction")
public class MemMonitor implements IMonitor<Long> {

	private OperatingSystemMXBean osBean;
	private long startingPoint;
	private long endingPoint;

	public MemMonitor() {
		this.osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
	}

	private long getUsage() {
		return osBean.getFreePhysicalMemorySize();

	}

	public void setStartingPoint() {
		this.startingPoint = this.getUsage();
	}

	public void setEndingPoint() {
		this.endingPoint = this.getUsage();
	}

	public Long getElapse() {
		return endingPoint - startingPoint;

	}

	@Override
	public Long getInitialReading() {
		return this.startingPoint;

	}

	@Override
	public Long getFinalReading() {
		return this.endingPoint;
	}

	@Override
	public String toString() {
		return "MEMORY";
	}

}
