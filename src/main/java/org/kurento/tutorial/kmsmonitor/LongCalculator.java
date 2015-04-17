package org.kurento.tutorial.kmsmonitor;

public class LongCalculator {
	private long sum = 0;
	private int num = 0;
	
	private long prevoiusValue;
	private long previousTimestamp;
	private long timestamp;
	
	public LongCalculator(long previousValue, long previousTimestamp){
		this.prevoiusValue = previousValue;
		this.previousTimestamp = previousTimestamp;
		this.timestamp = System.currentTimeMillis();
	}
	
	public LongCalculator(){
		this(0L, 0L);
	}
	
	public synchronized void addSample(long value){
		num ++;
		sum += value;
	}
	
	public synchronized long getSum(){
		return sum;
	}
	
	public synchronized double getAverage(){
		if(num == 0)
			return 0.0;
		
		return sum / (double)num;
	}
	
	public synchronized double getAveragedIncrement(){
		if(num == 0)
			return 0.0;

		return (sum - prevoiusValue) / (double) num;
	}
	
	public synchronized double getTemporalAveragedIncrement(){
		if(timestamp == previousTimestamp)
			return 0.0;
		
		return getAveragedIncrement() / (double) (timestamp - previousTimestamp);
	}
	
	public long getTimestamp(){
		return timestamp;
	}
}
