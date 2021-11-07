package maximum;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.robotics.localization.*;
import lejos.robotics.navigation.*;

// UNTESTED FOR EV3 with Rov3r?
/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 26 - Localization
 * Robot: R0v3r
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
public class Odometry {

   public static void main(String[] args) {
      double diam = DifferentialPilot.WHEEL_SIZE_EV3;
      double width = 15.2;
      
      DifferentialPilot robot = new DifferentialPilot(diam, width, Motor.C, Motor.B);
      OdometryPoseProvider pp = new OdometryPoseProvider(robot); 
      LCD.drawString("Start: " + pp.getPose(), 0, 1);
      
      robot.rotate(90);
      robot.travel(100);
      robot.arc(30, 90);
      robot.travel(50);
      
      LCD.drawString("End: " + pp.getPose(), 0, 2);
      LCD.drawString("PRESS ESC", 0, 4);
      Button.ESCAPE.waitForPressAndRelease();
   }
}