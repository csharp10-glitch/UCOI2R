package TheJOS;

import lejos.utility.Delay;

public class G10PilotMain {

	public static void main(String[] args) {
		G10Pilot g10 = new G10Pilot();

		
		g10.turn(360);
//		g10.turn(-360);
		
//		Delay.msDelay(15000);
//		
		g10.turn((float) 25.4, 180);
//		g10.turn((float) 25.4, 180);
//		g10.turn((float) 25.4, -360);
	}

}
