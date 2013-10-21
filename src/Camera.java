import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;

public class Camera
{
	Point3D eye;
	Vector3D u;
	Vector3D v;
	Vector3D n;
	
	First3D_Core core; 

	public Camera(Point3D pEye, Point3D pCenter, Vector3D up, First3D_Core core) {
		eye = pEye;
		n = Vector3D.difference(pEye, pCenter);
		n.normalize();
		u = Vector3D.cross(up, n);
		u.normalize();
		v = Vector3D.cross(n, u);
		this.core = core; 
	}
	
	public void setModelViewMatrix() {
		Vector3D minusEye = Vector3D.difference(new Point3D(0,0,0), eye);
		
		float[] matrix = new float[16];
		matrix[0] = u.x;	matrix[4] = u.y;	matrix[8] = u.z;	matrix[12] = Vector3D.dot(minusEye, u);
		matrix[1] = v.x;	matrix[5] = v.y;	matrix[9] = v.z;	matrix[13] = Vector3D.dot(minusEye, v);
		matrix[2] = n.x;	matrix[6] = n.y;	matrix[10] = n.z;	matrix[14] = Vector3D.dot(minusEye, n);
		matrix[3] = 0;		matrix[7] = 0;		matrix[11] = 0;		matrix[15] = 1;
		
		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		Gdx.gl11.glLoadMatrixf(matrix, 0);
	}
	
	public void slide(float delU, float delV, float delN) {
		float oldX = this.eye.x; 
		float oldZ = this.eye.z; 
		eye.add(Vector3D.sum(Vector3D.mult(delU, u), Vector3D.sum(Vector3D.mult(delV, v), Vector3D.mult(delN, n))));
		
		if(atVictoryPoint()){
			core.win = true;
		}
		
		for (int i = 0; i < core.maze.maze.length; i++)
		{
			for (int n = 0; n < core.maze.maze[i].length; n++)
			{
				if (core.maze.maze[i][n] != null)
				{
					if (hasCollided(core.maze.maze[i][n]))
					{
						fixCollision(core.maze.maze[i][n]);
						return;
					}	
				}
			}
		}
	}

	private boolean atVictoryPoint() {
		if (isBetween(core.maze.goal.location.x - 0.5, eye.x, core.maze.goal.location.x + 0.5)){
			if (isBetween(core.maze.goal.location.z - 0.5, eye.z, core.maze.goal.location.z + 0.5)){
					return true; 
			}
		}
		return false;
	}

	public void yaw(float angle) {
		float c = (float) Math.cos(angle*Math.PI/180.0f);
		float s = (float) Math.sin(angle*Math.PI/180.0f);
		Vector3D t = u;
		u = Vector3D.sum(Vector3D.mult(c, t), Vector3D.mult(s, n));
		n = Vector3D.sum(Vector3D.mult(-s, t), Vector3D.mult(c, n));
	}

	public void roll(float angle) {
		float c = (float) Math.cos(angle*Math.PI/180.0f);
		float s = (float) Math.sin(angle*Math.PI/180.0f);
		Vector3D t = u;
		u = Vector3D.sum(Vector3D.mult(c, t), Vector3D.mult(s, v));
		v = Vector3D.sum(Vector3D.mult(-s, t), Vector3D.mult(c, v));
	}

	public void pitch(float angle) {
		float c = (float) Math.cos(angle*Math.PI/180.0f);
		float s = (float) Math.sin(angle*Math.PI/180.0f);
		Vector3D t = v;
		v = Vector3D.sum(Vector3D.mult(c, t), Vector3D.mult(s, n));
		n = Vector3D.sum(Vector3D.mult(-s, t), Vector3D.mult(c, n));
	}
	
	public boolean hasCollided(Box b)
	{
		if (isBetween(b.location.x - 0.5, eye.x, b.location.x + 0.5))
		{
			//System.out.println("Collission on X axis");
			if (isBetween(b.location.y - 0.5, eye.y, b.location.y + 0.5))
			{
				//System.out.println("Collission on Y axis"); 
				if (isBetween(b.location.z - 0.5, eye.z, b.location.z + 0.5))
				{
					//System.out.println("Collission on Z axis"); 
					return true; 
				}
			}
		}
		return false;
	}
	
	public void fixCollision(Box b)
	{
		List<Box> neighbors = b.getNeighbors();
		float min = Float.MAX_VALUE;  
		float distance; 
		Box closest = null; 
		for (Box neighbor : neighbors)
		{
			if (neighbor != null)
			{
				distance = eye.distance(neighbor.location);
				if (distance < min)
				{
					min = distance; 
					closest = neighbor; 
				}
			}
		}
		distance = eye.distance(b.location);
		if (distance < min)
		{
			closest = b; 
		}
		//Find which side of the box we hit.
		min = Float.MAX_VALUE;
		List<Point3D[]> edges = closest.getEdges();
		int closestEdge = -1; 
		double closestDistance = Float.MAX_VALUE; 
		for (int i = 0; i < edges.size(); i++)
		{
			double currDistance = Point3D.pointToLineDistance(edges.get(i)[0], edges.get(i)[1], eye);
			if (currDistance < closestDistance)
			{
				closestDistance = currDistance;
				closestEdge = i; 
			}
		}
		/*
		 * 0 = top (z = 0.5)
		 * 1 = bottom (z = -0.5)
		 * 2 = left (x = 0.5)
		 * 3 = right (x = -0.5)
		 */
		if (closestEdge == 0)
		{
			eye.z = (float) (closest.location.z + 0.51);
		}
		if (closestEdge == 1)
		{
			eye.z = (float) (closest.location.z - 0.51); 
		}
		if (closestEdge == 2)
		{
			eye.x = (float) (closest.location.x + 0.51); 
		}
		if (closestEdge == 3)
		{
			eye.x = (float) (closest.location.x - 0.51);
		}
	}
	
	private boolean isBetween(double y1, double x, double y2)
	{
		if ((y1 <= x) && (x <= y2))
		{
			return true; 
		}
		return false; 
	}
}
