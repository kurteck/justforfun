package sort;

import java.util.List;

import datagen.DataGenerator;
import datastructures.Stats;

// Aspects that inform the sort algorithm:
//
// 1. The input data:
//    a. Data Type  (primitive, int, float, char, Object, Integer, String)
//    b. Ordering   (ascending, descending, mostly ascending, mostly descending, random, patterned)
//    c. Uniqueness (no duplicates, some duplicates, many duplicates, all duplicates)
//    d. Data Size  ("large", "small")
//    e. Deviation  (clustered, spread out, min, max, mean)
//    f. Stability  (need to preserve original order?)
//    g. Source     (memory, file, network, stream)
//    h. Context    (application knowledge or recent history) 
// 
// 2. The compute system:
//    a. Available Memory
//    b. Available CPUs
//    c. Single Machine or Cluster
//
// 3. The output 
//    a. Ordering  (ascending, descending)
//    b. Data Type (primitive, Object)
//    c. Format    (memory, file, network) 
//    d. Tolerance (does data need to be perfectly sorted or almost sorted)


// Approach
// 1. Learn about the data
// 2. Choose initial sort heuristic
// 3. Execute and Adapt sort path during runtime
public class KSort extends Sort implements SortAlgorithm {

	public static int TYPE_PRIMITIVE_INT = 1;
	public static int INPUT_ORDER_RANDOM = 1;
	
	private int dataType  = TYPE_PRIMITIVE_INT;
	private int ordering  = 0;
	private int numAscnd  = 0;
	private int numDscnd  = 0;
	private int pctAscnd  = 0;
	private int pctDscnd  = 0;
	private int minValue  = Integer.MAX_VALUE;
	private int maxValue  = Integer.MAX_VALUE;
	private int midValue  = 0;
	private int deviation = 0; 
	private int numElems  = 0;
	private int[] reversed;
	
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {

		// For:
		// DataType = Integers, ints
		// Range    = (<
		// Ordering = Any
		
		// 1. Small Range
// 1. The input data:
//	    a. Data Type  (primitive, int, float, char, Object, Integer, String)
//	    b. Ordering   (ascending, descending, mostly ascending, mostly descending, random, patterned)
//	    c. Uniqueness (no duplicates, some duplicates, many duplicates, all duplicates)
//	    d. Data Size  ("large", "small")
//	    e. Deviation  (clustered, spread out, min, max, mean)
//	    f. Stability  (need to preserve original order?)
//	    g. Source     (memory, file, network, stream)
//	    h. Context    (application knowledge or recent history) 

		
		
		
		return null;
	}

	
	public int[] sort(int[] data) {

		learn(data);

		System.out.println(pctAscnd);
//		if (ordering == FULLY_ASCENDING) {
//			return data;
//		}
//		else if (ordering == FULLY_DESCENDING) {
//			return reverse(data);
//		}
		
		
		if (pctAscnd >= 90) {
			return insertionSort(data);
		}
		
		// Where is the optimal threshold among InsertionSort, CountSort, & RadixSort for integer data?
		// Need to consider ordering, data size, and spread
		
		
		return null;
	}

	
	private void learn(int[] data) {
		
		dataType = TYPE_PRIMITIVE_INT;
		numElems = data.length;
		minValue = data[0];
		maxValue = data[0];
		midValue = data[numElems/2];
		numAscnd = 0;
		numDscnd = 0;
		for (int i=1; i < numElems; i++) {

			minValue = data[i] < minValue ? data[i] : minValue;
			maxValue = data[i] > maxValue ? data[i] : maxValue;
			if (data[i] < data[i-1]) {
				numDscnd++;
			}
			else {
				numAscnd++;
			}
		}
		pctAscnd = numAscnd*100/(numElems-1);
		pctDscnd = numDscnd*100/(numElems-1);
//		int numCompares = numElems - 1;
//		ordering = oSum * 100 / numCompares; //100 - ((numCompares - ordr) >> 1) * 100 / numCompares;
	}
	
	
	private static int[] reverse(int[] data) {
	
		int endIdx = data.length-1;
		int iters  = (data.length+1)/2;
		for (int i=0; i < iters; i++) {
			data[i] ^= data[endIdx-i];
			data[endIdx-i] ^= data[i];
			data[i] ^= data[endIdx-i];
		}
		
		return data;
	}
	
	
	private static int[] insertionSort(int[] data) {
		
		int numElems = data.length;
		for (int i=1; i < numElems; i++) {

			int insertIdx = i;
			int toInsert  = data[insertIdx];
			while (insertIdx > 0 && toInsert < data[insertIdx-1]) {
				data[insertIdx] = data[insertIdx-1];
				insertIdx--;
			}
			
			data[insertIdx] = toInsert; 
		}
		
		return data;
	}
	
	
	public String getName() { 
		return "KSort";
	}
	
	private static Stats test(int numIters, int numElems, boolean sortInput, boolean ascending, int pctAscnd) {
		
		KSort ks = new KSort();
		Stats stats = new Stats();
		for (int iters=0; iters < numIters; iters++) {
			int[] data = DataGenerator.getSemiSortedInts(numElems, pctAscnd);
			System.gc();
			long start = System.currentTimeMillis();
			ks.sort(data);
			long stop = System.currentTimeMillis();
			stats.time.update(stop-start);
			ks.print();
		}
		
		return stats;
	}

	
	public void print() {
		System.out.println("Data Type: " + dataType);
		System.out.println("NumElems:  " + numElems);
		System.out.println("Ordering:  " + ordering);
		System.out.println("NumAscnd:  " + numAscnd + "(" + pctAscnd + "%)");
		System.out.println("NumDscnd:  " + numDscnd + "(" + pctDscnd + "%)");
		System.out.println("MinValue:  " + minValue);
		System.out.println("MaxValue:  " + maxValue);
		System.out.println("MidValue:  " + midValue);
		System.out.println("Deviation: TBD");
	}
	

	
	public static void main(String[] args) {
		
		System.out.printf("%-15s  %10s  %15s  %5s  %5s  %5s\n", "Sort Algorithm", "Size", "Input Ordering", "Min", "Max", "Avg");
		System.out.printf("%-15s  %10s  %15s  %5s  %5s  %5s\n", "--------------", "-------", "---------------", "---", "---", "---");

		int numIters = 1;
		int numElems = 65536;
		boolean sort = true;
		boolean sasc = true;
		int pctAscnd = 91;
		Stats stats  = test(numIters, numElems, sort, sasc, pctAscnd);
		String order = sort ? sasc ? "Full Ascending" : "Full Descending" : "Random";
//		System.out.printf("%-15s  %10s  %15s  %5s  %5s  %5s\n", "KSort", numElems, order, stats.min, stats.max, stats.avg);
		
//		print(Sort.getSemiSortedInts(64, pctAscnd));
	}
	
}