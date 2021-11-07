package maximum;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.hardware.sensor.*;
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
public class Seeker {
	
	static EV3IRSensor ir = new EV3IRSensor(SensorPort.S4);
	static EV3MediumRegulatedMotor a = new EV3MediumRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor b = new EV3LargeRegulatedMotor(MotorPort.B);
	static EV3LargeRegulatedMotor d = new EV3LargeRegulatedMotor(MotorPort.D);
	
	public static void main(String [] agrs) {
		boolean keep_looping = true;
		boolean fired = false;
		
		SensorMode seek = ir.getMode("Seek");
		int speed = 100;
		float [] vals = new float[8];
		
		while(keep_looping) {
			Delay.msDelay(50);
			
			seek.fetchSample(vals, 0);
			int bearing = (int)vals[0];
			int range = (int)vals[1];
			LCD.drawString("B: " + bearing + " R: " + range + "cm   ", 0, 2);
			
			if(range==0&(bearing==128|bearing==0)) {
				// Acquire IR beacon:
				b.setSpeed(speed);
				d.setSpeed(speed);
				b.forward();
				d.backward();
				fired = false;
			} else if(range<25){ 
				// Firing range!
				b.flt();
				d.flt();
				if(!fired) {
					a.rotate((36/12)*-360);
					fired = true;
				}
			} else if(bearing<0) {
				// veer left B faster
				b.setSpeed(2*speed);
				d.setSpeed(speed);
				b.backward();
				d.backward();
				fired = false;
			} else if(bearing>0){
				// veer right D faster
				b.setSpeed(speed);
				d.setSpeed(2*speed);
				b.backward();
				d.backward();
				fired = false;
			} else {
				// Forward march!
				b.setSpeed(2*speed);
				d.setSpeed(2*speed);
				b.backward();
				d.backward();
				fired = false;
			}
			
			if(Button.ESCAPE.isDown()) keep_looping = false;
		}
		ir.close();
		a.close();
		b.close();
		d.close();
	}
}
