package TheJOS;

import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

public class A2Rov3r {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		double diam = DifferentialPilot.WHEEL_SIZE_NXT1;
		double trackwidth = 15.2;
		DifferentialPilot rov3r = new DifferentialPilot(diam, trackwidth, Motor.C, Motor.B);

		rov3r.arc(0, 180);
		Delay.msDelay(1000);
		rov3r.arc(0, 180);
		Delay.msDelay(1000);
		rov3r.arc(0, 360);
		Delay.msDelay(1000);
		rov3r.arc(0, 360);
		Delay.msDelay(1000);
		rov3r.arc(0, 360);
		Delay.msDelay(1000);
		rov3r.arc(0, 360);
		Delay.msDelay(1000);
		
		rov3r.arc(0, -360);
		Delay.msDelay(1000);
		rov3r.arc(0, -360);
		Delay.msDelay(1000);
		rov3r.arc(0, -360);
		Delay.msDelay(1000);
		rov3r.arc(0, -360);
		Delay.msDelay(1000);
		rov3r.arc(0, -360);
		Delay.msDelay(1000);
		
		rov3r.arc(10, 360);
		Delay.msDelay(1000);
		
		rov3r.arc(10, 180);
		Delay.msDelay(1000);
		rov3r.arc(0, 180);
		Delay.msDelay(1000);
		rov3r.arc(10, -180);
		Delay.msDelay(1000);
		rov3r.arc(0, 180);
		Delay.msDelay(1000);
		rov3r.arc(10, 180);
		Delay.msDelay(1000);
		rov3r.arc(0, 180);
		Delay.msDelay(1000);
		rov3r.arc(10, -180);
		Delay.msDelay(1000);
		rov3r.arc(0, 180);
		Delay.msDelay(1000);
		rov3r.arc(10, 180);
		Delay.msDelay(1000);
		rov3r.arc(0, 180);
		Delay.msDelay(1000);
		rov3r.arc(10, -180);
		Delay.msDelay(1000);
		rov3r.arc(0, 180);
		Delay.msDelay(1000);
		
		
	}
}
