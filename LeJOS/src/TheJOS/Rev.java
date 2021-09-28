package TheJOS;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.hardware.sensor.*;
import lejos.hardware.Button;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.*;
import lejos.utility.Delay;
import lejos.utility.Matrix;

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
public class Rev {
	RegulatedMotor rm = new EV3LargeRegulatedMotor(MotorPort.B); 
	Wheel wr = new WheeledChassis.Modeler(rm, 56);
	RegulatedMotor lm = new EV3LargeRegulatedMotor(MotorPort.A); 
	Wheel wl = new WheeledChassis.Modeler(lm, 56);
	Wheel[] wheels = {wl, wr};
	Chassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);


	public static void main(String [] args) {
		
	}
}