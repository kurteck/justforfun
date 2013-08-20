package datastructures.v2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public class Hashtable<K,V> {
	
	protected static int   DEFAULT_CAPACITY       = 16;    // 16 buckets
	protected static int   DEFAULT_EXPAND_FACTOR  = 2;     // double capacity on rebuild
	protected static double DEFAULT_LOAD_FACTOR   = 0.75d; // Rebuild at 75% capacity
	
	
	private int size;
	private int capacity;
	private int expandFactor;
	private double loadFactor;
	private List<LinkedList<BucketNode>> buckets;
	

	public Hashtable() {
		this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR, DEFAULT_EXPAND_FACTOR);
	}

	public Hashtable(int capacity, double loadFactor, int expandFactor) {
		
		this.size = 0;
		this.capacity = capacity;
		this.loadFactor = loadFactor;
		this.expandFactor = expandFactor;
		
		initBuckets(DEFAULT_CAPACITY);
	}
		
	
	/**
	 * Returns the element in the map with the given key
	 * or null if the element does not exist.
	 * 
	 * @param key
	 * @return
	 */
	public V get(K key) {
		
		LinkedList<BucketNode> bucket = getBucket(key);
		Iterator<BucketNode> bucketIter = bucket.iterator();
		while (bucketIter.hasNext()) {
			BucketNode aNode = bucketIter.next();
			if (aNode.key.equals(key)) {
				return aNode.value;
			}
		}
		
		return null;
	}
	

	/**
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		
		if (get(key) == null) {
			if (size >= (capacity * loadFactor)) {
				expand();
			}
			
			LinkedList<BucketNode> bucket = getBucket(key);
			bucket.addFirst(new BucketNode(key, value));
			size++;
		}
		
	}
	
	
	private void expand() {

		// Save old buckets and data
		List<LinkedList<BucketNode>> oldBuckets = buckets;
		int oldSize = oldBuckets.size();
		
		// Create new data structures
		size = 0;
		capacity = capacity * expandFactor;
		initBuckets(capacity);

		// And copy in old data
		for (int i=0; i < oldSize; i++) {

			LinkedList<BucketNode> oldBucket = oldBuckets.get(i);
			Iterator<BucketNode> oldBucketIter = oldBucket.iterator();
			while (oldBucketIter.hasNext()) {
				BucketNode oldBucketNode = oldBucketIter.next();
				put(oldBucketNode.key, oldBucketNode.value);
			}
		}

	}
	
		
	private LinkedList<BucketNode> getBucket(K key) {
		int bucketNum = Math.abs(key.hashCode()) % buckets.size();
		return buckets.get(bucketNum);
	}

	
	private void initBuckets(int capacity) {
		
		buckets = new ArrayList<LinkedList<BucketNode>>(capacity);
		for (int i=0; i < capacity; i++) {
			buckets.add(new LinkedList<BucketNode>());
		}
	}
	

	/**
	 * For debugging & testing
	 */
	public void printBuckets() {

		for (int i=0; i < buckets.size(); i++) {
			LinkedList<BucketNode> aBucket = buckets.get(i);
			System.out.print("Bucket #" + i + " [");
			for (BucketNode aNode : aBucket) {
				System.out.print("(" + aNode.key + "=" + aNode.value + "),");
			}
			System.out.print("]");
			System.out.println();
		}
		
	}
	
	
	/**
	 * Inner class that contains bucket keys and values
	 */
	private class BucketNode {
		
		public K key;
		public V value;
		
		public BucketNode(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
}