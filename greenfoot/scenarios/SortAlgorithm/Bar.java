import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * The bar which represents an object which visualize an int value
 * 
 * @author Rowanto
 * @version 1.0
 */
public class Bar extends Entity implements Comparable<Bar> {
	/**
	 * The real int value of this bar
	 */
	private int value;

	/**
	 * A bar can only exists in a container
	 */
	private Container container;

	/**
	 * The color of the bar
	 */
	public Color color;

	/**
	 * The thickness of the bar in pixel
	 */
	public static final int THICKNESS = 10;

	/**
	 * The margin between bars when in container
	 */
	public static final int MARGIN = 2;

	/**
	 * The height multiplier plays a role in the visualization of the bar The height of the bar draws in pixel will be the value of the
	 * multiplier multiplied with the actual int value of the bar
	 */
	public static final int HEIGHT_MULTIPLIER = 5;

	/**
	 * The default color of a bar
	 */
	public static final Color DEFAULT_COLOR = Color.GRAY;

	/**
	 * The selected color of a bar
	 */
	public static final Color SELECTED_COLOR = Color.BLACK;

	/**
	 * The constructor of the bar
	 * 
	 * @param container The container this bar should exists in
	 * @param value The actual int value of the bar
	 */
	public Bar(Container container, int value) {
		this.container = container;
		this.value = value;
		this.color = DEFAULT_COLOR;
	}

	/**
	 * Greenfoot act method. Just redraw
	 */
	public void act() {
		draw();
	}

	/**
	 * We will draw the bar, then the bar will be positioned into the correct position by the container's draw method
	 */
	public void addedToWorld(World world) {
		draw();
		container.add(this);
		container.draw();
	}

	/**
	 * Get the actual int value of the bar
	 * 
	 * @return The actual int value of the bar
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Set the actual int value of the bar
	 * 
	 * @param value The new actual int value of the bar
	 */
	public void setValue(int value) {
		this.value = value;
		draw();
		container.draw();
	}

	/**
	 * Get the color of the bar
	 * 
	 * @return The color of the bar
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Set the color of the bar
	 * 
	 * @param color the color of the bar
	 */
	public void setColor(Color color) {
		this.color = color;
		draw();
	}

	/**
	 * Set the color of the bar to the default color
	 */
	public void setToDefaultColor() {
		setColor(DEFAULT_COLOR);
	}

	/**
	 * Set the color of the bar to the selected color
	 */
	public void setToSelectedColor() {
		setColor(SELECTED_COLOR);
	}

	/**
	 * Get the container of this bar
	 * 
	 * @return The container of this bar
	 */
	public Container getContainer() {
		return container;
	}

	/**
	 * Set the container of this bar
	 * 
	 * @param container The new container of this bar
	 */
	public void setContainer(Container container) {
		this.container = container;
	}

	/**
	 * Draw the bar
	 */
	public void draw() {
		GreenfootImage image = new GreenfootImage(getImageLengthWithMargin(), getImageHeightWithMargin());
		image.setColor(getColor());
		image.fillRect(Bar.MARGIN, Bar.MARGIN, getImageLength(), getImageHeight());
		this.setImage(image);
	}

	/**
	 * We don't allow the bar to be moved by drag and drop by the user
	 */
	@Override
	public void setLocation(int x, int y) {
		// do nothing
	}

	/**
	 * The compare to methode for the comparable interface
	 */
	public int compareTo(Bar bar) {
		return getValue() - bar.getValue();
	}

	/**
	 * Check if this bar is bigger than the input bar
	 * 
	 * @param bar The input bar
	 * @return True if this bar is bigger than the input bar. False for everything else
	 */
	public boolean isBiggerThan(Bar bar) {
		int comparison = compareTo(bar);
		if (comparison > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check if this bar is smaller than the input bar
	 * 
	 * @param bar The input bar
	 * @return True if this bar is smaller than the input bar. False for everything else
	 */
	public boolean isSmallerThan(Bar bar) {
		int comparison = compareTo(bar);
		if (comparison < 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * The real set location we can use programatically
	 * 
	 * @param x The new x location of this bar
	 * @param y The new y location of this bar
	 */
	public void setLocationHard(int x, int y) {
		super.setLocation(x, y);
		draw();
	}

	/**
	 * Get the length of this image in pixel
	 * 
	 * @return The length of this image in pixel
	 */
	public int getImageLength() {
		return Bar.THICKNESS;
	}

	/**
	 * Get the height of this image in pixel. The height is the actual int value of this bar multiplied with the height multiplier.
	 * 
	 * @return The height of this image in pixel
	 */
	public int getImageHeight() {
		return getValue() * Bar.HEIGHT_MULTIPLIER;
	}

	/**
	 * Get the image length but with the margins
	 * 
	 * @return The image length but with margins
	 */
	public int getImageLengthWithMargin() {
		return Bar.MARGIN + getImageLength() + Bar.MARGIN;
	}

	/**
	 * Get the image height but with the margins
	 * 
	 * @return the image height but with margins
	 */
	public int getImageHeightWithMargin() {
		return Bar.MARGIN + getImageHeight() + Bar.MARGIN;
	}
}
