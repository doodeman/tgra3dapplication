import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The maze. 
 * @author kristleifur
 *
 */
public class Maze 
{
	//Every grid square that is true is filled with a box. 
	public Box maze[][];
	int xSize, ySize; 
	
	public Maze(String infile) throws IOException
	{
		readInput(infile); 
	}
	
	public void readInput(String infile) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(infile));
		List<String> lines = new ArrayList<String>();
		try 
		{
			String line = br.readLine(); 
			while (line != null)
			{
				lines.add(line); 
				line = br.readLine();
			}
			br.close();
		} 
		catch (Exception e)
		{
			br.close();
			e.printStackTrace(); 
		}
		
		xSize = lines.get(0).length(); 
		ySize = lines.size(); 
		maze = new Box[xSize][ySize]; 
		
		for (int i = 0; i < lines.size(); i++)
		{
			for (int n = 0, t = lines.get(i).length(); n < t; i++)
			{
				if (lines.get(i).charAt(n) != ' ')
				{
					maze[i][n] = new Box(null, new Point3D(i,n,0)); 
				}
			}
		}
	}
	
	/**
	 * Splits the maze into 3x3 grid chunks. 
	 */
	private void split()
	{
		List<Grid> grids = new ArrayList<Grid>();
		//How many grids accross the maze is
		int xThickness = xSize/3; 
		if (xThickness % 3 != 0)
		{
			xThickness++; 
		}
		
		int yThickness = ySize/3; 
		if (yThickness % 3 != 0)
		{
			yThickness ++; 
		}
		
		for (int i = 0; i < xThickness; i++)
		{
			for (int n = 0; n < yThickness; n++)
			{
				List<Box> gridBoxes = new ArrayList<Box>(); 
				try{ gridBoxes.add(maze[i*3][n*3]); } catch(ArrayIndexOutOfBoundsException e) { };
				try{ gridBoxes.add(maze[i*3][n*3+1]); } catch(ArrayIndexOutOfBoundsException e) { };
				try{ gridBoxes.add(maze[i*3][n*3+2]); } catch(ArrayIndexOutOfBoundsException e) { };
				try{ gridBoxes.add(maze[i*3+1][n*3]); } catch(ArrayIndexOutOfBoundsException e) { };
				try{ gridBoxes.add(maze[i*3+1][n*3+1]); } catch(ArrayIndexOutOfBoundsException e) { };
				try{ gridBoxes.add(maze[i*3+1][n*3+2]); } catch(ArrayIndexOutOfBoundsException e) { };
				try{ gridBoxes.add(maze[i*3+2][n*3]); } catch(ArrayIndexOutOfBoundsException e) { };
				try{ gridBoxes.add(maze[i*3+2][n*3+1]); } catch(ArrayIndexOutOfBoundsException e) { };
				try{ gridBoxes.add(maze[i*3+2][n*3+1]); } catch(ArrayIndexOutOfBoundsException e) { };
				
				grids.add(new Grid(gridBoxes));
			}
		}
	}
}
