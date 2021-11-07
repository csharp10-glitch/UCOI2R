package maximum;
import lejos.hardware.lcd.*;
import lejos.hardware.motor.*;
import lejos.hardware.Button;
import lejos.robotics.navigation.*;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 18 - Moves
 * Robot: Rov3r
 * Platform: EV3
 * @author Brian Bagnall
 * @version May 30, 2014
 */

public class MeasureMoves {
	
   public static void main(String [] args) {
      double diam = DifferentialPilot.WHEEL_SIZE_EV3;
      double trackwidth = 15.2; // or 14.5
		
      DifferentialPilot rov3r = new DifferentialPilot(diam, trackwidth, Motor.C, Motor.B);
      rov3r.travel(100);
      LCD.drawString("Press ENTER", 0, 3);
      Button.ENTER.waitForPressAndRelease();
      rov3r.rotate(1080);
      Motor.B.close();
      Motor.C.close();
   }
}
