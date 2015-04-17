package org.kurento.tutorial.kmsmonitor;

public class ExampleWithMain {
	
	final static String DEFAULT_KMS_WS_URI = "ws://localhost:8888/kurento";
	
	public static void main(String[] args) throws InterruptedException {
		KmsMonitor kmsMonitor = new KmsMonitor(System.getProperty("kms.ws.uri", DEFAULT_KMS_WS_URI));
		
		while(true){
			KmsStats kmsStats = kmsMonitor.updateStats();
			System.out.println("Num.Pipelines, " + kmsStats.getNumPipelines());
			System.out.println("Num.Elements, " + kmsStats.getNumElements());
			System.out.println("Num.WebRtcEndpoints, " + kmsStats.getNumWebRtcEndpoints());
			
			System.out.println("Inbound.Byte.Count, "+ kmsStats.getWebRtcStats().getInbound().getByteCount());
			System.out.println("Inbound.Delta.Nacks, "+ kmsStats.getWebRtcStats().getInbound().getDeltaNacks());
			System.out.println("Inbound.Jitter, " + kmsStats.getWebRtcStats().getInbound().getJitter());
			
			
			Thread.sleep(3000);
		}	
	}
}
