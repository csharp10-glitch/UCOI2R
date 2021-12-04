package realFinalProject;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class TestEscape {
	static Deque<Character> nodeStack = new ArrayDeque<>();
	static Deque<Character> exitPlan = new ArrayDeque<>();

	/**
	 * exitPlan: (top) O (exit) N J F B A (bottom) 
	 * nodeStack: (top) P (ball) L K J F B A (bottom) 
	 * result: P L K J N O
	 */
	public static void main(String[] args) {
		char[] exitCharArr = "ONJFBA".toCharArray();
		for (int i = exitCharArr.length - 1; i >= 0; i--) {
			exitPlan.push(exitCharArr[i]);
		}

		char[] nodeCharArr = "PLKJFBA".toCharArray();
		for (int i = nodeCharArr.length - 1; i >= 0; i--) {
			nodeStack.push(nodeCharArr[i]);
		}

		System.out.println(exitPlan);
		System.out.println(nodeStack);

		Iterator<Character> exitIt = exitPlan.descendingIterator();
		Iterator<Character> nodeIt = nodeStack.descendingIterator();
		Character common = null;
		while (exitIt.hasNext()) {
			Character exitNode = exitIt.next();
			Character node = nodeIt.next();
			if (exitNode.equals(node)) {
				common = exitNode;
				exitPlan.removeLast();
				nodeStack.removeLast();
			}
		}

		if (common != null) {
			exitPlan.addLast(common);
		}
		while (!exitPlan.isEmpty()) {
			nodeStack.addLast(exitPlan.removeLast());
		}
		System.out.println(exitPlan);
		System.out.println(nodeStack);
	}

}
