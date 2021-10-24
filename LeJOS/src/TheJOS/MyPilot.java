package TheJOS;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MoveController;
import lejos.robotics.navigation.MovePilot;

public class MyPilot extends MovePilot{
	public MyPilot(double wheelOffset, double linearSpeed, double angularSpeed) {
		super(initChassis(wheelOffset));
		setLinearSpeed(linearSpeed);
		setAngularSpeed(angularSpeed);
	}

	static Chassis initChassis(double wheelOffset) {
		double wheelDiam = MoveController.WHEEL_SIZE_NXT1;
		EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.D);
		EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.A);
		Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, wheelDiam).offset(-1*wheelOffset);
		Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, wheelDiam).offset(wheelOffset);
		return new WheeledChassis(new Wheel[] { leftWheel, rightWheel }, WheeledChassis.TYPE_DIFFERENTIAL);
	}	
	
	/**
	 * Rotate left slowly and check for line.
	 * @return true if line detected
	 */
	public boolean checkLineLeft(double maxAngle, double increment, int lineColor, EV3ColorSensor colourBlue) {
		for (double angleTurned = 0; angleTurned > -maxAngle; angleTurned -= increment) {
			rotate(angleTurned);
			if(detectColor(lineColor, colourBlue)|detectColor(13, colourBlue)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Rotate right slowly and check for line.
	 * @return true if line detected
	 */
	public boolean checkLineRight(double maxAngle, double increment, int lineColor, EV3ColorSensor colourBlue) {
		for (double angleTurned = 0; angleTurned < maxAngle; angleTurned += increment) {
			rotate(angleTurned);
			if(detectColor(lineColor, colourBlue)|detectColor(13, colourBlue)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean detectColor(int color, EV3ColorSensor colourBlue) {
		SensorMode colourID = colourBlue.getMode("ColorID");
		float[] colourSample = new float[1];
		colourID.fetchSample(colourSample,0);
		int colour = (int) colourSample[0]; 
		if (colour == color) {
			return true;	
		}
		if (colour == 1) {
			return true;	
		}
		if (colour == 13) {
			return true;	
		}
		
		return colour == color;
//		colorID colourID = new colorID();
//		int colour = colourID.detectColor(colourBlue);
//		return colour == color;
	}
}