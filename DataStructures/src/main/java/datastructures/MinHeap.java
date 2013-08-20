package datastructures;

import java.util.ArrayList;
import java.util.List;

public class MinHeap<T extends Comparable<? super T>> {

	private ArrayList<T> nodes;
	
	public MinHeap() {
		this(10);
	}

	public MinHeap(int capacity) {
		nodes = new ArrayList<T>(capacity);
	}
	
	public MinHeap(List<T> nodes) {
		
		if (nodes == null) {
			return;
		}

		this.nodes = new ArrayList<T>();
		this.nodes.addAll(nodes);
		rebuildHeap();
	}
	
	
	/** 
	 * Ensure all nodes adhere to min-heap rules. This method must visit 
	 * all N nodes and, for each node, makes log N swaps to restore the 
	 * heap structure.  As such, it runs in O(N log N) time.
	 */
	private void rebuildHeap() {

		if (nodes == null || nodes.size() <= 1) {
			return;
		}

		for (int pIdx=(nodes.size() - 2) / 2; pIdx >= 0; pIdx--) {
			heapify(pIdx);
		}
	}
	
	
	/**
	 * This method ensures that this is a valid min-heap.  Specifically, it assumes that 
	 * the entire heap is valid except for the subtree at the given parent index (pIdx).  
	 */
	private void heapify(int pIdx) {
		
		if (nodes == null || nodes.size() <= 1 || pIdx < 0 || pIdx >= nodes.size()) {
			return;
		}
		
		while (pIdx >= 0) {
			
			int mIdx = pIdx;
			int lIdx = pIdx * 2 + 1;
			int rIdx = pIdx * 2 + 2;
			T mNode  = nodes.get(mIdx);
			T lNode  = lIdx < nodes.size() ? nodes.get(lIdx) : null;
			T rNode  = rIdx < nodes.size() ? nodes.get(rIdx) : null;

			if (lNode != null && lNode.compareTo(mNode) < 0) {
				mIdx  = lIdx;
				mNode = lNode;
			}
			if (rNode != null && rNode.compareTo(mNode) < 0) {
				mIdx  = rIdx;
				mNode = rNode;
			}

//			System.out.println("pIdx: " + pIdx + "(" + nodes.get(pIdx) + ")  lIdx: " + lIdx + "(" + lNode + ")  rIdx:" + rIdx + "(" + rNode + ")  mIdx: " + mIdx + "(" + mNode + ")  nodes: " + nodes);
			if (mIdx != pIdx) {
				swap(mIdx, pIdx);
				pIdx = mIdx;
			}
			else {
				break;
			}
		}
	}
	
	
	/**
	 * Adds element T to the heap.
	 * 
	 * The heap is restored by moving element T up the tree
	 * until both of its children are larger (or empty).
	 * 
	 * @param element
	 */
	public void add(T element) {
		
		nodes.add(element);

		//L=2N+1, R=2N+2, P=(N-1)/2;
		int iIdx = nodes.size() - 1;
		int pIdx = (iIdx - 1) / 2;
		while (pIdx >= 0 && nodes.get(pIdx).compareTo(element) > 0) {
			swap(pIdx, iIdx);
			iIdx = pIdx;
			pIdx = (iIdx - 1) / 2;
		}
	}


	/**
	 * @return
	 */
	public T peek() {

		if (nodes == null || nodes.size() <=0 ) {
			return null;
		}
		
		return nodes.get(0);
	}
	

	/**
	 * @return
	 */
	public T remove() {

		if (nodes == null || nodes.size() <= 0) {
			return null;
		}
		
		T savedMin = nodes.get(0);
		T lastNode = nodes.get(nodes.size() - 1);
		nodes.set(0, lastNode);
		nodes.remove(nodes.size() - 1);
		heapify(0);
		
		return savedMin;
	}
	
	
	/**
	 * @param index1
	 * @param index2
	 */
	private void swap(int index1, int index2) {
		T temp = nodes.get(index1);
		nodes.set(index1, nodes.get(index2));
		nodes.set(index2, temp);
	}

	
	public boolean isEmpty() {
		return nodes.isEmpty();
	}
	
		
	public static void main(String[] args) {

		ArrayList<Integer> ints = new ArrayList<Integer>();
		MinHeap<Integer> heap = new MinHeap<Integer>();
		for (int i=8; i >= 0; i--) {
			ints.add(new Integer(i));
			heap.add(new Integer(i));
		}
		System.out.println(heap.nodes);
		System.out.println("Min: " + heap.remove());
		System.out.println(heap.nodes);
		System.out.println("Min: " + heap.remove());
		System.out.println(heap.nodes);
		System.out.println("Min: " + heap.peek());
		System.out.println(heap.nodes);

		MinHeap<Integer> heap2 = new MinHeap<Integer>(ints);
			System.out.println("Heap2: " + heap2.nodes);
	}
	
	
}