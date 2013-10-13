import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An example implementation of bubble sort
 * 
 * @author Rowanto
 * @version 1.0
 */
public class BubbleSort extends Algorithm {
	/**
	 * This method will be called whenever this object is put into the world Remember to call the super method of this overriden method.
	 */
	@Override
	public void addedToWorld(World world) {
		super.addedToWorld(world);
	}

	/**
	 * Main - This is the method BubbleSort will do. Just think of this as the usual main method. Remember to put Greenfoot.hold() at the
	 * place where you want the pause or hold to happens.
	 */
	@Override
	public void main() {
		while (true) {
			boolean sorted = sort();
			if (sorted == true) {
				Greenfoot.stop();
				break;
			}
			Greenfoot.hold();
		}
	}

	/**
	 * The actual sort method
	 * 
	 * @return true if everything is sorted, false if not
	 */
	public boolean sort() {
		boolean sorted = true;

		SequenceContainer sequenceContainer = (SequenceContainer) getWorld();
		Container container = sequenceContainer.getSingleContainer();
		for (int j = 0; j < (container.size() - 1); j++) {
			container.resetBarsColor();
			Bar firstBar = container.get(j);
			Bar secondBar = container.get(j + 1);

			if (secondBar.isSmallerThan(firstBar)) {
				container.swap(firstBar, secondBar);
				firstBar.setToSelectedColor();
				secondBar.setToSelectedColor();
				Greenfoot.hold();
				sorted = false;
			}
		}

		return sorted;
	}
}
