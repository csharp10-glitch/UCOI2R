//import java.util.Arrays;
//
//
//
//import lejos.hardware.motor.EV3LargeRegulatedMotor;
//import lejos.hardware.port.MotorPort;
//import lejos.hardware.port.SensorPort;
//import lejos.hardware.sensor.EV3GyroSensor;
//import lejos.hardware.sensor.EV3UltrasonicSensor;
//import lejos.hardware.sensor.SensorModes;
//import lejos.robotics.SampleProvider;
//import lejos.robotics.chassis.Chassis;
//import lejos.robotics.chassis.Wheel;
//import lejos.robotics.chassis.WheeledChassis;
//import lejos.robotics.navigation.MovePilot;
//
//public class Controller {
//
//	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
//	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
//	static SensorModes uss_sensor = new EV3UltrasonicSensor(SensorPort.S4);
//	static SensorModes gyro_sensor = new EV3GyroSensor(SensorPort.S3);
//
//	static int[][] grid = new int[6][5]; //TODO 23 width x 28 length
//	//0 - empty, 1 - block, 2 unknown, 3 path
//	
//	static int startY = 5;
//	static int startX = 4;
//	static int startRotation = 0;
//	static int endX = 0;
//	static int endY = 0;
//  static final int OPEN = 0;
//	static final int OBSTACLE = 1;
//	static final int PATH = 3;
//	static final int MOVEAMOUNT=25;
//	static final double MAXDISTANCE = .4;
//	static float distance;
//
//	static int x;
//	static int y;
//	static int r;
//	static Wheel wheel1 = WheeledChassis.modelWheel(LEFT_MOTOR, 3.9).offset(-10);
//	static Wheel wheel2 = WheeledChassis.modelWheel(RIGHT_MOTOR, 3.9).offset(10);
//	static Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
//	static MovePilot pilot = new MovePilot(chassis);
//
//	public static void main(String[] args) throws Exception {
//		// Set up the wheel for pilot.
//////////////////////////////////////////////////////////////////////////////////////////////////
//
//		// set up the chassis type: Differential pilot 
//		// Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 },
//		// WheeledChassis.TYPE_DIFFERENTIAL);
//
//////////////////////////////////////////////////////////////////////////////////////////////////
////
//		float distanceValue;
//		initializeMap(startX, startY, startRotation);// x,y,r
//		resetGrid();
//		printMap();
//
//		int index = 0;
//		while (true) {
//			index++; // Won't be Needed'
//			distanceValue = getDistance();
//			NextMove(distanceValue);
//		
//		}
//		
//	
//	}
//
//	public static void printMap() {
//		for (int i = 0; i < grid.length; i++) {
//			for (int j = 0; j < grid[i].length; j++) {
//				System.out.print(grid[i][j] + " , ");
//
//			}
//			System.out.println("");
//		}
//	}
//
//	// Gets the Distance
//	public static float getDistance() {
//
//
//		float distance =  5;
//
//
//		SampleProvider distance_provider = uss_sensor.getMode("Distance");
//		float[] sample = new float[distance_provider.sampleSize()];
//		distance_provider.fetchSample(sample, 0);
//		for(int i = 0; i < distance_provider.sampleSize(); i++){
//			distance += sample[i];
//		}
//		distance = distance / distance_provider.sampleSize();
//		return sample[0];
//
//	}
//
//	public static float getAngle(){
//		float angle = 0;
//		SampleProvider angle_provider = gyro_sensor.getMode("Angle");
//		float[] sample = new float[angle_provider.sampleSize()];
//		angle_provider.fetchSample(sample,0);
//		for(int i = 0; i < angle_provider.sampleSize();i++){
//			angle += sample[i];
//		}
//		angle = angle / angle_provider.sampleSize();
//		return angle;
//	}
//
//	public static void Rotate(int degree) {
//		
//		pilot.rotate(degree);
//		int currentAngle = (int)getAngle();
//		while(currentAngle > 360){
//			currentAngle = currentAngle - 360; //gyro will add or subtract past 360, correct for this.
//		}
//		while(currentAngle < 0){
//			currentAngle = currentAngle + 360;
//		}
//
////		if(currentAngle < degree)
////		{// if it couldn't get an accurate turn the first time, it likely won't the second either. we can tighten it a bit though.
////			pilot.rotate((degree - currentAngle) / 2); // try to move about half the distance, to get a little more accuracy
////		} 
////		else if(currentAngle > degree){
////			pilot.rotate((currentAngle - degree) / 2); // try to move about half the distance, to get a little more accuracy
////		}
//		r += degree; // Josh likely depends on this somewhere, but you could set it based off gyro, youd have to adjust for inaccuracies.
//		
//		while(r<0 || r >=360) {
//			if (r >= 360) {
//				r = r-360;
//			}
//			if(r<0) {
//				r = 360+r;
//			}
//		}
//		
//	}
//
//
//
//   /*
//	public static void Rotate(int degree) {
//		
//		pilot.rotate(degree);
//		
//		r += 90;
//		if (r == 360) {
//			r = 0;
//		}
//	}
//	*/
//
//
//	// moves an amount int centimeters.
//	public static void Move(int amount) {
//		pilot.travel(amount);
//		switch (r) {
//		case 90:
//			x++;
//			break;
//		case 180:
//			y++;
//			break;
//		case 270:
//			x--;
//			break;
//		case 0:
//			y--;
//			break;
//		}
//	}
//
//	public static void NextMove(float distanceVal) throws Exception {
//		System.out.println(distanceVal);
//		if (distanceVal < MAXDISTANCE) {
//			// Mark object as blocked. If the object is incorrectly marked in the map,
//			// restart the algorithm and mark the path.
//			switch (r) {
//			case 90:
//
//				if (x < grid[0].length -1 && grid[y][x + 1] != OBSTACLE) { //is there obstacle on map?
//					System.out.println(" Obstacle at y, x+1 ");
//					grid[y][x + 1] = OBSTACLE; // mark map
//					startPathfinding(); //find new path
//				}
//				 else {
//					MoveUp();
//					System.out.println(" Keep Moving  ");
//				}
//
//				break;
//			case 180:
//				if (y < grid.length -1 &&  grid[y + 1][x] != OBSTACLE) {
//					grid[y + 1][x] = OBSTACLE;
//					System.out.println(" Obstacle at y+1, x ");
//					startPathfinding();
//				} else {
//					MoveUp();
//					System.out.println(" Keep Moving  ");
//				}
//				break;
//			case 270:
//
//				if (x>0 && grid[y][x - 1] != OBSTACLE) {
//					System.out.println(" Obstacle at y, x-1 ");
//					grid[y][x - 1] = OBSTACLE;
//
//					startPathfinding();
//				} else {
//					MoveUp();
//					System.out.println(" Keep Moving  ");
//				}
//				break;
//			case 0:
//
//				if (y>0 && grid[y - 1][x] != OBSTACLE) {
//					System.out.println(" Obstacle at y-1");
//					grid[y - 1][x] = OBSTACLE;
//					startPathfinding();
//				} else {
//					MoveUp();
//					System.out.println(" Keep Moving  ");
//				}
//				break;
//
//			}
//
//		}
//
//		else {
//			MoveUp();
//		}
//	}
//
//	// Moves Towards the Next "3" marked by Pathfinder.java
//	public static void MoveUp() {
//
////		if (grid[y][x + 1] == PATH) { 
////			RotateTowards(y, x + 1);
////			Move(5);
////			
////			grid[x][y] = OPEN;
////		} else if (grid[y][x - 1] == PATH) {
////			RotateTowards(y, x - 1);
////			Move(5);
////			
////			grid[x][y] = OPEN;
////		} else if (grid[y + 1][x] == PATH) {
////			RotateTowards(y + 1, x);
////			Move(5);
////		
////			grid[x][y] = OPEN;
////		} else if (grid[y - 1][x] == PATH) {
////			RotateTowards(y - 1, x);
////			Move(5);
////		
////			grid[x][y] = OPEN;
//
//		
//		if (x < grid[0].length-1 && grid[y][x + 1] == PATH) { //checking right grid
//			System.out.println("Checked right grid and rotate towards y, x+1");
//			RotateTowards(x + 1, y);
//			int newx = x + 1;
//			System.out.println("rotated x + 1, y: " + newx + ", " + y);
//			grid[y][x] = OPEN;
//			Move(MOVEAMOUNT);
//			
//			grid[y][x] = OPEN;
//		} else if (x > 0 && grid[y][x - 1] == PATH) {  //checking left grid
//			System.out.println("Checked right grid and rotate towards y, x-1");
//			RotateTowards(x - 1, y);
//			int newx = x - 1;
//			System.out.println("rotated x - 1, y: " + newx + ", " + y);
//			grid[y][x] = OPEN;
//			Move(MOVEAMOUNT);
//			
//			grid[y][x] = OPEN;
//		} else if (y < grid.length-1 && grid[y + 1][x] == PATH) { //checking bottom grid
//			System.out.println("Checked right grid and rotate towards y+1,x");
//			RotateTowards(x, y + 1);
//			int newy = y + 1;
//			System.out.println("rotated x, y + 1: " + x + ", " + newy);
//			grid[y][x] = OPEN;
//			Move(MOVEAMOUNT);
//			
//			grid[y][x] = OPEN;
//		} else if (y > 0 && grid[y - 1][x] == PATH) { //checking top grid
//			System.out.println("Checked right grid and rotate towards y-1, x");
//			RotateTowards(x, y - 1);
//			int newy = y - 1;
//			System.out.println("rotated x, y - 1: " + x + ", " + newy);
//			grid[y][x] = OPEN;
//			Move(MOVEAMOUNT);
//			
//			grid[y][x] = OPEN;
//
//		}
//
//	}
//
//	public static void startPathfinding() throws Exception {
//		resetGrid();
//	}
//
//	// Rotates towards a given x and y.
//	public static void RotateTowards(int localX, int localY) {
//		if (localX > x) {
//			if (r == 0) {
//				Rotate(90);
//			} else if (r == 90) {
//				// Do Nothing
//			} else if (r == 180) {
//				Rotate(-90);
//			} else if (r == 270) {
//				Rotate(180);
//			}
//		}
//
//		if (localX < x) {
//			if (r == 0) {
//				Rotate(-90);
//			} else if (r == 90) {
//				Rotate(180);
//			} else if (r == 180) {
//				Rotate(90);
//			} else if (r == 270) {
//				// Do Nothing
//			}
//		}
//
//		if (localY > y) {
//			if (r == 0) {
//				Rotate(180);
//			} else if (r == 90) {
//				Rotate(90);
//			} else if (r == 180) {
//				// Do Nothing
//			} else if (r == 270) {
//				Rotate(-90);
//			}
//		}
//
//		if (localY < y) {
//			if (r == 0) {
//				// Do Nothing
//			} else if (r == 90) {
//				Rotate(-90);
//			} else if (r == 180) {
//				Rotate(180);
//			} else if (r == 270) {
//				Rotate(90);
//			}
//		}
//	}
//
//	// Resets all the 4s and 3s back to 0s
//	public static void resetGrid() throws Exception {
//		int i;
//		int j;
//		for (i = 0; i < grid.length; i++) {
//			for (j = 0; j < grid[i].length; j++) {
//				if (grid[i][j] != OBSTACLE) {
//					grid[i][j] = OPEN;
//				}
//			}
//	     i = 0;
//	     j = 0;
//	     		grid[endY][endX] = 4;
//	     		grid[y][x]=2;
//			
//				int[][] tempMaze = MazeDriver.execute(grid);
//				for (i = 0; i < grid.length; i++) {
//					for (j = 0; j < grid[i].length; j++) {
//						if (tempMaze[i][j] == PATH) {
//							grid[i][j] = PATH;
//						}
//						
//						
//					}
//
//				}
//				grid[endY][endX] = 3;
//				
//		}
//
//	}
//
//	public static void initializeMap(int xLocal, int yLocal, int rLocal) {
//
//		x = xLocal;
//		y = yLocal;
//		r = rLocal;
////
//		for (int i = 0; i < grid.length; i++) {
//			for (int j = 0; j < grid[i].length; j++) {
//				grid[i][j] = OPEN;
//
//			}
//		}
//	
//
//	}
//}