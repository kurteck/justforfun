package sort.v2;

import java.util.List;

import datagen.DataGenerator;

import sort.InsertionSort;
import sort.Sort;
import sort.SortAlgorithm;

public class QuickSort3Way extends Sort implements SortAlgorithm {
	
	public static int INSERTSORT_THRESHOLD = 15;
	
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		
		if (elements == null) {
			return null;
		}

		sort(elements, 0, elements.size() - 1);
		return elements;
	}
	
	
	public static <T extends Comparable<? super T>> void sort(List<T> elements, int start, int end) {
		
		int numElems = elements.size();
		if (start >= end || start < 0 || start > numElems-1 || end > numElems-1) {
			return;
		}

		if (end-start <= INSERTSORT_THRESHOLD) {
			InsertionSort.sort(elements, start, end);
			return;
		}
		
		int i = start-1;
		int p = start-1;
		int j = end;
		int q = end;
		int mid = (start >> 1) + (end >> 1) + (start & end & 0x1); // add back extra one if both odd
		T pivot = elements.get(mid);
//		System.out.println("pivot: " + pivot + " start: " + elements.get(start) + " end: " + elements.get(end));
		swap(elements, mid, end);
		
		while (true) {
			while (elements.get(++i).compareTo(pivot) < 0);
			while (pivot.compareTo(elements.get(--j)) < 0) {
				if (j == 1) {
					break;
				}
			}
			// should this be here?
			if (j == 1) {
				break;
			}
			
//			System.out.println("i: " + i + " j: " + j);
			if (i >= j) {
				break;
			}

			swap(elements, i, j);

			if (elements.get(i).compareTo(pivot) == 0) {
				p++;
				swap(elements, p, i);
			}

			if (pivot.compareTo(elements.get(j)) == 0) {
				q--;
				swap(elements, j, q);
			}
		}
		
		swap(elements, i, end);
		j = i-1;
		i = i+1;
		
		// collect equal values
		for (int k=start; k < p; k++, j--) {
			swap(elements, k, j);
		}

		for (int k=end-1; k > q; k--, i++) {
			swap(elements, i, k);
		}
		
		sort(elements, start, j);
		sort(elements, i, end);
	}
	
    
	
	public String getName() { 
		return "QuickSort-3Way";
	}
	
	public static void main(String[] args) {

		int numElems  = 32; 
		List<Integer> input = DataGenerator.toArrayList(DataGenerator.getSemiSortedInts(numElems, 95));
		QuickSort3Way qs = new QuickSort3Way();
		System.out.println(input);
		List<Integer> sorted = qs.sort(input);
		System.out.println(sorted);
	}
	
	
}