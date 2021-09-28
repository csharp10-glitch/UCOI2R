package TheJOS;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;
import lejos.utility.Matrix;

public class WheeledChassisTest {
	public static void main(String [] args) {
		double diam = 5.60;
		double trackwidth = 7.0;
		RegulatedMotor b = new EV3LargeRegulatedMotor(MotorPort.B);
		RegulatedMotor c = new EV3LargeRegulatedMotor(MotorPort.C);
		Wheel leftWheel = new WheeledChassis.Modeler(b, diam).offset(-3.5);
		Wheel rightWheel = new WheeledChassis.Modeler(c, diam).offset(3.5);
		Wheel[] wheels = {leftWheel, rightWheel};
		WheeledChassis rov3r = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);

		EV3IRSensor ir = new EV3IRSensor(SensorPort.S4);
		SensorMode distMode = ir.getMode("Distance");
		int distance = 255;

		rov3r.travel(10);
		while(!Button.ESCAPE.isDown()) {

			float [] sample = new float[distMode.sampleSize()];
			distMode.fetchSample(sample, 0);
			distance = (int)sample[0];

			LCD.drawString("DIST:     ", 0, 2);
			LCD.drawInt(distance, 3, 5, 2);

			if(distance < 70 && distance !=0) {
				rov3r.travel(-20);
				rov3r.rotate(45);
//				rov3r.forward();
			} 
			Delay.msDelay(100);
		}
		ir.close();
	}
}
