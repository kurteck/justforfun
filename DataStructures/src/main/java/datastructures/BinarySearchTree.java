package datastructures;
import java.util.ArrayList;
import java.util.LinkedList;

public class BinarySearchTree<E extends Comparable<E>> {
	
	int size  = 0;
	E element = null;
	BinarySearchTree<E> left  = null;
	BinarySearchTree<E> right = null;
	
	public BinarySearchTree() {
		this.size = 0;
		this.element = null;
	}

	public BinarySearchTree(E element) {
		this.size = 1;
		this.element = element;
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
		insert(this, element);
	}
	
	private void insert(BinarySearchTree<E> parent, E element) {
		
		if (parent.element == null) {
			parent.element = element;
			size++;
		}
		else if (element.compareTo(parent.element) <= 0) {
			if (parent.left == null) {
				parent.left = new BinarySearchTree<E>(element);
				size++;
			}
			else {
				insert(parent.left, element);
			}
		}
		else {
			if (parent.right == null) {
				parent.right = new BinarySearchTree<E>(element);
				size++;
			}
			else {
				insert(parent.right, element);
			}
		}
		
	}
	
	
	/**
	 * Returns the first element in the tree that is equivalent to the given element (i.e. compareTo = 0).  
	 * Note this may not be the same object instance as the one being passed in to the function.
	 */
	public E find_DFS_R(E element) {
		return find_DFS_R_Helper(this, element);
	}
	
	private E find_DFS_R_Helper(BinarySearchTree<E> parent, E element) {
		
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
		
		LinkedList<BinarySearchTree<E>> children = new LinkedList<BinarySearchTree<E>>(); // i.e. a Queue
		children.add(this);

		BinarySearchTree<E> aChild = null;
		while ((aChild = children.remove()) != null) {

			if (element.compareTo(aChild.element) == 0) {
				return aChild.element;
			}

			// Adding new children to end ensures that we search this level before going deeper
			if (aChild.left != null) {
				children.add(aChild.left);
			}
			if (aChild.right != null) {
				children.add(aChild.right);
			}
		}
		
		return null;
		
	}
	
	
	/**
	 * 
	 */
	public void printInOrder() {
		printInOrder(this);
	}
	
	private void printInOrder(BinarySearchTree<E> parent) {
		if (parent == null) {
			return;
		}

		printInOrder(parent.left);
		System.out.print(parent.element + ",");
		printInOrder(parent.right);
	}
	
	
	
	/**
	 * Returns the maximum height (depth) of this tree
	 * @return
	 */
	public int getHeight() {
		return getHeight(this, 0);
	}
	
	/**
	 * Returns the maximum height of the tree represented by the given node
	 * @return
	 */
	public int getHeight(BinarySearchTree<E> root) {
		return getHeight(root, 0);
	}

	/**
	 * @param root
	 * @param height
	 * @return
	 */
	private int getHeight(BinarySearchTree<E> root, int height) {

		if (root == null) {
			return 0;
		}
		
		int lHeight = (root.left  == null) ? 0 : getHeight(root.left, height) + 1;
		int rHeight = (root.right == null) ? 0 : getHeight(root.right, height) + 1;
		
		return Math.max(lHeight, rHeight);
	}
	
	
	/**
	 * Problem 4.3
	 * @param sortedList
	 * @return
	 */
	public static <E extends Comparable<E>> BinarySearchTree<E> createMinimalBST(ArrayList<E> sortedList) {
		if (sortedList == null || sortedList.size() == 0) {
			return new BinarySearchTree<E>();
		}

		return createMinimalBST(sortedList, 0, sortedList.size() - 1);
	}
	
	private static <E extends Comparable<E>> BinarySearchTree<E> createMinimalBST(ArrayList<E> list, int start, int end) {

		if (start > end) {
			return null;
		}
		
		int mid = (start + end) / 2;

		BinarySearchTree<E> root = new BinarySearchTree<E>(list.get(mid));
		root.left  = BinarySearchTree.createMinimalBST(list, start, mid-1);
		root.right = BinarySearchTree.createMinimalBST(list, mid+1, end);
		
		return root;
	}
	
	
	public static <E extends Comparable<E>> ArrayList<LinkedList<BinarySearchTree<E>>> toLinkedLists(BinarySearchTree<E> root) {
		
		ArrayList<LinkedList<BinarySearchTree<E>>> lists = new ArrayList<LinkedList<BinarySearchTree<E>>>();
		if (root == null) {
			return lists;
		}
		
		LinkedList<BinarySearchTree<E>> parents = new LinkedList<BinarySearchTree<E>>();
		parents.add(root);
		while (!parents.isEmpty()) {
			lists.add(parents);
			parents = getChildren(parents);
		}
		
		return lists;
	}

	private static <E extends Comparable<E>> LinkedList<BinarySearchTree<E>> getChildren(LinkedList<BinarySearchTree<E>> parents) {
		
		LinkedList<BinarySearchTree<E>> children = new LinkedList<BinarySearchTree<E>>();
		for (BinarySearchTree<E> parent : parents) {

			if (parent.left != null) {
				children.add(parent.left);
			}
			if (parent.right != null) {
				children.add(parent.right);
			}
		}
		
		return children;
	}
	
	
	/**
	 * 
	 * @param node1
	 * @param node2
	 * @return
	 */
	public BinarySearchTree<E> findCommonAncestor(BinarySearchTree<E> node1, BinarySearchTree<E> node2) {
	
		if (node1 == null || node2 == null) {
			return null;
		}
		else {
			return findCommonAncestorHelper(this, node1, node2);
		}
	}

	
	private BinarySearchTree<E> findCommonAncestorHelper(BinarySearchTree<E> root, BinarySearchTree<E> node1, BinarySearchTree<E> node2)
	{
		if (root == null) {
			return null;
		}
		
		BinarySearchTree<E> lAnc = findCommonAncestorHelper(root.left, node1, node2);
		BinarySearchTree<E> rAnc = findCommonAncestorHelper(root.right, node1, node2);

		// This is the common ancestor if we match node1 or node2.  
		// Note the highest ancestor in the tree will always be returned
		if (root == node1 || root == node2) {
			return root;
		}
		// This is the common ancestor when node1 and node2 are split in left/right subtrees
		else if (lAnc != null && rAnc != null) { 
			return root;
		}
		// Otherwise, return our previous knowledge...
		// when lAnc is not null or rAnc is not null then we have found one node in the tree
		// when lAnc and rAnc are both null then we haven't found any nodes yet.
		else {
			if (lAnc != null) {
				return lAnc;
			}
			else {
				return rAnc;
			}
		}
	}
	

	/**
	 * Returns the first Node in this tree that has a value matching the given element.
	 * This method is for internal use only as API clients should use this class as a 
	 * container for some another data structure <E>.
	 * 
	 * @param element
	 * @return
	 */
	protected BinarySearchTree<E> findNode_DFS_R(E element) {
		return findNode_DFS_R(this, element);
	}

	private BinarySearchTree<E> findNode_DFS_R(BinarySearchTree<E> root, E element) {
		
		if (root == null) {
			return null;
		}
		
		int cmp = element.compareTo(root.element);
		if (cmp == 0) {
			return root;
		}
		else if (cmp < 0) {
			return findNode_DFS_R(root.left, element);
		}
		else {
			return findNode_DFS_R(root.right, element);
		}
	}

	
	/**
	 * 
	 * This method prints all paths to the given sum given a binary tree containing
	 * integer values at each node.
	 * 
	 * @param root
	 */
	public void pathsToSum(BinarySearchTree<Integer> root, int targetSum) {
		
		if (root == null) {
			return;
		}
		
		pathsToSum(root, targetSum, 0, new LinkedList<BinarySearchTree<Integer>>());
		pathsToSum(root.left, targetSum);
		pathsToSum(root.right, targetSum);
	}
	

	private void pathsToSum(BinarySearchTree<Integer> root, int targetSum, int total, LinkedList<BinarySearchTree<Integer>> path) {
		
		if (root == null) {
			return;
		}

		// Update the path and total values using the given root node
		path.add(root);
		total += root.element.intValue();

		// Print the path if we have found our desired target sum
		if (total == targetSum) {
			printPath(path);
		}

		// Recurse to see if the left or right subtrees produce the target sum
		pathsToSum(root.left, targetSum, total, path);
		pathsToSum(root.right, targetSum, total, path);

		// And remember to remove root since the path will grow across recursive calls.
		path.removeLast();
	}

	private void printPath(LinkedList<BinarySearchTree<Integer>> path) {
		
		for (BinarySearchTree<Integer> aNode : path) {
			System.out.print(aNode.element);
			System.out.print(",");
		}
		System.out.println();
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		testStringTree();
		testIntegerTree();
		testCreateMinimumBST();
		testToLinkedLists();
		testFindCommonAncestor();
		testPathToSum();
	}
	
	private static void testStringTree() {

		System.out.println("**** String Tree Test ****");
		BinarySearchTree<String> bst = new BinarySearchTree<String>();

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

		System.out.println("**** Integer Tree Test ****");
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
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

	
	private static void testCreateMinimumBST() {

		System.out.println("**** Integer Tree Test ****");
		ArrayList<Integer> sortedList = new ArrayList<Integer>();
		for (int i=0; i < 1024; i++) {
			sortedList.add(new Integer(i));
		}
		
		BinarySearchTree<Integer> minimalBST = BinarySearchTree.createMinimalBST(sortedList);
		System.out.println("Height[" + minimalBST.getHeight() + "]");
		System.out.println("");
	}

	
	private static void testToLinkedLists() {
		
		System.out.println("**** To Linked List Test ****");
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i=0; i < 15; i++) {
			list.add(new Integer(i));
		}
		
		BinarySearchTree<Integer> bst = BinarySearchTree.createMinimalBST(list);
		System.out.println("Height[" + bst.getHeight() + "]");
		bst.printInOrder();
		System.out.println();
		
		ArrayList<LinkedList<BinarySearchTree<Integer>>> lists = BinarySearchTree.toLinkedLists(bst);
		for (LinkedList<BinarySearchTree<Integer>> aList : lists) {
			for (BinarySearchTree<Integer> aTreeNode : aList) {
				System.out.print(aTreeNode.element + ",");
			}
			System.out.println();
		}
		System.out.println("");
	}


	private static void testFindCommonAncestor() {
		
		System.out.println("**** Common Ancestor Test ****");
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i=0; i < 63; i++) {
			list.add(new Integer(i));
		}
		
		BinarySearchTree<Integer> bst   = BinarySearchTree.createMinimalBST(list);
		BinarySearchTree<Integer> node1 = bst.findNode_DFS_R(bst, new Integer(32));
		BinarySearchTree<Integer> node2 = bst.findNode_DFS_R(new Integer(46));
		BinarySearchTree<Integer> ancst = bst.findCommonAncestor(node1, node2);
		if (node1 == null || node2 == null) {
			System.out.print("Node1 or Node2 is not in this tree.");
		}
		else if (ancst == null) {
			System.out.print("The Common Ancestor of [" + node1.element + "] and [" + node2.element + "] is not in this tree.");
		}
		else {
			System.out.print("The Common Ancestor of [" + node1.element + "] and [" + node2.element + "] is [" + ancst.element + "]");
		}
		System.out.println("\n");
	}


	private static void testPathToSum() {
		
		System.out.println("**** Path To Sum Test ****");
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i=0; i < 15; i++) {
			list.add(new Integer(i % 10));
		}
		
		BinarySearchTree<Integer> bst = BinarySearchTree.createMinimalBST(list);
		bst.pathsToSum(bst, 6);
		System.out.println("");
	}
}