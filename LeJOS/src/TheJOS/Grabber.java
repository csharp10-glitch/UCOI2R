package TheJOS;

import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 9 - Instant Java
 * Robot: EV3 Brick
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
public class Grabber {

	public static void main(String[] args) {
		Sound.beep();
		RegulatedMotor m1 = new EV3MediumRegulatedMotor(MotorPort.D);
        //m1.setSpeed(120);
        m1.rotate(-2500);
        
        Sound.beepSequenceUp();
        m1.rotate(2500);
	}

}
