package a5;

import lejos.hardware.port.SensorPort;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import lejos.robotics.Color;
import finalProject.ColorSensor;
import finalProject.UltrasonicSensor;

public class RealignBehavior implements Behavior {
	final int REALIGN_DISTANCE_THRESHOLD = 5; // change this value to adjust the sensitivity of the behavior
	final int INITIAL_REVERSE_DISTANCE = -5; // how far back the bot should move before searching for the line
	final int LINE_COLOR = Color.BLACK;  // color of the line
	final int ROTATE_DELAY = 50; // delay time in miliseconds between each step in the rotation
	final int STEP_SIZE = 5; // degrees to turn when searching for the line
	
	
	boolean suppressed = false;
	
	A5Pilot pilot = new A5Pilot();
	UltrasonicSensor ussr = new UltrasonicSensor(SensorPort.S1);
	ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
	
	public RealignBehavior(A5Pilot pilot, UltrasonicSensor ussr, ColorSensor colorSensor) {
		this.pilot = pilot;
		this.ussr = ussr;
		this.colorSensor = colorSensor;
	}
	
	@Override
	public boolean takeControl() {
		return ussr.distance() < REALIGN_DISTANCE_THRESHOLD;
	}

	@Override
	public void action() {
		pilot.travel(INITIAL_REVERSE_DISTANCE);
		while(!suppressed)
		{
			// did we luck into landing on the line?
			if(colorSensor.getColor() == LINE_COLOR)
			{
				pilot.stop();
				return;
			}
			// look to the left first up to 90 degrees
			for(int i = STEP_SIZE; i <= 90; i += STEP_SIZE)
			{
				pilot.rotate(i);
				Delay.msDelay(ROTATE_DELAY);
				if(colorSensor.getColor() == LINE_COLOR)
				{
					pilot.stop();
					return;
				}
				if(suppressed)
				{
					return;
				}
			}
			// look to the right up to 90 degrees
			pilot.rotate(-90);
			for(int i = STEP_SIZE; i <= 90; i += STEP_SIZE)
			{
				pilot.rotate(i * -1);
				Delay.msDelay(ROTATE_DELAY);
				if(colorSensor.getColor() == LINE_COLOR)
				{
					pilot.stop();
					return;
				}
				if(suppressed)
				{
					return;
				}
			}
			
			// failing that make a complete circle until the line is found
			for(int i = STEP_SIZE; i <= 360; i += STEP_SIZE)
			{
				pilot.rotate(i);
				Delay.msDelay(ROTATE_DELAY);
				if(colorSensor.getColor() == LINE_COLOR)
				{
					pilot.stop();
					return;
				}
				if(suppressed)
				{
					return;
				}
			}
		}
	}

	@Override
	public void suppress() {
		pilot.stop();
		suppressed = true;
	}

}
