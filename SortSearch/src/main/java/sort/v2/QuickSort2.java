package sort.v2;
import java.util.List;

import sort.InsertionSort;
import sort.Sort;
import sort.SortAlgorithm;
import datagen.DataGenerator;

public class QuickSort2 extends Sort implements SortAlgorithm {
	
	public static int INSERTSORT_THRESHOLD = 15;
	
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		
		if (elements == null) {
			return null;
		}
		
		return sort(elements, 0, elements.size() - 1);
	}
	
	
	public static <T extends Comparable<? super T>> List<T> sort(List<T> elements, int start, int end) {
		
		int numElems = elements.size();
		if (start >= end || start < 0 || end < 0 || start > numElems-1 || end > numElems-1) {
			return elements;
		}

		if (end-start <= INSERTSORT_THRESHOLD) {
			InsertionSort.sort(elements, start, end);
			return elements;
		}

		int mid = (start >> 1) + (end >> 1) + (start & end & 0x1); // add back extra one if both odd
		T pivot = elements.get(mid);
		swap(elements, mid, end);

		int earliestBigger = start;
		for (int i=start; i < end; i++) {
			if (elements.get(i).compareTo(pivot) < 0) {
				swap(elements, i, earliestBigger);
				earliestBigger++;
			}
		}
		
		swap(elements, earliestBigger, end);
		
		sort(elements, start, earliestBigger-1);
		sort(elements, earliestBigger+1, end);
		
		return elements;
	}
	
	

	public String getName() { 
		return "QuickSort2";
	}
	
	public static void main(String[] args) {

		int numElems  = 524288; 
		List<Integer> input = DataGenerator.toArrayList(DataGenerator.getSemiSortedInts(numElems, 95));
		QuickSort2 qs = new QuickSort2();
		qs.sort(input);
	}
	
	
}