package finalProject;

import java.util.Stack;

import lejos.hardware.Button;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.Move;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.pathfinding.Node;

public class Main {
	static Stack<Node> nodeStack = new Stack<>();
	static Stack<Move> moveStack = new Stack<>();
	static final int OBJ_BUFFER_DISTANCE = 10; //mm
	static final float Y_INC = 10; // mm
	static final float X_INC = 10;
	
	public static void main(String[] args) {
		Pilot pilot = new Pilot();
		Navigator nav = new Navigator(pilot);
		TheClaw theClaw = new TheClaw(MotorPort.C);
		
		UltrasonicSensor ussr = new UltrasonicSensor(SensorPort.S1);
		ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
		Mast mast = new Mast(MotorPort.B);
		
		float x = 0; // left-most
		float y = 0; // bottom-most
		float startHeading = 90f; // 0 parallel to x-axis, 90 parallel to y-axis. Update this if maze entrance requires different heading
		
		OdometryPoseProvider poseProvider = new OdometryPoseProvider(pilot);
		poseProvider.setPose(new Pose(x, y, startHeading));
		nav.setPoseProvider(poseProvider);
		
		nodeStack.push(new Node(x,y));
		while(!Button.ESCAPE.isDown() && !pilot.isBallCaptured() && !pilot.isOutOfMaze() && !pilot.isBallReleased()) {
			int d = ussr.distance();
			//moving up into maze first
			if(d > OBJ_BUFFER_DISTANCE) {
				y += Y_INC;
				nav.goTo(x,y);
			} else {
				
			}
			
		}
	}
}
