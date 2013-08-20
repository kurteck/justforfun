package datastructures;

public class Hashtable<K,V> {
	
	protected static int    DEFAULT_CAPACITY      = 16;    // 16 buckets
	protected static int    DEFAULT_EXPAND_FACTOR = 2;     // double capacity on rebuild
	protected static double DEFAULT_LOAD_FACTOR   = 0.75d; // Rebuild at 75% capacity
	
	
	private int size;
	private int capacity;
	private int expandFactor;
	private int threshold;
	private double loadFactor;
	private BucketNode[] buckets;
	

	public Hashtable() {
		this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR, DEFAULT_EXPAND_FACTOR);
	}
	
	public Hashtable(int capacity) {
		this(capacity,  DEFAULT_LOAD_FACTOR, DEFAULT_EXPAND_FACTOR);
	}

	public Hashtable(int capacity, double loadFactor, int expandFactor) {
		
		this.size = 0;
		this.capacity = capacity;
		this.loadFactor = loadFactor;
		this.expandFactor = expandFactor;
		this.threshold = (int)(capacity * loadFactor);
		this.buckets = new BucketNode[capacity];
	}
		
	
	/**
	 * Returns the element in the map with the given key
	 * or null if the element does not exist.
	 * 
	 * @param key
	 * @return
	 */
	public V get(K key) {
		
		BucketNode<K,V> aNode = buckets[getBucketNumber(key)];
		while (aNode != null) {
			if (aNode.key.equals(key)) {
				return aNode.value;
			}
			aNode = aNode.next;
		}
		
		return null;
	}
	

	public void put(K key, V value) {

		int bucketNumber = getBucketNumber(key);
		BucketNode aNode = buckets[bucketNumber];
		while (aNode != null) {
			if (aNode.key.equals(key)) {
				return;
			}
			aNode = aNode.next;
		}

		if (size > threshold) {
			expand();
			bucketNumber = getBucketNumber(key);
		}
		
		BucketNode<K,V> newNode = new BucketNode<K,V>(key, value);
		newNode.next = buckets[bucketNumber];
		buckets[bucketNumber] = newNode;
		size++;
	}

	

//	protected void put_v1(K key, V value) {
//		if (get(key) == null) {
//			if (size >= threshold) {
//				expand();
//			}
//			
//			// Add node to front of bucket list
//			int bucketNumber = getBucketNumber(key);
//			BucketNode<K,V> newNode = new BucketNode<K,V>(key, value);
//			newNode.next = buckets[bucketNumber];
//			buckets[bucketNumber] = newNode;
//			
//			size++;
//		}
//	}
	
	
	private void expand() {

		// Save old buckets and data
		BucketNode[] oldBuckets = buckets;
		
		// Create new data structures
		this.size = 0;
		this.capacity = capacity * expandFactor;
		this.threshold = (int)(capacity * loadFactor);
		this.buckets = new BucketNode[capacity];

		// And copy in old data
		for (int i=0; i < oldBuckets.length; i++) {

			BucketNode<K,V> aNode = oldBuckets[i];
			while (aNode != null) {
				put(aNode.key, aNode.value);
				aNode = aNode.next;
			}
		}
	}
	
	
	/**
	 * Translates hashcode to bucket based upon current capacity
	 * @param key
	 * @return
	 */
	private int getBucketNumber(K key) {
		return Math.abs(key.hashCode()) % buckets.length;
	}
	
	
	/**
	 * For debugging & testing
	 */
	public void printBuckets() {

		for (int i=0; i < buckets.length; i++) {
			BucketNode<K,V> aNode = buckets[i];
			System.out.print("Bucket #" + i + " [");
			while (aNode != null) {
				System.out.print("(" + aNode.key + "=" + aNode.value + "),");
				aNode = aNode.next;
			}			
			System.out.print("]");
			System.out.println();
		}
		
	}
	
	
	/**
	 * Inner class that contains bucket keys and values
	 */
	static class BucketNode<K,V> {
		K key;
		V value;
		BucketNode<K,V> next;
		
		public BucketNode(K key, V value) {
			this.key   = key;
			this.value = value;
			this.next  = null;
		}
	}
}