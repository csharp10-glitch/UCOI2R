package TheJOS;

import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

@SuppressWarnings("deprecation")
public class A2Rov3r2 {
	public static void main(String[] args) {
		double diam = DifferentialPilot.WHEEL_SIZE_NXT1;
		double trackwidth = 20.32;  // Outer edge of wheel to outer edge of wheel
		double twMod = 0.8*trackwidth;
		DifferentialPilot rov3r = new DifferentialPilot(diam, twMod, Motor.C, Motor.B);

//		Angle
		double a = 180;
//		Testing to see if a constant modifier to the angle canproduce better results
		double mod = 1.1;
//		I'm lazy and don't feel like typing 1000 all the time
		int timeMS = 1000;
//		I'm lazy and want a central delay time
		int delay = 3;
		
//		Turn 180 degrees with a 1ft arc
		rov3r.arc(25.4, mod*a);
		Delay.msDelay(delay*timeMS);
//		turn around in place
		rov3r.arc(0, -180);
		Delay.msDelay(delay*timeMS);
//		return to home
		rov3r.arc(-25.4, -mod*a);
		Delay.msDelay(delay*timeMS);
//		turn back around in place
		rov3r.arc(0, 180);
		
	}
}
