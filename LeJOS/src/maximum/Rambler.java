package maximum;
import lejos.robotics.navigation.*;
import lejos.utility.Delay;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.*;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 23 - Coordinates
 * Robot: R0v3r
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
public class Rambler {

	public static final int AREA_WIDTH = 200;
	public static final int AREA_LENGTH = 200;
	public static boolean exit = false;   

	public static void main(String[] args) throws Exception {

		DifferentialPilot p = new DifferentialPilot(DifferentialPilot.WHEEL_SIZE_EV3, 15.2, Motor.C, Motor.B);
		Navigator nav = new Navigator(p);

		// Repeatedly drive to random points:
		while(!exit) {

			LCD.drawString("Target: ", 0, 1);
			double x_targ = Math.random() * AREA_WIDTH;
			double y_targ = Math.random() * AREA_LENGTH;
			LCD.drawString("X: " + (int)x_targ + "  ", 0, 3);
			LCD.drawString("Y: " + (int)y_targ + "  ", 0, 4);
			LCD.drawString("ENTER to drive", 0, 6);
			LCD.drawString("or press EXIT", 0, 7);
			Sound.beepSequenceUp();
			Button.LEDPattern(4);
			int button = Button.waitForAnyPress();
			Button.LEDPattern(0);

			if(button == Button.ID_ENTER) {
				nav.goTo(new Waypoint(x_targ, y_targ));
				nav.addWaypoint(new Waypoint(0, 0, 0));
				while(nav.isMoving())
					Delay.msDelay(50);

			} else exit = true;
		}
	}
}
