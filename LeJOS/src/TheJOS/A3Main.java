package TheJOS;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.Color;

public class A3Main {
	// User Configs - Will need to tweak during troubleshooting
	static final int GO_LINE_COLOR = Color.YELLOW;
	static final int STOP_COLOR = Color.RED;
	static final double PILOT_LINEAR_SPEED = 20; // m/s
	static final double PILOT_ANGULAR_SPEED = 10; // deg/s
	static final double OFFSET = 20.32 * 0.8 * 0.5; // m, 1/2 track width times a correction modifier
	static final double FWD_INCREMENT = 1; // m
	static final double MAX_TURN_ANGLE = 90; // deg
	static final double TURN_INCREMENT = 5; // deg

	public static void main(String[] args) {
		MyPilot pilot = new MyPilot(OFFSET, PILOT_LINEAR_SPEED, PILOT_ANGULAR_SPEED);

		// check color at starting point. Prompt for help if not correct color
		boolean lineDetected = pilot.detectColor(GO_LINE_COLOR);
		if (!lineDetected) {
			helpPilot(pilot, lineDetected);
		}

		while (true) {
			pilot.travel(FWD_INCREMENT);

			// stop moving if detects stop color
			if (pilot.detectColor(STOP_COLOR)) {
				break;
			}

			lineDetected = pilot.detectColor(GO_LINE_COLOR);
			if (!lineDetected) {
				lineDetected = pilot.checkLineLeft(MAX_TURN_ANGLE, TURN_INCREMENT, GO_LINE_COLOR);
				if (!lineDetected) {
					// reset to prior position
					pilot.rotate(MAX_TURN_ANGLE);
					lineDetected = pilot.checkLineRight(MAX_TURN_ANGLE, TURN_INCREMENT, GO_LINE_COLOR);

					// if line still not found on right, wait for user to reposition
					if (!lineDetected) {
						helpPilot(pilot, lineDetected);
					}
				}
			}
		}
	}

	/**
	 * Pilot needs help repositioning from user.
	 */
	public static void helpPilot(MyPilot pilot, boolean lineDetected) {
		while (!lineDetected) {
			LCD.drawString("HELP! I'm LOST!", 0, 3);
			Button.ENTER.waitForPressAndRelease();
			lineDetected = pilot.detectColor(GO_LINE_COLOR);
		}
	}
}
