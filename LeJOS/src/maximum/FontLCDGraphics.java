package maximum;
import lejos.hardware.*;
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
public class FontLCDGraphics {

	public static void main(String[] args) {
		GraphicsLCD g = LocalEV3.get().getGraphicsLCD();
		g.setFont(Font.getSmallFont());
		
		int h = g.getHeight(); // 128
		int w = g.getWidth(); // 178
		int fH = Font.getSmallFont().getHeight(); // 8
		for(int y=0;y<128;y=y+8)
			g.drawString("012345678901234567890123456789", 0, y, 0);
		
		Button.ESCAPE.waitForPressAndRelease();
	}

}
