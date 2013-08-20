
import java.util.Stack;

public class StackSort {
	
	public Stack<Integer> sort(Stack<Integer> aStack) {
		return sort(aStack, true);
	}
	
	private Stack<Integer> sort(Stack<Integer> aStack, boolean largestOnTop) {

		if (aStack == null) {
			return new Stack<Integer>();
		}

		int size = aStack.size();
		if (size == 0 || size == 1) {
			return aStack;
		}
		
		int halfSize = size / 2;
		Stack<Integer> h1 = new Stack<Integer>();
		Stack<Integer> h2 = aStack;
		for (int i=0; i < halfSize; i++) {
			h1.push(h2.pop());
		}
			
		return merge(sort(h1, !largestOnTop), sort(h2, !largestOnTop), largestOnTop);
	}


	private Stack<Integer> merge(Stack<Integer> s1, Stack<Integer> s2, boolean largestOnTop)
	{
		Stack<Integer> merged  = new Stack<Integer>();

		while (!s1.isEmpty() && !s2.isEmpty()) {

			boolean s1GtS2 = (s1.peek().intValue() > s2.peek().intValue());
			if (largestOnTop) {
				if (s1GtS2) {
					merged.push(s1.pop());
				}
				else {
					merged.push(s2.pop());
				}
			}
			else {
				if (s1GtS2) {
					merged.push(s2.pop());
				}
				else {
					merged.push(s1.pop());
				}
			}
		}
		
		while (!s1.isEmpty()) {
			merged.push(s1.pop());
		}
		
		while (!s2.isEmpty()) {
			merged.push(s2.pop());
		}
		
		return merged;
	}

	
	public static void main(String[] args) {

		StackSort stackSort = new StackSort();
//		testNumbersSort();
//		testBackwardSort();
		for (int i=0; i < 200; i++) {
			Stack<Integer> aStack = new Stack<Integer>();
			for (int j=i; j > 0; j--) {
				aStack.push(new Integer(j));
			}
			aStack = stackSort.sort(aStack);

			System.out.println(aStack.toString());
		}
/*	
		aStack.push(new Integer(7));
		aStack.push(new Integer(1));
		aStack.push(new Integer(6));
//		aStack.push(new Integer(12));
//		aStack.push(new Integer(5));
		aStack.push(new Integer(4));
		aStack.push(new Integer(8));
*/	
	}

}