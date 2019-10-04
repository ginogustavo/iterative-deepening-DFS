import java.io.BufferedReader;
import java.io.InputStreamReader;

import edu.uic.ai.puzzle.Cutoff;
import edu.uic.ai.puzzle.Node;
import edu.uic.ai.puzzle.Result;
import edu.uic.ai.puzzle.TreeSearch;

public class PuzzleApp {

	public static void main(String[] args) {
		try {
			/*
			1 0 2 4 5 7 3 8 9 6 11 12 13 10 14 15 
			1 0 2 4 5 7 3 8 9 6 11 12 13 10 14 15
			1 0 3 4 5 2 6 8 9 10 7 11 13 14 15 12
			1 2 3 4 5 6 8 0 9 11 7 12 13 10 14 15
			1 0 2 4 5 7 3 8 9 6 11 12 13 10 14 15
			1 2 0 4 6 7 3 8 5 9 10 12 13 14 11 15
			1 3 4 8 5 2 0 6 9 10 7 11 13 14 15 12
			*/
			
			// Reading input
			System.out.print("Enter initial state: ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String initial = reader.readLine();
			if (initial == null || initial.isEmpty() || initial.length() == 0) {
				initial = "1 0 2 4 5 7 3 8 9 6 11 12 13 10 14 15";
				System.out.println("default: " + initial);
			}
			reader.close();

			// Setting initial time and memory
			Runtime runtime = Runtime.getRuntime();
			long initialMem = ((runtime.totalMemory() - runtime.freeMemory()) / 1024);
			long startTime = System.currentTimeMillis();

			// Running Breadth First Search
			TreeSearch search = new TreeSearch();
			Result result = search.iterative_deepening_search(initial);

			// Setting final time and memory
			long endTime = System.currentTimeMillis();
			long timeElapsed = endTime - startTime;
			long finalMem = ((runtime.totalMemory() - runtime.freeMemory()) / 1024);
			long memoryUsed = ((finalMem - initialMem));

			// Results
			if (result instanceof Node) {
				Node node = (Node) result;
				System.out.println("\nMoves: " + node.getMoves());
				System.out.println("Number of Nodes Expanded: " + search.getNodeCount());
				System.out.println("Time Taken: " + timeElapsed+" ms");
				System.out.println("Memory Used: " + memoryUsed + " Kb");

			} else if (result instanceof Cutoff) {
				System.out.println("Cuttoff -> Limit: " + search.getCutOff());
			} else {
				System.out.println("Failure");
			}

		} catch (Exception e) {
			System.out.println("Something went wrong, re-run the App");
		} finally {
			System.out.println("\n\nEnd of the program");
			System.exit(0);
		}
	}
}
