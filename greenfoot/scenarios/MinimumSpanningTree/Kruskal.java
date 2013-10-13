import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * An example of kruskal implementation
 * 
 * @author Rowanto
 * @version 1.0
 */
public class Kruskal extends Algorithm {
	/**
	 * A list of all the minimal edges
	 */
	LinkedList<Edge> minimal = new LinkedList<Edge>();

	/**
	 * A list of all edges
	 */
	LinkedList<Edge> allEdges = new LinkedList<Edge>();

	/**
	 * This method will be called by the framework whenever this object is added to world
	 */
	public void addedToWorld(World world) {
		super.addedToWorld(world);
		// Add all edges to the allEdges Variable
		Graph graph = (Graph) this.getWorld();
		allEdges.addAll(graph.getAllEdges());
	}

	/**
	 * Main - This is the method Dijkstra will do. Just think of this as the usual main method. Remember to put Greenfoot.hold() at the
	 * place where you want the pause or hold to happens.
	 */
	@Override
	public void main() {
		// As long as there's still edge in allEdges do the following
		while (allEdges.size() > 0) {

			// Get the smallest edge
			Edge smallest = getSmallestFromAllEdges();

			// Remove the smallest edge from allEdges
			allEdges.remove(smallest);

			// Check whether a circle exists from the edges in minimal
			if (!checkForCircle(smallest)) {
				// If not, then add it in the minimal group
				// Mark it using the help method
				// Change the vertex colors to green
				minimal.add(smallest);
				smallest.markAsInMinimal();
				smallest.getStart().setColor(Color.GREEN);
				smallest.getEnd().setColor(Color.GREEN);
			} else {
				// If yes, mark it using the help method
				smallest.markAsNotInMinimal();
			}
			// Hold the scenario for the user
			Greenfoot.hold();
		}
		// Stop the scenario, the algorithm is finished
		Greenfoot.stop();
	}

	/**
	 * Get the smallest Edge available
	 * 
	 * @return the smallest edge on the graph
	 */
	public Edge getSmallestFromAllEdges() {
		Edge result = null;

		for (Edge edge : allEdges) {
			if ((result == null) || (result.getWeight() > edge.getWeight())) {
				result = edge;
			}
		}
		return result;
	}

	/**
	 * Check whether a circle exists in minimal group if a specified edge is added This is a not very clean implementation and should be
	 * avoided This method serves only as an example
	 * 
	 * @param edge The addition edge which will be added
	 * @return true if there's circle, false if there's no circle
	 */
	public boolean checkForCircle(Edge edge) {
		// A list for a list of visited vertice
		List<Vertex> visitedVertices = new LinkedList<Vertex>();

		// A list of available edges consisting of the
		// Edges in the minimap group + the edge we want to test
		List<Edge> availableEdges = new LinkedList<Edge>();
		availableEdges.add(edge);
		availableEdges.addAll(minimal);

		// Get all the vertice from the availableEdges
		List<Vertex> availableVertice = new LinkedList<Vertex>();
		for (Edge currentEdge : availableEdges) {
			Vertex start = currentEdge.getStart();
			Vertex end = currentEdge.getEnd();
			if (!availableVertice.contains(start)) {
				availableVertice.add(start);
			}
			if (!availableVertice.contains(end)) {
				availableVertice.add(end);
			}
		}

		// Give the variable to the help method to check if there's circle
		Boolean bool = innerDepthSearch(availableVertice, availableEdges, visitedVertices);
		System.out.println("circle : " + bool);

		// Return it
		return bool;
	}

	/**
	 * Internal modified in depth search to check for circle This is a not very clean implementation and should be avoided This method
	 * serves only as an example
	 * 
	 * @param availableVertice The available vertice
	 * @param edges The edges
	 * @param visitedVertices The visited vertices
	 * @return true if there's circle, false if there's no circle
	 */
	private boolean innerDepthSearch(List<Vertex> availableVertice, List<Edge> edges, List<Vertex> visitedVertices) {

		// Create a queue
		LinkedList<Vertex> queue = new LinkedList<Vertex>();

		// Add the first vertice to the queue
		queue.add(availableVertice.get(0));

		// As long as queue not 0 do the following
		while (queue.size() != 0) {
			// Remove the current vertex from the queue
			Vertex currentVertex = queue.remove();

			// Add it to the list of visited vertice
			visitedVertices.add(currentVertex);

			// save the neighbours of the vertex
			// and remove the edge pointing to these neighbours
			List<Vertex> neighbours = new LinkedList<Vertex>();
			List<Edge> toBeRemoved = new LinkedList<Edge>();
			for (Edge edge : edges) {
				if (edge.getStart() == currentVertex) {
					neighbours.add(edge.getEnd());
					toBeRemoved.add(edge);
				} else if (edge.getEnd() == currentVertex) {
					neighbours.add(edge.getStart());
					toBeRemoved.add(edge);
				}
			}
			edges.removeAll(toBeRemoved);

			// If we ever run across a visited vertex, the graph has a circle
			// The same also apply if the visited vertex is already in the queue
			for (Vertex vertex : neighbours) {
				if (visitedVertices.contains(vertex) || queue.contains(vertex)) {
					return true;
				}

				// if there's no circle, then continue by adding more vertice
				if (availableVertice.contains(vertex)) {
					queue.addFirst(vertex);
				}
			}
		}

		// if the method ever reach here, it means no circle is found
		return false;
	}
}
