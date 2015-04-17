package org.kurento.tutorial.kmsmonitor;

public class DoubleCalculator {
	
	private double sum = 0;
	private int num = 0;
	
	public synchronized void addSample(double value){
		num ++;
		sum += value;
	}
	
	public synchronized double getSum(){
		return sum;
	}
	
	public synchronized double getAverage(){
		if(num == 0)
			return 0.0;
		
		return sum / (double)num;
	}
}
