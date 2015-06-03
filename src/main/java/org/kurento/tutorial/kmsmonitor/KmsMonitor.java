package org.kurento.tutorial.kmsmonitor;

import java.util.List;
import java.util.Map;

import org.kurento.client.KurentoClient;
import org.kurento.client.MediaObject;
import org.kurento.client.MediaPipeline;
import org.kurento.client.RTCInboundRTPStreamStats;
import org.kurento.client.RTCOutboundRTPStreamStats;
import org.kurento.client.RTCStats;
import org.kurento.client.ServerManager;
import org.kurento.client.WebRtcEndpoint;

/**
 * This class makes possible to connect to a given KMS instance and recover
 * statistics from it. The specific KMS instance needs to be specified at built
 * time as a WebSocket URI. After that, the <code>updateStats</code> method needs to be called
 * with the desired frequency for gathering that KMS instance statistics.
 * 
 * Every time <code>updateStats</code> is called, statistics for that instant are gathered.
 * 
 * Example
 * <code>
 * KmsMonitor monitor = new KmsMonitor("ws://localhost:8888/kurento");
 * KmsStats stats = monitor.updateStats()
 *... access stats here. 
 *
 * 
 * </code>
 * 
 * 
 * @author llopez
 * 
 */
public class KmsMonitor {

	private String kmsWsUri;
	private long iterations = 0;
	private boolean iterating = false;

	private DoubleCalculator inboundJitter = new DoubleCalculator();
	private DoubleCalculator inboundFractionLost = new DoubleCalculator();
	private LongCalculator inboundByteCount = new LongCalculator();
	private LongCalculator inboundPacketLostCount = new LongCalculator();
	private LongCalculator inboundDeltaPlis = new LongCalculator();
	private LongCalculator inboundDeltaNacks = new LongCalculator();

	private DoubleCalculator outboundRtt = new DoubleCalculator();
	private DoubleCalculator outboundTargetBitrate = new DoubleCalculator();
	private LongCalculator outboundByteCount = new LongCalculator();
	private LongCalculator outboundDeltaPlis = new LongCalculator();
	private LongCalculator outboundDeltaNacks = new LongCalculator();

	private LongCalculator numPipelines = new LongCalculator();
	private LongCalculator numElements = new LongCalculator();
	private LongCalculator numWebRtcEndpoints = new LongCalculator();

	public KmsMonitor(String kmsWsUri) {
		this.kmsWsUri = kmsWsUri;
	}

	public synchronized KmsStats updateStats() {

		KurentoClient kurentoClient = createKurentoClient(kmsWsUri);

		startIteration();

		try {
			crunchKms(kurentoClient);
		} catch (Throwable t) {
			clean();
			throw new RuntimeException("Unexpected error while updating stats",
					t);
		} finally {
			kurentoClient.destroy();
		}
		return stopIteration();
	}

	private void crunchKms(KurentoClient kurentoClient) {
		ServerManager serverManager = kurentoClient.getServerManager();
		List<MediaPipeline> mediaPipelines = serverManager.getPipelines();
		numPipelines.addSample(mediaPipelines.size());
		for (MediaPipeline p : mediaPipelines) {
			crunchPipeline(p);
		}
	}

	private void crunchPipeline(MediaPipeline p) {

		try {
			List<MediaObject> mediaObjects = p.getChilds();
			numElements.addSample(mediaObjects.size());
			for (MediaObject o : mediaObjects) {
				if (o.getId().indexOf("WebRtcEndpoint") >= 0) {
					WebRtcEndpoint webRtcEndpoint = (WebRtcEndpoint) o;
					numWebRtcEndpoints.addSample(1);
					crunchWebRtcEndpoint(webRtcEndpoint);
				}
			}
		} catch (Throwable t) {
			// The pipeline may have been released. This does not need to be a
			// "severe" problem
			// TODO log t just in case
			t.printStackTrace();
		}
	}

	private void crunchWebRtcEndpoint(WebRtcEndpoint webRtcEndpoint) {
		try {
			Map<String, RTCStats> stats = webRtcEndpoint.getStats();
			for (RTCStats s : stats.values()) {
				switch (s.getType()) {
				case inboundrtp:
					RTCInboundRTPStreamStats inboudStats = (RTCInboundRTPStreamStats) s;
					inboundJitter.addSample(inboudStats.getJitter());
					inboundFractionLost
							.addSample(inboudStats.getFractionLost());
					inboundByteCount.addSample(inboudStats.getBytesReceived());
					inboundDeltaPlis.addSample(inboudStats.getPliCount());
					inboundPacketLostCount.addSample(inboudStats
							.getPacketsLost());
					inboundDeltaNacks.addSample(inboudStats.getNackCount());
					break;

				case outboundrtp:
					RTCOutboundRTPStreamStats outboundStats = (RTCOutboundRTPStreamStats) s;
					outboundRtt.addSample(outboundStats.getRoundTripTime());
					outboundTargetBitrate.addSample(outboundStats
							.getTargetBitrate());
					outboundByteCount.addSample(outboundStats.getBytesSent());
					outboundDeltaPlis.addSample(outboundStats.getPliCount());
					outboundDeltaNacks.addSample(outboundStats.getNackCount());
					break;

				default:
					break;
				}
			}
		} catch (Throwable t) {
			// The WebRtcEndpoint may have been released. This does not need to
			// be a "severe" problem
			// TODO log t just in case.
		}
	}

	private synchronized void startIteration() {
		if (iterating) {
			throw new RuntimeException(
					"Cannot invoke startIteration when another iteration is under execution");
		}
		iterating = true;
		iterations++;

		inboundJitter = new DoubleCalculator();
		inboundFractionLost = new DoubleCalculator();
		inboundByteCount = new LongCalculator();
		inboundDeltaPlis = new LongCalculator(inboundDeltaPlis.getSum(),
				inboundDeltaPlis.getTimestamp());
		inboundPacketLostCount = new LongCalculator();
		inboundDeltaNacks = new LongCalculator(inboundDeltaNacks.getSum(),
				inboundDeltaNacks.getTimestamp());

		outboundRtt = new DoubleCalculator();
		outboundTargetBitrate = new DoubleCalculator();
		outboundByteCount = new LongCalculator();
		outboundDeltaPlis = new LongCalculator(outboundDeltaPlis.getSum(),
				outboundDeltaNacks.getTimestamp());
		outboundDeltaNacks = new LongCalculator(outboundDeltaNacks.getSum(),
				outboundDeltaNacks.getTimestamp());

		numPipelines = new LongCalculator();
		numElements = new LongCalculator();
		numWebRtcEndpoints = new LongCalculator();
	}

	private synchronized void clean() {
		if (iterations == 0 || !iterating) {
			throw new RuntimeException(
					"Cannot invoke stopIteration while no iteration has been started");
		}
		iterating = false;
	}

	private synchronized KmsStats stopIteration() {

		Inbound inboundStats = new Inbound();
		inboundStats.setByteCount(inboundByteCount.getSum());
		inboundStats.setDeltaNacks(inboundDeltaNacks
				.getTemporalAveragedIncrement());
		inboundStats.setDeltaPlis(inboundDeltaPlis
				.getTemporalAveragedIncrement());
		inboundStats.setFractionLost(inboundFractionLost.getAverage());
		inboundStats.setJitter(inboundJitter.getAverage());
		inboundStats.setPacketLostCount(inboundPacketLostCount.getSum());

		Outbound outboundStats = new Outbound();
		outboundStats.setByteCount(outboundByteCount.getSum());
		outboundStats.setDeltaNacks(outboundDeltaNacks
				.getTemporalAveragedIncrement());
		outboundStats.setDeltaPlis(outboundDeltaNacks
				.getTemporalAveragedIncrement());
		outboundStats.setRtt(outboundRtt.getAverage());
		outboundStats.setTargetBitrate(outboundTargetBitrate.getAverage());

		WebRtcStats webRtcStats = new WebRtcStats(inboundStats, outboundStats);

		KmsStats kmsStats = new KmsStats();
		kmsStats.setWebRtcStats(webRtcStats);
		kmsStats.setNumPipelines((int) numPipelines.getSum());
		kmsStats.setNumWebRtcEndpoints((int) numWebRtcEndpoints.getSum());
		kmsStats.setNumElements((int) numElements.getSum());

		clean();

		return kmsStats;
	}

	private KurentoClient createKurentoClient(String kmsWsUri) {
		try {
			KurentoClient kurentoClient = KurentoClient.create(kmsWsUri);
			return kurentoClient;
		} catch (Throwable t) {
			throw new RuntimeException("Cannot connect to KMS instance in URI "
					+ kmsWsUri, t);
		}
	}

}
