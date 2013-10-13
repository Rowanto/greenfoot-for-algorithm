import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.awt.Color;

/**
 * The sequence container where all the bars and object are being displayed
 * 
 * @author Rowanto
 * @version 1.0
 */
public class SequenceContainer extends World {
	/**
	 * The default cell size of this sequence container
	 */
	public static final int DEFAULT_CELL_SIZE = 1;

	/**
	 * The width of this sequence container
	 */
	public static final int WORLD_WIDTH = 800;

	/**
	 * The height of this sequence container
	 */
	public static final int WORLD_HEIGHT = 400;

	/**
	 * The maximum number of generated random objects in a bar
	 */
	public static final int NUMBER_OF_RANDOM_OBJECTS = 40;

	/**
	 * The maximum value each bar can have
	 */
	public static final int MAXIXUM_VALUE_OF_RANDOM_ROD = 60;

	/**
	 * Constructor for objects of class SequenceContainer.
	 * 
	 */
	public SequenceContainer() {
		super(WORLD_WIDTH, WORLD_HEIGHT, DEFAULT_CELL_SIZE);
		setBackground("cell.jpg");
		setActOrder(Bar.class, Container.class);
		setPaintOrder(Bar.class, Container.class);

		createRandomContainer();
	}

	/**
	 * Add a container to the sequence container
	 * 
	 * @param container the container to be added
	 * @param x the x coordinate of the container
	 * @param y the y coordinate of the container
	 */
	public void addContainer(Container container, int x, int y) {
		addObject(container, x, y);
	}

	/**
	 * Add a bar to the sequence container
	 * 
	 * @param bar the bar to be added
	 */
	public void addBar(Bar bar) {
		addObject(bar, 0, 0);
	}

	/**
	 * We need to overwrite this to ensure correct behavior of the bar and container
	 */
	@Override
	public void removeObject(Actor actor) {
		if (actor instanceof Bar) {
			Bar bar = (Bar) actor;
			for (Container container : getAllContainer()) {
				if (container.contains(bar)) {
					container.remove(bar);
				}
			}
		}
		if (actor instanceof Container) {
			Container container = (Container) actor;
			for (Bar bar : container.getAll()) {
				super.removeObject(bar);
			}
		}
		super.removeObject(actor);
	}

	/**
	 * Prepare the sequence container for the start of the program. Generate a container, and random bars in it
	 */
	public void createRandomContainer() {
		this.removeObjects(this.getObjects(null));

		Container container = new Container();
		addObject(container, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);

		Random randomGenerator = new Random();
		for (int i = 0; i < NUMBER_OF_RANDOM_OBJECTS; i++) {
			Bar bar = new Bar(container, randomGenerator.nextInt(MAXIXUM_VALUE_OF_RANDOM_ROD) + 1);
			addObject(bar, 0, 0);
		}
	}

	/**
	 * Get all available container in the sequence container
	 * 
	 * @return List of all the containers in the sequence container
	 */
	public List<Container> getAllContainer() {
		ArrayList<Container> result = new ArrayList<Container>();
		for (Object obj : this.getObjects(Container.class)) {
			result.add((Container) obj);
		}
		return result;
	}

	/**
	 * Get a single container in the sequence container. This method make sense since most likely, there'll be only a single container in
	 * the sequence container
	 * 
	 * @return A single container from the world.
	 */
	public Container getSingleContainer() {
		List<Container> containers = getAllContainer();
		if (containers.size() > 0) {
			return containers.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Set the color of all available bars to the selected color
	 * 
	 * @param color The color to be set
	 */
	public void setColorOfBarsInSequenceContainer(Color color) {
		for (Object obj : getObjects(Bar.class)) {
			Bar bar = (Bar) obj;
			bar.setColor(color);
		}
	}

	/**
	 * Reset the color of all available bars to the default color
	 */
	public void resetColorOfBarsInSequenceContainer() {
		setColorOfBarsInSequenceContainer(Bar.DEFAULT_COLOR);
	}

}
