package TheJOS;

import lejos.hardware.*;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;


public class HelloWorld {

	public static void main(String[] args) {
		System.out.println("Hello Stupid World");
		// Play warning tones:
				for(int i=0;i<3;i++) {
					Sound.playTone(500, 800);
					Delay.msDelay(200);
				}
				Sound.playTone(1000, 800);
				
//				// Initialize motors:
//				UnregulatedMotor b = new UnregulatedMotor(MotorPort.B);
//				UnregulatedMotor c = new UnregulatedMotor(MotorPort.C);
//				b.setPower(100);
//				c.setPower(100);
//				
//				// Start rotating motors:
//				b.forward();
//				c.forward();
//				Delay.msDelay(1500);
//				
//				// Stop motors and close:
//				b.flt();
//				c.flt();
//				b.close();
//				c.close();
				
				Sound.beep();
//				RegulatedMotor a = new EV3MediumRegulatedMotor(MotorPort.A);
//		        a.rotate(-2500);
//		        
//		        Sound.beepSequenceUp();
//		        
//				a.forward();
//				Delay.msDelay(250);
//				a.rotate(2500);
//				a.flt();
//				a.close();
				
		Button.waitForAnyPress();
	}

}
