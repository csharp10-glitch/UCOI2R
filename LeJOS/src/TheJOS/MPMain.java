package TheJOS;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class MPMain {
	public static void main(String[] args) {
		double offset = 3.5;
		MyPilot pilot = new MyPilot(offset);

		double turnSpeed = 20;
		int samplesNo = 10;
		for (int i = 0; i < samplesNo; i++) {
			pilot.turn(turnSpeed, 180);
			//measure radius then continue
			LCD.drawString("Press ENTER", 0, 3);
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