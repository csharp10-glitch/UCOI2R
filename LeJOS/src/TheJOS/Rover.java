package TheJOS;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.hardware.sensor.*;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.*;
import lejos.utility.Delay;

public class Rover {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		double diam = DifferentialPilot.WHEEL_SIZE_EV3;
		double trackwidth = 15.2;
		DifferentialPilot rov3r = new DifferentialPilot(diam, trackwidth, Motor.C, Motor.B);

		EV3ColorSensor colourBlue = new EV3ColorSensor(SensorPort.S1);
		SensorMode colourID = colourBlue.getMode("ColorID");
		int colour = -1;
		EV3UltrasonicSensor SoundSei = new EV3UltrasonicSensor(SensorPort.S4);
		SensorMode distMode = SoundSei.getMode("Distance");
		int distance = 255;

		rov3r.forward();
		while (!Button.ESCAPE.isDown()) {
			
			float[] colourSample = new float[colourBlue.getColorID()];
			colourID.fetchSample(colourSample,0);
			colour = (int) colourSample[0]; 

			float[] sample = new float[distMode.sampleSize()];
			distMode.fetchSample(sample, 0);
			distance = (int) sample[0];
			
			if (colour == 2) {
				// Initialize motors:
				UnregulatedMotor b = new UnregulatedMotor(MotorPort.B);
				UnregulatedMotor c = new UnregulatedMotor(MotorPort.C);
				b.setPower(25);
				c.setPower(25);
				
				// Start rotating motors:
				b.forward();
				c.forward();
				Delay.msDelay(500);
				
				// Stop motors and close:
				b.flt();
				c.flt();
				b.close();
				c.close();
				
				
//				actuate claw
				Sound.beep();
//				RegulatedMotor a = new EV3MediumRegulatedMotor(MotorPort.A);
//		        a.rotate(-2500);
//		        
//		        Sound.beepSequenceUp();
//		        
//				a.forward();
//				Delay.msDelay(250);
//				a.rotate(2500);
//				a.flt();
//				a.close();
			}

			LCD.drawString("DIST:     ", 0, 2);
			LCD.drawInt(distance, 3, 5, 2);

			if (distance < 70 && distance != 0) {
				rov3r.travel(-20);
				rov3r.rotate(45);
				rov3r.forward();
			}
			Delay.msDelay(100);
		}
		SoundSei.close();
	}
}
