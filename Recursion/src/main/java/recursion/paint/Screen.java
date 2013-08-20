package recursion.paint;

import java.util.LinkedList;
import java.util.List;

public class Screen {
	
	private int width;
	private int height;
	private int[][] colors;
	
	public Screen(int width, int height) {
		this.width  = width;
		this.height = height;
		this.colors = new int[width][height];
	}
	
	
	public void fill(Point p, int fillColor) {
		
		if (!isPointOnScreen(p) || fillColor < 0) {
			return;
		}
		
		int replaceColor = getColor(p);
		List<Point> points = new LinkedList<Point>();
		points.add(p);

		fill(points, replaceColor, fillColor);
	}
	
	
	private void fill(List<Point> points, int replaceColor, int fillColor) {
		
		if (points == null || points.size() <= 0 || replaceColor==fillColor) {
			return;
		}
		
		Point aPoint = points.remove(0);
		if (getColor(aPoint) == replaceColor) {
			setColor(aPoint, fillColor);
			
			List<Point> morePoints = getSurroundingPoints(aPoint);
			points.addAll(morePoints);
		}
		
		fill(points, replaceColor, fillColor);
	}
	

	private List<Point> getSurroundingPoints(Point p) {
		
		List<Point> points = new LinkedList<Point>();

		for (int x=p.x-1; x <= p.x+1; x++) {
			for (int y=p.y-1; y <= p.y+1; y++) {
				if ((x != p.x) || (y != p.y)) {
					Point newPoint = new Point(x,y);
					if (isPointOnScreen(newPoint)) {
						points.add(newPoint);
					}
				}
			}
		}
		
		return points;
	}
	
	
	protected boolean isPointOnScreen(Point p) {
		return (p.x >= 0 && p.x < width && p.y >= 0 && p.y < height);
	}

	
	public int getColor(Point p) {
		if (!isPointOnScreen(p)) {
			return -1;
		}
		
		return colors[p.x][p.y];
	}


	public void setColor(Point p, int color) {
		if (isPointOnScreen(p)) {
			colors[p.x][p.y] = color;
		}
	}
	
	
	/**
	 * For testing only
	 */
	public void setColors(int[][] colors) {
		this.colors = colors;
	}
	
	public void print() {
		for (int x=0; x < width; x++) {
			for (int y=0; y < height; y++) {
				System.out.printf("%-3s", colors[x][y]);
			}
			System.out.println();
		}
	}
	
	
	public static void main(String[] args) {
		
		int[][] colors1 = {
				{  9,  0,  0,  0,  0,  0,  0,  0,  9  },
				{  0,  9,  9,  9,  9,  9,  9,  9,  0  },
				{  0,  9,  1,  1,  9,  1,  1,  9,  0  },
				{  0,  9,  1,  9,  9,  9,  1,  9,  0  },
				{  0,  9,  1,  9,  5,  9,  1,  9,  0  },
				{  0,  9,  1,  9,  9,  9,  1,  9,  0  },
				{  0,  9,  1,  1,  1,  1,  1,  9,  0  },
				{  0,  9,  9,  9,  9,  9,  9,  9,  0  },
				{  9,  0,  0,  0,  0,  0,  0,  0,  9  },
		};
		
		Screen screen = new Screen(9,9);
		screen.setColors(colors1);
		screen.print();
		System.out.println();

		screen.fill(new Point(0,0), 3);
		screen.print();
		System.out.println();
	}
}