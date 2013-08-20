
import java.util.Stack;

public class Towers {
	
	private int numMoves = 0;
	private int numDisks = 0;
	private Stack<Integer> tower1 = null;
	private Stack<Integer> tower2 = null;
	private Stack<Integer> tower3 = null;
	
	public Towers() {
		numDisks = 6;
		initTowers();
	}
	
	public void solve() {
		System.out.println("before");
		printTowers();

		moveDisks(numDisks, tower1, tower3, tower2);
		
		System.out.println("after");
		printTowers();
		System.out.println("# Moves = " + numMoves);
	}

	
	/**
	 * 
	 */
	private void initTowers() {
		
		tower1 = new Stack<Integer>();
		tower2 = new Stack<Integer>();
		tower3 = new Stack<Integer>();
		
		for (int i=numDisks; i > 0; i--) {
			tower1.push(new Integer(i));
		}
	}
	
	
	/**
	 * 
	 */
	public void printTowers() {
		
		System.out.println("Tower1: " + tower1.toString());
		System.out.println("Tower2: " + tower2.toString());
		System.out.println("Tower3: " + tower3.toString());
	}
	
	
	/**
	 * 
	 */
	public void moveDisks(int numDisks, Stack<Integer> origin, Stack<Integer> destination, Stack<Integer> buffer) {
		
		if (numDisks <= 0) {
			return;
		}
		
		moveDisks(numDisks - 1, origin, buffer, destination);
		moveTop(origin, destination);
		moveDisks(numDisks - 1, buffer, destination, origin);
		numMoves++;
	}

	
	/**
	 * 
	 */
	private void moveTop(Stack<Integer> from, Stack<Integer> to) {

		if (!from.isEmpty()) {
			if (!to.isEmpty()) {
				int fromVal = from.peek();
				int toVal   = to.peek(); 
				if (fromVal > toVal) {
					System.out.println("Error - Can't push " + fromVal + " on top of " + toVal);
					System.exit(0);
				}
			}

			to.push(from.pop());
		}
	}
	
	
	public static void main(String[] args) {
		Towers towers = new Towers();
		towers.solve();
	}

}