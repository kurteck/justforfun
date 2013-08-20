package datastructures;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

public class PerformanceTest {

	public static final int NUM_ITERS      = 20;
	public static final int INSERT_ELEMS   = 200000;
	public static final int RETRIEVE_ELEMS = 200000;
	public static final int SLEEP_TIME     = 10;
	
	public static Integer[] TEST_INTEGERS;
	public static String[]  TEST_STRINGS;
	
	public static void main(String[] args) {

		createTestData();
		try {
//			System.out.println("MyHashtable   Insert: " + testInsertPerformance_MyHashtable(NUM_ITERS, INSERT_ELEMS));
//			System.out.println("MyHasharray   Insert: " + testInsertPerformance_MyHasharray(NUM_ITERS, INSERT_ELEMS));
//			System.out.println("MyHashtable   Retrieve: " + testRetrievePerformance_MyHashtable(NUM_ITERS, RETRIEVE_ELEMS));
//			System.out.println("MyHasharray   Retrieve: " + testRetrievePerformance_MyHasharray(NUM_ITERS, RETRIEVE_ELEMS));
//
			System.out.println("Array         Insert: " + testInsertPerformance_Array(NUM_ITERS, INSERT_ELEMS));
			System.out.println("LinkedList    Insert: " + testInsertPerformance_LinkedList(NUM_ITERS, INSERT_ELEMS));
			System.out.println("ArrayList     Insert: " + testInsertPerformance_ArrayList(NUM_ITERS, INSERT_ELEMS));
			System.out.println("HashSet       Insert: " + testInsertPerformance_HashSet(NUM_ITERS, INSERT_ELEMS));
			System.out.println("TreeSet       Insert: " + testInsertPerformance_TreeSet(NUM_ITERS, INSERT_ELEMS));
			System.out.println("HashMap       Insert: " + testInsertPerformance_HashMap(NUM_ITERS, INSERT_ELEMS));
			System.out.println("MyHashtable   Insert: " + testInsertPerformance_MyHashtable(NUM_ITERS, INSERT_ELEMS));
			System.out.println("MyHashtableOA Insert: " + testInsertPerformance_MyHashtableOA(NUM_ITERS, INSERT_ELEMS));
			System.out.println("MyHasharray   Insert: " + testInsertPerformance_MyHasharray(NUM_ITERS, INSERT_ELEMS));
			System.out.println("MyLinkedList  Insert: " + testInsertPerformance_MyLinkedList(NUM_ITERS, INSERT_ELEMS)); 
			System.out.println("MyBST         Insert: " + testInsertPerformance_MyBST(NUM_ITERS, INSERT_ELEMS/10)); 

			System.out.println("Array         Retrieve: " + testRetrievePerformance_Array(NUM_ITERS, RETRIEVE_ELEMS));
			System.out.println("LinkedList    Retrieve: " + testRetrievePerformance_LinkedList(NUM_ITERS, RETRIEVE_ELEMS/10));
			System.out.println("ArrayList     Retrieve: " + testRetrievePerformance_ArrayList(NUM_ITERS, RETRIEVE_ELEMS));
			System.out.println("HashSet       Retrieve: " + testRetrievePerformance_HashSet(NUM_ITERS, RETRIEVE_ELEMS));
			System.out.println("TreeSet       Retrieve: " + testRetrievePerformance_TreeSet(NUM_ITERS, RETRIEVE_ELEMS));
			System.out.println("HashMap       Retrieve: " + testRetrievePerformance_HashMap(NUM_ITERS, RETRIEVE_ELEMS));
			System.out.println("MyHashtable   Retrieve: " + testRetrievePerformance_MyHashtable(NUM_ITERS, RETRIEVE_ELEMS));
			System.out.println("MyHashtableOA Retrieve: " + testRetrievePerformance_MyHashtableOA(NUM_ITERS, RETRIEVE_ELEMS));
			System.out.println("MyHasharray   Retrieve: " + testRetrievePerformance_MyHasharray(NUM_ITERS, RETRIEVE_ELEMS));
			System.out.println("MyBST         Retrieve: " + testRetrievePerformance_MyBST(NUM_ITERS, INSERT_ELEMS)); 
			System.out.println("MyLinkedList  Retrieve: " + testRetrievePerformance_MyLinkedList(NUM_ITERS, RETRIEVE_ELEMS/10)); 
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	
	/**
	 * Prepare test data now so that we do not expend time 
	 * searching for memory or creating objects doing the 
	 * performance tests.
	 */
	private static void createTestData() {
		
		TEST_INTEGERS = new Integer[INSERT_ELEMS];
//		TEST_STRINGS  = new String[NUM_ELEMS];
		for (int i=0; i < INSERT_ELEMS; i++) {
			TEST_INTEGERS[i] = new Integer(i);
//			TEST_STRINGS[i]  = TEST_INTEGERS[i].toString();
		}
	}

	
	
	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testInsertPerformance_Array(int iters, int elements)
	throws InterruptedException {
			
		Stats stats = new Stats();
		Integer[] integerArray = new Integer[elements];

		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				integerArray[i] = TEST_INTEGERS[i];
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}
	

	/**
	 * @param elements
	 * @return
	 */
	private static Stats testInsertPerformance_LinkedList(int iters, int elements)
	throws InterruptedException {
			
		Stats stats = new Stats();
		LinkedList<Integer> ll = new LinkedList<Integer>();

		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				ll.add(TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}

	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testInsertPerformance_ArrayList(int iters, int elements)
	throws InterruptedException {
			
		Stats stats = new Stats();
		ArrayList<Integer> al = new ArrayList<Integer>();

		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				al.add(TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}
	

	/**
	 * @param elements
	 * @return
	 */
	private static Stats testInsertPerformance_HashSet(int iters, int elements)
	throws InterruptedException {
			
		Stats stats = new Stats();
		HashSet<Integer> hs = new HashSet<Integer>();

		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				hs.add(TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}

	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testInsertPerformance_TreeSet(int iters, int elements)
	throws InterruptedException {
			
		Stats stats = new Stats();
		TreeSet<Integer> ts = new TreeSet<Integer>();

		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				ts.add(TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}

	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testInsertPerformance_HashMap(int iters, int elements)
	throws InterruptedException {
			
		Stats stats = new Stats();
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();

		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				hm.put(TEST_INTEGERS[i], TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}
	

	/**
	 * @param elements
	 * @return
	 */
	private static Stats testInsertPerformance_MyLinkedList(int iters, int elements)
	throws InterruptedException {
			
		Stats stats = new Stats();
		datastructures.LinkedList<Integer> ll = new datastructures.LinkedList<Integer>();

		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				ll.addToHead(TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}

	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testInsertPerformance_MyHashtable(int iters, int elements) 
	throws InterruptedException {
			
		Stats stats = new Stats();
		Hashtable<Integer, Integer> ht = new Hashtable<Integer, Integer>();

		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				ht.put(TEST_INTEGERS[i], TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}

	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testInsertPerformance_MyHashtableOA(int iters, int elements) 
	throws InterruptedException {
			
		Stats stats = new Stats();
		HashtableOA ht = new HashtableOA(elements);

		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				ht.put(TEST_INTEGERS[i], TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}
	

	/**
	 * @param elements
	 * @return
	 */
	private static Stats testInsertPerformance_MyHasharray(int iters, int elements) 
	throws InterruptedException {
			
		Stats stats = new Stats();
		Hasharray ha = new Hasharray();

		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				ha.put(TEST_INTEGERS[i], TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}

	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testInsertPerformance_MyBST(int iters, int elements) 
	throws InterruptedException {
			
		Stats stats = new Stats();
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();

		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				int intVal = (int)(Math.random() * INSERT_ELEMS);
				bst.insert(TEST_INTEGERS[intVal]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}
	

	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testRetrievePerformance_Array(int iters, int elements) 
	throws InterruptedException {
			
		Stats stats = new Stats();
		Integer[] integerArray = new Integer[elements];
		for (int i=0; i < elements; i++) {
			integerArray[i] = TEST_INTEGERS[i];
		}

		Integer anInt = null;
		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				anInt = integerArray[i];
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}
	

	/**
	 * @param elements
	 * @return
	 */
	private static Stats testRetrievePerformance_ArrayList(int iters, int elements)
	throws InterruptedException {
			
		Stats stats = new Stats();
		ArrayList<Integer> al = new ArrayList<Integer>();
		for (int i=0; i < elements; i++) {
			al.add(TEST_INTEGERS[i]);
		}

		Integer anInt = null;
		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				anInt = al.get(TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}

	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testRetrievePerformance_LinkedList(int iters, int elements)
	throws InterruptedException {
			
		Stats stats = new Stats();
		LinkedList<Integer> ll = new LinkedList<Integer>();
		for (int i=0; i < elements; i++) {
			ll.add(TEST_INTEGERS[i]);
		}

		Integer anInt = null;
		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				anInt = ll.get(TEST_INTEGERS[i]);
//				ll.contains(TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}

	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testRetrievePerformance_HashSet(int iters, int elements)
	throws InterruptedException {
			
		Stats stats = new Stats();
		HashSet<Integer> hs = new HashSet<Integer>();
		for (int i=0; i < elements; i++) {
			hs.add(TEST_INTEGERS[i]);
		}

		boolean exists;
		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				exists = hs.contains(TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}

	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testRetrievePerformance_TreeSet(int iters, int elements)
	throws InterruptedException {
			
		Stats stats = new Stats();
		TreeSet<Integer> ts = new TreeSet<Integer>();
		for (int i=0; i < elements; i++) {
			ts.add(TEST_INTEGERS[i]);
		}

		boolean exists;
		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				exists = ts.contains(TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}
	
	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testRetrievePerformance_HashMap(int iters, int elements)
	throws InterruptedException {
			
		Stats stats = new Stats();
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		for (int i=0; i < elements; i++) {
			hm.put(TEST_INTEGERS[i], TEST_INTEGERS[i]);
		}

		Integer anInt = null;
		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				anInt = hm.get(TEST_INTEGERS[i]);
			}

			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}
	
	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testRetrievePerformance_MyLinkedList(int iters, int elements)
	throws InterruptedException {
			
		Stats stats = new Stats();
		datastructures.LinkedList<Integer> ll = new datastructures.LinkedList<Integer>();
		for (int i=0; i < elements; i++) {
			ll.addToTail(TEST_INTEGERS[i]);
		}

		Integer anInt = null;
		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				anInt = ll.get(TEST_INTEGERS[i]);
//				ll.contains(TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}

	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testRetrievePerformance_MyHashtable(int iters, int elements) 
	throws InterruptedException {
			
		Stats stats = new Stats();
		Hashtable<Integer, Integer> ht = new Hashtable<Integer, Integer>(elements);
		for (int i=0; i < elements; i++) {
			ht.put(TEST_INTEGERS[i], TEST_INTEGERS[i]);
		}

		Integer anInt = null;
		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				anInt = ht.get(TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}
	
	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testRetrievePerformance_MyHashtableOA(int iters, int elements) 
	throws InterruptedException {
			
		Stats stats = new Stats();
		HashtableOA ht = new HashtableOA(elements);
		for (int i=0; i < elements; i++) {
			ht.put(TEST_INTEGERS[i], TEST_INTEGERS[i]);
		}

		Object anInt = null;
		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				anInt = ht.get(TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}
	
	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testRetrievePerformance_MyHasharray(int iters, int elements) 
	throws InterruptedException {
			
		Stats stats = new Stats();
		Hasharray ha = new Hasharray(elements);
		for (int i=0; i < elements; i++) {
			ha.put(TEST_INTEGERS[i], TEST_INTEGERS[i]);
		}

		Object anInt = null;
		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				anInt = ha.get(TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}
	
	
	/**
	 * @param elements
	 * @return
	 */
	private static Stats testRetrievePerformance_MyBST(int iters, int elements) 
	throws InterruptedException {
			
		Stats stats = new Stats();
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		for (int i=0; i < elements; i++) {
			int intVal = (int)(Math.random() * INSERT_ELEMS);
			bst.insert(TEST_INTEGERS[intVal]);
		}

		for (int iter = 0; iter < iters; iter++) {
			System.gc(); Thread.sleep(SLEEP_TIME);
			long start = System.currentTimeMillis();
			for (int i=0; i < elements; i++) {
				bst.find_DFS_R(TEST_INTEGERS[i]);
			}
			long elapsed = System.currentTimeMillis() - start;
			stats.time.update(elapsed);
		}
		
		return stats;
	}

}