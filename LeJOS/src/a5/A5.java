package a5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Stack;

import javax.xml.stream.XMLStreamException;

import a4.ColorSensor;
import a4.Mast;
import a4.TheClaw;
import a4.UltrasonicSensor;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.geometry.Point;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.mapping.SVGMapLoader;
import lejos.robotics.navigation.DestinationUnreachableException;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.pathfinding.ShortestPathFinder;
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
		
		// Mapping
		LineMap map = A5.initializeMap();
		ShortestPathFinder pathPlanner = new ShortestPathFinder(map);
		pathPlanner.lengthenLines(bufferSpaceForWall);	
		
		//Data structure for future DFS implementation
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
		try {
			SVGMapLoader loader = new SVGMapLoader(new FileInputStream(new File("src/a5/3x3Maze.svg")));
			LineMap lineMap = loader.readLineMap();
			return lineMap.flip(); //svg files upper left is 0,0 whereas LeJos bottom left is 0,0 for (x,y). Thus requiring flipping 
		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace();
		} 
		return null;
	}
}
