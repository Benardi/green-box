package org.ufcg.si.util.monitor;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

@SuppressWarnings("restriction")
public class MemMonitor {
	private OperatingSystemMXBean osBean;

	public MemMonitor() {
		this.osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
	}

	public long getUsage() {
		return osBean.getFreePhysicalMemorySize();

	}

}
