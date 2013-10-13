import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * An example of BFS implementation
 * 
 * @author Rowanto
 * @version 1.0
 */
public class BreadthFirstSearch extends Algorithm {
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

			List<Vertex> neighbours = currentVertex.getNeighbours();
			for (Vertex vertex : neighbours) {
				// Set the color to blue if,
				// a vertex is red colored (never been traveled to)
				// or the new distance is smaller
				if (vertex.isColor(Color.RED) || (vertex.getDistance() > (currentDistance + 1))) {
					vertex.setDistance(currentDistance + 1);
					vertex.setColor(Color.BLUE);
					vertex.setPrevious(currentVertex);
					queue.add(vertex);
				}
			}

			// Change vertex color to green indicating all its neighbors
			// haven been traveled to
			currentVertex.setColor(Color.GREEN);

			// Hold the scenario, this ends a step in greenfoot
			Greenfoot.hold();
		}
		// Stop the scenario, indicating everything is finished
		Greenfoot.stop();
	}
}