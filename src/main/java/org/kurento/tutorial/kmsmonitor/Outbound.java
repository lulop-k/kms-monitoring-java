package org.kurento.tutorial.kmsmonitor;

public class Outbound {
	private double rtt;
	private double deltaPlis;
	private double deltaNacks;
	private long byteCount;
	private double targetBitrate;

	public double getRtt() {
		return rtt;
	}

	void setRtt(double rtt) {
		this.rtt = rtt;
	}

	public double getDeltaPlis() {
		return deltaPlis;
	}

	void setDeltaPlis(double deltaPlis) {
		this.deltaPlis = deltaPlis;
	}

	public double getDeltaNacks() {
		return deltaNacks;
	}

	void setDeltaNacks(double deltaNacks) {
		this.deltaNacks = deltaNacks;
	}

	public long getByteCount() {
		return byteCount;
	}

	void setByteCount(long byteCount) {
		this.byteCount = byteCount;
	}

	public double getTargetBitrate() {
		return targetBitrate;
	}

	void setTargetBitrate(double targetBitrate) {
		this.targetBitrate = targetBitrate;
	}
}
