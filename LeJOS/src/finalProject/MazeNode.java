package finalProject;

import java.util.ArrayList;
import java.util.List;

import lejos.robotics.navigation.Move;
import lejos.robotics.pathfinding.Node;

public class MazeNode extends Node{
	private boolean visited = false;
	List<Move> moves = new ArrayList<>();
	
	public MazeNode(float x, float y) {
		super(x, y);
	}
	
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	/**
	 * Bi-directionally add neighbors
	 */
	public boolean becomeNeighbors(MazeNode neighbor) {
		neighbor.addNeighbor(this);
		return addNeighbor(neighbor);
	}
	
	public List<Move> getMoves() {
		return moves;
	}
}
