package TheJOS;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class MovePilotTest {
	public static void main(String [] args) {
		double diam = 5.6;
		double trackwidth = 7;
		@SuppressWarnings("deprecation")
		MovePilot rov3r = new MovePilot(diam, trackwidth, Motor.C, Motor.B);
//		MovePilot mov3r = new 

		EV3IRSensor ir = new EV3IRSensor(SensorPort.S4);
		SensorMode distMode = ir.getMode("Distance");
		int distance = 255;

		rov3r.forward();
		while(!Button.ESCAPE.isDown()) {

			float [] sample = new float[distMode.sampleSize()];
			distMode.fetchSample(sample, 0);
			distance = (int)sample[0];

			LCD.drawString("DIST:     ", 0, 2);
			LCD.drawInt(distance, 3, 5, 2);

			if(distance < 70 && distance !=0) {
				rov3r.travel(-20);
				rov3r.rotate(45);
				rov3r.forward();
			} 
			Delay.msDelay(100);
		}
		ir.close();
	}
}
