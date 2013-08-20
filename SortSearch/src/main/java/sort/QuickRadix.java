package sort;

import java.util.List;

import datagen.DataGenerator;

public class QuickRadix extends Sort implements SortAlgorithm {
	
	public static int RADIXSORT_THRESHOLD  = 64;
	
	@SuppressWarnings("unchecked")
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		
		if (elements == null) {
			return null;
		}
		
		int numElems = elements.size();
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
		if (start >= end || start < 0 || end < 0 || start > numElems-1 || end > numElems-1) {
			return;
		}

		int length = end-start;
		if (length <= RADIXSORT_THRESHOLD) {
			RadixSort.sort(elements, start, end);
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
		return "QuickRadix";
	}
	
	public static void main(String[] args) {

		List<Integer> data   = DataGenerator.toArrayList(DataGenerator.getRandomData(16, 0, 100));
		System.out.println("Input: " + data);
		QuickPrimitive qs = new QuickPrimitive();
		List<Integer> sorted = qs.sort(data);
		System.out.println("Sorted: " + sorted);
		System.out.println(isSorted(sorted));
	}
}