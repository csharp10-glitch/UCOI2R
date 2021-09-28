package TheJOS;

import lejos.hardware.Battery;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;

public class ClawBatteryTest {
	public static void main(String[] args) {
		Sound.beep();
		System.out.println(Battery.getVoltageMilliVolt());
		System.out.println(Battery.getBatteryCurrent());

		RegulatedMotor theClaw = new EV3MediumRegulatedMotor(MotorPort.D);
		theClaw.rotateTo(0);
		theClaw.rotateTo(-90);
		theClaw.rotateTo(0);
		
		Sound.beep();
		System.out.println(Battery.getVoltageMilliVolt());
		System.out.println(Battery.getBatteryCurrent());
	}
}