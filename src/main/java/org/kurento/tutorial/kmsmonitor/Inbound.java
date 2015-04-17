package org.kurento.tutorial.kmsmonitor;

public class Inbound {

	private double jitter;
	private double fractionLost;
	private double deltaNacks;
	private double deltaPlis;
	private long byteCount;
	private long packetLostCount;

	public double getJitter() {
		return jitter;
	}

	void setJitter(double jitter) {
		this.jitter = jitter;
	}

	public double getFractionLost() {
		return fractionLost;
	}

	void setFractionLost(double fractionLost) {
		this.fractionLost = fractionLost;
	}

	public double getDeltaNacks() {
		return deltaNacks;
	}

	void setDeltaNacks(double deltaNacks) {
		this.deltaNacks = deltaNacks;
	}

	public double getDeltaPlis() {
		return deltaPlis;
	}

	void setDeltaPlis(double deltaPlis) {
		this.deltaPlis = deltaPlis;
	}

	public long getByteCount() {
		return byteCount;
	}

	void setByteCount(long byteCount) {
		this.byteCount = byteCount;
	}

	public long getPacketLostCount() {
		return packetLostCount;
	}

	void setPacketLostCount(long packetLostCount) {
		this.packetLostCount = packetLostCount;
	}

}
