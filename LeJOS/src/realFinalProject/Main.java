package realFinalProject;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

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
	public static final float MAZE_LENGTH = 500; // TODO @soups check size for me
	public static final float MAZE_WIDTH = 500; // TODO @soups check size for me
	public static final float NODE_SIZE = MAZE_LENGTH / 100; // 5 nodes x 5 nodes maze
	public static final float MOVE_INC = NODE_SIZE / 2; // TODO check me. move increments in mm, stops midway between two nodes and look for marker to ensure robot is centered
	public static final float MOVE_TOL = 10; // move tolerance mm
	public static final float HEAD_TOL = 15; // heading tolerance degrees
	public static final int BALL_COLOR = Color.BLUE;
	public static final int EXIT_COLOR = Color.RED;
	// TODO check my headings
	public static final float LEFT_HEADING = 0; //degs
	public static final float FWD_HEADING = 90; //degs
	public static final float RIGHT_HEADING = 180; //degs
	public static final float BACK_HEADING = 270; //degs
	
	public static void main(String[] args) {
		Main main = new Main();
		Pilot pilot = new Pilot();
		Navigator nav = new Navigator(pilot);
		TheClaw theClaw = new TheClaw(MotorPort.C);

		UltrasonicSensor ussr = new UltrasonicSensor(SensorPort.S1);
		ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
		Mast mast = new Mast(MotorPort.B);
		
		MazeNode currentNode = new MazeNode(MOVE_INC, MOVE_INC); 	// center of the left-bottom-most node
		currentNode.setVisited(true);
		nodeStack.push(currentNode);
		MazeNode frontNode = null;
		MazeNode leftNode = null;
		MazeNode rightNode = null;

		OdometryPoseProvider poseProvider = new OdometryPoseProvider(pilot);
		poseProvider.setPose(new Pose(currentNode.getX(), currentNode.getY(), pilot.getHeading()));
		nav.setPoseProvider(poseProvider);
		// TODO @soups might need to add gyro sensor here for better heading detection

		PilotMoveListener moveListener = new PilotMoveListener();
		pilot.addMoveListener(moveListener);

		// algorithm explaination:
		// https://youtu.be/Yzsp6l-neGo
		// https://youtu.be/GEpEoliVNNY
		while (!Button.ESCAPE.isDown()) {
			// Check ball
			int color = colorSensor.getColor();
			if ((color == BALL_COLOR) || (color == 7) || (color == 1)) { // TODO @soups why 7 and 1 too? 
				// @Cao, because that's what works. The sensor is designed to 
				// look for a very precise rgb ratio that identifies a lego brick's
				// color. This often doesn't fit the typical ratios for a color.
				// e.g. it's "Lego Brick" Blue, not "hey, I passed kindergarten,
				// that's blue" Blue.
				pilot.travel(5);
				theClaw.grab();
				if (Math.abs(theClaw.checkRotation() - theClaw.getClosedRotation()) < 5) {
					pilot.setBallCaptured(true);
					
					if(!exitPlan.isEmpty()) {
						// fast track back to the end
						exit(pilot, theClaw);
						break;
					}
				}
			}

			// Look for neighbors: front, left right
			mast.lookFront();
			if (ussr.distance() > MOVE_INC) {
				frontNode = makeFrontNode(pilot.getHeading(), currentNode.getX(), currentNode.getY());
				currentNode.addNeighbor(frontNode);
			}

			mast.lookLeft();
			if (ussr.distance() > MOVE_INC) {
				leftNode = makeLeftNode(pilot.getHeading(), currentNode.getX(), currentNode.getY());
				if (leftNode != null) {
					currentNode.addNeighbor(leftNode);
				}
			}

			mast.lookRight();
			if (ussr.distance() > MOVE_INC) {
				rightNode = makeRightNode(pilot.getHeading(), currentNode.getX(), currentNode.getY());
				if (rightNode != null) {
					currentNode.addNeighbor(rightNode);
				}
			}

			MazeNode nextNode = currentNode.getUnvisitedNeighbor();
			if (nextNode == null) { // deadend or visited all neighbors already
				nextNode = retrace(pilot);
			} else { // not deadend
				// check exit
				if (currentNode.isExitNode(MAZE_WIDTH, MAZE_LENGTH, NODE_SIZE) || colorSensor.getColor() == EXIT_COLOR) {
					if (pilot.isBallCaptured()) {
						theClaw.release();
						break;
					} else {
						exitPlan.addAll(nodeStack);
						nextNode = retrace(pilot);
					}
				}
				
				nextNode.setCameFrom(currentNode);
				nodeStack.push(nextNode);
				moveListener.setNode(nextNode);
				pilot.goTo(currentNode, nextNode);
				while(pilot.isMoving()) {
					Delay.msDelay(1000);
				}
				currentNode = nextNode;
				currentNode.setVisited(true);
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
		if (inRange(heading, FWD_HEADING, HEAD_TOL)) { // facing forward 90 degs. front node is north
			return new MazeNode(x, y + NODE_SIZE);
		} else if (inRange(heading, LEFT_HEADING, HEAD_TOL)) { // facing left 0 degs. front node is west
			return new MazeNode(x - NODE_SIZE, y);
		} else if (inRange(heading, RIGHT_HEADING, HEAD_TOL)) { // facing right. 180 degs. front node is east
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
		if (inRange(heading, FWD_HEADING, HEAD_TOL)) { // facing forward 90 degs. left node is west
			return new MazeNode(x - NODE_SIZE, y);
		} else if (inRange(heading, LEFT_HEADING, HEAD_TOL)) { // facing left 0 degs. left node is south
			return new MazeNode(x, y - NODE_SIZE);
		} else if (inRange(heading, RIGHT_HEADING, HEAD_TOL)) { // facing right 180 degs. left node is north
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
		if (inRange(heading, FWD_HEADING, HEAD_TOL)) { // facing forward 90 degs. right node is east
			return new MazeNode(x + NODE_SIZE, y);
		} else if (inRange(heading, LEFT_HEADING, HEAD_TOL)) { // facing left 0 degs. right node is north
			return new MazeNode(x, y + NODE_SIZE);
		} else if (inRange(heading, RIGHT_HEADING, HEAD_TOL)) { // facing right 180 degs. right node is south
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
	public static MazeNode retrace(Pilot pilot) {
		while(nodeStack.peek().visitedAllNeighbors()) {
			pilot.goTo(nodeStack.pop(), nodeStack.peek());
			while(pilot.isMoving()) {
				Delay.msDelay(1000);
			}
		}
		return nodeStack.peek().getUnvisitedNeighbor();
	}
	
	public static void exit(Pilot pilot, TheClaw theClaw) {
		if(exitPlan.isEmpty() || nodeStack.isEmpty())
			return;
		Iterator<MazeNode> exitIt = exitPlan.descendingIterator();
		Iterator<MazeNode> nodeIt = nodeStack.descendingIterator();
		MazeNode common = null;
		while(exitIt.hasNext()) {
			MazeNode exitNode = exitIt.next();
			MazeNode node = nodeIt.next();
		    if(exitNode.equals(node)) {
		    	common = exitNode;
		    	exitPlan.removeLast();
		    	nodeStack.removeLast();
		    }
		}
		
		if(common != null) {
			exitPlan.addLast(common);
		}
		while(!exitPlan.isEmpty()) {
			nodeStack.addLast(exitPlan.removeLast());
		}
		
		while(nodeStack.size() > 1) {
			MazeNode currentNode = nodeStack.pop();
			MazeNode nextNode = nodeStack.peek();
			pilot.goTo(currentNode, nextNode);
			while(pilot.isMoving()) {
				Delay.msDelay(1000);
			}
		}
		theClaw.release();
	}
}
