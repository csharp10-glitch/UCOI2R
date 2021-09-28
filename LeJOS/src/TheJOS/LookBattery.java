package TheJOS;

import lejos.hardware.Battery;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class LookBattery {
	public static void main(String[] args) {
		Sound.beep();
		System.out.println(Battery.getVoltageMilliVolt());
		System.out.println(Battery.getBatteryCurrent());

		RegulatedMotor turnTable = new EV3MediumRegulatedMotor(MotorPort.A);
		turnTable.rotateTo(0);
		turnTable.rotateTo(180);
		turnTable.rotateTo(-180);
		turnTable.rotateTo(0);
		
		Sound.beep();
		System.out.println(Battery.getVoltageMilliVolt());
		System.out.println(Battery.getBatteryCurrent());
	}
}