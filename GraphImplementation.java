import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * Implementation of a graph with topological sort.
 *
 */
public class GraphImplementation implements Graph {
	int[][] adjMatrix;
	int vertices;

	/**
	 * Initializes the adjacency matrix to the number of vertices
	 */
	public GraphImplementation(int vertices) {
		adjMatrix = new int[vertices][vertices];
		this.vertices = vertices;
	}

	@Override
	public void addEdge(int v1, int v2) {
		adjMatrix[v1][v2] = 1;
	}

	@Override
	public List<Integer> topologicalSort() {
		int[] incident = new int[vertices];

		// should visit all nodes (vertices)
		int visited = 0;

		for (int j = 0; j < vertices; j++) {

			int incidents = 0;
			for (int i = 0; i < vertices; i++) {
				if (adjMatrix[i][j] != 0) {
					incidents++;
				}
			}
			incident[j] = incidents;
		}

		// Queue of vertices to run through
		Queue<Integer> toVisit = new LinkedList<Integer>();
		ArrayList<Integer> sorted = new ArrayList<Integer>();

		// initial queue of vertices to visit
		for (int i = 0; i < vertices; i++) {
			if (incident[i] == 0) {
				toVisit.add(i);
			}
		}

		// check current vertices
		Integer current = toVisit.poll();
		while (current != null) {
			sorted.add(current);
			visited++;

			for (int i : neighbors(current)) {
				incident[i] = incident[i] - 1;
				if (incident[i] == 0) {
					toVisit.add(i);
				}
			}

			current = toVisit.poll();
		}

		// print topological sort
		for (int i : sorted) {
			System.out.println(i);
		}

		// print message if not valid graph for topological (e.g. cycle occurs)
		if (visited != vertices) {
			System.out.println("Topological sort not possible.");
		}
		return sorted;
	}

	@Override
	public int[] neighbors(int vertex) {
		int[] neigh = new int[vertices];

		for (int i = 0; i < adjMatrix.length; i++) {
			if (adjMatrix[vertex][i] > 0) {
				neigh[i] = i;
			}
		}

		// turn neigh into an array with proper size
		neigh = Arrays.stream(neigh).filter(i -> i != 0).toArray();

		return neigh;
	}

}
