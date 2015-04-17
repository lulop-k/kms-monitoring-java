package org.kurento.tutorial.kmsmonitor;


public class KmsStats {
	private WebRtcStats webRtcStats;
	private int numPipelines;
	private int numElements;
	private int numWebRtcEndpoints;

	public WebRtcStats getWebRtcStats() {
		return webRtcStats;
	}

	void setWebRtcStats(WebRtcStats webRtcStats) {
		this.webRtcStats = webRtcStats;
	}

	public int getNumPipelines() {
		return numPipelines;
	}

	void setNumPipelines(int numPipelines) {
		this.numPipelines = numPipelines;
	}

	public int getNumElements() {
		return numElements;
	}

	void setNumElements(int numElements) {
		this.numElements = numElements;
	}

	public int getNumWebRtcEndpoints() {
		return numWebRtcEndpoints;
	}

	void setNumWebRtcEndpoints(int numWebRtcEndpoints) {
		this.numWebRtcEndpoints = numWebRtcEndpoints;
	}

}
