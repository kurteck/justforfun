
public class GridPathCounter {

	private long[][] countCache;
	private int numX;
	private int numY;
	
	public GridPathCounter(int numX, int numY) {
		this.countCache = new long[numX+1][numY+1];
		this.numX = numX;
		this.numY = numY;
	}
	
	public long countPaths() {
		return countPaths(0, 0);
	}
	

	private long countPaths(int x, int y) {
		
		// Check Boundaries
		if (x < 0 || y < 0 || x > numX || y > numY || isBlockedPath(x,y)) {
			return 0;
		}
		// Check if result was already calculated
		else if (countCache[x][y] != 0) {
			return countCache[x][y];
		}
		// Return 1 if we found a path to the destination
		else if (x == numX && y == numY) {
			return 1;
		}
		// Otherwise move down and/or right as appropriate
		else if (x < numX && y < numY) {
			long down  = countPaths(x, y+1);
			countCache[x][y+1] = down;

			long right = countPaths(x+1, y);
			countCache[x+1][y] = right;
			return down + right;
		}
		else if (x < numX) {
			long right = countPaths(x+1, y);
			countCache[x+1][y] = right;
			return right;
		}
		else {
			long down  = countPaths(x, y+1);
			countCache[x][y+1] = down;
			return down;
		}
	}
	
	private boolean isBlockedPath(int x, int y) {
//		if (x <= 2 && y == 1) {
//			return true;
//		}
		
		return false;
	}
	
	
	protected void printCache() {
		for (int y=0; y <= numY; y++) {
			for (int x=0; x <= numX; x++) {
			System.out.println("CountCache[" + x + "," + y + "] = " + countCache[x][y]);
			}
		}
	}
	

	public static void main(String[] args) {
		
		int numX = 33;
		int numY = 33;
		GridPathCounter gpc = new GridPathCounter(numX, numY);
		System.out.println("CountPaths(" + numX + "," + numY +") = " + gpc.countPaths());
	}

}