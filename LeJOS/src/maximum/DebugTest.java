package maximum;
import lejos.hardware.*;
import lejos.hardware.lcd.*;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 15 - Debugging Spray
 * Robot: EV3 Brick
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
public class DebugTest {

	public static void main(String[] args) {
		System.out.println("Hello std");
		System.err.println("Hello err");
		int x = 6;
		LCD.drawString("HELLO", 0, 3);
		LCD.drawString("NUMBER " + x, 0, 4);
		x = x - 4;
		LCD.drawString("I AM", 0, 5);
		LCD.drawString("NUMBER " + x, 0, 6);
		Button.waitForAnyEvent();
	}
}
