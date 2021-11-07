package maximum;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.utility.Delay;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 17 - IR Sensor and Beacon
 * Robot: Warbird
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
public class RemoteControl {
	
	public static void main(String [] agrs) {
		EV3IRSensor ir = new EV3IRSensor(SensorPort.S4);
		boolean keep_looping = true;
		int channel = 0;
		
		while(keep_looping) {
			Delay.msDelay(25);
			
			// Get the IR commands
			byte [] cmds = new byte[4];
			ir.getRemoteCommands(cmds, 0, cmds.length);
			
			// Figure out which channel is active:
			int command = 0;
			for(int i=0;i<4;i++) {
				if(cmds[i] > 0) {
					channel = i+1;
					command = cmds[i];
				}
			}
			
			LCD.drawString("COM:" + command + " ", 0, 2);
			LCD.drawString("CHAN:" + channel, 0, 4);
			
			if(command == 8) keep_looping = false;
		}
		ir.close();
	}
}