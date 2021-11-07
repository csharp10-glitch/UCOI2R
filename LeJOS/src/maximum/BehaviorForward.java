package maximum;

import lejos.robotics.*;
import lejos.robotics.subsumption.*;

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
public class BehaviorForward implements Behavior {

	RegulatedMotor left;
	RegulatedMotor right;
		
	public BehaviorForward(RegulatedMotor left, RegulatedMotor right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		left.backward();
		right.backward();
	}

	@Override
	public void suppress() {
		// nothing to suppress
	}

}
