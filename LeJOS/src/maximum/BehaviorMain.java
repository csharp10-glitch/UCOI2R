package maximum;

import lejos.robotics.*;
import lejos.robotics.subsumption.*;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.hardware.sensor.*;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 18 - Behavior Based Robotics
 * Robot: Warbird
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
public class BehaviorMain {

	static Arbitrator arby;
	
	public static void main(String[] args) {
		RegulatedMotor left = new EV3LargeRegulatedMotor(MotorPort.B);
		RegulatedMotor right = new EV3LargeRegulatedMotor(MotorPort.D);
		SharedIRSensor ir = new SharedIRSensor();
		
		Behavior b1 = new BehaviorForward(left, right);
		Behavior b2 = new BehaviorProximity(left, right, ir);
		Behavior b3 = new BehaviorRemote(left, right, ir);
		Behavior [] behave = {b1, b2, b3};
		arby = new Arbitrator(behave);
		arby.go();
	}
}

class SharedIRSensor extends Thread {
	
	EV3IRSensor ir = new EV3IRSensor(SensorPort.S4);
	SampleProvider sp = ir.getDistanceMode();
	public int control = 0;
	public int distance = 255;

	SharedIRSensor() {
		this.setDaemon(true);
		this.start();
	}
	
	public void run() {
		while (true) {
			float [] sample = new float[sp.sampleSize()];
			control = ir.getRemoteCommand(0);
			sp.fetchSample(sample, 0);
			if((int)sample[0] == 0)
				distance = 255;
			else
				distance = (int)sample[0];
			LCD.drawString("Control: " + control, 0, 0);
			LCD.drawString("Distance: " + distance + "   ", 0, 1);
			Thread.yield();
		}
	}
}
