package maximum;
import lejos.hardware.*;
import lejos.hardware.lcd.LCD;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 11 - Sound
 * Robot: EV3 Brick
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
public class NoteScales {
  public static void main(String [] args) {
	for(int freq=2500;freq>1000;freq-=100) {
      LCD.drawString("FREQ: " + freq, 2, 3);
      Sound.playNote(Sound.FLUTE, freq, 100);
    }
  }
}