package a4;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.opencv.core.Mat;

import finalProject.ColorSensor;
import finalProject.Mast;
import finalProject.TheClaw;
import finalProject.UltrasonicSensor;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class Main {

	public static void main(String[] args) {
		A4Pilot a4Pilot = new A4Pilot();
		boolean captured = false;

		ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
		UltrasonicSensor ussr = new UltrasonicSensor(SensorPort.S1);
		TheClaw theClaw = new TheClaw(MotorPort.C);
		Mast mast = new Mast(MotorPort.B);
		int distance = 255;

		int d = 0;

//		mast.search();
//		theClaw.startTest();

		while (!Button.ESCAPE.isDown() && !captured) {
			Map<Integer, Integer> distanceAngle = new HashMap<>();
			distanceAngle.put(1000, 0); // ensures there is a minimum element in distanceAngle
			a4Pilot.travel(1);
			mast.startIncSearch();

			// look to the right a little and see if there is something there
			for (int i = 0; i < 22; i++) {
				mast.incrementalSearch();
				Delay.msDelay(100);
				d = ussr.distance();
				System.out.println(d); 
				// crude easy noise filter
				if ((d <= 300) && (d >= 100)) {
					distanceAngle.put(d, mast.checkRotation());
				}

			}

			if (distanceAngle != null) {  // always look before you leap
				int minDistance = Collections.min(distanceAngle.keySet());
				System.out.println("kv: " + minDistance + ":" + distanceAngle.get(minDistance));
				// turn in the direction of the ball up to 5 degrees. any more and we risk running wild
				if (distanceAngle.get(minDistance) > 0) {
					a4Pilot.rotate(Math.min(5, distanceAngle.get(minDistance)*0.5)); //0.5 gear ratio
				} else if (distanceAngle.get(minDistance) < 0) {
					a4Pilot.rotate(Math.max(-5, distanceAngle.get(minDistance)*0.5));
				}
				// destroy distance angle
				distanceAngle = null;
			}

			if ((colorSensor.getColor() == Color.BLUE) || (colorSensor.getColor() == 7) // empirical neccessities 
					|| (colorSensor.getColor() == 1)) {
				a4Pilot.travel(5);
				theClaw.grab();
				if (Math.abs(theClaw.checkRotation() - theClaw.getClosedRotation()) < 5) {
					captured = true;
				}
			}

//			distanceAngle.clear();
		}
		// We cant automate the masts angles like we can the claw due to the gear train (it pops loose), 
		// so for convienience we put it back home
		mast.mastMotor.rotateTo(0);

	}

//	public static void main(String[] args) {
//		A4Pilot a4Pilot = new A4Pilot();
//		boolean captured = false;
//
//		ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
//		UltrasonicSensor ussr = new UltrasonicSensor(SensorPort.S1);
//		TheClaw theClaw = new TheClaw(MotorPort.B);
//		int distance = 255;
//
//
//
//		a4Pilot.forward();
//		while (!Button.ESCAPE.isDown() && !captured) {
//			distance = ussr.distance();
//
//			LCD.drawString("DIST:     ", 0, 2);
//			LCD.drawInt(distance, 3, 5, 2);
//
//			a4Pilot.forward();
//			if (colorSensor.getColor() == Color.BLUE) {
//				a4Pilot.travel(2.54);
//				theClaw.startTest();
//				
//			}
//
//			Delay.msDelay(100);
//		}
//		ussr.sensor.close();
//		colorSensor.sensor.close();
//
//	}

}
