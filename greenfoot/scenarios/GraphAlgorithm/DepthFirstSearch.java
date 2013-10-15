import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
import java.util.List;

/**
 * An example of DFS implementation
 * 
 * @author Rowanto
 * @version 1.0
 */
public class DepthFirstSearch extends Algorithm {

	public void addedToWorld(World world) {
		super.addedToWorld(world);
	}

	/**
	 * The main method which will be called if the scenario is played
	 */
	@Override
	public void main() {
		processQueue(((Graph) getWorld()).getStartVertex());
		Greenfoot.stop();
	}

	public void processQueue(Vertex currentVertex) {
		currentVertex.setColor(Color.BLUE);
		currentVertex.setExtraInfo("first");
		int currentDistance = currentVertex.getDistance();
		Greenfoot.hold();

		// Get the neighbours
		List<Vertex> neighbours = currentVertex.getNeighbours();
		for (Vertex neighbour : neighbours) {
			// Set the color to blue if,
			// a vertex is red colored (never been traveled to)
			if (neighbour.isColor(Color.RED)) {
				neighbour.setDistance(currentDistance + 1);
				neighbour.setColor(Color.BLUE);
				neighbour.setExtraInfo("first");
				neighbour.setPrevious(currentVertex);
				processQueue(neighbour);
			}
		}

		// Change vertex color to green indicating all its neighbors
		// haven been traveled to. Also set the extra info as last
		currentVertex.setColor(Color.GREEN);
		currentVertex.setExtraInfo("last");

		// Hold the scenario, this ends a step in greenfoot
		Greenfoot.hold();
	}
}
