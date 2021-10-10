package TheJOS;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class G10Pilot {
	
	double wheelDiameter = 5.6;
	double trackWidth = 20.32*0.8;
	double twMod = 0.8;
	double twRaw = 20.32;
	RegulatedMotor leftMotor;
	RegulatedMotor rightMotor;
	int turnRadius = 0;
	float leftOmega = 0;
	float rightOmega = 0;
	float leftTach = 0;
	float rightTach = 0;
	int v = 360;
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
		leftMotor.startSynchronization();
		leftMotor.forward();
		rightMotor.forward();
		leftMotor.endSynchronization();
		float lmtc = leftMotor.getTachoCount();
		float rmtc = rightMotor.getTachoCount();
		float[] tc = {lmtc, rmtc};
	}
	
	public float[] forward(int dps){
		leftMotor.startSynchronization();
		leftMotor.setSpeed(dps);
		rightMotor.setSpeed(dps);
		leftMotor.endSynchronization();
		float lmtc = leftMotor.getTachoCount();
		float rmtc = rightMotor.getTachoCount();
		float[] tc = {lmtc, rmtc};
		return tc;
	}
	
	public void turn(int degrees){
		int wTheta = (int) (trackWidth*degrees);
		int v = 360;
		int t = (int) (degrees*trackWidth*wheelDiameter/(360));
		leftMotor.setSpeed(v);
		rightMotor.setSpeed(v);
		leftMotor.startSynchronization();
		leftMotor.forward();
		rightMotor.backward();
		leftMotor.endSynchronization();
		Delay.msDelay(1000*t);
		leftMotor.startSynchronization();
		leftMotor.stop();
		rightMotor.stop();
		leftMotor.endSynchronization();
		float lmtc = leftMotor.getTachoCount();
		float rmtc = rightMotor.getTachoCount();
		float[] tc = {lmtc, rmtc};
	}
	
	public void turn(float radius, int degrees){
		double circum = 2*3.14159*radius;
		int v0 = leftMotor.getSpeed();
		int v = 360;
		double r1 = radius-trackWidth/2.0;
		double cr1 = r1*2*3.14159;
		double r2 = radius+trackWidth/2.0;
		double cr2 = r2*2*3.14159;
		double dr1 = cr1/cd;
		double dr2 = cr2/cd;
		int deg1 = (int) (dr1/360);
		int deg2 =(int) (dr2/360);
		double rp = (2*radius/trackWidth) + 1;
		double rm = (2*radius/trackWidth) - 1;
		int dt = degrees/360;
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
		leftMotor.startSynchronization();
		leftMotor.forward();
		rightMotor.forward();
		leftMotor.endSynchronization();
		if (dt > 15) {
			dt = 5;
		}
		Delay.msDelay(dt*1000);
		leftMotor.startSynchronization();
		leftMotor.stop();
		rightMotor.stop();
		leftMotor.endSynchronization();
		float lmtc = leftMotor.getTachoCount();
		float rmtc = rightMotor.getTachoCount();
		float[] tc = {lmtc, rmtc};
		leftMotor.setSpeed(v0);
		rightMotor.setSpeed(v0);
	}
	
	public double deg2r(int degrees) {
		double rad = 3.14159/180;
		return rad;
	}

}
