package TheJOS;

import lejos.hardware.Battery;
import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class BatteryTest {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Sound.beep();
		System.out.println(Battery.getVoltageMilliVolt());
		System.out.println(Battery.getBatteryCurrent());
		@SuppressWarnings("deprecation")
		double diam = DifferentialPilot.WHEEL_SIZE_NXT1;
		double trackwidth = 7;
		DifferentialPilot rov3r = new DifferentialPilot(diam, trackwidth, Motor.C, Motor.B);

		rov3r.travel(25);
		Sound.beep();
		System.out.println(Battery.getVoltageMilliVolt());
		System.out.println(Battery.getBatteryCurrent());
	}
}
