package maximum;
import lejos.hardware.port.*;
import lejos.hardware.sensor.*;
import lejos.robotics.*;
import lejos.utility.Delay;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 16 - Sensor Framework
 * Robot: Warbird
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
public class SensorFramework {

	public static void main(String [] args) {
		EV3IRSensor sensor = new EV3IRSensor(SensorPort.S4);
		SampleProvider distance= sensor.getMode("Distance");
		float[] sample = new float[distance.sampleSize()];
		for(int i=0;i<20;i++) { 
		  distance.fetchSample(sample, 0);
		  System.out.println(i + ". " + sample[0]);
		  Delay.msDelay(250);
		}
		
	}
	
}
