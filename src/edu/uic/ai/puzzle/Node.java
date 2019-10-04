package edu.uic.ai.puzzle;

import java.util.ArrayList;
import java.util.List;

public class Node extends Result {

	private String state = "";
	private String moves = "";
	private String time = "";
	private String memory = "";

	private int[][] stateArray = null;

	public List<Action> getActions() {

		// List of possible actions to be returned
		List<Action> possibleActions = new ArrayList<>();
		
		String lastMove = "";
		if (moves.length()!=0)
			lastMove = moves.substring(moves.length() - 1);

		// Get Position of Zero in the Matrix
		String xyPosition = getZeroPosition(this.getStateArray());
		int xPosition = Integer.parseInt(xyPosition.charAt(0) + "");
		int yPosition = Integer.parseInt(xyPosition.charAt(2) + "");
		
		/*
		 * According to the position of tile 0 and the limits in a 4x4 matrix (2d array)
		 * possible actions can be added
		 */
		if (xPosition >= 1 && !lastMove.equals("D")) // if row(i) >=1, then 0 can move UP
			possibleActions.add(Action.UP);

		if (xPosition <= 2 && !lastMove.equals("U")) // if row(i) <=2, then 0 can move DOWN
			possibleActions.add(Action.DOWN);

		if (yPosition >= 1 && !lastMove.equals("R")) // if column(j) >=1, then 0 can move LEFT
			possibleActions.add(Action.LEFT);

		if (yPosition <= 2 && !lastMove.equals("L")) // if column(j) <=2, then 0 can move RIGHT
			possibleActions.add(Action.RIGHT);

		return possibleActions;
	}

	public void parseState() {
		int[][] array = new int[4][4];
		String[] stringArray = this.state.split(" ");
		int counter = 0;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				array[i][j] = Integer.parseInt(stringArray[counter]);
				counter++;
			}
		}
		this.setStateArray(array);

	}

	public static String getZeroPosition(int[][] state) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (state[i][j] == 0) {
					return i + "," + j; // e.g. 2,3
				}
			}
		}
		return "";
	}

	// Getters and Setters
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMoves() {
		return moves;
	}

	public void setMoves(String moves) {
		this.moves = moves;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public int[][] getStateArray() {
		return stateArray;
	}

	public void setStateArray(int[][] stateArray) {
		this.stateArray = stateArray;
	}
}
