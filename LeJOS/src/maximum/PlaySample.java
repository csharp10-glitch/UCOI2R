package maximum;
import java.io.File;
import lejos.hardware.*;
import lejos.hardware.lcd.*;

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
public class PlaySample {
  public static void main(String [] args) {
    File myFile = new File("austinpowers.wav");
    LCD.drawString("Playing " + myFile.getName(), 0, 2);
    Sound.playSample(myFile, 100);
    LCD.drawString("Done hit ESC", 0, 4);
    Button.ESCAPE.waitForPressAndRelease();
  }
}