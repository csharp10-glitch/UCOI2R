package maximum;

import lejos.hardware.*;
import lejos.hardware.lcd.*;
import lejos.utility.Delay;


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
class BadMusic extends Thread {
 
   public static void main(String [] args) {
      new BadMusic().start();
      new Counting().start();
      LCD.drawString("ESC to Exit", 2, 5);
      Button.waitForAnyPress();
      System.exit(0);
   }
 
   public void run() {
      while(true) {
         int freq = (int)(Math.random() * 1000);
         int delay = (int)(Math.random() * 300) + 100;
         Sound.playTone(freq, delay, 40);
      }
   }
}

class Counting extends Thread {
   public void run() {
      for(int i=0;i<1000;++i) {
         LCD.drawInt(i, 0, 2);
         Delay.msDelay(1000);
      }
   }
}