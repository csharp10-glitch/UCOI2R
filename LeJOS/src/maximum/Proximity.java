package maximum;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.*;
import lejos.robotics.*;
import lejos.utility.Delay;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 16 - Sensor Framework
 * Robot: Warbird
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
public class Proximity {
	
	public static void main(String [] agrs) {
		EV3IRSensor ir = new EV3IRSensor(SensorPort.S4);
		SensorMode distMode = ir.getMode("Distance");
		RangeFinderAdapter ranger = new RangeFinderAdapter(distMode);
		boolean keep_looping = true;
		
		while(keep_looping) {
			Delay.msDelay(50);
			
			LCD.drawString("D: " + ranger.getRange() + " cm  ", 0, 2);
			
			if(Button.ESCAPE.isDown()) keep_looping = false;
		}
		ir.close();
	}
}