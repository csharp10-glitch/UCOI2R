package TheJOS;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.navigation.DifferentialPilot;

public class Circle {
	double diam = DifferentialPilot.WHEEL_SIZE_NXT1;
	double trackwidth = 15.2;
	DifferentialPilot rov3r = new DifferentialPilot(diam, trackwidth, Motor.C, Motor.B);

	
	
	
}
