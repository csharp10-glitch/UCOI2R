package finalProject;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MoveController;
import lejos.robotics.navigation.MovePilot;

public class Pilot extends MovePilot {
	static final double PILOT_LINEAR_SPEED = 20; // m/s
	static final double PILOT_ANGULAR_SPEED = 10; // deg/s
	static final double OFFSET = 20.32 * 0.8 * 0.5; // m, 1/2 track width times a correction modifier
	static final double FWD_INCREMENT = 1; // m
	static final double MAX_TURN_ANGLE = 15; // deg
	static final double TURN_INCREMENT = 1; // deg
	
	private boolean ballCaptured = false;
	private boolean ballReleased = false;
	private boolean outOfMaze = false;
	
	// Our default pilot constructing
	public Pilot() {
		super(initChassis(OFFSET));
		setLinearSpeed(PILOT_LINEAR_SPEED);
		setAngularSpeed(PILOT_ANGULAR_SPEED);
	}
	
	// Our default chassis settings
	static Chassis initChassis(double wheelOffset) {
		double wheelDiam = MoveController.WHEEL_SIZE_NXT1;
		EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.D);
		EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.A);
		Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, wheelDiam).offset(-1 * wheelOffset);
		Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, wheelDiam).offset(wheelOffset);
		return new WheeledChassis(new Wheel[] { leftWheel, rightWheel }, WheeledChassis.TYPE_DIFFERENTIAL);
	}
	
	public boolean isBallCaptured() {
		return ballCaptured;
	}
	
	public void setBallCaptured(boolean ballCaptured) {
		this.ballCaptured = ballCaptured;
	}
	
	public boolean isBallReleased() {
		return ballReleased;
	}
	
	public void setBallReleased(boolean ballReleased) {
		this.ballReleased = ballReleased;
	}
	
	public boolean isOutOfMaze() {
		return outOfMaze;
	}
	
	public void setOutOfMaze(boolean outOfMaze) {
		this.outOfMaze = outOfMaze;
	}
}

