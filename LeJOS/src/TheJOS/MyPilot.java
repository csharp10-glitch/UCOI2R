package TheJOS;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MoveController;
import lejos.robotics.navigation.MovePilot;

public class MyPilot extends MovePilot{

	public MyPilot(double wheelOffset) {
		super(initChassis(wheelOffset));
	}

	static Chassis initChassis(double wheelOffset) {
		double wheelDiam = MoveController.WHEEL_SIZE_NXT1;
		EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, wheelDiam).offset(-1*wheelOffset);
		Wheel rightWheel = WheeledChassis.modelWheel(leftMotor, wheelDiam).offset(wheelOffset);
		return new WheeledChassis(new Wheel[] { leftWheel, rightWheel }, WheeledChassis.TYPE_DIFFERENTIAL);
	}
	
	/**
	 * 
	 * @param speed
	 * @param angle
	 */
	public void moveLinear(double speed, double distance) {
		setLinearSpeed(speed);
		travel(distance);
	}
	
	/**
	 * 
	 * @param speed
	 * @param angle
	 */
	public void turn(double speed, double angle) {
		setAngularSpeed(speed);
		rotate(angle);
	}
}