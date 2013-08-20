package datastructures;

public class Hasharray {
	
	protected static int    DEFAULT_CAPACITY      = 16;    // 16 ArrayNodes
	protected static int    DEFAULT_ARRAY_SIZE    = 2;     // # of KV pairs per ArrayNode
	protected static int    DEFAULT_EXPAND_FACTOR = 2;     // double capacity on rebuild
	protected static double DEFAULT_LOAD_FACTOR   = 0.60d; // Rebuild at 75% capacity
	
	
	private int size;
	private int capacity;
	private int expandFactor;
	private int threshold;
	private double loadFactor;
	private ArrayNode[] arrayNodes;
	

	public Hasharray() {
		this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR, DEFAULT_EXPAND_FACTOR);
	}
	
	public Hasharray(int capacity) {
		this(capacity,  DEFAULT_LOAD_FACTOR, DEFAULT_EXPAND_FACTOR);
	}

	public Hasharray(int capacity, double loadFactor, int expandFactor) {
		
		this.size = 0;
		this.capacity = capacity;
		this.loadFactor = loadFactor;
		this.expandFactor = expandFactor;
		this.threshold = (int)(capacity * loadFactor);
		this.arrayNodes = new ArrayNode[capacity];
	}
		
	
	/**
	 * Returns the element in the map with the given key
	 * or null if the element does not exist.
	 * 
	 * @param key
	 * @return
	 */
	public Object get(Object key) {
		
		ArrayNode aNode = arrayNodes[getBucketNumber(key)];
		while (aNode != null) {
			for (int i=0; i < aNode.size; i++) {
				if (aNode.elems[i].key.equals(key)) {
					return aNode.elems[i].val;
				}
			}
			aNode = aNode.next;
		}
		
		return null;
	}
	

	public void put(Object key, Object val) {

		// expand array if we are too big
		if (size > threshold) {
			expand();
		}

		// Search bucket to see if this is a duplicate
		int bucketNumber = getBucketNumber(key);
		ArrayNode aNode = arrayNodes[bucketNumber];
		ArrayNode first = aNode;
		while (aNode != null) {
			for (int i=0; i < aNode.size; i++) {
				if (aNode.elems[i].key.equals(key)) {
					return;
				}
			}
			aNode = aNode.next;
		}

		// Add element if it is not a duplicate
		if (first == null) {
			arrayNodes[bucketNumber] = new ArrayNode(key, val);
		}
		else {
			arrayNodes[bucketNumber] = first.add(key,val);
		}
		
		size++;
	}

	
	private void expand() {

		// Save old buckets and data
		ArrayNode[] oldArrayNodes = arrayNodes;
		
		// Create new data structures
		this.size = 0;
		this.capacity = capacity * expandFactor;
		this.threshold = (int)(capacity * loadFactor);
		this.arrayNodes = new ArrayNode[capacity];

		// And copy in old data
		for (int i=0; i < oldArrayNodes.length; i++) {

			ArrayNode aNode = oldArrayNodes[i];
			while (aNode != null) {
				for (int j=0; j < aNode.size; j++) {
					put(aNode.elems[j].key, aNode.elems[j].val);
				}
				aNode = aNode.next;
			}
		}
	}
	
	
	/**
	 * Translates hashcode to bucket based upon current capacity
	 * @param key
	 * @return
	 */
	private int getBucketNumber(Object key) {
		return Math.abs(key.hashCode()) % arrayNodes.length;
	}
	
	
	/**
	 * For debugging & testing
	 */
	public void print() {

		for (int i=0; i < arrayNodes.length; i++) {
			ArrayNode aNode = arrayNodes[i];
			System.out.print("Array #" + i + " [");
			while (aNode != null) {
				for (int j=0; j < DEFAULT_ARRAY_SIZE; j++) {
					System.out.print("(" + aNode.elems[j].key + "=" + aNode.elems[j].val + "),");
				}
				aNode = aNode.next;
			}			
			System.out.print("]");
			System.out.println();
		}
		
	}
	
	
	/**
	 * Inner class that contains bucket keys and values
	 */
	private static class ArrayNode {
		
		int size;
		Element[] elems;
		ArrayNode next;
		
		public ArrayNode(Object key, Object val) {
			this.size = 1;
			this.elems = new Element[DEFAULT_ARRAY_SIZE];
			this.next = null;
			
			elems[0] = new Element();
			elems[0].key = key;
			elems[0].val = val;
		}

		public ArrayNode add(Object key, Object val) {
			
			if (size >= DEFAULT_ARRAY_SIZE) {
				ArrayNode newNode = new ArrayNode(key,val);
				newNode.next = this;
				return newNode;
			}
			else {
				elems[size] = new Element();
				elems[size].key = key;
				elems[size].val = val;
				size++;
				return this;
			}
		}
		
		private static class Element {
			Object key;
			Object val;
		}
	}
	
}