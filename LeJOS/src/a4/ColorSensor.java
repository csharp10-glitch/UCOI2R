package a4;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class ColorSensor {
	EV3ColorSensor sensor;
	SampleProvider color;
	int mode;
	float[] sample;

	public ColorSensor(Port s4) {
		sensor = new EV3ColorSensor(s4);
		color = sensor.getMode("Color");
	}

	public int getColor() {
		sample = new float[color.sampleSize()];
		color.fetchSample(sample, 0);
		setMode(mode(sample));
		return getMode();
	}

	public void setMode(int newMode) {
		this.mode = newMode;
	}

	public int getMode() {
		return mode;
	}

	public int mode(float[] sample) {
		int modeCount1 = 0;
		int key1 = 0;
		int modeCount2 = 0;
		int key2 = 0;
		for (int i = 0; i < sample.length; i++) {
			key1 = (int) sample[i];
			modeCount1 = 1;
			for(int j = 1; j < (sample.length - i); j++) {
				if (sample[i+j]==key1) {
					modeCount1++;
				}
			}
			if (modeCount1 > modeCount2) {
				modeCount2 = modeCount1;
				key2 = key1;
			}
		}
		return key2;
	}

}
