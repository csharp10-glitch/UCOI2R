package a4;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.Port;

public class TheClaw {
	Port clawPort = null;
	EV3MediumRegulatedMotor clawMotor = null;
	int openRotation;
	int closedRotation;
	int deltaRotation;
	int currentRotation;
	
	public TheClaw(Port b) {
		this.clawPort = b;
		clawMotor = new EV3MediumRegulatedMotor(b);
		while (!clawMotor.isStalled()) {
			clawMotor.rotate(5);
		}
		clawMotor.resetTachoCount();
		openRotation = clawMotor.getLimitAngle();
		clawMotor.rotate(-5);
		while (!clawMotor.isStalled()) {
			clawMotor.rotate(-5);
		}
		closedRotation = clawMotor.getLimitAngle();
		deltaRotation = closedRotation - openRotation;
		clawMotor.rotateTo(openRotation-3);
		currentRotation = clawMotor.getLimitAngle();
	}
	
	public void startTest() {
		// confirm claw is open, or open claw
		int diff = Math.abs(clawMotor.getLimitAngle() - openRotation);
		if (diff > 5) {
			while (!clawMotor.isStalled()) {
				clawMotor.rotate(5);
			}
			clawMotor.resetTachoCount();
			openRotation = clawMotor.getLimitAngle();
			clawMotor.rotateTo(openRotation-3);
		}
	}
	
	public void grab() {
		clawMotor.rotateTo(closedRotation+3);
	}
	
	

}
