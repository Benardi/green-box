package org.ufcg.si.util.monitor;

public class MonitorFactory {

	public IMonitor<?> getMonitor(String aspect) {
		IMonitor<?> monitor;
		aspect = aspect.toLowerCase();

		switch (aspect) {
		case "time":
			monitor = new TimeMonitor();
			break;
		case "memory":
			monitor = new MemMonitor();
			break;
		case "cpu":
			monitor = new CPUMonitor();
		default:
			monitor = null;

		}
		return monitor;

	}
}
