
import com.badlogic.gdx.graphics.GL11;

import com.badlogic.gdx.Gdx;

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
		
		for (int i = 0; i < core.maze.maze.length; i++)
		{
			for (int n = 0; n < core.maze.maze[i].length; n++)
			{
				if (core.maze.maze[i][n] != null)
				{
					if (hasCollided(core.maze.maze[i][n]))
					{
						Box b = core.maze.maze[i][n]; 
						if (isBetween(b.location.x, this.eye.x, b.location.x + 0.6) || isBetween(b.location.x - 0.6, this.eye.x, b.location.x))
						{
							this.eye.x = oldX; 
						}
						if (isBetween(b.location.z, this.eye.z, b.location.z + 0.6) || isBetween(b.location.z - 0.6, this.eye.z, b.location.z))
						{
							this.eye.z = oldZ; 
						}
					}	
				}
			}
		}
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
	
	private boolean isBetween(double y1, double x, double y2)
	{
		if ((y1 <= x) && (x <= y2))
		{
			return true; 
		}
		return false; 
	}
}
