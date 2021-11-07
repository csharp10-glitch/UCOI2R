package maximum;

import lejos.hardware.*;
import lejos.hardware.lcd.*;
import lejos.utility.Delay;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 7 - The World of leJOS
 * Robot: EV3 Brick
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
public class ButtonGalore implements KeyListener {

	public static void main(String[] args) {
		KeyListener listener = new ButtonGalore();
		Button.ENTER.addKeyListener(listener);
		for(int i=0;i<20;++i) {
	         LCD.drawInt(i, 0, 2);
	         Delay.msDelay(1000);
		}
	}

	public void keyPressed(Key k) {
		Sound.beep();
	}

	public void keyReleased(Key k) {
		Sound.buzz();
	}
}
