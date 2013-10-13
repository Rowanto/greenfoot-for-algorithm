import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 * A subclass of the world, the graph which will host all the objects
 * 
 * @author Rowanto
 * @version 1.0
 */
public class Graph extends World {
	/**
	 * The default cell size of this graph
	 */
	public static final int DEFAULT_CELL_SIZE = 1;

	/**
	 * The width and height of this graph
	 */
	public static final int WORLD_SIZE = 600;

	/**
	 * The minimal number of random generated vertex
	 */
	public static final int MIN_NUM_Vertex = 5;

	/**
	 * The maximal number of random generated vertex
	 */
	public static final int MAX_NUM_Vertex = 15;

	/**
	 * The default distance between two vertice when randomly generated.
	 */
	public static final int DEFAULT_DISTANCE = 50;

	/**
	 * The default background file name
	 */
	public static final String DEFAULT_BACKGROUND_FILENAME = "cell.jpg";

	/**
	 * Controls whether the image should snap to the image grid or not
	 */
	public static final boolean SNAP_TO_IMAGE_GRID = true;

	/**
	 * Controls whether to show the name and the extra info in vertices.
	 */
	public static boolean detailedInfo = true;

	/**
	 * Constructor for objects of class Graph.
	 * 
	 */
	public Graph() {
		super(WORLD_SIZE, WORLD_SIZE, DEFAULT_CELL_SIZE);
		setPaintOrder(Vertex.class, Edge.class);
		setBackground(DEFAULT_BACKGROUND_FILENAME);
	}

	/**
	 * Add a vertex to the graph
	 * 
	 * @param vertex the vertex to be added
	 * @param x the x coordinate of the vertex
	 * @param y the y coordinate of the vertex
	 */
	public void addVertex(Vertex vertex, int x, int y) {
		addObject(vertex, x, y);
	}

	/**
	 * Add an edge to the graph
	 * 
	 * @param edge the edge to be added
	 */
	public void addEdge(Edge edge) {
		addObject(edge, 0, 0);
	}

	/**
	 * Add a random amount of vertices
	 * 
	 * @param verticeCount The number of vertices to be generated
	 */
	private void addRandomVertice(int verticeCount) {
		Random randomGenerator = new Random();
		for (int i = 0; i < verticeCount; i++) {
			StringBuilder name = new StringBuilder();
			int aNumericValue = Integer.valueOf('a');
			int charIndex = i;
			while (true) {
				name.append(Character.toChars(charIndex % 26 + aNumericValue));
				if ((charIndex / 26) == 0) {
					break;
				}
				charIndex = charIndex - 26;
			}
			Vertex Vertex = new Vertex(name.toString());

			int randomX;
			int randomY;
			List<Vertex> objectsFound;

			randomX = randomGenerator.nextInt(WORLD_SIZE + 1);
			randomY = randomGenerator.nextInt(WORLD_SIZE + 1);

			objectsFound = getAllVertices(randomX, randomY);

			int tries = 0;
			while (objectsFound.size() != 0) {
				if (tries > 50) {
					JOptionPane.showMessageDialog(new JInternalFrame(), "It seems like there's no place left to add a vertex.",
							"No place left?", JOptionPane.INFORMATION_MESSAGE);

					return;
				}
				randomX = randomGenerator.nextInt(WORLD_SIZE + 1);
				randomY = randomGenerator.nextInt(WORLD_SIZE + 1);

				objectsFound = getAllVertices(randomX, randomY);
				tries++;
			}

			this.addObject(Vertex, randomX, randomY);
		}
	}

	/**
	 * Add a random amount of edges to the existing vertice
	 * 
	 * @param edgeCount The number of edges to be generated
	 */
	private void addRandomEdges(int edgeCount) {
		Random randomGenerator = new Random();
		List<Vertex> vertices = getAllVertices();

		int maximumAmountOfEdges = getMaximumAmountOfEdges();
		if (edgeCount > maximumAmountOfEdges) {
			JOptionPane.showMessageDialog(new JInternalFrame(), "Number of edges exceed the maximum amount. Maximum amount is "
					+ maximumAmountOfEdges + ".", "Too much edges.", JOptionPane.INFORMATION_MESSAGE);

			return;
		}

		Vertex previousVertex = null;
		int tileSize = new GreenfootImage(DEFAULT_BACKGROUND_FILENAME).getWidth();
		for (Vertex vertex : vertices) {
			if (previousVertex != null) {
				int distance = getDistanceBetweenTwoVerticeInPixel(previousVertex, vertex) / tileSize;
				Edge edge = new Edge(previousVertex, vertex, distance);
				addObject(edge, 0, 0);
			}
			previousVertex = vertex;
		}

		for (Vertex vertex : vertices) {
			Vertex startEdge = vertex;
			Vertex endEdge = vertices.get(randomGenerator.nextInt(vertices.size()));
			if (!checkEdgeExists(startEdge, endEdge)) {
				int distance = getDistanceBetweenTwoVerticeInPixel(previousVertex, vertex) / tileSize;
				Edge edge = new Edge(startEdge, endEdge, distance);
				addObject(edge, 0, 0);
			}
		}
		/*
		 * int addedEdge = 0; while (addedEdge <= edgeCount) { Vertex startEdge = vertices.get(randomGenerator.nextInt(vertices.size()));
		 * Vertex endEdge = vertices.get(randomGenerator.nextInt(vertices.size())); if (!checkEdgeExists(startEdge,endEdge)) { Edge edge =
		 * new Edge(startEdge, endEdge); addObject(edge, 0, 0); addedEdge++; } }
		 */
		setVertexAsStart(getRandomVertex());
	}

	/**
	 * Create a list of predefined vertice and edges
	 */
	public void prepareExampleGraph() {
		this.removeObjects(this.getObjects(null));

		Vertex vertex = new Vertex("a");
		addObject(vertex, 210, 150);

		Vertex vertex2 = new Vertex("b");
		addObject(vertex2, 90, 150);

		Vertex vertex3 = new Vertex("c");
		addObject(vertex3, 90, 270);

		Vertex vertex4 = new Vertex("d");
		addObject(vertex4, 210, 270);

		Vertex vertex5 = new Vertex("e");
		addObject(vertex5, 330, 150);

		Vertex vertex6 = new Vertex("f");
		addObject(vertex6, 330, 270);

		Vertex vertex7 = new Vertex("g");
		addObject(vertex7, 450, 150);

		Vertex vertex8 = new Vertex("h");
		addObject(vertex8, 450, 270);

		Edge edge = new Edge(vertex2, vertex3, DEFAULT_DISTANCE);
		addObject(edge, 0, 0);

		Edge edgex = new Edge(vertex3, vertex2, DEFAULT_DISTANCE);
		addObject(edgex, 0, 0);

		Edge edge2 = new Edge(vertex, vertex2, DEFAULT_DISTANCE);
		addObject(edge2, 0, 0);

		Edge edge3 = new Edge(vertex, vertex4, DEFAULT_DISTANCE);
		addObject(edge3, 0, 0);

		Edge edge3x = new Edge(vertex4, vertex, DEFAULT_DISTANCE);
		addObject(edge3x, 0, 0);

		Edge edge4 = new Edge(vertex4, vertex5, DEFAULT_DISTANCE);
		addObject(edge4, 0, 0);

		Edge edge4x = new Edge(vertex5, vertex4, DEFAULT_DISTANCE);
		addObject(edge4x, 0, 0);

		Edge edge5 = new Edge(vertex5, vertex6, DEFAULT_DISTANCE);
		addObject(edge5, 0, 0);

		Edge edge6 = new Edge(vertex4, vertex6, DEFAULT_DISTANCE);
		addObject(edge6, 0, 0);

		Edge edge7 = new Edge(vertex5, vertex7, DEFAULT_DISTANCE);
		addObject(edge7, 0, 0);

		Edge edge8 = new Edge(vertex7, vertex8, DEFAULT_DISTANCE);
		addObject(edge8, 0, 0);

		Edge edge9 = new Edge(vertex6, vertex8, DEFAULT_DISTANCE);
		addObject(edge9, 0, 0);

		Edge edge9x = new Edge(vertex8, vertex6, DEFAULT_DISTANCE);
		addObject(edge9x, 0, 0);

		setVertexAsStart(vertex);
	}

	/**
	 * Create a list of predefined vertice and edges for dijkstra algorithm
	 */
	public void prepareDijkstraGraph() {
		this.removeObjects(this.getObjects(null));

		Vertex vertex = new Vertex("a");
		addObject(vertex, 93, 225);

		Vertex vertex2 = new Vertex("b");
		addObject(vertex2, 202, 107);

		Vertex vertex3 = new Vertex("c");
		addObject(vertex3, 212, 336);

		Vertex vertex4 = new Vertex("d");
		addObject(vertex4, 380, 103);

		Vertex vertex5 = new Vertex("e");
		addObject(vertex5, 404, 345);

		Vertex vertex6 = new Vertex("f");
		addObject(vertex6, 520, 216);

		Edge edge = new Edge(vertex, vertex2, 2);
		addObject(edge, 279, 218);

		Edge edge2 = new Edge(vertex, vertex3, 1);
		addObject(edge2, 428, 471);

		Edge edge3 = new Edge(vertex2, vertex3, 3);
		addObject(edge3, 554, 452);

		Edge edge4 = new Edge(vertex2, vertex4, 3);
		addObject(edge4, 503, 497);

		Edge edge5 = new Edge(vertex3, vertex5, 1);
		addObject(edge5, 537, 449);

		Edge edge6 = new Edge(vertex5, vertex4, 2);
		addObject(edge6, 548, 458);

		Edge edge7 = new Edge(vertex4, vertex6, 2);
		addObject(edge7, 471, 505);

		Edge edge8 = new Edge(vertex5, vertex6, 5);
		addObject(edge8, 364, 518);

		setVertexAsStart(vertex);
	}

	/**
	 * Create a list of predefined vertice and edges for bellman-ford algorithm
	 */
	public void prepareBellmanGraph() {
		Vertex vertex = new Vertex("a");
		addObject(vertex, 92, 212);

		Vertex vertex2 = new Vertex("b");
		addObject(vertex2, 268, 97);

		Vertex vertex3 = new Vertex("c");
		addObject(vertex3, 503, 91);

		Vertex vertex4 = new Vertex("d");
		addObject(vertex4, 275, 345);

		Vertex vertex5 = new Vertex("e");
		addObject(vertex5, 512, 337);

		Edge edge = new Edge(vertex, vertex2, 6);
		addObject(edge, 386, 403);

		Edge edge2 = new Edge(vertex, vertex4, 7);
		addObject(edge2, 457, 500);

		Edge edge3 = new Edge(vertex4, vertex5, 9);
		addObject(edge3, 547, 497);

		Edge edge4 = new Edge(vertex2, vertex4, 8);
		addObject(edge4, 503, 486);

		Edge edge5 = new Edge(vertex4, vertex3, -3);
		addObject(edge5, 507, 521);

		Edge edge6 = new Edge(vertex2, vertex5, -4);
		addObject(edge6, 566, 432);

		Edge edge7 = new Edge(vertex5, vertex3, 7);
		addObject(edge7, 493, 483);

		Edge edge8 = new Edge(vertex2, vertex3, 5);
		addObject(edge8, 529, 494);

		Edge edge9 = new Edge(vertex3, vertex2, -2);
		addObject(edge9, 536, 462);

		setVertexAsStart(vertex);
	}

	/**
	 * Create a random amount of vertice and edges.
	 */
	public void prepareRandomGraph() {
		Random randomGenerator = new Random();
		int verticeCount = randomGenerator.nextInt(MAX_NUM_Vertex - MIN_NUM_Vertex + 1) + MIN_NUM_Vertex;
		int edgeCount = randomGenerator.nextInt(getMaximumAmountOfEdges() + 1);
		prepareRandomGraph(verticeCount, edgeCount);
	}

	/**
	 * Create an amount of random vertice and edges.
	 * 
	 * @param vertexCount The number of
	 */
	public void prepareRandomGraph(int vertexCount, int edgeCount) {
		this.removeObjects(this.getObjects(null));

		addRandomVertice(vertexCount);
		int maximumCountOfEdges = getMaximumAmountOfEdges();
		if (edgeCount > maximumCountOfEdges) {
			edgeCount = maximumCountOfEdges;
			System.out.println("The possible amount of edges is only : " + maximumCountOfEdges);
		}
		addRandomEdges(edgeCount);
	}

	/**
	 * Get all the vertice which exists on the graph
	 * 
	 * @return A list of vertice which exists on the graph
	 */
	public List<Vertex> getAllVertices() {
		List<Vertex> result = new ArrayList<Vertex>();
		for (Object object : this.getObjects(Vertex.class)) {
			result.add((Vertex) object);
		}
		return result;
	}

	/**
	 * Get all the vertice which exists on a specific location
	 * 
	 * @param xPosition The x coordinate of the existing vertice
	 * @param yPosition The y coordinate of the existing vertice
	 * @return A list of vertice which exists on the coordinates
	 */
	public List<Vertex> getAllVertices(int xPosition, int yPosition) {
		List<Vertex> result = new ArrayList<Vertex>();
		for (Object object : this.getObjectsAt(xPosition, yPosition, Vertex.class)) {
			result.add((Vertex) object);
		}
		return result;
	}

	/**
	 * Get all the edges which exists on the graph
	 * 
	 * @return A list of edges which exists on the graph
	 */
	public List<Edge> getAllEdges() {
		List<Edge> result = new ArrayList<Edge>();
		for (Object object : this.getObjects(Edge.class)) {
			result.add((Edge) object);
		}
		return result;
	}

	/**
	 * Get all the edges which exists on a specific location
	 * 
	 * @param xPosition The x coordinate of the existing edges
	 * @param yPosition The y coordinate of the existing edges
	 * @return A list of edges which exists on the coordinates
	 */
	public List<Edge> getAllEdges(int xPosition, int yPosition) {
		List<Edge> result = new ArrayList<Edge>();
		for (Object object : this.getObjectsAt(xPosition, yPosition, Edge.class)) {
			result.add((Edge) object);
		}
		return result;
	}

	/**
	 * Get all vertice which are neighbours with a vertex
	 * 
	 * @param vertex The vertex whose neighbours we want to find
	 * @return A list of vertice which are the neighbours of the input vertex
	 */
	public List<Vertex> getNeighbours(Vertex vertex) {
		List<Vertex> result = new ArrayList<Vertex>();
		for (Edge edge : this.getAllEdges()) {
			if (edge.getStart() == vertex) {
				result.add(edge.getEnd());
			} else if (edge.getEnd() == vertex) {
				result.add(edge.getStart());
			}
		}
		return result;
	}

	/**
	 * Get all the edges which points or starts from a vertex
	 * 
	 * @param vertex The vertex whose incident edges we want to fine
	 * @return A list of edges which are incident with the input vertex
	 */
	public List<Edge> getIncidentEdges(Vertex vertex) {
		List<Edge> result = new ArrayList<Edge>();
		for (Edge edge : this.getAllEdges()) {
			if ((edge.getStart() == vertex) || (edge.getEnd() == vertex)) {
				result.add(edge);
			}
		}
		return result;
	}

	/**
	 * Get all the edges which starts from a vertex
	 * 
	 * @param vertex The vertex whose from-edges we want to fine
	 * @return A list of edges which are starts from the input vertex
	 */
	public List<Edge> getEdgesFromVertex(Vertex vertex) {
		List<Edge> result = new ArrayList<Edge>();
		for (Edge edge : this.getAllEdges()) {
			if (edge.getStart() == vertex) {
				result.add(edge);
			}
		}
		return result;
	}

	/**
	 * Get all the edges which points to a vertex
	 * 
	 * @param vertex The vertex whose destination edges we want to fine
	 * @return A list of edges which are points to the input vertex
	 */
	public List<Edge> getEdgesToVertex(Vertex vertex) {
		List<Edge> result = new ArrayList<Edge>();
		for (Edge edge : this.getAllEdges()) {
			if (edge.getEnd() == vertex) {
				result.add(edge);
			}
		}
		return result;
	}

	/**
	 * Get the edge from one vertex to another vertex
	 * 
	 * @param startVertex The start vertex from which the edge starts from
	 * @param endVertex The end vertex to which the edge points to
	 * @return The edge which starts from startVertex and points to endVertex. Return null is none exists.
	 */
	public Edge getEdgeFromVertexToVertex(Vertex startVertex, Vertex endVertex) {
		for (Edge edge : this.getAllEdges()) {
			if ((edge.getStart() == startVertex) && (edge.getEnd() == endVertex)) {
				return edge;
			}
		}
		return null;
	}

	/**
	 * Get the start vertex on the graph
	 * 
	 * @return The start vertex on the graph
	 */
	public Vertex getStartVertex() {
		for (Vertex vertex : this.getAllVertices()) {
			if (vertex.getDistance() == 0) {
				return vertex;
			}
		}
		return null;
	}

	/**
	 * Get a random vertex from the graph
	 * 
	 * @return A random vertex from the graph if any vertex exists. Null if no vertex exists.
	 */
	public Vertex getRandomVertex() {
		Random randomGenerator = new Random();
		List<Vertex> vertices = getAllVertices();
		if (vertices.size() == 0) {
			return null;
		}
		Vertex vertex = vertices.get(randomGenerator.nextInt(vertices.size()));
		return vertex;
	}

	/**
	 * Get a vertex based from its name
	 * 
	 * @param name The name of the vertex
	 * @return The first vertex found in the graph which has the same name, and null if no vertex found
	 */
	public Vertex getVertexFromName(String name) {
		for (Vertex vertex : this.getAllVertices()) {
			if (vertex.getName().equals(name)) {
				return vertex;
			}
		}
		return null;
	}

	/**
	 * Make a vertex a start vertex (vertex with the value of 0)
	 * 
	 * @param vertex The start vertex
	 */
	public void setVertexAsStart(Vertex vertex) {
		for (Vertex currentVertex : this.getAllVertices()) {
			currentVertex.setDistance(Integer.MAX_VALUE);
			currentVertex.setColor(Color.RED);
		}
		vertex.setDistance(0);
		vertex.setColor(Color.BLUE);
	}

	/**
	 * Get the maximum amount of possible edges on the graph
	 * 
	 * @return The maximal amount of possible edges on the graph
	 */
	public int getMaximumAmountOfEdges() {
		List<Vertex> vertices = getAllVertices();

		int maximumAmountOfEdges = vertices.size() * (vertices.size() - 1) / 2;
		return maximumAmountOfEdges;
	}

	/**
	 * Check if an edge between two vertice exists
	 * 
	 * @param vertexOne The first vertex
	 * @param vertexTwo The second vertex
	 * @return True if exists, false if none exists
	 */
	public boolean checkEdgeExists(Vertex vertexOne, Vertex vertexTwo) {
		List<Edge> edges = vertexOne.getEdges();
		for (Edge edge : edges) {
			if (edge.getStart() == vertexOne || edge.getEnd() == vertexOne) {
				if (edge.getStart() == vertexTwo || edge.getEnd() == vertexTwo) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Get the reversed edge of an existing edge
	 * 
	 * @param edge The existing edge
	 * @return The existing reversed edge of the edge. Returns null if such edge does not exists.
	 */
	public Edge getReverseEdge(Edge edge) {
		List<Edge> edges = getAllEdges();
		for (Edge currentEdge : edges) {
			if ((edge.getStart() == currentEdge.getEnd()) && (edge.getEnd() == currentEdge.getStart())) {
				return currentEdge;
			}
		}
		return null;
	}

	/**
	 * Check if an edge between two vertice exists with the exception of one edge
	 * 
	 * @param vertexOne The first vertex
	 * @param vertexTwo The second vertex
	 * @param exceptionEdge The exception edge
	 * @return True if exists with the exceptionEdge as exception, false if none exists
	 */
	public boolean checkEdgeExists(Vertex vertexOne, Vertex vertexTwo, Edge exceptionEdge) {
		List<Edge> edges = vertexOne.getEdges();
		for (Edge edge : edges) {
			if (edge == exceptionEdge) {
				continue;
			}
			if (edge.getStart() == vertexOne || edge.getEnd() == vertexOne) {
				if (edge.getStart() == vertexTwo || edge.getEnd() == vertexTwo) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Get the distance between two objects on the graph
	 * 
	 * @param start The first object on the graph
	 * @param end The second object on the graph
	 * @return The distance between two objects in pixel
	 */
	public int getDistanceBetweenTwoVerticeInPixel(Actor start, Actor end) {
		int xDifference = (end.getX() - start.getX()) * Graph.DEFAULT_CELL_SIZE;
		int yDifference = (end.getY() - start.getY()) * Graph.DEFAULT_CELL_SIZE;
		return (int) Math.hypot(xDifference, yDifference);
	}

	/**
	 * Get all the edges which exists at a specified coordinate
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return A list of all found edges at the coordinate
	 */
	public List<Edge> getEdgesFromCoordinate(int x, int y) {
		List<Edge> result = new ArrayList<Edge>();
		for (Object obj : getObjectsAt(x, y, Edge.class)) {
			result.add((Edge) obj);
		}
		return result;
	}

	/**
	 * Check if at least an edge exists at a specified coornate
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return true if exists, false if none exists
	 */
	public boolean checkEdgeExistsInCoordinate(int x, int y) {
		if (getObjectsAt(x, y, Edge.class).size() == 0) {
			return false;
		} else {
			return true;
		}
	}
}
