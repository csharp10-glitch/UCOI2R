package a5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;

import javax.xml.stream.XMLStreamException;

import a4.ColorSensor;
import a4.Mast;
import a4.TheClaw;
import a4.UltrasonicSensor;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.geometry.Line;
import lejos.robotics.geometry.Point;
import lejos.robotics.geometry.Rectangle;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.mapping.SVGMapLoader;
import lejos.robotics.navigation.DestinationUnreachableException;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.pathfinding.ShortestPathFinder;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class A5 {
	final static float bufferSpaceForWall = 24.0f; // min distance (mm) away from the line. in our case wall

	public static void main(String[] args) {
		
		// Pilot
		A5Pilot pilot = new A5Pilot();
		Navigator nav = new Navigator(pilot);
		
		// Sensors
		ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
		UltrasonicSensor ussr = new UltrasonicSensor(SensorPort.S1);
		TheClaw theClaw = new TheClaw(MotorPort.C);
		Mast mast = new Mast(MotorPort.B);
		
		// Subsumption behaviors
		Behavior checkObstaclesBehavior = new CheckObstacles();
		Arbitrator arbitrator = new Arbitrator(new Behavior[] {});
		
		// Mapping
		LineMap map = A5.initializeMap();
		ShortestPathFinder pathPlanner = new ShortestPathFinder(map);
		pathPlanner.lengthenLines(bufferSpaceForWall);	
		
		//Data structure for future DFS implementation. See https://www.youtube.com/watch?v=iaBEKo5sM7w
		Stack<Point> stack = new Stack<>();
		Point startPt = new Point(340,420); // Update as needed
		stack.add(startPt);
		Point endPt = new Point(260, 0); // Update as needed
		
		float startHeading = 90f; // 0 parallel to x-axis, 90 parallel to y-axis. Update this if maze entrance requires different heading
		try {
			Pose start = new Pose(startPt.x, startPt.y, startHeading);
			Waypoint end = new Waypoint(endPt.x, endPt.y); 
			Path path = pathPlanner.findRoute(start, end);
			
			// TODO Need more work below
			int y = 3;
			LCD.drawString("Path Planned:", 0, y++);
			for (Waypoint point: path) {
				nav.goTo(point);
				
				// use subsumption models to add in other behaviors see lecture 5. For a5 we will need to detect walls and readjust for rotation errors
				// you can put object detection here
				// if objected detected: clear the path, stop, and replan with a new LineMap
				// nav.clearPath();
				// break;
				
				LCD.drawString(point.x + " ," + point.y, 1, y++);
				Delay.msDelay(2000);
			}
			
		} catch (DestinationUnreachableException e) {
			e.printStackTrace();
		}
	}
	
	public static LineMap initializeMap() {
		ArrayList<Line> lines = new ArrayList<>();
		
		// Horizontals top down
		lines.add(new Line(20.0f, 500.0f, 500.0f, 500.0f));
		lines.add(new Line(180.0f, 340.0f, 500.0f, 340.0f));
		lines.add(new Line(180.0f, 180.0f, 340.0f, 180.0f));
		lines.add(new Line(20.0f, 20.0f, 180.0f, 20.0f));
		lines.add(new Line(340.0f, 20.0f, 500.0f, 20.0f));
		
		// Verticals left to right
		lines.add(new Line(20.0f, 20.0f, 20.0f, 500.0f));
		lines.add(new Line(180.0f, 20.0f, 180.0f, 180.0f));
		lines.add(new Line(500.0f, 20.0f, 500.0f, 500.0f));
		Line[] lineArr = new Line[lines.size()];

		lineArr = lines.toArray(lineArr);
		
		return new LineMap(lineArr, new Rectangle(0.0f, 0.0f , 520.0f, 520.0f));
	}
}
