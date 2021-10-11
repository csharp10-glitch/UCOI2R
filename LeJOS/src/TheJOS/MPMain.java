package TheJOS;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class MPMain {
	public static void main(String[] args) {
		double offset = 20.32*0.8*0.5;              // 1/2 track width times a correction modifier
		MyPilot pilot = new MyPilot(offset);		// initialize new pilot

		double turnSpeed = 20;						// setup a turning speed variable
		int samplesNo = 10;							// determine number of samples we want
		for (int i = 0; i < samplesNo; i++) {
			pilot.turn(turnSpeed, 180);				// turn a half circle at set turn speed
			//measure radius then continue
			LCD.drawString("Measure, Press ENTER", 0, 3);
			Button.ENTER.waitForPressAndRelease();
		}
		
		for (int i = 0; i < samplesNo; i++) {
			pilot.turn(turnSpeed, 360);
			//measure radius then continue
			LCD.drawString("Press ENTER", 0, 3);
			Button.ENTER.waitForPressAndRelease();
		}
	}
	
}