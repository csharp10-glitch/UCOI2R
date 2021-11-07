package maximum;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.robotics.*;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832222
 * Variant Press (C) 2012
 * Chapter 27 - Walking Robots
 * Robot: Shambler
 * Platform: NXT
 * @author Brian Bagnall
 * @version July 20, 2012
 */
public class Shambler {

	public static int STANDING = 145;
	public static int RETRACTED = 0;
	public static int BACKWARD = (int)(140 * 20.0/12.0); // movement gears 20 to 12
	public static int FORWARD = 0;
	public static double RATIO = 24.0/1.0; // screw gear ratio
	public static RegulatedMotor a_motor = null;
	public static RegulatedMotor b_motor = null;
	public static RegulatedMotor c_motor = null;

	public static void main(String[] args) {
		a_motor = new EV3LargeRegulatedMotor(MotorPort.A);
		b_motor = new EV3LargeRegulatedMotor(MotorPort.B);
		c_motor = new EV3MediumRegulatedMotor(MotorPort.C);

		a_motor.setSpeed(70); // vertical lifter
		b_motor.setSpeed(110); // lateral movement
		c_motor.setSpeed(110); // rotation
		
		for(int i=0;i<4;i++) {
			stepForward(3);
			rotate(90);
		}
	}

	public static void stepForward(int steps) {
		for(int i=0;i<steps;i++) {
			a_motor.rotateTo(STANDING);
			b_motor.setSpeed(300);
			b_motor.rotateTo(BACKWARD);
			b_motor.setSpeed(110);
			a_motor.rotateTo(RETRACTED);
			b_motor.rotateTo(FORWARD);
		}
	}

	public static void rotate(int degrees) {
		c_motor.rotate((int)(RATIO * degrees));
		a_motor.rotateTo(STANDING);
		c_motor.setSpeed(1000);
		c_motor.rotateTo(0);
		c_motor.setSpeed(70);
		a_motor.rotateTo(RETRACTED);
	}
}
