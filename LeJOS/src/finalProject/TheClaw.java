package finalProject;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.Port;

public class TheClaw {
	Port clawPort = null;
	EV3MediumRegulatedMotor clawMotor = null;
	int openRotation;
	int closedRotation;
	int deltaRotation;
	int currentRotation;
	
	// Initiate THE CLAW!
	public TheClaw(Port c) {
		this.clawPort = c;
		clawMotor = new EV3MediumRegulatedMotor(c);
		
		// automatically find the widest open angle for the claw
		while (!clawMotor.isStalled()) {
			clawMotor.rotate(-5);
		}
		// set the stalled position to zer0
		clawMotor.resetTachoCount();
		openRotation = clawMotor.getLimitAngle();
		
		// unstall
		clawMotor.rotate(5);
		// look for completely closed rotation
		while (!clawMotor.isStalled()) {
			clawMotor.rotate(5);
		}
		closedRotation = clawMotor.getLimitAngle();
		deltaRotation = closedRotation - openRotation;
		// open that bad boy up
		clawMotor.rotateTo(25);
		currentRotation = clawMotor.getLimitAngle();
	}
	
	public void grab() {
		clawMotor.rotateTo(closedRotation-3);
		currentRotation = clawMotor.getLimitAngle();
	}
	
	// TODO @soups check pls
	public void release() {
		clawMotor.rotateTo(25);
		currentRotation = clawMotor.getLimitAngle();
	}
	
	public int checkRotation() {
		currentRotation = clawMotor.getLimitAngle();
		return currentRotation;
	}
	
	public int getClosedRotation() {
		return closedRotation;
	}

}
