import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
import java.util.List;

/**
 * An example of bellman ford implementation
 * 
 * @author Rowanto
 * @version 1.0
 */
public class BellmanFord extends Algorithm {
	/**
	 * This method will be called whenever this object is put into the world Remember to call the super method of this overriden method.
	 */
	@Override
	public void addedToWorld(World world) {
		super.addedToWorld(world);
		initializeSingleSource();
	}

	/**
	 * Main - This is the method BellmanFord will do. Just think of this as the usual main method. Remember to put Greenfoot.hold() at the
	 * place where you want the pause or hold to happens.
	 */
	@Override
	public void main() {
		List<Vertex> allVertice = ((Graph) getWorld()).getAllVertices();
		List<Edge> allEdges = ((Graph) getWorld()).getAllEdges();

		// Relax edges again and again
		for (int i = 1; i < allVertice.size() - 1; i++) {
			for (Edge edge : allEdges) {
				Vertex u = edge.getStart();
				Vertex v = edge.getEnd();
				relax(u, v);
			}
			// Hold it for the user
			Greenfoot.hold();
		}

		// Check for negative-weight cycle
		for (Edge edge : allEdges) {
			Vertex u = edge.getStart();
			Vertex v = edge.getEnd();
			if ((u.getDistance() + edge.getWeight()) < v.getDistance()) {
				System.out.println("Graph contains a negative-weight cycle");
				break;
			}
		}

		// Stop the scenario
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
}
