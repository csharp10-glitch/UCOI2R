package maximum;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Behavior;

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
public class BehaviorProximity implements Behavior {

	RegulatedMotor left;
	RegulatedMotor right;
	SharedIRSensor ir; 
	boolean backing_up = false;
	
	public BehaviorProximity(RegulatedMotor left, RegulatedMotor right, SharedIRSensor ir) {
		this.left = left;
		this.right = right;
		this.ir = ir;
	}
	
	@Override
	public boolean takeControl() {
		return (ir.distance < 50);
	}

	@Override
	public void action() {
		backing_up = true;
		
		left.rotate(600, true);
		right.rotate(600);
		
		left.rotate(-450, true);
		right.rotate(450);
		
		backing_up = false;
	}

	@Override
	public void suppress() {
		// Wait until backup done
		while(backing_up) {Thread.yield();}
	}
}
