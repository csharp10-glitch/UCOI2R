package finalProject;

import java.util.Stack;

import lejos.hardware.Button;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;

public class Main {
	static Stack<MazeNode> nodeStack = new Stack<>();
	// TODO check dimensions below for me
	static final float MAZE_LENGTH = 500; // TODO @soups check size for me
	static final float MAZE_WIDTH = 500; // TODO @soups check size for me
	static final float NODE_SIZE = MAZE_LENGTH / 100; // 5 nodes x 5 nodes maze
	static final float MOVE_INC = NODE_SIZE / 2; // TODO check me. move increments in mm, stops midway between two nodes
													// and look for marker to ensure robot is centered

	public static void main(String[] args) {
		Pilot pilot = new Pilot();
		Navigator nav = new Navigator(pilot);
		TheClaw theClaw = new TheClaw(MotorPort.C);

		UltrasonicSensor ussr = new UltrasonicSensor(SensorPort.S1);
		ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
		Mast mast = new Mast(MotorPort.B);

		// center of the left-bottom-most node
		float x = 0;
		float y = 0;
		float startHeading = 90f; // 0 parallel to x-axis, 90 parallel to y-axis. TODO update as needed

		OdometryPoseProvider poseProvider = new OdometryPoseProvider(pilot);
		poseProvider.setPose(new Pose(x, y, startHeading));
		nav.setPoseProvider(poseProvider);

		PilotMoveListener moveListener = new PilotMoveListener(nodeStack);
		pilot.addMoveListener(moveListener);

		MazeNode currentNode = new MazeNode(x, y);
		nodeStack.push(currentNode);
		MazeNode frontNode = null;
		MazeNode leftNode = null;
		MazeNode rightNode = null;

		// TODO implement my pseudocode https://youtu.be/Yzsp6l-neGo
		// TODO implement my pseudocode https://youtu.be/GEpEoliVNNY
		// TODO decide where to save the moves to traverse between nodes
		while (!Button.ESCAPE.isDown() && !pilot.isBallCaptured() && !pilot.isOutOfMaze() && !pilot.isBallReleased()) {
			if (y <= MAZE_LENGTH) { // TODO check this condition for me
				mast.lookFront();
				int frontDist = ussr.distance();
				if (frontDist > MOVE_INC) {

//					frontNode = new MazeNode(x, y+NODE_SIZE); //TODO use findNode here
					currentNode.becomeNeighbors(frontNode);
				}
			}

		}
	}

	// TODO make new based on heading
	public MazeNode findNode(float heading) {
		return null;
	}
}
