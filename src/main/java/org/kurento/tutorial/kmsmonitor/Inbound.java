package org.kurento.tutorial.kmsmonitor;

/**
 * This class provides statistics for inbound (incoming to a specific KMS
 * instance) streams.
 * 
 * @author llopez
 * 
 */
public class Inbound {

	private double jitter;
	private double fractionLost;
	private double deltaNacks;
	private double deltaPlis;
	private long byteCount;
	private long packetLostCount;

	/**
	 * 
	 * @return average packet jitter measured in milliseconds for the last RTCP
	 *         RR period, which is usually 0.5 seconds. The average is
	 *         calculated considering all WebRtcEndpoints at the KMS instance
	 */
	public double getJitter() {
		return jitter;
	}

	void setJitter(double jitter) {
		this.jitter = jitter;
	}

	/**
	 * 
	 * @return average packet fraction lost for the last RTCP RR period, which
	 *         is usually 0.5 seconds. The fraction lost it the ratio between
	 *         the number of packets lost and the number of packets received.
	 *         The average is calculated considering all WebRtcEndpoints at the
	 *         KMS instance
	 */
	public double getFractionLost() {
		return fractionLost;
	}

	void setFractionLost(double fractionLost) {
		this.fractionLost = fractionLost;
	}

	/**
	 * 
	 * @return total number of NACK packets received per time unit. This is
	 *         calculated considering all NACKs received in all WebRtcEnpoints
	 *         at a KMS instance during a time interval and dividing it by the
	 *         interval duration. The interval is taken as the time period
	 *         between two consecutive calls to KmsMonitor#updateStats
	 */
	public double getDeltaNacks() {
		return deltaNacks;
	}

	void setDeltaNacks(double deltaNacks) {
		this.deltaNacks = deltaNacks;
	}

	/**
	 * 
	 * @return total number of PLI packets received per time unit. This is
	 *         calculated considering all PLIs received in all WebRtcEnpoints at
	 *         a KMS instance during a time interval and dividing it by the
	 *         interval duration. The interval is taken as the time period
	 *         between two consecutive calls to KmsMonitor#updateStats
	 */
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

	/**
	 * 
	 * @return total number of packets lost in all alive WebRtcEndpoints and
	 *         during their whole life of the KMS instance.
	 */
	public long getPacketLostCount() {
		return packetLostCount;
	}

	void setPacketLostCount(long packetLostCount) {
		this.packetLostCount = packetLostCount;
	}

}
