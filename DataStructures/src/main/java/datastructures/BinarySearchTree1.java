package datastructures;
public class BinarySearchTree1<E extends Comparable<E>> {
	
	int size = 0;
	TreeNode root = null;
	
	// OR - "private static class TreeNode<E>"
	private class TreeNode {
		
		E element = null;
		TreeNode left   = null;
		TreeNode right  = null;
		
		public TreeNode(E element) {
			this.element = element;
		}
	}
	
	
	/**
	 * Inserts element into the tree
	 * 
	 * Runtime:
	 * Worst case is O(N)     when tree degenerates into a linked list
	 * Best  case is O(log N) when tree is balanced
	 *  
	 * @param element
	 */
	public void insert(E element) {

		if (root == null) {
			root = new TreeNode(element);
			size++;
		}
		else {
			insert(root, element);
		}
	}
	
	private void insert(TreeNode parent, E element) {
		
		if (element.compareTo(parent.element) <= 0) {
			if (parent.left == null) {
				parent.left = new TreeNode(element);
				size++;
			}
			else {
				insert(parent.left, element);
			}
		}
		else {
			if (parent.right == null) {
				parent.right = new TreeNode(element);
				size++;
			}
			else {
				insert(parent.right, element);
			}
		}
		
	}
	
	
	/**
	 * Returns the element in the tree that is equivalent to the given element (i.e. compareTo = 0).  
	 * Note this may not be the same as ".equals" which evaluates to the same object instance.
	 */
	public E find_DFS_R(E element) {
		return find_DFS_R_Helper(root, element);
	}
	
	private E find_DFS_R_Helper(TreeNode parent, E element) {
		
		if (parent == null) {
			return null;
		}
		
		int cmp = element.compareTo(parent.element); 
		if (cmp == 0) {
			return parent.element;
		}
		else if (cmp < 0) {
			return find_DFS_R_Helper(parent.left, element);
		}
		else if (cmp > 0) {
			return find_DFS_R_Helper(parent.right, element);
		}
		
		return null;
	}
	

	/**
	 * Performs a breath-first search on this tree for the given element.
	 * 
	 * @param element
	 * @return
	 */
	public E find_BFS_I(E element) {
		
		LinkedList<TreeNode> children = new LinkedList<TreeNode>(); // Queue
		children.addToTail(root);

		TreeNode aChild = null;
		while ((aChild = children.removeHead()) != null) {

			if (element.compareTo(aChild.element) == 0) {
				return aChild.element;
			}

			// Adding new children to end ensures that we search this level before going deeper
			if (aChild.left != null) {
				children.addToTail(aChild.left);
			}
			if (aChild.right != null) {
				children.addToTail(aChild.right);
			}
		}
		
		return null;
		
	}
	
	
	
	
	public void printInOrder() {
		printInOrder(root);
	}
	
	private void printInOrder(TreeNode parent) {
		if (parent == null) {
			return;
		}

		printInOrder(parent.left);
		System.out.print(parent.element + ",");
		printInOrder(parent.right);
	}
	
	
	public static void main(String[] args) {

		testStringTree();
		testIntegerTree();
	}
	
	private static void testStringTree() {

		System.out.println("String Tree Test");
		BinarySearchTree1<String> bst = new BinarySearchTree1<String>();

		String twenty = new String("twenty");
		bst.insert(new String("one"));
		bst.insert(new String("two"));
		bst.insert(twenty);
		bst.insert(new String("four"));
		bst.insert(new String("five"));
		
		System.out.println("Size [" + bst.size + "]");
		bst.printInOrder();
		System.out.println();
		
		System.out.println("Searching...");
		String toFind1 = new String("twenty");
		String toFind2 = new String("twenty");
		String result1 = bst.find_DFS_R(toFind1);
		String result2 = bst.find_BFS_I(toFind2);
		
		System.out.println("[" + System.identityHashCode(toFind1) + "] vs [" + System.identityHashCode(result1) + "]");
		System.out.println("[" + System.identityHashCode(toFind2) + "] vs [" + System.identityHashCode(result2) + "]");
		System.out.println("");
	}
	

	private static void testIntegerTree() {

		System.out.println("Integer Tree Test");
		BinarySearchTree1<Integer> bst = new BinarySearchTree1<Integer>();
		Integer twenty = new Integer(20);
		bst.insert(new Integer(5));
		bst.insert(new Integer(2));
		bst.insert(twenty);
		bst.insert(new Integer(11));
		bst.insert(new Integer(17));
		bst.insert(new Integer(-1));
		bst.insert(new Integer(9));
		
		System.out.println("Size [" + bst.size + "]");
		bst.printInOrder();
		System.out.println();
		
		System.out.println("Searching...");
		Integer toFind1 = new Integer(20);
		Integer toFind2 = new Integer(20);
		Object result1 = (Object)bst.find_DFS_R(toFind1);
		Object result2 = (Object)bst.find_BFS_I(toFind2);
		
		System.out.println("[" + System.identityHashCode(toFind1) + "] vs [" + System.identityHashCode(result1) + "]");
		System.out.println("[" + System.identityHashCode(toFind2) + "] vs [" + System.identityHashCode(result2) + "]");
		System.out.println("");
	}

}