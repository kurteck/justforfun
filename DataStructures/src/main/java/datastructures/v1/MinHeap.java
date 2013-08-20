package datastructures.v1;

import java.util.ArrayList;

public class MinHeap<T extends Comparable<? super T>> {

	private ArrayList<T> nodes;
	
	public MinHeap() {
		this(10);
	}

	public MinHeap(int capacity) {
		nodes = new ArrayList<T>(capacity);
	}
	
	public MinHeap(ArrayList<T> elements) {
		nodes = makeHeap(elements);
	}
	
	
	private ArrayList<T> makeHeap(ArrayList<T> nodes) {

		int numNodes = nodes.size();
		for (int pIdx=(numNodes-1)/2; pIdx >=0; pIdx--) {
			
			int mIdx = pIdx;
			int lIdx = pIdx * 2 + 1;
			int rIdx = pIdx * 2 + 2;
			T mNode  = nodes.get(mIdx);
			T lNode  = lIdx < (numNodes - 1) ? nodes.get(lIdx) : null;
			T rNode  = rIdx < (numNodes - 1) ? nodes.get(rIdx) : null;

			if (lNode != null && lNode.compareTo(mNode) < 0) {
				mIdx  = lIdx;
				mNode = lNode;
			}
			if (rNode != null && rNode.compareTo(mNode) < 0) {
				mIdx  = rIdx;
				mNode = rNode;
			}

			if (mIdx != pIdx) {
				T temp = nodes.get(mIdx);
				nodes.set(mIdx, nodes.get(pIdx));
				nodes.set(pIdx, temp);

				pIdx = mIdx;
			}		
		}
		
		return nodes;
		
	}
	
	
	/**
	 * Restore the min-heap property.  When this method is called, the min-heap
	 * property holds everywhere, except possibly at node i and its children.
	 * When this method returns, the min-heap property holds everywhere.
	 * @param a the list to sort
	 * 
	 * @param nodes
	 * @param i
	 */
	private void minHeapify(int i) {
		
		int numNodes = nodes.size();
		int pIdx = i;
		while (pIdx >= 0) {
			
			int mIdx = pIdx;
			int lIdx = pIdx * 2 + 1;
			int rIdx = pIdx * 2 + 2;
			T mNode  = nodes.get(mIdx);
			T lNode  = lIdx < (numNodes - 1) ? nodes.get(lIdx) : null;
			T rNode  = rIdx < (numNodes - 1) ? nodes.get(rIdx) : null;

			if (lNode != null && lNode.compareTo(mNode) < 0) {
				mIdx  = lIdx;
				mNode = lNode;
			}
			if (rNode != null && rNode.compareTo(mNode) < 0) {
				mIdx  = rIdx;
				mNode = rNode;
			}

//			System.out.println("pIdx: " + pIdx + " lIdx: " + lIdx + " rIdx: " + rIdx + " mIdx: " + mIdx + " nodes: " + nodes);
			if (mIdx != pIdx) {
				swap(mIdx, pIdx);
				pIdx = mIdx;
			}
			else {
				break;
			}
		}
	}
	
	
	//L=2N+1, R=2N+2, P=(N-1)/2;
	public void add(T element) {
		
//		System.out.println("Adding: " + element);
		nodes.add(element);

		int insertIdx = nodes.size() - 1;
		int parentIdx = (insertIdx - 1)/ 2;
//		System.out.println("InsertIdx: " + insertIdx + " ParentIdx: " +parentIdx + " NumElemes: " + nodes.size() + " Nodes: " + nodes);
		while (parentIdx >= 0 && nodes.get(parentIdx).compareTo(element) > 0) {
			swap(parentIdx, insertIdx);
			insertIdx = parentIdx;
			parentIdx = (insertIdx - 1) / 2;
//			System.out.println("InsertIdx: " + insertIdx + " ParentIdx: " +parentIdx + " NumElemes: " + nodes.size() + " Nodes: " + nodes);
		}
	}


	public T peekMin() {
		if (nodes == null || nodes.size() <= 0) {
			return null;
		}
		return nodes.get(0);
	}
	

	public T removeMin() {

		if (nodes == null || nodes.size() <= 0) {
			return null;
		}
		else if (nodes.size() == 1) {
			return nodes.remove(0);
		}
		
		// Replace root with last node
		int numNodes = nodes.size();
		T savedMin = nodes.get(0);
//		System.out.println("Saved minimum value is: " + savedMin + "       nodes: " + nodes);
		nodes.set(0, nodes.get(numNodes - 1));
		nodes.remove(numNodes - 1);
		numNodes--;
		
		// And push it element down until all children are bigger
		int pIdx = 0;
		while (pIdx >= 0) {
			
			int mIdx = pIdx;
			int lIdx = pIdx * 2 + 1;
			int rIdx = pIdx * 2 + 2;
			T mNode  = nodes.get(mIdx);
			T lNode  = lIdx < (numNodes - 1) ? nodes.get(lIdx) : null;
			T rNode  = rIdx < (numNodes - 1) ? nodes.get(rIdx) : null;

			if (lNode != null && lNode.compareTo(mNode) < 0) {
				mIdx  = lIdx;
				mNode = lNode;
			}
			if (rNode != null && rNode.compareTo(mNode) < 0) {
				mIdx  = rIdx;
				mNode = rNode;
			}

//			System.out.println("pIdx: " + pIdx + " lIdx: " + lIdx + " rIdx: " + rIdx + " mIdx: " + mIdx + " nodes: " + nodes);
			if (mIdx != pIdx) {
				swap(mIdx, pIdx);
				pIdx = mIdx;
			}
			else {
				break;
			}
		}
		
		return savedMin;
	}
	
	
	
	private void swap(int index1, int index2) {
		T temp = nodes.get(index1);
		nodes.set(index1, nodes.get(index2));
		nodes.set(index2, temp);
	}

	
	public static void main(String[] args) {

		ArrayList<Integer> ints = new ArrayList<Integer>();
		MinHeap<Integer> heap = new MinHeap<Integer>();
		for (int i=8; i >= 0; i--) {
			ints.add(new Integer(i));
			heap.add(new Integer(i));
		}
		System.out.println(heap.nodes);
		System.out.println("Min: " + heap.removeMin());
		System.out.println(heap.nodes);
		System.out.println("Min: " + heap.removeMin());
		System.out.println(heap.nodes);
		System.out.println("Min: " + heap.peekMin());
		System.out.println(heap.nodes);
		
		MinHeap<Integer> heap2 = new MinHeap<Integer>(ints);
		System.out.println("Heap2: " + heap2.nodes);
	}
	
}