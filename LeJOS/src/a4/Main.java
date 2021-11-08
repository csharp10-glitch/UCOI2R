package a4;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.opencv.core.Mat;

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

		mast.search();

//		theClaw.startTest();

		while (!Button.ESCAPE.isDown() && !captured) {
			Map<Integer, Integer> distanceAngle = new HashMap<>();
			distanceAngle.put(1000, 0);
			a4Pilot.travel(1);
			mast.startIncSearch();

			for (int i = 0; i < 18; i++) {
				mast.incrementalSearch();
				Delay.msDelay(100);
				d = ussr.distance();
				System.out.println(d);
				if ((d <= 300) && (d >= 100)) {
					distanceAngle.put(d, mast.checkRotation());
				}

			}

			if (distanceAngle != null) {
				int minDistance = Collections.min(distanceAngle.keySet());
				System.out.println("kv: " + minDistance + ":" + distanceAngle.get(minDistance));
//				a4Pilot.rotate(distanceAngle.get(minDistance) * 0.5); // -2 multiplier for gearing
				if (distanceAngle.get(minDistance) > 0) {
					a4Pilot.rotate(Math.min(5, distanceAngle.get(minDistance)*0.5));
				} else if (distanceAngle.get(minDistance) < 0) {
					a4Pilot.rotate(Math.max(-5, distanceAngle.get(minDistance)*0.5));
				}
				distanceAngle = null;
			}

			if ((colorSensor.getColor() == Color.BLUE) || (colorSensor.getColor() == 7)
					|| (colorSensor.getColor() == 1)) {
				a4Pilot.travel(4.2);
				theClaw.grab();
				if (Math.abs(theClaw.checkRotation() - theClaw.closedRotation) < 5) {
					captured = true;
				}
			}

//			distanceAngle.clear();

//			d++;
		}
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
