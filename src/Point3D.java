
public class Point3D {
	public float x;
	public float y;
	public float z;
	
	public Point3D(float xx, float yy, float zz) {
		x = xx;
		y = yy;
		z = zz;
	}
	
	public Point3D(double xx, double yy, double zz)
	{
		x = (float) xx; 
		y = (float) yy; 
		z = (float) zz; 
	}
	public void set(float xx, float yy, float zz) {
		x = xx;
		y = yy;
		z = zz;
	}
	
	public void add(Vector3D v) {
		x += v.x;
		y += v.y;
		z += v.z;
	}
	
	public float distance(Point3D that)
	{
		float xdist, ydist; 
		xdist = this.x - that.x; 
		ydist = this.x - that.x; 
		return (float) Math.sqrt(xdist*xdist + ydist*ydist); 
	}
	
	/**
	 * Distance of point X from the line formed by the points a and b. 
	 * Borrowed from http://www.ahristov.com/tutorial/geometry-games/point-line-distance.html and then adapted. 
	 * @param a
	 * @param b
	 * @param x
	 * @return
	 */
	 public static double pointToLineDistance(Point3D A, Point3D B, Point3D P) 
	 {
		 double normalLength = Math.sqrt((B.x-A.x)*(B.x-A.x)+(B.z-A.z)*(B.z-A.z));
		 return Math.abs((P.x-A.x)*(B.z-A.z)-(P.z-A.z)*(B.x-A.x))/normalLength;
	 }
}
