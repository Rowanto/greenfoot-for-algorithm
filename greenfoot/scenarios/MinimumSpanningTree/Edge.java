import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.AffineTransform;

/**
 * An edge which consists of a start vertex, end vertex, and a weight. If the weight is < 0, then the edge will be red-colored
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
	 * The weight for this edge
	 */
	private int weight;

	/**
	 * The current color for this edge
	 */
	private Color color = LINE_COLOR;

	/**
	 * The thickness of the line for the arrow's body in pixel
	 */
	public static final int THICKNESS = 2;

	/**
	 * The normal color of the edge
	 */
	public static final Color LINE_COLOR = new Color(96, 24, 24);

	/**
	 * The color of the edge when it's not in the group of a minimal tree
	 */
	public static final Color LINE_COLOR_NOT_MINIMAL = new Color(249, 62, 62);

	/**
	 * The color of the edge when it's in the group of minimal tree
	 */
	public static final Color LINE_COLOR_MINIMAL_GROUP = new Color(70, 18, 18);

	/**
	 * The font size of the weight text
	 */
	public static final int FONT_SIZE = 15;

	/**
	 * Margin between the text and the line
	 */
	public static final int TEXT_MARGIN = 5;

	/**
	 * The constructor of the edge
	 * 
	 * @param start The start vertex of this this edge
	 * @param end The end vertex of this edge
	 * @param weight The weight of this edge
	 */
	public Edge(Vertex start, Vertex end, int weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
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
			// Determine the resulting text size which we will use for determinate image size
			GreenfootImage tempImage = new GreenfootImage(1, 1);
			String text = Integer.toString(getWeight());
			float fontSize = (float) FONT_SIZE;
			tempImage.setFont(tempImage.getFont().deriveFont(fontSize));

			Font tempFont = tempImage.getFont();

			FontMetrics fontMetrics = tempImage.getAwtImage().getGraphics().getFontMetrics(tempFont);
			int textWidth = fontMetrics.stringWidth(text);
			int textHeight = (int) tempFont.createGlyphVector(fontMetrics.getFontRenderContext(), text).getVisualBounds().getHeight();

			// Determine the image size
			int imageWidth = getGraph().getDistanceBetweenTwoVerticeInPixel(start, end);
			int imageHeigth = getThickness() + (TEXT_MARGIN + textHeight) * 2;
			;

			// Create the image object
			GreenfootImage image = new GreenfootImage(imageWidth, imageHeigth);

			// Set the color
			image.setColor(getColor());

			// Draw the basic line
			image.fillRect(0, ((imageHeigth - getThickness()) / 2), imageWidth, getThickness());

			// Set this object to use the image we defined
			this.setImage(image);

			// Now the object is draw, we need to place it correctly
			int xCenter = (start.getX() + end.getX()) / 2;
			int yCenter = (start.getY() + end.getY()) / 2;
			this.setLocationHard(xCenter, yCenter);

			// And turn it to face to the end vertex
			this.turnTowards(end.getX(), end.getY());

			// Set the fontsize
			image.setFont(image.getFont().deriveFont(fontSize));

			// Now we need to write the text and rotate as necessary
			int rotation = getRotation();
			if ((rotation > 90) && (rotation <= 270)) {
				AffineTransform fontTransform = new AffineTransform();
				Font textFont = image.getFont();
				fontTransform.rotate(Math.toRadians(180));
				Font rotatedFont = textFont.deriveFont(fontSize).deriveFont(fontTransform);
				image.setFont(rotatedFont);
			}

			int mostLeft;
			int mostBottom;
			if ((rotation > 90) && (rotation <= 270)) {
				mostLeft = (image.getWidth() - textWidth) / 2 + textWidth;
				mostBottom = (image.getHeight() - textHeight) / 2 + TEXT_MARGIN + textHeight / 2;
			} else {
				mostLeft = (image.getWidth() - textWidth) / 2;
				mostBottom = (image.getHeight() + textHeight) / 2 + TEXT_MARGIN + textHeight / 2;
			}
			image.drawString(text, mostLeft, mostBottom);
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
	 * A getter for the weight of this edge
	 * 
	 * @return The weight of this edge
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Get the thickness of this edge The thickness will double if the edge is marked as in the minimal group
	 * 
	 * @return The thickness of the line according to its mark
	 */
	public int getThickness() {
		if (getColor() == LINE_COLOR_MINIMAL_GROUP) {
			return THICKNESS * 2;
		} else {
			return THICKNESS;
		}
	}

	/**
	 * Get the current color of this edge
	 * 
	 * @return The color of this edge
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Set the current color of this edge
	 * 
	 * @param color The color to set
	 */
	public void setColor(Color color) {
		this.color = color;
		draw();
	}

	/**
	 * Mark the edge as an edge in the minimal group
	 */
	public void markAsInMinimal() {
		setColor(LINE_COLOR_MINIMAL_GROUP);
	}

	/**
	 * Mark the edge as an edge not in the minimal group
	 */
	public void markAsNotInMinimal() {
		setColor(LINE_COLOR_NOT_MINIMAL);
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
	 * A setter for the weight of this edge
	 * 
	 * @param value The new weight
	 */
	public void setWeight(int value) {
		this.weight = value;
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
	public void move(int weight) {
		double radians = Math.toRadians(getRotation());

		// We round to the nearest integer, to allow moving one unit at an angle
		// to actually move.
		int dx = (int) Math.round(Math.cos(radians) * weight);
		int dy = (int) Math.round(Math.sin(radians) * weight);
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
