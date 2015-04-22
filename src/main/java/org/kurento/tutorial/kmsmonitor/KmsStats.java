package org.kurento.tutorial.kmsmonitor;

/**
 * This class provides a data structure for holding relevant aggregate
 * statistics that can be gathered form a KMS instance. 
 * 
 * @author llopez
 * 
 */
public class KmsStats {
	private WebRtcStats webRtcStats;
	private int numPipelines;
	private int numElements;
	private int numWebRtcEndpoints;

	/** @return WebRTC specific statistics for the KMS instance.
	 * */
	public WebRtcStats getWebRtcStats() {
		return webRtcStats;
	}

	void setWebRtcStats(WebRtcStats webRtcStats) {
		this.webRtcStats = webRtcStats;
	}

	/**
	 * 
	 * @return number of active pipelines the KMS instance.
	 */
	public int getNumPipelines() {
		return numPipelines;
	}

	void setNumPipelines(int numPipelines) {
		this.numPipelines = numPipelines;
	}

	/**
	 * 
	 * @return total number of media elements in the KMS instance.
	 */
	public int getNumElements() {
		return numElements;
	}

	void setNumElements(int numElements) {
		this.numElements = numElements;
	}

	/**
	 * 
	 * @return total number of WebRtcEndpoints in the KMS instance
	 */
	public int getNumWebRtcEndpoints() {
		return numWebRtcEndpoints;
	}

	void setNumWebRtcEndpoints(int numWebRtcEndpoints) {
		this.numWebRtcEndpoints = numWebRtcEndpoints;
	}

}
