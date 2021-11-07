package maximum;


import lejos.hardware.*;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 2 - Installing leJOS
 * Robot: EV3 Brick
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
public class HelloWorld {
  public static void main (String[] args) {
    System.out.println("Hello World");
    Button.waitForAnyPress();
  }
}