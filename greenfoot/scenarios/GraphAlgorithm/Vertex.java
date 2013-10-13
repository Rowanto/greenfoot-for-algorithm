import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
import java.util.List;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;

/**
 * A vertex used for graph algorithms.
 * 
 * @author Rowanto
 * @version 1.0
 */
public class Vertex extends Entity {

	/**
	 * The name of this vertex
	 */
	private String name;

	/**
	 * The distance to this vertex Will have the default value of the maximum of integer
	 */
	private int distance = Integer.MAX_VALUE;

	/**
	 * The previous vertex of this vertex in optimal path Used as a helper variable for dijkstra
	 */
	private Vertex previous = null;

	/**
	 * The extra info of this vertex
	 */
	private String extraInfo;

	/**
	 * The image file for a red vertex
	 */
	public static final String FILE_NAME_RED = "button-red.png";

	/**
	 * The image file for a green vertex
	 */
	public static final String FILE_NAME_GREEN = "button-green.png";

	/**
	 * The image file for a blue vertex
	 */
	public static final String FILE_NAME_BLUE = "button-blue.png";

	/**
	 * The image file for a gray vertex
	 */
	public static final String FILE_NAME_GRAY = "button-gray.png";
	
	/**
	 * The image file for a gray vertex
	 */
	public static final String FILE_NAME_WHITE = "button-white.png";		

	/**
	 * The font size of the text which will appear in the middle of the vertex The size of other text will be based from this size
	 */
	public static final int FONT_SIZE = 20;

	/**
	 * The margin between the name, distance, and extra info
	 */
	public static final int MARGIN_BETWEEN_TEXT = 5;

	/**
	 * The color of the text in the vertex
	 */
	public static final Color FONT_COLOR = Color.WHITE;

	/**
	 * The current color of the vertex
	 */
	private Color color = Color.RED;

	/**
	 * The constructor of the vertex, do nothing
	 */
	public Vertex() {
	}

	/**
	 * The constructor of the vertex which also specify name
	 * 
	 * @param name The name of this vertex
	 */
	public Vertex(String name) {
		this.name = name;
	}

	/**
	 * Act - do whatever the Vertex wants to do. This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
	 */
	@Override
	public void act() {
		// Add your action code here.
	}

	/**
	 * This method will be called by the framework whenever this object is added to world
	 */
	@Override
	public void addedToWorld(World world) {
		draw();
	}

	/**
	 * Getter for the name of this vertex
	 * 
	 * @return the name or an empty string if there's no name
	 */
	public String getName() {
		if (name == null) {
			return "";
		}
		return name;
	}

	/**
	 * Setter for the name of this vertex
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
		draw();
		for (Vertex vertex : ((Graph) getWorld()).getAllVertices())
		{
			Vertex previous = vertex.getPrevious();
			if (previous == null)
			{
				continue;
			}
			else
			{
				if (previous == this)
				{
					vertex.draw();
				}
			}
		}
	}

	/**
	 * Getter for the distance of this vertex
	 * 
	 * @return The distance to this vertex
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * A setter for the distance of this vertex
	 * 
	 * @param distance The new distance to this vertex
	 */
	public void setDistance(int distance) {
		this.distance = distance;
		draw();
	}

	/**
	 * Getter for the previous vertex of this vertex in optimal path
	 * 
	 * @return The previous vertex of this vertex in optimal path
	 */
	public Vertex getPrevious() {
		return previous;
	}

	/**
	 * Get the name of this vertex and its previous vertex's name
	 * 
	 * @return The combined name of this vertex and its previous. "(previousName) thisName"
	 */
	public String getNameAndPreviousAsString() {
		Vertex previous = getPrevious();
		if (previous == null) {
			return this.getName();
		} else {
			return "(" + previous.getName() + ") " + this.getName();
		}
	}

	/**
	 * A setter for the previous vertex of this vertex in optimal path
	 * 
	 * @param previous new The previous vertex of this vertex in optimal path
	 */
	public void setPrevious(Vertex previous) {
		this.previous = previous;
		draw();
	}

	/**
	 * Getter for the extra information in this vertex
	 * 
	 * @return the extraInfo or an empty string if there's no extra info
	 */
	public String getExtraInfo() {
		if (extraInfo == null) {
			return "";
		}
		return extraInfo;
	}

	/**
	 * Setter for the extra information in this vertex
	 * 
	 * @param extraInfo the extraInfo to set
	 */
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
		draw();
	}

	/**
	 * Getter for the color of this vertex
	 * 
	 * @return The color of this vertex
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for the color of this vertex
	 * 
	 * @param color The new color of this vertex
	 */
	public void setColor(Color color) {
		this.color = color;
		draw();
	}

	/**
	 * Setter for the color using r,g,b value
	 * 
	 * @param r The red value between 0-255
	 * @param g The green value between 0-255
	 * @param b The blue value between 0-255
	 */
	public void setColor(int r, int g, int b) {
		setColor(new Color(r, g, b));
	}

	/**
	 * Check whether the current color matches the specified color
	 * 
	 * @param color The specified Color
	 * @return true if it's the same color, false if not the same
	 */
	public boolean isColor(Color color) {
		if (this.color.equals(color)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Set this vertex as the start vertex
	 */
	public void setAsStartVertex() {
		getGraph().setVertexAsStart(this);
	}

	/**
	 * Get the current image of this vertex Will use predefined image if the color is red, blue, green, or gray If not, then the gray image
	 * will be colorized to the current color
	 */
	public GreenfootImage getGreenfootImage() {
		Color color = getColor();
		// Give the finished image if the color is
		// Red, Blue, Green, or Gray
		if (color.equals(Color.RED)) {
			return new GreenfootImage(FILE_NAME_RED);
		}
		if (color.equals(Color.BLUE)) {
			return new GreenfootImage(FILE_NAME_BLUE);
		}
		if (color.equals(Color.GREEN)) {
			return new GreenfootImage(FILE_NAME_GREEN);
		}
		if (color.equals(Color.GRAY)) {
			return new GreenfootImage(FILE_NAME_GRAY);
		}
		if (color.equals(Color.WHITE)) {
			return new GreenfootImage(FILE_NAME_WHITE);
		}				

		// For other color, we will colorize the gray image
		GreenfootImage image = new GreenfootImage(FILE_NAME_GRAY);
		BufferedImage nativeImage = image.getAwtImage();

		for (int x = 0; x < nativeImage.getWidth(); x++) {
			for (int y = 0; y < nativeImage.getHeight(); y++) {
				// Break down the color
				// Actually we need to get rgb, but since
				// This is a gray image, the r, g, and b are the same
				int pixelColor = nativeImage.getRGB(x, y);
				int alpha = (pixelColor & 0xff000000) >> 24;
				int brightness = (pixelColor & 0x00ff0000) >> 16;

				// Just continue if transparancy is 0
				if (alpha == 0)
					continue;

				// Need a multiplier for the brightness
				// In short, make the brightness range from 0 to 1
				float brightnessMultiplier = brightness / 255f;

				// Calculate the wanted color with the brightness of the pixel
				int newR = Math.round(color.getRed() * brightnessMultiplier);
				int newG = Math.round(color.getGreen() * brightnessMultiplier);
				int newB = Math.round(color.getBlue() * brightnessMultiplier);

				// Colorize it if not transparent
				int newColor = (alpha << 24) | newR << 16 | newG << 8 | newB;
				nativeImage.setRGB(x, y, newColor);
			}
		}
		return image;
	}

	/**
	 * A method to draw this vertex, should be called whenever there's change with this vertex
	 */
	public void draw() {
		// Get the image file of the current vertex according to its color
		GreenfootImage image = getGreenfootImage();

		// Set the brush color
		image.setColor(FONT_COLOR);

		// Change the distance to unlimited symbol if integer is max
		String text;
		if (distance < Integer.MAX_VALUE) {
			text = Integer.toString(distance);
		} else {
			text = "\u221e";
		}

		// Draw the text for the distance or value
		int[] sizeOfTextForValue = calculateTextSize(image, FONT_SIZE, text);
		int textWidthForValue = sizeOfTextForValue[0];
		int textHeightForValue = sizeOfTextForValue[1];
		int mostLeftForValue = (image.getWidth() - textWidthForValue) / 2;
		int mostBottomForValue = (image.getHeight() + textHeightForValue) / 2;
		image.drawString(text, mostLeftForValue, mostBottomForValue);

		// Draw the text for the vertex name (if exists)
		float fontSizeForName = FONT_SIZE * 0.8f;
		String name = getName();
		if ((name.length() > 0) && Graph.detailedInfo) {
			int[] sizeOfTextForName = calculateTextSize(image, fontSizeForName, name);
			int textWidthForName = sizeOfTextForName[0];
			int mostLeftForName = (image.getWidth() - textWidthForName) / 2;
			int mostBottomForName = mostBottomForValue - textHeightForValue - MARGIN_BETWEEN_TEXT;
			// Make the text bold
			image.setFont(image.getFont().deriveFont(Font.BOLD));
			image.drawString(name, mostLeftForName, mostBottomForName);
		}

		// Draw the text for the vertex's previous (if exists)
		float fontSizeForPrevious = FONT_SIZE * 0.6f;
		Vertex previous = getPrevious();
		if ((previous != null) && Graph.detailedInfo) {
			String previousName = previous.getName();
			int[] sizeOfTextForPrevious = calculateTextSize(image, fontSizeForPrevious, previousName);
			int textWidthForPrevious = sizeOfTextForPrevious[0];
			int textHeightForPrevious = sizeOfTextForPrevious[1];
			int mostLeftForPrevious = mostLeftForValue - MARGIN_BETWEEN_TEXT - textWidthForPrevious;
			int mostBottomForPrevious = (image.getHeight() + textHeightForPrevious) / 2;
			image.drawString(previousName, mostLeftForPrevious, mostBottomForPrevious);
		}

		// Draw the text for the vertex's extra info (if exists)
		float fontSizeForInfo = FONT_SIZE * 0.6f;
		String info = getExtraInfo();
		if ((name.length() > 0) && Graph.detailedInfo) {
			int[] sizeOfTextForInfo = calculateTextSize(image, fontSizeForInfo, info);
			int textWidthForInfo = sizeOfTextForInfo[0];
			int textHeightForInfo = sizeOfTextForInfo[1];
			int mostLeftForInfo = (image.getWidth() - textWidthForInfo) / 2;
			int mostBottomForInfo = mostBottomForValue + MARGIN_BETWEEN_TEXT + textHeightForInfo;
			image.drawString(info, mostLeftForInfo, mostBottomForInfo);
		}

		this.setImage(image);
	}

	/**
	 * Calculate the resulting size of the text. After this method is called. The input image will be also ready for writing.
	 * 
	 * @param image The GreenfootImage we will be working with
	 * @param fontSize The font size of the text
	 * @param text The text itself
	 * @return An array of Integer[2] which contains the width and the height of the resulting text
	 */
	private int[] calculateTextSize(GreenfootImage image, float fontSize, String text) {
		image.setFont(image.getFont().deriveFont(Font.PLAIN, fontSize));

		Font currentFont = image.getFont();

		FontMetrics fontMetrics = image.getAwtImage().getGraphics().getFontMetrics(currentFont);
		int textWidth = fontMetrics.stringWidth(text);
		int textHeight = (int) currentFont.createGlyphVector(fontMetrics.getFontRenderContext(), text).getVisualBounds().getHeight();

		int[] result = new int[2];
		result[0] = textWidth;
		result[1] = textHeight;
		return result;
	}

	/**
	 * Get all edges which points or starts from this vertex
	 * 
	 * @return A list of edges which points or starts from this vertex
	 */
	public List<Edge> getEdges() {
		return getGraph().getIncidentEdges(this);
	}

	/**
	 * Get all vertice which are neighbours with this vertex
	 * 
	 * @return A list of vertice which are neighbours with this vertex
	 */
	public List<Vertex> getNeighbours() {
		return getGraph().getNeighbours(this);
	}

	/**
	 * Every time we change location, we will want to redraw the edges of this vertex
	 */
	@Override
	public void setLocation(int x, int y) {
		if (Graph.SNAP_TO_IMAGE_GRID) {
			// Snap to the middle of a grid if the setting in graph is true
			GreenfootImage backgroundImage = new GreenfootImage(Graph.DEFAULT_BACKGROUND_FILENAME);
			int imageWidth = backgroundImage.getWidth();
			int imageHeight = backgroundImage.getHeight();
			int xCenterOfCell = (x / imageWidth) * imageWidth + imageWidth / 2;
			int yCenterOfCell = (y / imageHeight) * imageHeight + imageHeight / 2;
			super.setLocation(xCenterOfCell, yCenterOfCell);
		} else {
			super.setLocation(x, y);
		}
		for (Edge edge : this.getEdges()) {
			edge.draw();
		}
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
