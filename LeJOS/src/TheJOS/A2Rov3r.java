package TheJOS;

import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

@SuppressWarnings("deprecation")
public class A2Rov3r {
	public static void main(String[] args) {
		double diam = DifferentialPilot.WHEEL_SIZE_NXT1;
		double trackwidth = 20.32;  // Outer edge of wheel to outer edge of wheel
		double twMod = 0.8*trackwidth;
		DifferentialPilot rov3r = new DifferentialPilot(diam, twMod, Motor.C, Motor.B);

		
//		Test piecemeal 360
		rov3r.arc(0, 180);
		Delay.msDelay(1000);
		rov3r.arc(0, 180);
		Delay.msDelay(1000);
		
//		Test 4 360s
		rov3r.arc(0, 360);
		Delay.msDelay(1000);
		rov3r.arc(0, -360);
		Delay.msDelay(1000);
		rov3r.arc(0, 360);
		Delay.msDelay(1000);
		rov3r.arc(0, -360);
		Delay.msDelay(1000);
	}
}
