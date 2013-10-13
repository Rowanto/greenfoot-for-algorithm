import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * An edge which consists of a start vertex and end vertex. If the distance is < 0, then the edge will be red-colored
 * 
 * @author Rowanto <rowanto@gmail.com>
 * @version 1.0
 */
public class Edge extends Entity {
	/**
	 * The start vertex for this edge
	 */
	private Vertex start;

	/**
	 * The end vertex for this edge
	 */
	private Vertex end;

	/**
	 * The thickness of the line for the arrow's body in pixel
	 */
	public static final int THICKNESS = 2;

	/**
	 * The color of the edge
	 */
	public static final Color LINE_COLOR = new Color(96, 24, 24);

	/**
	 * Margin between the text and the line
	 */
	public static final int TEXT_MARGIN = 5;

	/**
	 * The constructor of the edge
	 * 
	 * @param start The start vertex of this this edge
	 * @param end The end vertex of this edge
	 */
	public Edge(Vertex start, Vertex end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * Act - do whatever the Edge wants to do. This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
	 */
	public void act() {
		draw();
	}

	/**
	 * This method will be called by the framework whenever this object is added to world
	 */
	@Override
	public void addedToWorld(World world) {
		draw();
		Graph graph = getGraph();
		if (graph.checkEdgeExists(start, end, this)) {
			graph.removeObject(this);
			System.out.println("Edge between " + start + " and " + end + " already exists.");
		}
	}

	/**
	 * A method to draw the edge, this should be called whenever there're some changes
	 */
	public void draw() {
		if ((start != null) && (end != null)) {
			// Determine the image size
			int imageWidth = getGraph().getDistanceBetweenTwoVerticeInPixel(start, end);
			int imageHeigth = THICKNESS;

			// Create the image object
			GreenfootImage image = new GreenfootImage(imageWidth, imageHeigth);

			// Set the color
			image.setColor(getColor());

			// Draw the basic line
			image.fillRect(0, ((imageHeigth - THICKNESS) / 2), imageWidth, THICKNESS);

			// Set this object to use the image we defined
			this.setImage(image);

			// Now the object is draw, we need to place it correctly
			int xCenter = (start.getX() + end.getX()) / 2;
			int yCenter = (start.getY() + end.getY()) / 2;
			this.setLocationHard(xCenter, yCenter);

			// And turn it to face to the end vertex
			this.turnTowards(end.getX(), end.getY());

			this.setImage(image);
		}
	}

	/**
	 * A getter for the start vertex of this edge
	 * 
	 * @return The start vertex of this edge
	 */
	public Vertex getStart() {
		return start;
	}

	/**
	 * A getter for the end vertex of this edge
	 * 
	 * @return The end vertex of this edge
	 */
	public Vertex getEnd() {
		return end;
	}

	/**
	 * Get the current color of this edge
	 * 
	 * @return The color of this edge
	 */
	public Color getColor() {
		return LINE_COLOR;
	}

	/**
	 * A setter for the start vertex of this edge
	 * 
	 * @param start The new start vertex for this edge
	 */
	public void setStart(Vertex start) {
		this.start = start;
		draw();
	}

	/**
	 * A setter for the end vertex of this edge
	 * 
	 * @param end The new end vertex for this edge
	 */
	public void setEnd(Vertex end) {
		this.end = end;
		draw();
	}

	/**
	 * This method will be called whenever the user visually move an actor We want to prevent this, that's why it's overwritten
	 */
	@Override
	public void setLocation(int x, int y) {
		// do nothing since we don't allow edge to be moved manually
	}

	@Override
	public void move(int distance) {
		double radians = Math.toRadians(getRotation());

		// We round to the nearest integer, to allow moving one unit at an angle
		// to actually move.
		int dx = (int) Math.round(Math.cos(radians) * distance);
		int dy = (int) Math.round(Math.sin(radians) * distance);
		setLocationHard(getX() + dx, getY() + dy);
	}

	/**
	 * The real method we use to set the location of an edge
	 * 
	 * @param x The new x coordinate of this edge
	 * @param y The new y coordinate of this edge
	 */
	public void setLocationHard(int x, int y) {
		super.setLocation(x, y);
	}

	/**
	 * Get the graph
	 * 
	 * @return The graph
	 */
	public Graph getGraph() {
		return (Graph) super.getWorld();
	}
}
