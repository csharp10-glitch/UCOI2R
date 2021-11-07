package maximum;
import lejos.hardware.*;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.hardware.sensor.*;
import lejos.robotics.*;
import lejos.utility.Delay;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 4 - Motors, Sensors and Accessories
 * Robot: Warbird
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
public class ColorAttack implements KeyListener {

	public static boolean keepGoing = true;
	
	public static void main(String[] args) {
		Button.ESCAPE.addKeyListener(new ColorAttack());
		
		EV3MediumRegulatedMotor m = new EV3MediumRegulatedMotor(MotorPort.A); 
		
		EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S3);
		ColorAdapter ca = new ColorAdapter(cs);
		
		while(keepGoing) {
			Color c = ca.getColor();
			// calculate red percentage:
			double total = c.getRed() + c.getBlue() + c.getGreen();
			double red_ratio = c.getRed() / total;
			
			System.out.println("RED " + red_ratio);
			if(red_ratio > 0.33) {
				Sound.playTone(1000, 100);
				m.rotate((36/12)*-360, false);
			} else
				Sound.playTone(500, 100);
			Delay.msDelay(1000);
		}
		cs.close();
		m.close();

	}

	public void keyPressed(Key k) {}

	public void keyReleased(Key k) {
		keepGoing = false;
	}
}
