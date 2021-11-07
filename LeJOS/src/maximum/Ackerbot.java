package maximum;

import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.robotics.*;
import lejos.robotics.navigation.*;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 25 - Ackerman Steering
 * Robot: Ackerbot
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
public class Ackerbot {

	//static RegulatedMotor motor;
	public static double GEAR_RATIO = 36.0/20.0;
	public static RegulatedMotor medium = null; 
	public static RegulatedMotor large = new EV3LargeRegulatedMotor(MotorPort.B);

	public static int CENTER = -74; // OLD L beam inverted= -71.
	public static int RIGHT = -CENTER;
	public static int LEFT = -51; // big jump between 50 and 51
	// carpet = 28, hardwood = 26:
	public static double MINTURN_RADIUS = 28;

	public static void main(String[] args) throws Exception {
		Ackerbot.calibrate(MotorPort.C);
		medium = new EV3MediumRegulatedMotor(MotorPort.C);
		Ackerbot.recenter(medium);
		medium.setAcceleration(200);

		// Multiply wheel size by gear ratio
		double size = SteeringPilot.WHEEL_SIZE_EV3 * GEAR_RATIO;

		SteeringPilot p = new SteeringPilot(size, large,
				medium, MINTURN_RADIUS, LEFT, RIGHT);
		large.setAcceleration(1000);

		p.travel(40);
		p.arc(-MINTURN_RADIUS, -360); // right
		p.travel(40);

		medium.rotateTo(0);
		large.close();
		medium.close();
	}

	public static void calibrate(Port port) {
		UnregulatedMotor m = new UnregulatedMotor(port);
		m.setPower(50);
		m.forward();
		int old = -999999;
		while(m.getTachoCount() != old) {
			old = m.getTachoCount();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
		m.flt();
				m.close();
	}

	public static void recenter(RegulatedMotor steering) {
		steering.setSpeed(100);
		steering.rotateTo(CENTER);
		steering.flt();
		steering.resetTachoCount();
	}
}