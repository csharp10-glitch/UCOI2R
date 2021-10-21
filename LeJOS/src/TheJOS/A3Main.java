package TheJOS.a3;

import TheJOS.MyPilot;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.Color;

public class A3Main {
	/**
	 * User preferences
	 * Length in m
	 * Angle in deg. 
	 * Speed in m/s
	 * Acceleration in m/s^2
	 * 
	 */
	static final int GO_LINE_COLOR = Color.YELLOW;
	static final int STOP_COLOR = Color.RED;
	static final double PILOT_LINEAR_SPEED = 20;
	static final double PILOT_ANGULAR_SPEED = 10;
	static final double OFFSET = 20.32 * 0.8 * 0.5; // 1/2 track width times a correction modifier
	static final double FWD_INCREMENT = 1;
	
	public static void main(String[] args) {
		// init pilot
		MyPilot pilot = new MyPilot(OFFSET, PILOT_LINEAR_SPEED, PILOT_ANGULAR_SPEED);

		// check color at starting point. prompt for replacement if not correct color
		boolean lineDetected = pilot.detectColor(GO_LINE_COLOR);
		while (!lineDetected) {
			LCD.drawString("Put on colored start line!", 0, 3);
			Button.ENTER.waitForPressAndRelease();
			lineDetected = pilot.detectColor(GO_LINE_COLOR);
		}
		
		//move straight and check color periodically
		while(true) {
			if(lineDetected = pilot.detectColor(GO_LINE_COLOR)) {
				pilot.travel(GO_LINE_COLOR);
			} else {
				//rotate left slowly for line detection. max 90 degree. move straight when see line
				//reset to position prior to turn and then turn right slowly to check line. move straight when see line
			}
			if(pilot.detectColor(STOP_COLOR)) {
				break;
			}
		}
		
	}

}
