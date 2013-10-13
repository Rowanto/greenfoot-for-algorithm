import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
import java.util.LinkedList;

/**
 * An example of dijkstra implementation
 * 
 * @author Rowanto
 * @version 1.0
 */
public class Dijkstra extends Algorithm {
	/**
	 * All vertice which has been reached from the source
	 */
	LinkedList<Vertex> source = new LinkedList<Vertex>();

	/**
	 * All existing vertice
	 */
	LinkedList<Vertex> q = new LinkedList<Vertex>();

	/**
	 * This method will be called by the framework whenever this object is added to world
	 */
	@Override
	public void addedToWorld(World world) {
		super.addedToWorld(world);
		initializeSingleSource();
		q.addAll(((Graph) getWorld()).getAllVertices());
	}

	/**
	 * Main - This is the method Dijkstra will do. Just think of this as the usual main method. Remember to put Greenfoot.hold() at the
	 * place where you want the pause or hold to happens.
	 */
	@Override
	public void main() {
		// As long as q not empty is
		while (q.size() > 0) {

			// Get the vertex with the smallest distance from q
			Vertex u = extractMinFromQ();
			// Remove it from q itself
			q.remove(u);

			// If the distance from u is unlimited, then don't bother
			if (u.getDistance() == Integer.MAX_VALUE) {
				continue;
			}

			// if not, then add u to source
			source.add(u);

			// Relax all its neighbours
			for (Vertex v : u.getNeighbours()) {
				relax(u, v);
			}

			// Set the color as green
			u.setColor(Color.GREEN);

			// Hold the algorithm for the user
			Greenfoot.hold();
		}
		// Stop the algorith as it's finished
		Greenfoot.stop();
	}

	/**
	 * Initialize the graph
	 */
	public void initializeSingleSource() {
		Graph graph = (Graph) getWorld();
		Vertex startVertex = graph.getStartVertex();
		for (Vertex vertex : graph.getAllVertices()) {
			vertex.setDistance(Integer.MAX_VALUE);
			vertex.setPrevious(null);
		}
		graph.setVertexAsStart(startVertex);
	}

	/**
	 * The relax method
	 * 
	 * @param startVertex
	 * @param endVertex
	 */
	public void relax(Vertex startVertex, Vertex endVertex) {
		// Get the enge between the two vertices
		Edge edgeFromStartToEnd = startVertex.getEdgeToThisVertex(endVertex);

		// If there's no edge, just return
		if (edgeFromStartToEnd == null) {
			return;
		}

		// If the new distance smaller is, then update it
		int edgeWeight = edgeFromStartToEnd.getWeight();
		if (endVertex.getDistance() > startVertex.getDistance() + edgeWeight) {
			endVertex.setDistance(startVertex.getDistance() + edgeWeight);
			endVertex.setPrevious(startVertex);
			endVertex.setColor(Color.BLUE);
		}
	}

	/**
	 * Extract the min from Q
	 * 
	 * @return The vertex with the least value
	 */
	public Vertex extractMinFromQ() {
		// if q is empty, return null
		if (q.size() == 0) {
			return null;
		}

		// Get the vertex from q which has the smallest distance
		Vertex result = null;
		for (Vertex vertex : q) {
			if ((result == null) || (result.getDistance() > vertex.getDistance())) {
				result = vertex;
			}
		}
		return result;
	}
}
