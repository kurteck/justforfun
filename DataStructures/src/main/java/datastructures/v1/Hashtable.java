package datastructures.v1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public class Hashtable<K,V> {
	
	protected int DEFAULT_NUM_BUCKETS = 10;
	
	private double loadFactor;
	private List<LinkedList<BucketNode>> buckets;
	
	public Hashtable() {
		initBuckets(DEFAULT_NUM_BUCKETS);
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
			LinkedList<BucketNode> bucket = getBucket(key);
			bucket.addFirst(new BucketNode(key, value));
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

	
	
	private LinkedList<BucketNode> getBucket(K key) {
		int bucketNum = Math.abs(key.hashCode()) % buckets.size();
		return buckets.get(bucketNum);
	}

	
	private void initBuckets(int numBuckets) {
		
		buckets = new ArrayList<LinkedList<BucketNode>>(numBuckets);
		for (int i=0; i < numBuckets; i++) {
			buckets.add(new LinkedList<BucketNode>());
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