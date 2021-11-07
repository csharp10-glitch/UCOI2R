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
public class BehaviorRemote implements Behavior {

	RegulatedMotor left;
	RegulatedMotor right;
	SharedIRSensor ir; 
	boolean _continue = false;
	
	public BehaviorRemote(RegulatedMotor left, RegulatedMotor right, SharedIRSensor ir) {
		this.left = left;
		this.right = right;
		this.ir = ir;
	}
		
	@Override
	public boolean takeControl() {
		return (ir.control != 0);
	}

	@Override
	public void action() {
		
		_continue = true;
		long old_time = 0;
		
		while (_continue) {
			
	        switch(ir.control) {
	        case 0:
	            left.stop();
	            right.stop();
	            break;
	        case 1:
	            left.backward();
	            old_time = System.currentTimeMillis();
	            break;
	        case 2:
	            left.forward();
	            old_time = System.currentTimeMillis();
	            break;
	        case 3:
	            right.backward();
	            old_time = System.currentTimeMillis();
	            break;
	        case 4:
	        	right.forward();
	        	old_time = System.currentTimeMillis();
	            break;
	        case 5:
	        	right.backward();
	        	left.backward();
	        	old_time = System.currentTimeMillis();
	            break;
	        case 6:
	        	right.forward();
	        	left.backward();
	        	old_time = System.currentTimeMillis();
	            break;
	        case 7:
	        	left.forward();
	        	right.backward();
	        	old_time = System.currentTimeMillis();
	            break;
	        case 8:
	        	right.forward();
	        	left.forward();
	        	old_time = System.currentTimeMillis();
	            break;
	        case 9:
	        	System.exit(1); 
	        }
	        if(System.currentTimeMillis() - old_time > 3000) _continue = false;
	    }
	}

	@Override
	public void suppress() {
		_continue = false;
	}

}
