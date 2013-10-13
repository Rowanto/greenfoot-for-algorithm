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
	 * The thickness of the line for the arrow's body in pixel
	 */
	public static final int THICKNESS = 2;

	/**
	 * The length (width) of the arrow head in pixel
	 */
	public static final int ARROW_LENGTH = 10;

	/**
	 * The angle between the arrow head and its body in pixel
	 */
	public static final int ARROW_ANGLE = 45;

	/**
	 * The offset used for lining up this edge and another edge if they are on top of each other
	 */
	public static final int OFFSET = 8;

	/**
	 * The color of the edge
	 */
	public static final Color LINE_COLOR = new Color(96, 24, 24);

	/**
	 * The color of the edge when its weight is negative
	 */
	public static final Color LINE_COLOR_NEGATIVE = new Color(249, 62, 62);

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
	}

	/**
	 * A method to draw the edge, this should be called whenever there're some changes
	 */
	public void draw() {
		if ((start != null) && (end != null)) {
			// Get the height and width of the arrow
			int heightOfArrow = (int) (Math.abs(Math.sin(Math.toRadians(ARROW_ANGLE))) * ARROW_LENGTH);
			int widthOfArrow = (int) (Math.abs(Math.cos(Math.toRadians(ARROW_ANGLE))) * ARROW_LENGTH);

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
			int tempHeigthBasedOnArrow = THICKNESS + (heightOfArrow * 2);
			int tempHeigthBasedOnText = THICKNESS + (TEXT_MARGIN + textHeight) * 2;
			int imageHeigth;
			if (tempHeigthBasedOnArrow > tempHeigthBasedOnText) {
				imageHeigth = tempHeigthBasedOnArrow;
			} else {
				imageHeigth = tempHeigthBasedOnText;
			}

			// Create the image object
			GreenfootImage image = new GreenfootImage(imageWidth, imageHeigth);

			// Set the color
			image.setColor(getColor());

			// Draw the basic line
			image.fillRect(0, ((imageHeigth - THICKNESS) / 2), imageWidth, THICKNESS);

			// Determine how much pixel to subtract for putting the arrow's head
			int arrowSubtract = getEnd().getImage().getHeight() / 2;

			// Draw the arrow's head
			int x1 = imageWidth - arrowSubtract;
			int x2 = x1 - widthOfArrow;
			int x3 = x2 - THICKNESS;
			int x4 = x1 - THICKNESS;

			int y1 = imageHeigth / 2;
			int y2 = y1 - heightOfArrow;
			int y3 = y1 + heightOfArrow;

			// Removing the comments for the code below will make the arrow a triangle
			/*
			 * x3 = x2; x4 = x3;
			 */

			// Removing the comments for the code below will make the arrow fatter
			x4 = (int) (x1 - widthOfArrow * 0.75);

			int[] xPoints = { x1, x2, x3, x4 };
			int[] yPointsDown = { y1, y2, y2, y1 };
			int[] yPointsUp = { y1, y3, y3, y1 };
			image.fillPolygon(xPoints, yPointsDown, xPoints.length);
			image.fillPolygon(xPoints, yPointsUp, xPoints.length);

			// Set this object to use the image we defined
			this.setImage(image);

			// Now offseting the y axis if there's another edge pointing to the other vertex
			int xOffset = 0;
			int yOffset = 0;
			boolean moveTheOtherWay = false;
			Edge reverseEdge = getGraph().getReverseEdge(this);
			if (reverseEdge != null) {
				int thisHasGreaterWeight = 1;
				if (this.getWeight() > reverseEdge.getWeight()) {
					moveTheOtherWay = true;
				} else {
					// Solve the same weight problem
					if ((this.getX() != reverseEdge.getX()) || (this.getY() != reverseEdge.getY())) {
						moveTheOtherWay = true;
					}
				}

				if (moveTheOtherWay) {
					thisHasGreaterWeight = -1;
				}

				xOffset = OFFSET * thisHasGreaterWeight;
				yOffset = OFFSET * thisHasGreaterWeight;

				int xDifference = this.getStart().getX() - this.getEnd().getX();
				int yDifference = this.getStart().getY() - this.getEnd().getY();
				if (xDifference == 0) {
					yOffset = 0;
				}
				if (yDifference == 0) {
					xOffset = 0;
				}
			}

			// Now the object is draw, we need to place it correctly
			int xCenter = (start.getX() + end.getX()) / 2;
			int yCenter = (start.getY() + end.getY()) / 2;
			this.setLocationHard(xCenter, yCenter);

			// And turn it to face to the end vertex
			this.turnTowards(end.getX(), end.getY());

			// Now we need to set the location again
			this.setLocationHard(this.getX() + xOffset, this.getY() + yOffset);

			// Set the fontsize
			image.setFont(image.getFont().deriveFont(fontSize));

			// Now we need to write the text and rotate as necessary
			int rotation = getRotation();
			if ((rotation > 90) && (rotation <= 270)) {
				AffineTransform fontTransform = new AffineTransform();
				Font textFont = image.getFont();
				fontTransform.rotate(Math.toRadians(180));
				Font rotatedFont = textFont.deriveFont(fontTransform);
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

			// Draw the other part if moved the other way
			if (moveTheOtherWay) {
				reverseEdge.draw();
			}
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
	 * Get the current color of this edge
	 * 
	 * @return The color of this edge
	 */
	public Color getColor() {
		if (getWeight() < 0) {
			return LINE_COLOR_NEGATIVE;
		} else {
			return LINE_COLOR;
		}
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
