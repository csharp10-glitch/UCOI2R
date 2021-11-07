package maximum;
import lejos.hardware.Button;
import lejos.hardware.ev3.*;
import lejos.hardware.lcd.*;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 10 - The leJOS API
 * Robot: EV3 Brick
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
public class FontDisplay {

	public static void main(String[] args) {
		for(int y=0;y<12;y++)
			LCD.drawString("01234567890123456789", 0, y);
		Button.ESCAPE.waitForPressAndRelease();
	}

}
