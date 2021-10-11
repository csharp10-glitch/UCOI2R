package TheJOS;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.navigation.MovePilot;

public class MovePilotNoChassisMain {
	public static void main(String[] args)
	{
		final double trackWidth = 20.32; // in centimeters
		final double wheelDiameter = 5.6; // in centimeters
		final double angularSpeed = 20;
		final double linearSpeed = 10;
		
		final int samplesNo = 10;
		
		EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		
		// create MovePilot
		@SuppressWarnings("deprecation")
		MovePilot movePilot = new MovePilot(wheelDiameter,trackWidth, leftMotor, rightMotor);
		movePilot.setAngularSpeed(angularSpeed);
		movePilot.setLinearSpeed(linearSpeed);
		
		for(int i = 0; i < samplesNo; i++)
		{
			movePilot.rotate(180);
			LCD.drawString("Press ENTER", 0, 3);
			Button.ENTER.waitForPressAndRelease();
		}
		
		for(int i = 0; i < samplesNo; i++)
		{
			movePilot.rotate(360);
			LCD.drawString("Press ENTER", 0, 3);
			Button.ENTER.waitForPressAndRelease();
		}
	}
}
