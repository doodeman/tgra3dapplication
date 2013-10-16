import java.util.List;


/**
 * A list of boxes that make up a 3x3 square of boxes. 
 * Used to split the maze into smaller chunks for collision detection
 * @author kristleifur
 *
 */
public class Grid 
{
	public List<Box> boxes; 
	public Grid north, south, west, east; 
	
	public Grid(List<Box> boxes)
	{
		this.boxes = boxes; 
	}
}
