package org.ufcg.si.util.monitoring.gauge;

public class TimeGauge implements IGauge<Long> {

	private long startingPoint;
	private long endingPoint;

	public void setStartingPoint() {
		this.startingPoint = System.currentTimeMillis();
	}

	public void setEndingPoint() {
		this.endingPoint = System.currentTimeMillis();
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
		return "TIME";
	}

}
