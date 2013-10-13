import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * An example of DFS implementation
 * 
 * @author Rowanto
 * @version 1.0
 */
public class DepthFirstSearch extends Algorithm {
	LinkedList<Vertex> queue = new LinkedList<Vertex>();

	/**
	 * As soon as this algorithm is added to the world, we add the start vertex to the queue
	 */
	public void addedToWorld(World world) {
		super.addedToWorld(world);
		Graph graph = (Graph) this.getWorld();
		Vertex startVertex = graph.getStartVertex();

		queue.add(startVertex);
	}

	/**
	 * The main method which will be called if the scenario is played
	 */
	@Override
	public void main() {
		processQueue();
	}

	/**
	 * Our main method for processing the queue
	 */
	public void processQueue() {
		while (queue.size() > 0) {

			// remove the current vertex from queue and save it
			Vertex currentVertex = queue.remove();
			int currentDistance = currentVertex.getDistance();

			// Get all the outward edges
			List<Edge> outwardEdges = currentVertex.getEdgesFromThisVertex();
			for (Edge edge : outwardEdges) {
				// Get the other vertex
				Vertex vertex = edge.getEnd();

				// Calculate the new distance
				int newDistance = currentDistance + edge.getWeight();

				// Set the color to blue if,
				// a vertex is red colored (never been traveled to)
				// or the new distance is smaller
				if (vertex.isColor(Color.RED) || (vertex.getDistance() > newDistance)) {
					vertex.setDistance(newDistance);
					vertex.setColor(Color.BLUE);
					vertex.setExtraInfo("first");
					vertex.setPrevious(currentVertex);
					queue.addFirst(vertex);
				}
			}

			// Change vertex color to green indicating all its neighbors
			// haven been traveled to. Also set the extra info as last
			currentVertex.setColor(Color.GREEN);
			currentVertex.setExtraInfo("last");

			// Hold the scenario, this ends a step in greenfoot
			Greenfoot.hold();
		}

		// Stop the scenario, indicating everything is finished
		Greenfoot.stop();
	}
}
