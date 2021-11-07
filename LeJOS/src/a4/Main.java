package a4;

import java.util.ArrayList;
import java.util.List;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class Main {

	public static void main(String[] args) {
		A4Pilot a4Pilot = new A4Pilot();
		boolean captured = false;

		ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
//		UltrasonicSensor ussr = new UltrasonicSensor(SensorPort.S1);
		TheClaw theClaw = new TheClaw(MotorPort.C);
		int distance = 255;
		
		theClaw.startTest();

//		a4Pilot.travel();
		while (!Button.ESCAPE.isDown() && !captured) {
			if (colorSensor.getColor() == Color.BLUE) {
				a4Pilot.travel(3);
				theClaw.grab();
				
			}
		}

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
