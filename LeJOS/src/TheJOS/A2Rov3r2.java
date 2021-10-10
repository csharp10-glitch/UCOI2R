package TheJOS;

import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

public class A2Rov3r2 {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		double diam = DifferentialPilot.WHEEL_SIZE_NXT1;
		double trackwidth = 20.32;
		double tw2 = 17.78;
		double tw3 = 19.1;
		double tw4 = 0.80*trackwidth;
		DifferentialPilot rov3r = new DifferentialPilot(diam, tw4, Motor.C, Motor.B);

		int f = -1;
		
//		rov3r.arc(0, 180*f);
//		Delay.msDelay(1000);
//		rov3r.arc(0, 180*f);
//		Delay.msDelay(1000);
//		rov3r.arc(0, -360*f);
//		Delay.msDelay(1000);
//		rov3r.arc(0, 360*f);
//		Delay.msDelay(1000);
//		rov3r.arc(0, -360*f);
//		Delay.msDelay(1000);
//		rov3r.arc(0, 360);
//		Delay.msDelay(1000);
//		rov3r.arc(0, 360);
//		Delay.msDelay(1000);
		
//		rov3r.arc(0, -360);
//		Delay.msDelay(1000);
//		rov3r.arc(0, -360);
//		Delay.msDelay(1000);
//		rov3r.arc(0, -360);
//		Delay.msDelay(1000);
//		rov3r.arc(0, -360);
//		Delay.msDelay(1000);
//		rov3r.arc(0, -360);
//		Delay.msDelay(1000);
//		
//		rov3r.arc(10, 360);
//		Delay.msDelay(1000);
//		
		double a = 180;
		double mod = 1.1;
		int timeMS = 1000;
		int delay = 3;
		
		rov3r.arc(25.4, mod*a);
		Delay.msDelay(delay*timeMS);
		rov3r.arc(0, -180);
		Delay.msDelay(delay*timeMS);
		rov3r.arc(-25.4, -mod*a);
		Delay.msDelay(delay*timeMS);
		rov3r.arc(0, 180);
//		Delay.msDelay(1000);
//		rov3r.arc(0, 180);
//		Delay.msDelay(1000);
//		rov3r.arc(10, 180);
//		Delay.msDelay(1000);
//		rov3r.arc(0, 180);
//		Delay.msDelay(1000);
//		rov3r.arc(10, -180);
//		Delay.msDelay(1000);
//		rov3r.arc(0, 180);
//		Delay.msDelay(1000);
//		rov3r.arc(10, 180);
//		Delay.msDelay(1000);
//		rov3r.arc(0, 180);
//		Delay.msDelay(1000);
//		rov3r.arc(10, -180);
//		Delay.msDelay(1000);
//		rov3r.arc(0, 180);
//		Delay.msDelay(1000);
		
		
	}
}
