public class Maze {
	
	public static final int MAZE_SIZE = 8;
	
	private int startX;
	private int startY;
	private int rows;
	private int cols;
	private char[][] grid;
	

	private Maze() {
		;
	}
	
	
	public void findExit() {
		boolean[][] visited = new boolean[rows][cols];
		findExit(visited, startX, startY);
	}

	private boolean findExit(boolean[][] visited, int currentX, int currentY) {
		
		if (currentX < 0 || currentY < 0 || currentX > rows-1 || currentY > cols-1) {
			return false;
		}

		
		char cell = grid[currentX][currentY];
		if (cell == 'W' || cell == 'x') {
			return false;
		}

		grid[currentX][currentY] = 'O';
		print();
		grid[currentX][currentY] = 'x';
		if (cell == 'E') {
			return true;
		}

		try {
			Thread.sleep(200);
		}
		catch (InterruptedException ie) {
			;
		}
		
		// go back if we've already tried this path
		// otherwise, remember we have been here before
		if (visited[currentX][currentY] == true) {
			return false;
		}

		visited[currentX][currentY] = true;
		if (findExit(visited, currentX, currentY-1)) {
			return true;
		}
		else if (findExit(visited, currentX, currentY+1)) {
			return true;
		}
		else if (findExit(visited, currentX-1, currentY)) {
			return true;
		}
		else if (findExit(visited, currentX+1, currentY)) {
			return true;
		}

		return false;
	}
	
	
	public char[][] getGrid() {
		return this.grid;
	}
	
	
	
	public void print() {
		
		int rows = grid.length;
		int cols = grid[0].length;
		
		for (int x=0; x < rows; x++) {
			for (int y=0; y < cols; y++) {
				System.out.print(grid[x][y]);
			}
			System.out.println();
		}
	}
	
	
	
	public static final Maze makeRandomMaze(int rows, int cols) {

		int startX = rows-1;
		int startY = cols/2;
		
		Maze maze   = new Maze();
		maze.rows   = rows;
		maze.cols   = cols;
		maze.startX = startX;
		maze.startY = startY;

		maze.grid = makeRandomGrid(rows, cols);
		maze.grid[startX][startY] = 'S';
		maze.grid[rows/2][cols-1] = 'E';
		
		return maze;
	}
	
	private static char[][] makeRandomGrid(int rows, int cols) {
		
		char grid[][] = new char[rows][cols];

		for (int y=1; y < cols-1; y++) {
			for (int x=1; x < rows-1; x++) {
				int cellNum = (int)(Math.random() * 10);
				if (cellNum < 7) { //70 chance open%
					grid[x][y] = ' ';
				}
				else {
					grid[x][y] = 'W';
				}
			}
		}
		
		for (int x=0; x < rows; x++) {
			grid[x][0] = 'W';
			grid[x][cols-1] = 'W';
		}

		for (int y=0; y < cols; y++) {
			grid[0][y] = 'W';
			grid[rows-1][y] = 'W';
		}
					
		return grid;
	}
	
	
	public static void main(String[] args) {
		
		Maze maze = Maze.makeRandomMaze(10, 20);
		maze.findExit();
	}
	
}