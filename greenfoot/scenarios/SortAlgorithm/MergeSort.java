import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * An example implementation of merge sort
 * 
 * @author Rowanto
 * @version 1.0
 */
public class MergeSort extends Algorithm {
	/**
	 * This method will be called whenever this object is put into the world Remember to call the super method of this overriden method.
	 */
	@Override
	public void addedToWorld(World world) {
		super.addedToWorld(world);
	}

	/**
	 * Main - This is the method MergeSort will do. Just think of this as the usual main method. Remember to put Greenfoot.hold() at the
	 * place where you want the pause or hold to happens.
	 */
	@Override
	public void main() {
		SequenceContainer sequenceContainer = (SequenceContainer) getWorld();
		Container container = sequenceContainer.getSingleContainer();
		int middle = container.size() / 2;
		mergeSort(0, middle, middle + 1, container.size() - 1);
		Greenfoot.stop();
	}

	public void mergeSort(int firstLeftIndex, int firstRightIndex, int secondLeftIndex, int secondRightIndex) {
		// First we get the world or the sequence container
		SequenceContainer sequenceContainer = (SequenceContainer) getWorld();

		// Then we get the container of the items
		Container container = sequenceContainer.getSingleContainer();

		// Differentiate the color of the selected bars for
		// visualizing the bar groups we want to process
		container.resetBarsColor();
		container.setBarsColor(firstLeftIndex, firstRightIndex, Color.RED);
		container.setBarsColor(secondLeftIndex, secondRightIndex, Color.BLUE);
		Greenfoot.hold();

		// If the one of the half's index is not the same,
		// it means we can still break it down recursively
		if (!(firstLeftIndex == firstRightIndex)) {
			int middle = (firstLeftIndex + firstRightIndex) / 2;
			mergeSort(firstLeftIndex, middle, middle + 1, firstRightIndex);
		}
		if (!(secondLeftIndex == secondRightIndex)) {
			int middle = (secondLeftIndex + secondRightIndex) / 2;
			mergeSort(secondLeftIndex, middle, middle + 1, secondRightIndex);
		}

		// Now we paint all of the processed bar to another color to indicate that we are
		// not going deeper recursively and we are going to sort the visualized halves
		container.setBarsColor(firstLeftIndex, secondRightIndex, Color.LIGHT_GRAY);
		Greenfoot.hold();

		// Merge the two groups using bubble sort
		while (true) {
			boolean sorted = true;
			for (int j = firstLeftIndex; j < secondRightIndex; j++) {
				container.resetBarsColor();
				Bar firstBar = container.get(j);
				Bar secondBar = container.get(j + 1);

				if (secondBar.isSmallerThan(firstBar)) {
					container.swap(firstBar, secondBar);
					sorted = false;
				}
			}
			if (sorted) {
				break;
			}
		}

		// Mark the processed bars as green after finishing a merge
		// And hold so that the user can see
		container.setBarsColor(firstLeftIndex, secondRightIndex, Color.GREEN);
		Greenfoot.hold();
	}
}
