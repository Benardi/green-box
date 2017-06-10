package org.ufcg.si.util.monitoring;

public class GaugeFactory {

	
	public IGauge<Long> createTimeGauge(){
		IGauge<Long> monitor = new TimeGauge();
		return monitor;
	}
	
	public IGauge<Long> createMemGauge(){
		IGauge<Long> monitor = new MemGauge();
		return monitor;
	}
	
	public IGauge<Double> createCPUGauge(){
		IGauge<Double> monitor = new CPUGauge();
		return monitor;
	}
	
	
}
