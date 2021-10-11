package TheJOS;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class G10Pilot {
	
//	Setting variables to our robots defaults
	double wheelDiameter = 5.6;
	double trackWidth = 20.32*0.8;
//	Experimentally determined modifier for trackwidth to get best turn results
	double twMod = 0.8;
	double twRaw = 20.32;
	RegulatedMotor leftMotor;
	RegulatedMotor rightMotor;
	int turnRadius = 0;
//	Wheel rotational speed
	float leftOmega = 0;
	float rightOmega = 0;
//	Motor tach counts
	float leftTach = 0;
	float rightTach = 0;
//	Degrees per second expected for motor
	int velocity = 360;
//	distance one wheel rotation will cover
	double cd = wheelDiameter*3.14159;
	
	
	G10Pilot() {
		leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		RegulatedMotor[] motorArray = {rightMotor};
		leftMotor.synchronizeWith(motorArray);
	}
	
	G10Pilot(double wheelDiameter, double trackWidth,
			RegulatedMotor leftMotor, RegulatedMotor rightMotor) {
		this.wheelDiameter = wheelDiameter;
		this.trackWidth = trackWidth*twMod;
		this.twRaw = trackWidth;
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		RegulatedMotor[] motorArray = {rightMotor};
		leftMotor.synchronizeWith(motorArray);
	}
	
	public void forward(){
		leftMotor.setSpeed(360);
		rightMotor.setSpeed(360); //d = 1 circumference / second
		
//		Ensure motors move at the same time
		leftMotor.startSynchronization();
		leftMotor.forward();
		rightMotor.forward();
		leftMotor.endSynchronization();
		
//		record tach count changes
		float lmtc = leftMotor.getTachoCount();
		float rmtc = rightMotor.getTachoCount();
		float[] tc = {lmtc, rmtc};
	}
	
	public float[] forward(int dps){
		leftMotor.setSpeed(dps);
		rightMotor.setSpeed(dps);
		
//		Ensure motors move at the same time
		leftMotor.startSynchronization();
		leftMotor.forward();
		rightMotor.forward();
		leftMotor.endSynchronization();
		
//		record tach count changes
		float lmtc = leftMotor.getTachoCount();
		float rmtc = rightMotor.getTachoCount();
		float[] tc = {lmtc, rmtc};
		return tc;
	}
	
	public void turn(int degrees){
//		radians to turn
		double wTheta = (trackWidth*degrees*0.5);
//		radial velocity of wheel
		int omega = 360;
//		amount of seconds to rotate wheels
		int t = (int) (degrees*trackWidth/(360*wheelDiameter));
		leftMotor.setSpeed(omega);
		rightMotor.setSpeed(omega);
		
//		Ensure motors move at the same time
		leftMotor.startSynchronization();
		leftMotor.forward();
		rightMotor.backward();
		leftMotor.endSynchronization();
		
//		run for:
		Delay.msDelay(1000*t);

//		Ensure motors move at the same time
		leftMotor.startSynchronization();
		leftMotor.stop();
		rightMotor.stop();
		leftMotor.endSynchronization();
	}
	
	public void turn(float radius, int degrees){
//		store current speed
		int v0 = leftMotor.getSpeed();
//		set speed to 360dps for easier math
		int v = 360;
		
//		calculate inner turn radius
		double r1 = radius-trackWidth/2.0;
//		calculate inner circumference
		double cr1 = r1*2*3.14159;
//		calculate outer turn radius
		double r2 = radius+trackWidth/2.0;
//		calculate outer circumference
		double cr2 = r2*2*3.14159;
		
//		numer of wheel turns needed
		double dr1 = cr1/cd;
		double dr2 = cr2/cd;
		int deg1 = (int) (dr1/360);
		int deg2 =(int) (dr2/360);
		double rp = (2*radius/trackWidth) + 1;
		double rm = (2*radius/trackWidth) - 1;
		int dt = degrees/360;
		
//		handle left vs right turns
		if (deg1 > deg2) {
			leftMotor.setSpeed(v);
			int vr = (int) (v*rp/rm);
			rightMotor.setSpeed(vr);
			dt = (int) (dt*deg1);
		}
		else {
			rightMotor.setSpeed(v);
			int vl = (int) (v*rm/rp);
			leftMotor.setSpeed(vl);
			dt = (int) (dt*deg2);
		}
		
//		Ensure motors move at the same time
		leftMotor.startSynchronization();
		leftMotor.forward();
		rightMotor.forward();
		leftMotor.endSynchronization();

//		run for:
		Delay.msDelay(dt*1000);
		
//		Ensure motors move at the same time
		leftMotor.startSynchronization();
		leftMotor.stop();
		rightMotor.stop();
		leftMotor.endSynchronization();
		
//		reurn to default speed
		leftMotor.setSpeed(v0);
		rightMotor.setSpeed(v0);
	}
}
