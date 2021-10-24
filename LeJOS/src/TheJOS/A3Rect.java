package TheJOS;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class A3Rect {
	// User Configs - Will need to tweak during troubleshooting
	static final int GO_LINE_COLOR = 6;
	static final int GO_LINE_COLOR2 = 13;
	static final int GO_LINE_COLOR3 = Color.BLUE;
	static final int GO_LINE_COLOR4 = Color.BLUE;
	static final int STOP_COLOR = 2;
	static final int STOP_COLOR2 = 0;
	static final int STOP_COLOR3 = Color.RED;
	static final int STOP_COLOR4 = Color.RED;

	static final double PILOT_LINEAR_SPEED = 20; // m/s
	static final double PILOT_ANGULAR_SPEED = 10; // deg/s
	static final double OFFSET = 20.32 * 0.8 * 0.5; // m, 1/2 track width times a correction modifier
	static final double FWD_INCREMENT = 1; // m
	static final double MAX_TURN_ANGLE = 45; // deg
	static final double TURN_INCREMENT = 1; // deg

	public static void main(String[] args) {
		EV3ColorSensor colourBlue = new EV3ColorSensor(SensorPort.S1);
		MyPilot pilot = new MyPilot(OFFSET, PILOT_LINEAR_SPEED, PILOT_ANGULAR_SPEED);

		// check color at starting point. Prompt for help if not correct color
		boolean lineDetected = (pilot.detectColor(GO_LINE_COLOR, colourBlue));
		if (!lineDetected) {
			helpPilot(pilot, lineDetected, colourBlue);
		}

		boolean go = true;

		while (true) {
			pilot.travel(FWD_INCREMENT);



			lineDetected = (pilot.detectColor(GO_LINE_COLOR, colourBlue));
			if (!lineDetected) {
				int turn = 1;
				for (turn = 1; turn <= MAX_TURN_ANGLE; turn++) {
					lineDetected = (pilot.checkLineLeft(turn, TURN_INCREMENT, GO_LINE_COLOR, colourBlue));
					pilot.rotate(1.2*turn);
					if (lineDetected & (turn > 4)) {
						pilot.travel(14.5);
						pilot.rotate(-87);
//						while (!lineDetected) {
//							lineDetected = search(pilot, lineDetected, colourBlue);
//						}
//						pilot.travel(2);
//						while (!lineDetected) {
//							lineDetected = search(pilot, lineDetected, colourBlue);
//						}
						turn = 0;
						continue;
						
					}

					if (!lineDetected) {
						lineDetected = (pilot.checkLineRight(turn, TURN_INCREMENT, GO_LINE_COLOR, colourBlue));
						pilot.rotate(-turn);
						if (lineDetected & (turn > 4)) {
							pilot.travel(14.5);
							pilot.rotate(85);
//							while (!lineDetected) {
//								lineDetected = search(pilot, lineDetected, colourBlue);
//							}
//							pilot.travel(2);
//							while (!lineDetected) {
//								lineDetected = search(pilot, lineDetected, colourBlue);
//							}
							turn = 0;
							continue;
						}
					}
					if(lineDetected) {
						break;
					}
//					pilot.rotate(-turn);
				}

				if (!lineDetected) {
					helpPilot(pilot, lineDetected, colourBlue);
					Delay.msDelay(5000);
					break;
				}
			}
		}

	}

	/**
	 * Pilot needs help repositioning from user.
	 */
	public static void helpPilot(MyPilot pilot, boolean lineDetected, EV3ColorSensor colourBlue) {
		while (!lineDetected) {
			LCD.drawString("HELP! I'm LOST!", 0, 3);
			Delay.msDelay(5000);
			lineDetected = (pilot.detectColor(GO_LINE_COLOR, colourBlue));
		}
	}

	public static boolean search(MyPilot pilot, boolean lineDetected, EV3ColorSensor colourBlue) {
		int turn = 1;
		while (!lineDetected) {
			if(turn <= MAX_TURN_ANGLE) {
				lineDetected = (pilot.checkLineLeft(turn, TURN_INCREMENT, GO_LINE_COLOR, colourBlue));
				if (!lineDetected) {
					pilot.rotate(turn);
					lineDetected = (pilot.checkLineRight(turn, TURN_INCREMENT, GO_LINE_COLOR, colourBlue));
					if (!lineDetected) {
						pilot.rotate(-turn);
					}
				}
				turn++;
			}
		}
		return lineDetected;
	}
}
