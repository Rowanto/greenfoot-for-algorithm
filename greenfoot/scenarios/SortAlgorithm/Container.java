import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Iterator;

/**
 * A collection class which implements the List<> Class using internal ArrayList The advantage of this class is, it will redraw itself
 * automatically if there's any need to redraw. Example will be after removing a bar from the container, the container will redraw itself to
 * reflect the change.
 * 
 * @author Rowanto
 * @version 1.0
 */
public class Container extends Entity implements List<Bar> {
	/**
	 * The actual list of the bars
	 */
	private List<Bar> bars = new ArrayList<Bar>();

	/**
	 * The margin which exists invisibly outside of the border of the container
	 */
	public static final int MARGIN = 10;

	/**
	 * The padding which exists between the border of the container and the bars
	 */
	public static final int PADDING = 20;

	/**
	 * The Greenfoot act method, just redraw.
	 */
	public void act() {
		draw();
	}

	/**
	 * The container will be redraw when it's added to world.
	 * 
	 * @param world The world the object is going to be added to.
	 */
	@Override
	public void addedToWorld(World world) {
		draw();
	}

    /**
     * Appends the specified element to the end of this container.
     *
     * @param bar element to be appended to this container
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     */
	public boolean add(Bar bar) {
		Boolean bool = bars.add(bar);
		draw();
		return bool;
	}

    /**
     * Inserts the specified element at the specified position in this
     * container. Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
	@Override
	public void add(int index, Bar element) {
		bars.add(index, element);
		draw();
	}


    /**
     * Appends all of the elements in the specified collection to the end of
     * this container, in the order that they are returned by the
     * specified collection's Iterator.  The behavior of this operation is
     * undefined if the specified collection is modified while the operation
     * is in progress.  (This implies that the behavior of this call is
     * undefined if the specified collection is this container, and this
     * container is nonempty.)
     *
     * @param c collection containing elements to be added to this container
     * @return <tt>true</tt> if this container changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
	@Override
	public boolean addAll(Collection<? extends Bar> c) {
		Boolean bool = bars.addAll(c);
		draw();
		return bool;
	}

    /**
     * Inserts all of the elements in the specified collection into this
     * container, starting at the specified position.  Shifts the element
     * currently at that position (if any) and any subsequent elements to
     * the right (increases their indices).  The new elements will appear
     * in the container in the order that they are returned by the
     * specified collection's iterator.
     *
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param c collection containing elements to be added to this container
     * @return <tt>true</tt> if this container changed as a result of the call
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws NullPointerException if the specified collection is null
     */
	@Override
	public boolean addAll(int index, Collection<? extends Bar> c) {
		Boolean bool = bars.addAll(c);
		draw();
		return bool;
	}

    /**
     * Removes all of the elements from this container. The container will
     * be empty after this call returns.
     */
	@Override
	public void clear() {
		bars.clear();
		draw();
	}

    /**
     * Returns <tt>true</tt> if this container contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this container contains
     * at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o element whose presence in this container is to be tested
     * @return <tt>true</tt> if this container contains the specified element
     */
	@Override
	public boolean contains(Object obj) {
		return bars.contains(obj);
	}

	/**
	 * Returns true if this container contains all of the elements of the specified collection.
	 * 
	 * @param c collection to be checked for containment in this list
	 * @return true if this list contains all of the elements of the specified collection
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return bars.containsAll(c);
	}

	/**
	 * Compares the specified object with this list for equality. 
	 * Returns true if and only if the specified object is also a list, 
	 * both lists have the same size, and all corresponding pairs of elements in the two lists are equal. 
	 * (Two elements e1 and e2 are equal if (e1==null ? e2==null : e1.equals(e2)).) 
	 * In other words, two lists are defined to be equal if they contain the same elements in the same order. 
	 * This definition ensures that the equals method works properly across different implementations of the List interface.
	 * 
	 * @param o the object to be compared for equality with this list
	 * @return true if the specified object is equal to this list
	 */
	@Override
	public boolean equals(Object o) {
		return bars.equals(o);
	}

    /**
     * Returns the bar at the specified position in this container.
     *
     * @param  index index of the element to return
     * @return the bar at the specified position in this container
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
	@Override
	public Bar get(int i) {
		return bars.get(i);
	}

	/**
	 * Get a bar based from object equality from the container Will returns null if the object doesn't exist in the container
	 * 
	 * @param bar The bar you you want to get
	 * @return The bar you want to get
	 */
	public Bar get(Bar bar) {
		for (Bar currentBar : bars) {
			if (currentBar == bar) {
				return currentBar;
			}
		}
		return null;
	}

	/**
	 * Get all of the bars in the container
	 * 
	 * @return List of all bars in the container
	 */
	public List<Bar> getAll() {
		return bars;
	}

	/**
	 * Get the bar with the highest value in the container
	 * 
	 * @return The bar with the highest value in the container
	 */
	public Bar getHighest() {
		Bar highestBar = null;
		for (Bar bar : bars) {
			if ((highestBar == null) || (highestBar.getValue() < bar.getValue())) {
				highestBar = bar;
			}
		}
		return highestBar;
	}

	/**
	 * Get the bar with the lowest value in the container
	 * 
	 * @return The bar with the lowest value in the container
	 */
	public Bar getLowest() {
		Bar lowestBar = null;
		for (Bar bar : bars) {
			if ((lowestBar == null) || (lowestBar.getValue() < bar.getValue())) {
				lowestBar = bar;
			}
		}
		return lowestBar;
	}

	/**
	 * Returns the hash code value for this container.
	 * 
	 * @return the hash code value for this container
	 */
	@Override
	public int hashCode() {
		return bars.hashCode();
	}

	/**
	 * Returns the index of the first occurrence of the specified element in this container, 
	 * or -1 if this container does not contain the element. 
	 * More formally, returns the lowest index i such that (o==null ? get(i)==null : o.equals(get(i))), 
	 * or -1 if there is no such index.
	 * 
	 * @param o element to search for
	 * @return the index of the first occurrence of the specified element in this container, or -1 if this container does not contain the element
	 */
	@Override
	public int indexOf(Object o) {
		return bars.indexOf(o);
	}

    /**
     * Returns an iterator over the elements in this container in proper sequence.
     *
     * <p>The returned iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
     *
     * @return an iterator over the elements in this container in proper sequence
     */
	@Override
	public Iterator<Bar> iterator() {
		return bars.iterator();
	}

	/**
	 * Returns true if this container contains no elements.
	 * 
	 * @return true if this container contains no elements
	 */
	@Override
	public boolean isEmpty() {
		return bars.isEmpty();
	}

	/**
	 * Returns the index of the last occurrence of the specified element in this container, 
	 * or -1 if this container does not contain the element. 
	 * More formally, returns the highest index i such that (o==null ? get(i)==null : o.equals(get(i))), 
	 * or -1 if there is no such index.
	 * 
	 * @param o element to search for
	 * @return the index of the last occurrence of the specified element in this container, or -1 if this container does not contain the element
	 */
	@Override
	public int lastIndexOf(Object o) {
		return bars.lastIndexOf(o);
	}

	/**
	 * Returns a list iterator over the elements in this container (in proper sequence).
	 * 
	 * @return a list iterator over the elements in this container (in proper sequence)
	 */
	@Override
	public ListIterator<Bar> listIterator() {
		return bars.listIterator();
	}

	/**
	 * Returns a list iterator of the elements in this container (in proper sequence), 
	 * starting at the specified position in this container. 
	 * The specified index indicates the first element that would be returned by an initial call to next. 
	 * An initial call to previous would return the element with the specified index minus one.
	 * 
	 * @param index index of first element to be returned from the list iterator (by a call to the next method)
	 * @return a list iterator of the elements in this container (in proper sequence), 
	 * starting at the specified position in this container
	 */
	@Override
	public ListIterator<Bar> listIterator(int index) {
		return bars.listIterator(index);
	}

    /**
     * Removes the element at the specified position in this container.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the container
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */	
	@Override
	public Bar remove(int index) {
		Bar bar = bars.remove(index);
		draw();
		return bar;
	}

    /**
     * Removes the first occurrence of the specified element from this container,
     * if it is present.  If the container does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index
     * <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
     * (if such an element exists).  Returns <tt>true</tt> if this container
     * contained the specified element (or equivalently, if this container
     * changed as a result of the call).
     *
     * @param o element to be removed from this container, if present
     * @return <tt>true</tt> if this container contained the specified element
     */	
	@Override
	public boolean remove(Object obj) {
		Boolean bool = bars.remove(obj);
		draw();
		return bool;
	}

    /**
     * Removes from this container all of its elements that are contained in the
     * specified collection.
     *
     * @param c collection containing elements to be removed from this container
     * @return {@code true} if this container changed as a result of the call
     * @throws ClassCastException if the class of an element of this container
     *         is incompatible with the specified collection (optional)
     * @throws NullPointerException if this container contains a null element and the
     *         specified collection does not permit null elements (optional),
     *         or if the specified collection is null
     * @see Collection#contains(Object)
     */	
	@Override
	public boolean removeAll(Collection<?> c) {
		Boolean bool = bars.removeAll(c);
		draw();
		return bool;
	}

    /**
     * Retains only the elements in this container that are contained in the
     * specified collection.  In other words, removes from this container all
     * of its elements that are not contained in the specified collection.
     *
     * @param c collection containing elements to be retained in this container
     * @return {@code true} if this container changed as a result of the call
     * @throws ClassCastException if the class of an element of this container
     *         is incompatible with the specified collection (optional)
     * @throws NullPointerException if this container contains a null element and the
     *         specified collection does not permit null elements (optional),
     *         or if the specified collection is null
     * @see Collection#contains(Object)
     */	
	@Override
	public boolean retainAll(Collection<?> c) {
		Boolean bool = bars.retainAll(c);
		draw();
		return bool;
	}

    /**
     * Replaces the element at the specified position in this container with
     * the specified element.
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */	
	@Override
	public Bar set(int index, Bar element) {
		Bar bar = bars.set(index, element);
		draw();
		return bar;
	}

    /**
     * Returns the number of elements in this container.
     *
     * @return the number of elements in this container
     */	
	@Override
	public int size() {
		return bars.size();
	}

    /**
     * Returns a view of the portion of this container between the specified
     * <tt>fromIndex</tt>, inclusive, and <tt>toIndex</tt>, exclusive.  (If
     * <tt>fromIndex</tt> and <tt>toIndex</tt> are equal, the returned container is
     * empty.)  The returned container is backed by this container, so non-structural
     * changes in the returned container are reflected in this container, and vice-versa.
     * The returned container supports all of the optional container operations supported
     * by this container.<p>
     *
     * This method eliminates the need for explicit range operations (of
     * the sort that commonly exist for arrays).  Any operation that expects
     * a container can be used as a range operation by passing a subcontainer view
     * instead of a whole container.  For example, the following idiom
     * removes a range of elements from a container:
     * <pre>
     *      container.subList(from, to).clear();
     * </pre>
     * Similar idioms may be constructed for <tt>indexOf</tt> and
     * <tt>lastIndexOf</tt>, and all of the algorithms in the
     * <tt>Collections</tt> class can be applied to a subcontainer.<p>
     *
     * The semantics of the container returned by this method become undefined if
     * the backing container (i.e., this container) is <i>structurally modified</i> in
     * any way other than via the returned container.  (Structural modifications are
     * those that change the size of this container, or otherwise perturb it in such
     * a fashion that iterations in progress may yield incorrect results.)
     *
     * @param fromIndex low endpoint (inclusive) of the subcontainer
     * @param toIndex high endpoint (exclusive) of the subcontainer
     * @return a view of the specified range within this container
     * @throws IndexOutOfBoundsException for an illegal endpoint index value
     *         (<tt>fromIndex &lt; 0 || toIndex &gt; size ||
     *         fromIndex &gt; toIndex</tt>)
     */   
	@Override
	public List<Bar> subList(int fromIndex, int toIndex) {
		return bars.subList(fromIndex, toIndex);
	}

    /**
     * Returns an array containing all of the elements in this container
     * in proper sequence (from first to last element).
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this container.  (In other words, this method must allocate
     * a new array).  The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all of the elements in this container in
     *         proper sequence
     */	
	@Override
	public Object[] toArray() {
		return bars.toArray();
	}

    /**
     * Returns an array containing all of the elements in this container in proper
     * sequence (from first to last element); the runtime type of the returned
     * array is that of the specified array.  If the container fits in the
     * specified array, it is returned therein.  Otherwise, a new array is
     * allocated with the runtime type of the specified array and the size of
     * this container.
     *
     * <p>If the container fits in the specified array with room to spare
     * (i.e., the array has more elements than the container), the element in
     * the array immediately following the end of the collection is set to
     * <tt>null</tt>.  (This is useful in determining the length of the
     * container <i>only</i> if the caller knows that the container does not contain
     * any null elements.)
     *
     * @param a the array into which the elements of the container are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing the elements of the container
     * @throws ArrayStoreException if the runtime type of the specified array
     *         is not a supertype of the runtime type of every element in
     *         this container
     * @throws NullPointerException if the specified array is null
     */	
	@SuppressWarnings("hiding")
	@Override
	public <Bar> Bar[] toArray(Bar[] a) {
		return bars.toArray(a);
	}

	/**
	 * Swap two specified bars in the container
	 * 
	 * @param firstBar The first bar
	 * @param secondBar The second bar
	 * @return True if the bars exist and swap is successful, and false if the bars not exists
	 */
	public boolean swap(Bar firstBar, Bar secondBar) {
		if (bars.contains(firstBar) && bars.contains(secondBar)) {
			int firstBarIndex = bars.indexOf(firstBar);
			int secondBarIndex = bars.indexOf(secondBar);

			Bar tempBar = set(firstBarIndex, secondBar);
			set(secondBarIndex, tempBar);
			return true;
		}
		return false;
	}

	/**
	 * Draw the current container, it will not redraw the bars since they should have been drawn.
	 */
	public void draw() {
		int imageLengthWithPadding = Container.PADDING * 2 + getImageLength();
		int imageHeightWithPadding = Container.PADDING * 2 + getImageHeight();

		int imageLengthWithMargin = Container.MARGIN * 2 + imageLengthWithPadding;
		int imageHeightWithMargin = Container.MARGIN * 2 + imageHeightWithPadding;

		GreenfootImage image = new GreenfootImage(imageLengthWithMargin, imageHeightWithMargin);
		image.drawRect(Container.MARGIN, Container.MARGIN, imageLengthWithPadding, imageHeightWithPadding);

		this.setImage(image);
		arrangeBars();
	}

	/**
	 * Arrange the bar location to be in the correct actual position
	 */
	public void arrangeBars() {
		int x = getX();
		int y = getY();

		int mostLeft = x - (getImageLength() / 2);
		int bottom = y + (getImageHeight() / 2);

		for (int i = 0; i < bars.size(); i++) {
			Bar bar = bars.get(i);
			int xLocation = mostLeft + bar.getImageLength() / 2;
			int yLocation = bottom - bar.getImageHeight() / 2;
			bar.setLocationHard(xLocation, yLocation);
			mostLeft = mostLeft + bar.getImageLengthWithMargin();
		}
	}

    /**
     * Assign a new location for this container. This moves the container to the specified
     * location. The location is specified as the coordinates of a cell in the world.
     * 
     * <p>If this method is overridden it is important to call this method as
     * "super.setLocation(x,y)" from the overriding method, to avoid infinite recursion.
     * 
     * @param x Location index on the x-axis
     * @param y Location index on the y-axis
     */
	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		draw();
	}

	/**
	 * Change a range of bars' color to the input color
	 * 
	 * @param beginIndex The begin index of the bar in the container which color we want to change
	 * @param endIndex The end index of the bar in the container which color we want to change
	 * @param color The new color of the bars
	 */
	public void setBarsColor(int beginIndex, int endIndex, Color color) {
		for (int i = beginIndex; i <= endIndex; i++) {
			bars.get(i).setColor(color);
		}
	}

	/**
	 * Change all the the bars' color in this container
	 * 
	 * @param color The new color of the bars in this container
	 */
	public void setBarsColor(Color color) {
		for (Bar bar : bars) {
			bar.setColor(color);
		}
	}

	/**
	 * Reset all the bars color in this container
	 */
	public void resetBarsColor() {
		setBarsColor(Bar.DEFAULT_COLOR);
	}

	/**
	 * Get the image length of this container
	 * 
	 * @return The image length of this container
	 */
	private int getImageLength() {
		return (Bar.MARGIN + Bar.THICKNESS + Bar.MARGIN) * bars.size();
	}

	/**
	 * Get the image height of this container
	 * 
	 * @return The image height of this container
	 */
	private int getImageHeight() {
		int imageHeight;
		Bar highestBar = getHighest();
		if (highestBar == null) {
			imageHeight = 0;
		} else {
			imageHeight = highestBar.getImageHeight();
		}
		return imageHeight;
	}
}
