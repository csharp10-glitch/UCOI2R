package TheJOS;

import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

public class A2Rov3r {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		double diam = DifferentialPilot.WHEEL_SIZE_NXT1;
		double trackwidth = 20.32;
		double tw2 = 17.78;
		double tw3 = 19.1;
		double tw4 = 10.16;
		double tw5 = 0.8*trackwidth;
		DifferentialPilot rov3r = new DifferentialPilot(diam, tw5, Motor.C, Motor.B);

		int f = -1;
		
//		rov3r.arc(0, 180*f);
//		Delay.msDelay(1000);
//		rov3r.arc(0, 180*f);
//		Delay.msDelay(1000);
		rov3r.arc(0, 360*f);
		Delay.msDelay(1000);
		rov3r.arc(0, -360*f);
		Delay.msDelay(1000);
		rov3r.arc(0, 360*f);
		Delay.msDelay(1000);
		rov3r.arc(0, -360*f);
		Delay.msDelay(1000);
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
