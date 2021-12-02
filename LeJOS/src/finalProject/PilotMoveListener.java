package finalProject;

import java.util.Stack;

import lejos.robotics.navigation.Move;
import lejos.robotics.navigation.MoveListener;
import lejos.robotics.navigation.MoveProvider;

public class PilotMoveListener implements MoveListener {
	private Stack<MazeNode> stack;
	
	public PilotMoveListener(Stack<MazeNode> stack) {
		this.stack = stack;
	}
	
	@Override
	public void moveStarted(Move event, MoveProvider mp) {
	
	}

	@Override
	public void moveStopped(Move event, MoveProvider mp) {
		// TODO All need to flesh out logic
		stack.peek().getMoves().add(event);
	}

}
