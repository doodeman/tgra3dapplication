import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;

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
	private FloatBuffer buffer; 
	public GoalObject goal;
	
	public Maze(String infile, FloatBuffer wallBuffer) 
	{
		this.buffer = wallBuffer; 
		try {
			readInput(infile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void readInput(String infile) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(Gdx.files.internal("lib/" + infile).toString()));
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
			for (int n = 0, t = lines.get(i).length(); n < t; n++)
			{
				if (lines.get(i).charAt(n) == 'o'){
					goal = new GoalObject(100, 100, new Point3D(i,1,n));
				}
				else if (lines.get(i).charAt(n) != ' ')
				{
					maze[n][i] = new Box(buffer, new Point3D(i,1,n), this.maze, i, n); 
				}
			}
		}
	}
}
