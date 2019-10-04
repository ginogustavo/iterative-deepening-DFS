package edu.uic.ai.puzzle;

import java.util.List;

public class TreeSearch {

	int[][] goal_state = new int[4][4]; // Target goal configuration

	// Initializing the Target goal configuration
	public TreeSearch() {
		int tileNumber = 1;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (i == 3 && j == 3) {
					goal_state[i][j] = 0;
				} else
					goal_state[i][j] = tileNumber;
				tileNumber++;
			}
		}
	}

	/**
	 * 
	 * @param problem
	 * @param limit
	 * @return Solution or Failure/Cutoff
	 */
	public Result depth_limited_search(String problem, int limit) {
		Node initialNode = new Node(); // Making a node based on initial state
		initialNode.setState(problem);
		initialNode.parseState(); // Parse from string to 2-D Array.

		// resulting Node, which can be solution, failure or cutoff
		return recursive_DLS(initialNode, limit);

	}

	public void printNode(Node node) {
		int[][] a = node.getStateArray();
		System.out.println("\n");
		System.out.println("---------------------------------");
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(a[i][j] + "\t| ");
			}
			System.out.print("\n---------------------------------\n");
		}

	}

	public int nodeCount = 0;

	public Result recursive_DLS(Node node, int limit) {

		//printNode(node);

		nodeCount++;

		if (goal_test(node.getStateArray())) { // if passed the test return Solution as Node
			return node;
		} else if (limit == 0) {
			return new Cutoff(); // Return that it was CUTOFF
		} else {

			boolean cutoff_ocurred = false;

			// Getting the list of actions available per Node
			List<Action> actions = node.getActions();

			for (Action action : actions) {

				Node child = childNode(node, action);

				Result result = recursive_DLS(child, limit - 1);

				if (result instanceof Cutoff) // if result = cutoff
					cutoff_ocurred = true;
				else if (!(result instanceof Failure)) // if result != failure
					return result;
			}

			if (cutoff_ocurred) {
				return new Cutoff();
			} else
				return new Failure();

		}
	}

	public void iterative_deepening_search(String problem) {
	}

	////////////////////////// Utility methods ///////////////////

	/**
	 * Test the goal by comparing the given state(array) with the target
	 * @param nodeState
	 * @return
	 */
	public boolean goal_test(int[][] nodeState) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (nodeState[i][j] != goal_state[i][j]) { // if only one is different
					return false; // goal test failed
				}
			}
		}
		return true; // by default
	}

	/**
	 * Create a new Child
	 * @param parent
	 * @param action
	 * @return
	 */
	public Node childNode(Node parent, Action action) {

		// Initialize new Child Node with parameters
		Node newChild = new Node();
		newChild.setMoves(parent.getMoves());
		newChild.setStateArray(parent.getStateArray());

		// Getting position of 0 in the matrix: e.g.  2,3
		String xyPosition = Node.getZeroPosition(newChild.getStateArray());
		int xpos = Integer.parseInt(xyPosition.charAt(0) + "");
		int ypos = Integer.parseInt(xyPosition.charAt(2) + "");

		int oldXPosition = xpos;
		int newXPosition = xpos;
		int oldYPosition = ypos;
		int newYPosition = ypos;

		// Calculate the new position
		switch (action) {
		case UP:
			newXPosition--;
			newChild.setMoves(newChild.getMoves() + "U");
			break;
		case DOWN:
			newXPosition++;
			newChild.setMoves(newChild.getMoves() + "D");
			break;
		case LEFT:
			newYPosition--;
			newChild.setMoves(newChild.getMoves() + "L");
			break;
		case RIGHT:
			newYPosition++;
			newChild.setMoves(newChild.getMoves() + "R");
			break;
		default:
			break;
		}

		// make copy of the state to process it
		int[][] tempNew = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				tempNew[i][j] = newChild.getStateArray()[i][j];
			}
		}

		// Value to be exchanged with 0
		int valueToExchange = newChild.getStateArray()[newXPosition][newYPosition];

		// Updated new Values for the new matrix
		tempNew[newXPosition][newYPosition] = 0;
		tempNew[oldXPosition][oldYPosition] = valueToExchange;

		// Assign new matrix to the created Child node
		newChild.setStateArray(tempNew);
		return newChild;
	}

}
