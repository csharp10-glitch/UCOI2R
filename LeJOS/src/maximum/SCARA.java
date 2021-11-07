package maximum;
import lejos.hardware.Sound;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.robotics.RegulatedMotor;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 19 - SCARA Arm
 * Robot: Scarab
 * Platform: EV3
 * @author Brian Bagnall
 * @version February 24, 2014
 */
public class SCARA {

	// Tacho count for full rotation:
	static final int SHOULDER_FULL_CIRCLE = 360;
	static final int ELBOW_FULL_CIRCLE = 360;
	
	static RegulatedMotor claw;
	static RegulatedMotor fore;
	static RegulatedMotor base;
	
	// Calibration limiter values:
	static int LIFT_CLAW = 1050;
	static int OPEN_CLAW = 0;
	static int FOREARM_STRAIGHT = -145;
	static int BASEARM_STRAIGHT = 95;
	
	// length of base arm (shoulder to elbow) cm
	static double ARM_BASE_LENGTH = 15.5;
	// length of fore arm (elbow to claw) cm
	static double ARM_FORE_LENGTH = 16.0;
	
	public static void main(String[] args) {
		
		SCARA arm = new SCARA();
		Sound.beepSequenceUp();
		arm.calibrate();
		
		// Any odd number array size works:
		double [] x = {23.5, 23.5, 27};
		double [] y = {9.4, -9.4, 1.7};
		
		int i=0;
		for(int r=0;r<10;r++) {
			arm.gotoXY(x[i], y[i]);
			arm.closeClaw();
			i++;
			if(i>=x.length) i=0;
			arm.gotoXY(x[i], y[i]);
			arm.openClaw();
			i++;
			if(i>=x.length) i=0;
		}
		claw.rotateTo(0);
		fore.rotateTo(0);
		base.rotateTo(0);
		Sound.beepSequence();
	}
	
	public void calibrate() {
		// Calibrate claw:
		calibrate(MotorPort.C, 20, true);
		claw = new EV3MediumRegulatedMotor(MotorPort.C);
		claw.setSpeed(100);
		claw.resetTachoCount();
		
		// calibrate base arm:
		calibrate(MotorPort.A, 20, true);
		base = new EV3LargeRegulatedMotor(MotorPort.A);
		base.setSpeed(10);
		base.rotate(BASEARM_STRAIGHT);
		base.resetTachoCount();
		
		// calibrate forearm:
		calibrate(MotorPort.B, 30, false);
		fore = new EV3LargeRegulatedMotor(MotorPort.B);
		fore.setSpeed(30);
		fore.rotate(FOREARM_STRAIGHT);
		fore.resetTachoCount();
		
		//System.out.println("BASE:" + base.getTachoCount());
		//System.out.println("FORE:" + fore.getTachoCount());
		//System.out.println("CLAW:" + claw.getTachoCount());
	}
	
	
	public void calibrate(Port port, int pwr, boolean reverse) {
		
		UnregulatedMotor motor = new UnregulatedMotor(port);
		motor.setPower(pwr);
		if(reverse)
			motor.backward();
		else
			motor.forward();
		int old = -99999999;
		while(motor.getTachoCount() != old) {
			old=motor.getTachoCount();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
		motor.stop();
		motor.close();
		Sound.beep();
	}
	
	
	public void openClaw() {
		claw.rotateTo(OPEN_CLAW);
	}
	
	public void closeClaw() {
		claw.rotateTo(LIFT_CLAW);
	}
	
	public void gotoXY(double x, double y) {
		
		double c = Math.sqrt(x * x + y * y);
		double angle1a = Math.asin(y/c);
		double angle1b = Math.acos((ARM_BASE_LENGTH * ARM_BASE_LENGTH + c * c - ARM_FORE_LENGTH * ARM_FORE_LENGTH)/(2F *ARM_BASE_LENGTH * c));
		
		double angle1 = angle1a + angle1b;
		double angle2 = Math.acos((ARM_BASE_LENGTH * ARM_BASE_LENGTH + ARM_FORE_LENGTH * ARM_FORE_LENGTH - c * c)/(2F *ARM_BASE_LENGTH * ARM_FORE_LENGTH));
		
		if(x < 0) {
			angle1 = Math.PI - angle1;
			angle2 = Math.PI - angle2 + Math.PI;
		}
		System.out.println("A1: " + Math.toDegrees(angle1));
		System.out.println("A2: " + Math.toDegrees(angle2));
		
		rotateShoulder(angle1);
		rotateElbow(angle2);
	}
	
	/**
	 * The neutral straight-arm position has the elbow at 180 degrees. If you try to 
	 * rotateElbow(0) the arm will fold up on itself.
	 * 
	 * @param toAngle Angle in rads (not degrees)
	 */
	public void rotateElbow(double toAngle) {
		// Arm starts at tacho 0 which is actually 180 degrees, hence subtract 1/2 ELBOW_FULL_CIRCLE
		//int toCount = (int)(((toAngle/(2.0*Math.PI)) * ELBOW_FULL_CIRCLE) - ELBOW_FULL_CIRCLE/2.0);
		int toCount = (int)Math.toDegrees(toAngle) - 180;
		System.out.println("ELBOW " + toCount);
		toCount *= -1.0; // Elbow motor reversed
		fore.rotateTo(toCount);
	}
	
	/**
	 * 
	 * @param toAngle Angle in rads (not degrees)
	 */
	public void rotateShoulder(double toAngle) {
		//int toCount = (int)((toAngle/(2.0*Math.PI)) * SHOULDER_FULL_CIRCLE);
		int toCount = (int)Math.toDegrees(toAngle);
		System.out.println("SHOUL " + toCount);
		toCount *= 1.0; // Shoulder NOT reversed
		base.rotateTo(toCount);
	}
}