package finalProject;

import java.util.ArrayDeque;
import java.util.Deque;

import lejos.hardware.Button;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.utility.Delay;

public class Main {
	static Deque<MazeNode> nodeStack = new ArrayDeque<>();
	static Deque<MazeNode> exitPlan = new ArrayDeque<>();

	// TODO check dimensions below for me
	static final float MAZE_LENGTH = 500; // TODO @soups check size for me
	static final float MAZE_WIDTH = 500; // TODO @soups check size for me
	static final float NODE_SIZE = MAZE_LENGTH / 100; // 5 nodes x 5 nodes maze
	static final float MOVE_INC = NODE_SIZE / 2; // TODO check me. move increments in mm, stops midway between two nodes and look for marker to ensure robot is centered
	static final float MOVE_TOL = 10; // move tolerance mm
	static final float HEAD_TOL = 15; // heading tolerance degrees
	static final int BALL_COLOR = Color.BLUE;
	static final int EXIT_COLOR = Color.RED;
	
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
		float heading = 90f; // 0 parallel to x-axis, 90 parallel to y-axis. TODO update as needed

		MazeNode currentNode = new MazeNode(x, y);
		currentNode.setVisited(true);
		nodeStack.push(currentNode);
		MazeNode frontNode = null;
		MazeNode leftNode = null;
		MazeNode rightNode = null;

		OdometryPoseProvider poseProvider = new OdometryPoseProvider(pilot);
		poseProvider.setPose(new Pose(x, y, heading));
		nav.setPoseProvider(poseProvider);
		// TODO @soups might need to add gyro sensor here for better heading detection

		PilotMoveListener moveListener = new PilotMoveListener();
		pilot.addMoveListener(moveListener);

		// algorithm explaination:
		// https://youtu.be/Yzsp6l-neGo
		// https://youtu.be/GEpEoliVNNY
		while (!Button.ESCAPE.isDown() || (!pilot.isBallCaptured() && !pilot.isOutOfMaze())) {
			// Check ball
			int color = colorSensor.getColor();
			if ((color == BALL_COLOR) || (color == 7) || (color == 1)) { // TODO @soups why 7 and 1 too?
				pilot.travel(5);
				theClaw.grab();
				if (Math.abs(theClaw.checkRotation() - theClaw.getClosedRotation()) < 5) {
					pilot.setBallCaptured(true);
				}
			}

			// Look for neighbors: front, left right
			mast.lookFront();
			if (ussr.distance() > MOVE_INC) {
				frontNode = makeFrontNode(heading, x, y);
				currentNode.addNeighbor(frontNode);
			}

			mast.lookLeft();
			if (ussr.distance() > MOVE_INC) {
				leftNode = makeLeftNode(heading, x, y);
				if (leftNode != null) {
					currentNode.addNeighbor(leftNode);
				}
			}

			mast.lookRight();
			if (ussr.distance() > MOVE_INC) {
				rightNode = makeRightNode(heading, x, y);
				if (rightNode != null) {
					currentNode.addNeighbor(rightNode);
				}
			}

			MazeNode nextNode = currentNode.getUnvisitedNeighbor();
			if (nextNode == null) { // deadend or visited all neighbors already
				nextNode = retrace();
			} else { // not deadend
				// check exit
				if (currentNode.isExitNode(MAZE_WIDTH, MAZE_LENGTH) || colorSensor.getColor() == EXIT_COLOR) {
					if (pilot.isBallCaptured()) {
						theClaw.release();
						pilot.setBallReleased(true); // WOOT WOOT
					} else {
						exitPlan.addAll(nodeStack);
						nextNode = retrace();
					}
				}
				
				nextNode.setCameFrom(currentNode);
				nodeStack.push(nextNode);
				moveListener.setNode(nextNode);
				nav.goTo(nextNode.getX(), nextNode.getY());
				while(pilot.isMoving()) {
					Delay.msDelay(1000);
				}
				currentNode = nextNode;
				currentNode.setVisited(true);
				
				x = currentNode.getX();
				y = currentNode.getY();
				heading = poseProvider.getPose().getHeading();
			}
		}
	}

	/**
	 * @param heading
	 * @param x
	 * @param y
	 * @return node front of robot based on its heading
	 */
	public static MazeNode makeFrontNode(float heading, float x, float y) {
		if (inRange(heading, 90, HEAD_TOL)) { // facing forward 90 degs. front node is north
			return new MazeNode(x, y + NODE_SIZE);
		} else if (inRange(heading, 0, HEAD_TOL)) { // facing left 0 degs. front node is west
			return new MazeNode(x - NODE_SIZE, y);
		} else if (inRange(heading, 180, HEAD_TOL)) { // facing right. 180 degs. front node is east
			return new MazeNode(x + NODE_SIZE, y);
		} else {
			System.out.println("ERROR: Heading OUT OF RANGE");
			return null;
		}
	}

	/**
	 * 
	 * @param heading
	 * @param x
	 * @param y
	 * @return node left of robot based on its heading
	 */
	public static MazeNode makeLeftNode(float heading, float x, float y) {
		if (inRange(heading, 90, HEAD_TOL)) { // facing forward 90 degs. left node is west
			return new MazeNode(x - NODE_SIZE, y);
		} else if (inRange(heading, 0, HEAD_TOL)) { // facing left 0 degs. left node is south
			return new MazeNode(x, y - NODE_SIZE);
		} else if (inRange(heading, 180, HEAD_TOL)) { // facing right 180 degs. left node is north
			return new MazeNode(x, y + NODE_SIZE);
		} else {
			System.out.println("ERROR: Heading OUT OF RANGE");
			return null;
		}
	}

	/**
	 * 
	 * @param heading
	 * @param x
	 * @param y
	 * @return node right of robot based on its heading
	 */
	public static MazeNode makeRightNode(float heading, float x, float y) {
		if (inRange(heading, 90, HEAD_TOL)) { // facing forward 90 degs. right node is east
			return new MazeNode(x + NODE_SIZE, y);
		} else if (inRange(heading, 0, HEAD_TOL)) { // facing left 0 degs. right node is north
			return new MazeNode(x, y + NODE_SIZE);
		} else if (inRange(heading, 180, HEAD_TOL)) { // facing right 180 degs. right node is south
			return new MazeNode(x, y - NODE_SIZE);
		} else {
			System.out.println("ERROR: Heading OUT OF RANGE");
			return null;
		}
	}

	/**
	 * @param value
	 * @param target
	 * @param tolerance
	 * @return true if value is within range of target while accounting for tolerance
	 */
	public static boolean inRange(float value, float target, float tolerance) {
		return (target - tolerance) <= value && value <= (target + tolerance);
	}

	/**
	 * move back to first node in stack that has unvisited neighbor
	 */
	public static MazeNode retrace() {
		return null;
	}
}
