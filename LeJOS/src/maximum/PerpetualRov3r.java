package maximum;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.hardware.sensor.*;
import lejos.hardware.Button;
import lejos.robotics.navigation.*;
import lejos.utility.Delay;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 18 - Moves
 * Robot: Rov3r
 * Platform: EV3
 * @author Brian Bagnall
 * @version May 30, 2014
 */
public class PerpetualRov3r {

	public static void main(String [] args) {
		double diam = DifferentialPilot.WHEEL_SIZE_EV3;
		double trackwidth = 15.2;
		DifferentialPilot rov3r = new DifferentialPilot(diam, trackwidth, Motor.C, Motor.B);

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