package TheJOS;

import lejos.hardware.Battery;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class LookBattery {
	public static void main(String[] args) {
		Sound.beep();

		RegulatedMotor turnTable = new EV3MediumRegulatedMotor(MotorPort.D);

		EV3GyroSensor guyRho = new EV3GyroSensor(SensorPort.S4);

		SampleProvider sp = guyRho.getAngleAndRateMode();
		float[] sample = new float[sp.sampleSize()];
		sp.fetchSample(sample, 0);

		guyRho.reset();
		int v1 = 0;
		int v2 = 0;
		
//		System.out.println(rate);
		turnTable.rotateTo(0);
		sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        v1 = (int)sample[0];
        v2 = (int)sample[1];
		System.out.println("Gyro angle: " + v1 + " Gyro rate: " + v2 );
		turnTable.rotateTo(180);
		sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        v1 = (int)sample[0];
        v2 = (int)sample[1];
		System.out.println("Gyro angle: " + v1 + " Gyro rate: " + v2 );
		turnTable.rotateTo(-180);
		sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        v1 = (int)sample[0];
        v2 = (int)sample[1];
		System.out.println("Gyro angle: " + v1 + " Gyro rate: " + v2 );
		turnTable.rotateTo(0);
		sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        v1 = (int)sample[0];
        v2 = (int)sample[1];
		System.out.println("Gyro angle: " + v1 + " Gyro rate: " + v2 );
		
		Sound.beep();
//		System.out.println(Battery.getVoltageMilliVolt());
//		System.out.println(Battery.getBatteryCurrent());
	}
}