import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maze 
{
	public boolean maze[][];
	
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
		
		int xSize = lines.get(0).length(); 
		int ySize = lines.size(); 
		maze = new boolean[xSize][ySize]; 
		
		for (int i = 0; i < lines.size(); i++)
		{
			for (int n = 0, t = lines.get(i).length(); n < t; i++)
			{
				if (lines.get(i).charAt(n) != ' ')
				{
					maze[i][n] = true; 
				}
			}
		}
	}
}
