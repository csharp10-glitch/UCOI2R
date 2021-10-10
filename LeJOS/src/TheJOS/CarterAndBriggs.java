package TheJOS;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class CarterAndBriggs {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		double diam = 5.6;
		double trackwidth = 19.1;
		RegulatedMotor rm = new EV3LargeRegulatedMotor(MotorPort.B);
		RegulatedMotor lm = new EV3LargeRegulatedMotor(MotorPort.C);
		
		double r1 = 0;
		double r2 = 10;
		
//		rm.setAcceleration(360);
//		lm.setAcceleration(360);
		
		rm.setSpeed(700);
		lm.setSpeed(700);
		
		rm.forward();
		lm.backward();
		Delay.msDelay(1000);
		
		// Stop motors and close:
		rm.stop();
		lm.stop();
//		rm.close();
//		lm.close();
		
		int dtheta = 180;
		int r = 10;
		int dt = 1000;
		int omega = dtheta/dt;
		
		ZTR ztr = new ZTR(diam, trackwidth);
		
//		lm.setAcceleration(1);
		
//		rm.setSpeed(ztr.vR(r)*omega);
//		lm.setSpeed(ztr.vL(r)*omega);
//		
//		rm.forward();
//		lm.forward();
//		Delay.msDelay(dt);
//		
//		rm.flt();
//		lm.flt();
//		rm.close();
//		lm.close();
//		
//		rm.forward();
//		lm.forward();
//		Delay.msDelay(dt);
//		
//		rm.flt();
//		lm.flt();
//		rm.close();
//		lm.close();
		
		
		
	}
}
