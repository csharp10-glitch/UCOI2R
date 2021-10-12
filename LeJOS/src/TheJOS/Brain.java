package TheJOS;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;

public class Brain {
	int startColor = 4;
	int endColor = 5;
	int ballColor = 2;
	int squareSize = 25;
	
	int[][] grid = new int[5][5];
	int[][] adjacencyMatrix = new int[5][5];
	int[][] incidenceMatrix = new int[5][5];
	
	EV3ColorSensor colourBlue = new EV3ColorSensor(SensorPort.S1);
	SensorMode colourID = colourBlue.getMode("ColorID");
	int colour = -1;
	EV3UltrasonicSensor SoundSei = new EV3UltrasonicSensor(SensorPort.S4);
	SensorMode distMode = SoundSei.getMode("Distance");
	int distance = 255;
	
	float[] colourSample = new float[colourBlue.getColorID()];
	float[] sample = new float[distMode.sampleSize()];

//	colour = (int) colourSample[0]; 

//	float[] sample = new float[distMode.sampleSize()];
//	distMode.fetchSample(sample, 0);
//	distance = (int) sample[0];
//	
	public boolean checkBlue(){
		colourSample = new float[colourBlue.getColorID()];
		colourID.fetchSample(colourSample,0);
		colour = (int) colourSample[0]; 
		if (colour == 2) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checkStart() {
		colourSample = new float[colourBlue.getColorID()];
		colourID.fetchSample(colourSample,0);
		colour = (int) colourSample[0]; 
		if (colour == startColor) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checkEnd() {
		colourSample = new float[colourBlue.getColorID()];
		colourID.fetchSample(colourSample,0);
		colour = (int) colourSample[0]; 
		if (colour == endColor) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checkOpen() {
		sample = new float[distMode.sampleSize()];
		distMode.fetchSample(sample, 0);
		distance = (int) sample[0];
		if (colour == startColor) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	

}
