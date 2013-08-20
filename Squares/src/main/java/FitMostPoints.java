import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FitMostPoints {
	
	
	public static void main(String[] args) {
		
		for (int iters=0; iters < 100; iters++) {
			
			List<Point> points = new ArrayList<Point>();
			for (int i=0; i < 150; i++) {
				double pX = Math.random() * 10;
				double pY = Math.random() * 10;
	
				points.add(new Point(pX, pY));
			}
			Line lineWithMostPoints = findLineIntersectingMostPoints(points);
			System.out.println("Line with most points is: " + lineWithMostPoints);
		}

	}
	
	/**
	 * Finds a line that fits the most number of points in the given list
	 * @param points
	 * @return
	 */
	public static Line findLineIntersectingMostPoints(List<Point> points) {

		if (points == null) {
			return null;
		}
		
		List<Point> uniquePoints = removeDuplicatePoints(points);
		int numUniquePoints = uniquePoints.size();
		int numCompares     = (numUniquePoints) * (numUniquePoints - 1) / 2; 
//		System.out.println("There are unique points " + uniquePoints.size() + ".  Making " + numCompares + " comparisons.");
		long start = System.currentTimeMillis();
		Map<Line, Line> lines    = createLines(uniquePoints);
		Line lineWithMostPoints  = getLineWithMostPoints(lines);
		long elapsed = System.currentTimeMillis() - start;
//		System.out.println("Elapsed: " + elapsed);
		
		return lineWithMostPoints;
	}
	
	
	/**
	 * Removes duplicate points from the list.
	 * 
	 * @param points
	 * @return
	 */
	private static List<Point> removeDuplicatePoints(List<Point> points) {

		List<Point> uniquePoints = new ArrayList<Point>(points.size());
		
		for (int i=0; i < points.size(); i++) {

			Point p1 = points.get(i);
			boolean isDup = false;
			for (int j=i+1; j < points.size(); j++) {

				Point p2 = points.get(j);
				if (p1.equals(p2)) {
					isDup = true;
					break;
				}
			}
			
			if (!isDup) {
				uniquePoints.add(p1);
			}
		}
		
		return uniquePoints;
	}

	
	/**
     * Makes a list of all possible lines for the given points.  
     * This will require (N choose 2) comparisons (eg. ABC=AB, AC, CB)
     * but note the returned Map will contain fewer lines if some points
     * lie on the same line.
	 * @param points
	 * @return
	 */
	private static Map<Line, Line>createLines(List<Point> points) {
		
		Map<Line, Line> lines = new HashMap<Line, Line>();

		for (int i=0; i < points.size(); i++) {

			Point p1 = points.get(i);
			for (int j=i+1; j < points.size(); j++) {
				
				Point p2 = points.get(j);
				Line aLine = new Line(p1, p2);
				if (lines.containsKey(aLine)) {
					aLine = lines.get(aLine);
					aLine.addPoint(p2);
				}
				else {
					lines.put(aLine, aLine);
				}
			}
		}

		return lines;
	}
	
	
	private static Line getLineWithMostPoints(Map<Line, Line> lines) {
		
		Line bestLine = null;
		for (Line aLine : lines.values()) {
			if (bestLine == null || aLine.getPoints().size() > bestLine.getPoints().size()) {
				bestLine = aLine;
			}
		}
		
		return bestLine;
	}
}