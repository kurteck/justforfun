import java.util.HashSet;
import java.util.Set;


public class Line {
	
	public static double EPSILON  = 0.001;
	
	Point p1;
	Point p2;
	double slope;
	double xIntercept;
	double yIntercept;
	private Set<Point> points;
	
	public Line(Point p1, Point p2) {

		this.p1 = p1;
		this.p2 = p2;
		this.points = new HashSet<Point>();
		points.add(p1);
		points.add(p2);
		setSlopeXYIntercept();
	}

	private void setSlopeXYIntercept() {

		// This line is actually just a point - should not be allowed
		if (p1.equals(p2)) {
			slope = Double.NaN;
		}
		// Vertical line with infinite slope
		else if (Math.abs(p2.x - p1.x) <= EPSILON) {
			slope = Double.POSITIVE_INFINITY;
			xIntercept = p1.x;
		}
		// Horizontal line
		else if (Math.abs(p2.y - p1.y) <= EPSILON) {
			slope = 0;
			yIntercept = p1.y;
		}
		// Normal line
		else {
			slope = (p2.y - p1.y) / (p2.x - p1.x);
			yIntercept = p1.y - (slope * p1.x);
		}
	}

	
	/**
	 * Adds newPoint to this line if the newPoint fits this 
	 * line (i.e. generates the same slope & x,y intercepts) 
	 * 
	 * @param newPoint
	 */
	public boolean addPoint(Point newPoint) {
	
		boolean containsPoint = containsPoint(newPoint);
		if (containsPoint) {
			points.add(newPoint);
			return true;
		}
		
		return false;
	}
	
	
	public boolean containsPoint(Point aPoint) {

		if (aPoint == null) {
			return false;
		}
		
		Line testLine = new Line(p1, aPoint);
		if (this.equals(testLine)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public boolean isPoint() {
		return slope == Double.NaN;
	}

	
	public Set<Point> getPoints() { 
		return points;
	}
	
	
	/**
	 * Returns the distance to the given point
	 */
	public double length() {
		return p1.distanceTo(p2);
	}

	
	/**
	 * Returns true if the given line segment has approximately 
	 * the same start and end points as this line.
	public boolean equals(Line l2) {
		
		if (l2 == null) {
			return false;
		}
		
		return p1.equals(l2.p1) && p2.equals(l2.p2);
	}
	 */

	@Override
	public boolean equals(Object o) {
		
		if (o == null || !(o instanceof Line)) {
			return false;
		}
		if (o == this) {
			return true;
		}

		Line l2 = (Line)o;
		if (Math.abs(this.slope - l2.slope) > EPSILON) {
			return false;
		}

		if (this.slope == Double.POSITIVE_INFINITY) {
			return Math.abs(this.xIntercept - l2.xIntercept) <= EPSILON;
		}
		else {
			return Math.abs(this.yIntercept - l2.yIntercept) <= EPSILON;
		}
	}
	
	@Override
	public int hashCode() {
		
		int hash = 13;
		if (this.slope == Double.POSITIVE_INFINITY) {
			hash = hash + 17 * (int)Math.round(xIntercept);
		}
		else {
			hash = hash + 29 * (int)Math.round(slope);
			hash = hash + 31 * (int)Math.round(yIntercept);
		}
		
		return hash;
	}
	
	/**
	 * Returns a new line with the same end-points as this line.
	 * @return
	 */
	public Line copy() {
		return new Line(p1.copy(),p2.copy());
	}


	public void printPoints() {
		for (Point aPoint : points) {
			System.out.print("(" + aPoint.x + "," + aPoint.y + ") ");
		}
	}
	
	public String toString() { 
		return "Hash[" + hashCode() +"]  slope[" + slope + "]  yIntercept[" + yIntercept + "]  xIntercept[" + xIntercept + "]  Num Points[" + points.size() + "]";
	}
	
}