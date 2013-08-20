public class Point {
	
	public static double EPSILON = 0.001;
	
	double x;
	double y;
	
	public Point (double x, double y) {

		this.x = x;
		this.y = y;
	}

	
	/**
	 * Returns the distance to the given point
	 */
	public double distanceTo(Point p2) {
		
		double yDist = p2.y - y;
		double xDist = p2.x - x;

		return Math.sqrt(xDist*xDist + yDist*yDist);
	}

	
	/**
	 * Returns true if the given point is less than or equal 
	 * to Point.EPSILON distance from this point.
	 */
	public boolean equals(Object o) {
		
		if (o == null || !(o instanceof Point)) {
			return false;
		}
		if (this == o) {
			return true;
		}

		Point p2 = (Point)o;
		return distanceTo(p2) <= EPSILON;
	}
	
	
	public int hashCode() {
		
		int hash = 17;
		hash += 37 * Double.doubleToLongBits(x);
		hash += 41 * Double.doubleToLongBits(y);
		
		return hash;
	}
	
	
	/**
	 * Returns a new Point with the same x,y coordinates
	 * as this Point.
	 * @return
	 */
	public Point copy() {
		return new Point(x,y);
	}
	
	
	/**
	 * 
	 */
	public String toString() {
		return x + "," + y;
	}
}