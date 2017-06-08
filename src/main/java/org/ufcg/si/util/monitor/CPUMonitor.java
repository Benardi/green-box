package org.ufcg.si.util.monitor;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class CPUMonitor {
	private OperatingSystemMXBean osBean;

	public CPUMonitor() {
		this.osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
	}

	public double getUsage() {
		double load = -1;

        for(int i=0; i<10; i++) {
            load = osBean.getSystemCpuLoad();
            System.out.println(load);
            if((load<0.0 || load>1.0) && load != -1.0) {
                throw new RuntimeException("getSystemCpuLoad() returns " + load
                       +  " which is not in the [0.0,1.0] interval");
            }
            try {
                Thread.sleep(200);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		return load;
	}

	public static void main(String[] args) {
		CPUMonitor cp = new CPUMonitor();
		System.out.println(cp.getUsage());

	}

}
