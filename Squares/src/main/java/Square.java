public class Square {

	double side;
	Point lowerLeft;
	Point center;
	
	public Square(double side, Point lowerLeft) {

		this.side = side;
		this.lowerLeft = lowerLeft;
		this.center = new Point(lowerLeft.x + side/2, lowerLeft.y + side/2);
	}
	
	
	/**
	 * Returns the area of this square
	 */
	public double area() {
		return side*side;
	}

	
	/**
	 * True if the given square has the same center
	 * as this square.
	 */
	public boolean hasSameCenter(Square s2) {

		if (s2 == null) {
			return false;
		}
		
		return center.equals(s2.center);
	}

	
	/**
	 * True if the given square has the same center
	 * and lowerLeft position as this square.
	 */
	public boolean hasSameOrientation(Square s2) {
		
		if (s2 == null) {
			return false;
		}

		return center.equals(s2.center) && hasSameCenter(s2);
	}

	
	/**
	 * True if the given square has the same center
	 * and lowerLeft position as this square.
	 */
	public boolean equals(Square s2) {
		
		if (s2 == null) {
			return false;
		}
		
		return hasSameOrientation(s2);
	}
	
	
	/**
	 * True if this square contains the given point
	 */
	public boolean contains(Point p) {
		
		if (p == null) {
			return false;
		}
		
		boolean containsX = (p.x <= lowerLeft.x + side) && (p.x >= lowerLeft.x);
		boolean containsY = (p.y <= lowerLeft.y + side) && (p.y >= lowerLeft.y);
		
		return containsX && containsY;
	}
	
	
	/**
	 * True if this square touches any point on the given line
	 */
	public boolean touches(Line line) {

		if (line == null) {
			return false;
		}
		
		return contains(line.p1) || contains(line.p2);
	}
	
	
	/**
	 * Returns a line describing all points that touch the given square.
	 * The end-points of the intersecting line may be:
	 * 
	 * 1) On the square's edge if the line passed through the square or stops at a side.
	 * 2) In the square's interior if the line is contained fully or partially within the square.
	 * 
	 * If no points intersect with the given square then null is returned.
	 * 
	 * @param l1
	 * @return
	 */
	public Line intersection(Line line) {

		// Line lies wholly outside this square
		if (line == null || !touches(line)) {
			return null;
		}
		// Line is contained wholly within this square.
		else if (contains(line.p1) && contains(line.p2)) {
			return line;
		}
		// Line is partially within this square so we must cut it at an edge
		else {
			Line newLine = line.copy();
			Point endToKep = newLine.p1;
			Point endToCut = newLine.p2;
			if (this.contains(newLine.p2)) {
				endToKep = newLine.p2;
				endToCut = newLine.p1;
			}

			/* Trim X point and reset Y if necessary */
			if (endToCut.x > lowerLeft.x + side) {
				endToCut.x = lowerLeft.x + side;
				endToCut.y = newLine.slope * (endToCut.x - endToKep.x) + endToKep.y;
			}
			else if (endToCut.x < lowerLeft.x) {
				endToCut.x = lowerLeft.x;
				endToCut.y = newLine.slope * (endToCut.x - endToKep.x) + endToKep.y;
			}

			/* Trim Y point and reset X if necessary */
			if (endToCut.y > lowerLeft.y + side) {
				endToCut.y = lowerLeft.y + side;
				endToCut.x = (endToCut.y - endToKep.y) / newLine.slope + endToKep.x;
			}
			else if (endToCut.y < lowerLeft.y) {
				endToCut.y = lowerLeft.y;
				endToCut.x = (endToCut.y - endToKep.y) / newLine.slope + endToKep.x;
			}

			return newLine;
		}
	}
}