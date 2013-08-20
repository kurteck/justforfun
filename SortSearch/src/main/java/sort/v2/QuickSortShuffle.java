package sort.v2;

import java.util.List;

import datagen.DataGenerator;

import sort.InsertionSort;
import sort.Sort;
import sort.SortAlgorithm;

public class QuickSortShuffle extends Sort implements SortAlgorithm {
	
	public static int INSERTSORT_THRESHOLD = 15;
	
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		
		if (elements == null) {
			return null;
		}
		
//		long start = System.currentTimeMillis();
		shuffle(elements);
//		long end = System.currentTimeMillis();
//		System.out.println("Elapsed: " + (end-start));
		
		return sort(elements, 0, elements.size() - 1);
	}

	
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements, int start, int end) {

		int numElems = elements.size();
		if (start >= end || start < 0 || end < 0 || start > numElems-1 || end > numElems-1) {
			return elements;
		}
		
		if (end-start <= INSERTSORT_THRESHOLD) {
			InsertionSort.sort(elements, start, end);
			return elements;
		}

		int right = partition(elements, start, end);
        sort(elements, start, right-1);
        sort(elements, right+1, end);
        
        return elements;
    }
	

	private static <T extends Comparable<? super T>> int partition(List<T> elements, int start, int end) {
		
        int left  = start;
        int right = end + 1;
//		int mid   = (start >> 1) + (end >> 1) + (start & end & 0x1); // add back extra one if both odd
        T pivot   = elements.get(start);
//        swap(elements, start, mid);
        
        while (true) { 

            // Search for left element that is greater than or equal to pivot
        	while (elements.get(++left).compareTo(pivot) < 0) {
	        	if (left >= end) {
	               	break;
	        	}
        	}
        	
            // Search for right element that is less than or equal to pivot
        	while (elements.get(--right).compareTo(pivot) > 0) {
	        	if (right <= start) {
	               	break;
	        	}
        	}

        	// Ensure we are still within the boundaries of this list
            if (left >= right) {
            	break;
            }
            
            swap(elements, left, right);
        }

        // put pivot into correct place
        swap(elements, start, right);

        // and return its index so that we can continue partitioning
        return right;
    }
    
    
	private static <T extends Comparable<? super T>> void shuffle(List<T> elements) {

		int numElems = elements.size();
		for (int i = numElems-1; i >=1; i--) {
			int random = (int)(Math.random() * i);
			swap(elements, i, random);
		}
	}
	
	
	
	public String getName() { 
		return "QuickSortSuffle";
	}
	
	public static void main(String[] args) {

		int numElems  = 32; 
		List<Integer> input = DataGenerator.toArrayList(DataGenerator.getSemiSortedInts(numElems, 25));
		QuickSortShuffle qs = new QuickSortShuffle();
		System.out.println(input);
		List<Integer> sorted = qs.sort(input);
		System.out.println(sorted);
		
	}
	
	
}