package org.kurento.tutorial.kmsmonitor;

/**
 * This class provides a data structure for holding WebRTC specific stats.
 * 
 * @author llopez
 * 
 */
public class WebRtcStats {
	private Inbound inbound;
	private Outbound outbound;

	WebRtcStats(Inbound inbound, Outbound outbound) {
		this.inbound = inbound;
		this.outbound = outbound;
	}

	/**
	 * 
	 * @return stats for the inbound (incoming to the KMS instance) streams
	 */
	public Inbound getInbound() {
		return inbound;
	}

	/**
	 * 
	 * @return stats for the outbound (outgoing from the KMS instance) streams
	 */
	public Outbound getOutbound() {
		return outbound;
	}

}
