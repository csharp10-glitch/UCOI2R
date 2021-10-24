package TheJOS;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class colorID {
	public final int black = 0;
	public final int white =  1;
	public final int red = 2;
	public final int orange = 3;
	public final int yellow = 4;
	public final int green = 5;
	public final int blue = 6;
	public final int purple = 7;
	public final int pink = 8;
	public final int browninsh = 9;
	
	public int detectColor(EV3ColorSensor colorSensor) {
		colorSensor.setCurrentMode("RGB");
		float[] sample = new float[colorSensor.sampleSize()];
		int colorID = fuzzyColor(sample);
		return colorID;
	}
			
	public int fuzzyColor(float[] color) {
		int length = color.length;
		int[] group = new int[length];
		for (int i = 0; i < length; i++){
			if (color[i]<0.002) {
				group[i] = 0;
			}
			else if(color[i]<0.003) {
				group[i]=1;
			}
			else {
				group[i]=2;
			}
		if (group[0]==0 & group[1]==0 & group[2]==0) {
			return 0;
		}
		if (group[0]==2 & group[1]==2 & group[2]==2) {
			return 1;
		}
		if (group[0]==2 & group[1]==0 & group[2]==0) {
			return 2;
		}
		if (group[0]==2 & group[1]==1 & group[2]==0) {
			return 3;
		}
		if (group[0]==2 & group[1]==2 & group[2]==0) {
			return 4;
		}
		if (group[0]==0 & group[1]==2 & group[2]==0) {
			return 5;
		}
		if ((group[0]==0 & group[1]==0 & group[2]==2)|(group[0]==0 & group[1]==1 & group[2]==2)|(group[0]==0 & group[1]==2 & group[2]==2)) {
			return 6;
		}
		if (group[0]==1 & group[1]==0 & group[2]==2) {
			return 7;
		}
		if (group[0]==2 & group[1]==0 & group[2]==2) {
			return 8;
		}
		if (group[0]==1 & group[1]==1 & group[2]==1) {
			return 9;
		}
		}
		return 9;
	}

}
