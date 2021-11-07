package a4;

import org.opencv.core.RotatedRect;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.Port;

public class Mast {

	private int minRotation = -90;
	private int maxRotation = 90;
//	private int deltaRotation;
	private int currentRotation;
	public EV3MediumRegulatedMotor mastMotor;
	private Port mastPort;

	public Mast() {
		// TODO Auto-generated constructor stub
	}
	
	public Mast(Port c) {
		mastPort = c;
		mastMotor = new EV3MediumRegulatedMotor(c);
		currentRotation = 0;
	}
	
	public void startTest() {
		// confirm claw is open, or open claw
		int diff = Math.abs(mastMotor.getLimitAngle() - minRotation);
		if (diff > 5) {
			while (!mastMotor.isStalled()) {
				mastMotor.rotate(-5);
			}
			mastMotor.resetTachoCount();
			minRotation = mastMotor.getLimitAngle();
			mastMotor.rotateTo(minRotation-15);
		}
	}
	
	public int checkRotation() {
		currentRotation = mastMotor.getLimitAngle();
		return currentRotation;
	}
	
	public void search() {
		mastMotor.rotateTo(minRotation);
		int searchInterval = 8;
		int searchArc = maxRotation-minRotation;
		searchArc /= searchInterval;
		for (int i = 1; i <= searchInterval; i++) {
			mastMotor.rotate(searchArc);
		}
		mastMotor.rotateTo(0);
	}
	
	public void startIncSearch() {
		mastMotor.rotateTo(minRotation);
	}
	
	public void incrementalSearch() {
		if (currentRotation>=maxRotation) {
			mastMotor.rotateTo(minRotation);
		}
		else {
			mastMotor.rotate(23);
		}
	}

}
