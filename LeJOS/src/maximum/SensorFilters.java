package maximum;
import lejos.hardware.port.*;
import lejos.hardware.sensor.*;
import lejos.robotics.*;
import lejos.robotics.filter.*;

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
public class SensorFilters {

	public static void main(String[] args) {
		SensorModes sensor = new EV3IRSensor(SensorPort.S4);
		SampleProvider distance = sensor.getMode("Distance");
		SampleProvider average = new MeanFilter(distance, 5);
		float[] sample = new float[average.sampleSize()];
		average.fetchSample(sample, 0);
	}

}
