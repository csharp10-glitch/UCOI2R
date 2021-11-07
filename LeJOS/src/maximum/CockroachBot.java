package maximum;
import lejos.hardware.port.*;
import lejos.hardware.sensor.*;
import lejos.hardware.motor.*;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 9 - Instant Java
 * Robot: theoretical code
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
class CockroachBot implements SensorConstants{
   
   EV3ColorSensor cs;
   SensorMode ambient;
   
   public CockroachBot() {
      cs = new EV3ColorSensor(SensorPort.S2);
      ambient = cs.getMode("Ambient");
   }
      
   public static void main(String [] args) {
      CockroachBot bot = new CockroachBot();
      Motor.B.forward();
      Motor.C.forward();
      while(bot.isBright()) {
         // Keep moving forward
      }
      Motor.B.stop();
      Motor.C.stop();
   }
   
   public boolean isBright() {
      float[] sample = new float[ambient.sampleSize()];
      ambient.fetchSample(sample, 0);
      return (sample[0] > 55);
   }
}
