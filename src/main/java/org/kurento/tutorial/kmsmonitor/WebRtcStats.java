package org.kurento.tutorial.kmsmonitor;

public class WebRtcStats {
	private Inbound inbound;
	private Outbound outbound;
	
	 WebRtcStats(Inbound inbound, Outbound outbound){
		this.inbound = inbound;
		this.outbound = outbound;
	}

	public Inbound getInbound() {
		return inbound;
	}
	
	public Outbound getOutbound() {
		return outbound;
	}
	
}
