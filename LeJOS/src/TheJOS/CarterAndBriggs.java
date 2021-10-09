package TheJOS;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

public class CarterAndBriggs {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		double diam = 5.6;
		double trackwidth = 15.2;
		RegulatedMotor rm = new EV3LargeRegulatedMotor(MotorPort.B);
		RegulatedMotor lm = new EV3LargeRegulatedMotor(MotorPort.C);
		
		double r1 = 0;
		double r2 = 10;
		
		rm.setAcceleration(1);
		lm.setAcceleration(-1);
		
		rm.setSpeed(10);
		lm.setSpeed(-10);
		
		rm.forward();
		lm.forward();
		Delay.msDelay(1500);
		
		// Stop motors and close:
		rm.flt();
		lm.flt();
		rm.close();
		lm.close();
		
		int dtheta = 180;
		int r = 10;
		int dt = 10000;
		int omega = dtheta/dt;
		
		ZTR ztr = new ZTR(diam, trackwidth);
		
		lm.setAcceleration(1);
		
		rm.setSpeed(ztr.vR(r)*omega);
		lm.setSpeed(ztr.vL(r)*omega);
		
		rm.forward();
		lm.forward();
		Delay.msDelay(dt);
		
		
		
	}
}
