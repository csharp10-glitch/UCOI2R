package maximum;
import lejos.hardware.*;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.utility.Delay;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 3 - The First
 * Robot: Drag Racer
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
public class DragRacer {

	public static void main(String[] args) {
		Button.waitForAnyPress();
		
		// Play warning tones:
		for(int i=0;i<3;i++) {
			Sound.playTone(500, 800);
			Delay.msDelay(200);
		}
		Sound.playTone(1000, 800);
		
		// Initialize motors:
		UnregulatedMotor b = new UnregulatedMotor(MotorPort.B);
		UnregulatedMotor c = new UnregulatedMotor(MotorPort.C);
		b.setPower(100);
		c.setPower(100);
		
		// Start rotating motors:
		b.forward();
		c.forward();
		Delay.msDelay(1500);
		
		// Stop motors and close:
		b.flt();
		c.flt();
		b.close();
		c.close();
	}
}