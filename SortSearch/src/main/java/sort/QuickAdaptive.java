package sort;

import java.util.List;

import datagen.DataGenerator;

public class QuickAdaptive extends Sort implements SortAlgorithm {
	
	// Boundaries separated by powers of 2:       0   1   2   3	  4	  5 ... 
	// Number of elements:                        1   2   4   8   16  32 ...
	public static int[] INSERTSORT_THRESHOLDS = {18, 18, 26, 34, 24, 43, 56, 35, 56, 45, 48, 38, 39, 43, 39, 39, 36, 35, 36, 37, 36};

	
	private int insertSortThreshold;
	
	@SuppressWarnings("unchecked")
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		
		if (elements == null) {
			return null;
		}
		
		int numElems = elements.size();
		if (numElems <= 1) {
			return elements;
		}
		
		int thresholdIndex = 31 - Integer.numberOfLeadingZeros(numElems);
		thresholdIndex = thresholdIndex > INSERTSORT_THRESHOLDS.length-1 ? INSERTSORT_THRESHOLDS.length-1 : thresholdIndex;
		insertSortThreshold = INSERTSORT_THRESHOLDS[thresholdIndex];
		
		int[] elemsArray = new int[numElems];
		for (int i=0; i < numElems; i++) {
			elemsArray[i] = ((Integer)elements.get(i)).intValue();
		}
		sort(elemsArray, 0, numElems - 1);
		
		for (int i=0; i < numElems; i++) {
			elements.set(i, (T)new Integer(elemsArray[i]));
		}
		return elements;
	}


	
	/**
	 * @param elements
	 * @param start
	 * @param end
	 */
	public void sort(int[] elements, int start, int end) {

		int numElems = elements.length;
		if (start >= end || start < 0 || end < 0 || end > numElems-1) {
			return;
		}

		if (end-start <= insertSortThreshold) {
			InsertionSort.sort(elements, start, end);
			return;
		}

		int right = partition(elements, start, end);
		sort(elements, right+1, end);
        sort(elements, start, right-1);
    }
	

	private static int partition(int[] elements, int start, int end) {
		
        int left  = start;
        int right = end + 1;
		int pIndx = (int)(Math.random() * (end-start+1)) + start;
        int pivot = elements[pIndx];
        swap(elements, start, pIndx);
        
        while (true) { 

            // Search for left element that is greater than or equal to pivot
        	while (elements[++left] < pivot) {
	        	if (left >= end) {
	               	break;
	        	}
        	}
        	
            // Search for right element that is less than or equal to pivot
        	while (elements[--right] > pivot) {
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
    
	
	public String getName() { 
		return "QS-ISA-P";
	}
	
	public static void main(String[] args) {

		List<Integer> data = DataGenerator.toArrayList(DataGenerator.getRandomData(16, 0, 100));
		System.out.println(data);
		QuickPrimitive qir = new QuickPrimitive();
		qir.sort(data);
		System.out.println(data);
		System.out.println(isSorted(data));
	}
}