package datastructures;

public class HashtableOA {
	
	public static int    DEFAULT_EXPAND_FACTOR    = 2;
	public static int    DEFAULT_INITIAL_CAPACITY = 16;
	public static double DEFAULT_LOAD_FACTOR      = 0.75d;

	
	private int size;
	private int capacity;
	private int threshold;
	private double loadFactor;
	private HashElement[] elements;
	
	public HashtableOA() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}
	
	public HashtableOA(int capacity) {
		this(capacity, DEFAULT_LOAD_FACTOR);
	}
		
	public HashtableOA(int capacity, double loadFactor) {

		this.size = 0;
		this.capacity = capacity;
		this.loadFactor = loadFactor;
		this.threshold = (int)(capacity * loadFactor);
		this.elements = new HashElement[capacity];
	}

	
	public void put(Object key, Object obj) {

		if (size >= threshold) {
			expand();
		}
		
		int bucketNum = getBucketNum(key);
		for (int i = 0; i < capacity; i++) {
			bucketNum = (bucketNum + i) % capacity;
			HashElement anElem = elements[bucketNum]; 
			// insert if does not exist
			if (anElem == null) {
				elements[bucketNum] = new HashElement(key, obj);
				size++;
				return;
			}
			// duplicate
			else if (anElem.key.equals(key)) {
				return;
			}
		}
	}


	/**
	 * Retrieves the given
	 * @param key
	 * @return
	 */
	public Object get(Object key) {
		
		int bucketNum = getBucketNum(key);
		for (int i = 0; i < capacity; i++) {
			bucketNum = (bucketNum + i) % capacity;
			HashElement anElem = elements[bucketNum]; 
			if (anElem == null) {
				return null;
			}
			else if (anElem.key.equals(key)) {
				return anElem.value;
			}
		}
		
		return null;
	}
	
	
	/**
	 * 
	 */
	protected void expand() {

		HashElement oldElems[] = elements;
		
		this.size      = 0;
		this.capacity  = capacity * DEFAULT_EXPAND_FACTOR;
		this.threshold = (int)(capacity * loadFactor);
		this.elements  = new HashElement[capacity];
		
		for (int i=0; i < oldElems.length; i++) {
			HashElement anElem = oldElems[i];
			if (anElem != null) {
				put(anElem.key, anElem.value);
			}
		}
	}
	
	
	protected int getBucketNum(Object key) {
		return Math.abs(key.hashCode()) % capacity;
	}
	
	
	private class HashElement {
		
		Object key;
		Object value;
		
		public HashElement(Object key, Object value) {
			this.key = key;
			this.value = value;
		}
	}
	
}