package a4;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;

public class UltrasonicSensor {
	EV3UltrasonicSensor sensor;
	SampleProvider distMode;
	SampleProvider average;
	int distance;
	float[] sample;

	public UltrasonicSensor(Port s1) {
		sensor = new EV3UltrasonicSensor(s1);
		distMode = sensor.getMode("Distance");
		average = new MeanFilter(distMode, 5);
		sample = new float[average.sampleSize()];
		average.fetchSample(sample, 0);
	}

	public int distance() {
		sample = new float[distMode.sampleSize()];
		distMode.fetchSample(sample, 0);
		distance = (int) sample[0];
		return distance;
	}

//	ir.close();
}
