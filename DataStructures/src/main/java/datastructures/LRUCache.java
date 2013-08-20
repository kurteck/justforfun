package datastructures;

import java.util.HashMap;

public class LRUCache<K,V> {
	
	private int maxEntries;
	private int numEntries;
	private CacheEntry head;
	private CacheEntry tail;
	private HashMap<K, CacheEntry> cacheEntryMap;
	
	public LRUCache(int maxEntries) {
		this.numEntries = 0;
		this.maxEntries = maxEntries > 0 ? maxEntries : 10;
		this.head = null;
		this.tail = null;
		this.cacheEntryMap = new HashMap<K, CacheEntry>();
	}

	
	/**
	 * @param entry
	 */
	public V get(K key) {
		
		CacheEntry entry = cacheEntryMap.get(key);
		if (entry != null) {
			moveToFrontOfList(entry);
			return entry.val;
		}
		else {
			return null;
		}
	}
	
	
	/**
	 * @param entry
	 */
	public void add(K key, V value) {
		
		// Make this the most recently used entry if it already exists
		if (cacheEntryMap.containsKey(key)) {
			CacheEntry entry = cacheEntryMap.get(key);
			moveToFrontOfList(entry);
			return;
		}

		// Remove old entries we have enough room to fit this new one
		while (numEntries >= maxEntries) {
			removeEntry(tail);
			cacheEntryMap.remove(tail.key); // performance cost?
		}
		
		// Now add the entry to the LRUList and the lookup map
		CacheEntry entry = new CacheEntry(key, value);
		cacheEntryMap.put(key, entry);
		addEntry(entry);
	}
	
	
	/**
	 * @param entry
	 */
	private void moveToFrontOfList(CacheEntry entry) {
		if (entry == null || numEntries < 2 || head == entry) {
			return;
		}
		removeEntry(entry);
		addEntry(entry);
	}

	
	/**
	 * @param entry
	 */
	private void removeEntry(CacheEntry entry) {
		if (entry == null) {
			return;
		}
		if (tail == entry) {
			tail = entry.prev;
		}
		if (head == entry) {
			head = entry.next;
		}
		if (entry.prev != null) { 
			entry.prev.next = entry.next;
		}
		if (entry.next != null) {
			entry.next.prev = entry.prev;
		}
		numEntries--;
	}

	
	/**
	 * @param entry
	 */
	private void addEntry(CacheEntry entry) {
		if (entry == null) {
			return;
		}

		if (tail == null) {
			tail = entry;
		}
		if (head != null) {
			head.prev = entry;
		}
		entry.next = head;
		entry.prev = null;
		head = entry;
		numEntries++;
	}
	
	
	/**
	 * @param entry
	 */
	public void print() {
		
		System.out.printf("%-5s", numEntries);
		printLRU();
		System.out.println();
	}
	
	private void printLRU() {
		
		System.out.print("Head-->");
		CacheEntry runner = head;
		while (runner != null) {
			System.out.print(runner);
			runner = runner.next;
			if (runner != null) {
				System.out.print("->");
			}
		}
		System.out.print("--||");
	}
	
	
	
	private class CacheEntry {

		K key;
		V val;
		CacheEntry next;
		CacheEntry prev;
		
		public CacheEntry(K key, V val) {
			this.key = key;
			this.val = val;
			next = null;
			prev = null;
		}
		
		public String toString() {
			return "(" + key.toString() + "," + val.toString() + ")";
		}
	}

	
	public static void main(String[] args) {
		
		LRUCache<String, Integer> cache = new LRUCache<String, Integer>(10);

		for (int i=0; i < 15; i++) {
			Integer anInt = new Integer(i);
			cache.add(anInt.toString(), anInt);
			cache.print();
		}
		
		cache.get("8");
		cache.print();
		cache.get("3");
		cache.print();
		cache.get("11");
		cache.print();
		
	}
	
}