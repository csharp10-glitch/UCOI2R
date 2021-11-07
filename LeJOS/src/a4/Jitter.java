package a4;

import lejos.robotics.navigation.MovePilot;

public class Jitter {

	public void doJitter(MovePilot myPilot) {
		myPilot.rotate(1);
		myPilot.rotate(-2);
		myPilot.rotate(1);
	}
	
}
