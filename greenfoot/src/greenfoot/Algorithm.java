package greenfoot;

/**
 * This is an extends for the actor class
 * The students will have to program their algorithm as the subclass of this class
 * 
 * @author Rowanto
 * @version 1.0
 */
public abstract class Algorithm extends Actor {
	
	@Override
	public void addedToWorld(World world)
	{
		// There can only be one algorithm object in the world
		for (Object obj : world.getObjects(null))
		{
			if (obj instanceof Algorithm)
			{
				if (obj != this)
				{
					world.removeObject((Actor) obj);
				}
			}
		}
	}
}
