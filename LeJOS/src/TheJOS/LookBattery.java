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
//		System.out.println(Battery.getVoltageMilliVolt());
//		System.out.println(Battery.getBatteryCurrent());

		RegulatedMotor turnTable = new EV3MediumRegulatedMotor(MotorPort.D);
		
//		EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S4);
		EV3GyroSensor guyRho = new EV3GyroSensor(SensorPort.S4);
//		SensorMode mode = guyRho.getMode("RateAndAngle");
//		int rate = 0;
//		int angle = 0;
//		float [] sample = new float[mode.sampleSize()];
//		mode.fetchSample(sample, 0);
//		rate = (int)sample[0];
//		angle = (int)sample[1];
		
		SampleProvider sp = guyRho.getAngleMode();
		int value = 0;

        //Control loop
        final int iteration_threshold = 20;
        for(int i = 0; i <= iteration_threshold; i++) {

        	float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            value = (int)sample[0];

			System.out.println("Iteration: " + i);
			System.out.println("Gyro angle: " + value);

            Delay.msDelay(500);
        }
		
//		System.out.println(rate);
		turnTable.rotateTo(0);
		turnTable.rotateTo(180);
//		System.out.println(angle);
		turnTable.rotateTo(-180);
//		System.out.println(angle);
		turnTable.rotateTo(0);
//		System.out.println(angle + " " + rate);
		
		Sound.beep();
//		System.out.println(Battery.getVoltageMilliVolt());
//		System.out.println(Battery.getBatteryCurrent());
	}
}