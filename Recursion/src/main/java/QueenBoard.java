public class QueenBoard {
	
	public static final int BOARD_SIZE = 8;
	
	// col7  col6  col5  col4  col3  col2  col1  col0
	// 1111, 1110, 1101, 1100, 1011, 1010, 1000, 0000
	// row7, row6  row5  row4  row3  row2  row0  empty
	private int state = 0;
	private int numQueens = 0;
	
	
	public void setQueen(int row, int col) {
		
		if (row < 0 || col < 0 || row >= BOARD_SIZE || col >= BOARD_SIZE) {
			return;
		}

		if (hasQueen(row, col)) {
			return;
		}
		
		int clear = 0xF << (col*4);
		state &= ~clear;
		
		int setQueen = (0x8 | row) << (col*4);
		state |= setQueen;
		
		numQueens++;
	}
	
	
	public boolean hasQueen(int row, int col) {
		
		if (row < 0 || col < 0 || row >= BOARD_SIZE || col >= BOARD_SIZE) {
			return false;
		}
		
		int column = (state >>> (col*4)) & 0xF;
		if (column == (0x8 | row)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Check all columns to see if their value = row
	public boolean hasQueenInRow(int row) {
		if (row < 0 || row >= BOARD_SIZE) {
			return false;
		}

		for (int col=0; col < BOARD_SIZE; col++) {
			if (hasQueen(row, col)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public boolean hasQueenInCol(int col) {

		if (col < 0 || col >= BOARD_SIZE) {
			return false;
		}

		int column = (state >>> (col*4)) & 0xF;
		if (column > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	
	public boolean hasQueenInDiag(int row, int col) {
		
		if (row < 0 || col < 0 || row >= BOARD_SIZE || col >= BOARD_SIZE) {
			return false;
		}

		for (int i=0; i < BOARD_SIZE; i++) {
			if (hasQueen(row-i, col-i) || hasQueen(row-i, col+i) || hasQueen(row+i, col-i) || hasQueen(row+i, col+i)) {
				return true;
			}
		}
		
		return false;
	}

	
	public void print() {
		
		for (int col=0; col < BOARD_SIZE; col++) {
			for (int row=0; row < BOARD_SIZE; row++) {
				if (hasQueen(row, col)) {
					System.out.print("Q ");
				}
				else {
					System.out.print("x ");
				}
			}
			System.out.println();
		}
	}
	
	
	public QueenBoard copy() {
		
		QueenBoard newBoard = new QueenBoard();
		newBoard.state = state;
		newBoard.numQueens = numQueens;
	
		return newBoard;
	}
	
	
	public static void makeBoards() {
		QueenBoard emptyBoard = new QueenBoard();
		makeBoards(emptyBoard);
	}
	

	/**
	 * 
	 */
	private static void makeBoards(QueenBoard board) {
		
		int col = board.numQueens;
		for (int row=0; row < BOARD_SIZE; row++) {
			if (!board.hasQueenInRow(row) && 
				!board.hasQueenInCol(col) && 
				!board.hasQueenInDiag(row,col)) {
				
				QueenBoard newBoard = board.copy();
				newBoard.setQueen(row, col);
				
				if (newBoard.numQueens == BOARD_SIZE) {
					newBoard.print();
					System.out.println();
				}
				else {
					makeBoards(newBoard);
				}
			}
		}
	}
	
	
	public static void printlnBits(int bits) {
		for (int i=31; i >=0; i--) {
			System.out.print((bits >> i) & 0x1);
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		
//		QueenBoard qb = new QueenBoard();
//		qb.setQueen(3,2);
//		QueenBoard.printlnBits(qb.state);
//		System.out.println(qb.hasQueenInCol(3));
//		System.out.println(qb.hasQueenInRow(5));
//		System.out.println(qb.hasQueenInDiag(7,6));
//		qb.print();
		
		QueenBoard.makeBoards();
	}
	
	
	
}