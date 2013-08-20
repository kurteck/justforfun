package sort.v2;

import java.util.List;

import datagen.DataGenerator;

import sort.InsertionSort;
import sort.QuickSort;
import sort.Sort;
import sort.SortAlgorithm;

public class QuickSort3 extends Sort implements SortAlgorithm {
	
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
//		System.out.println("pivot: " + pivot + " start: " + elements.get(start) + " end: " + elements.get(end) + " list: " + elements.subList(start,  end+1));
		swap(elements, mid, end);

		int left  = start;
		int right = end;
		while (left < right) {
			
			while (elements.get(left).compareTo(pivot) < 0 && left < right) {
				left++;
			}
			
			while (elements.get(right).compareTo(pivot) > 0 && left < right) {
				right--;
			}
//			System.out.println("Left: " + left + "  Right: " + right);
			if (left < right) {
				swap(elements, left, right);
				left++;
				right--;
			}
		}
		sort(elements, start, right);
		sort(elements, left, end);
		
		return elements;
	}
	
	
	
	public String getName() { 
		return "QuickSort3";
	}
	
	
	public static void main(String[] args) {

		int numElems  = 128; 
		List<Integer> input = DataGenerator.toArrayList(DataGenerator.getSemiSortedInts(numElems, 95));
		QuickSort qs = new QuickSort();
		System.out.println(input);
		List<Integer> sorted = qs.sort(input);
		System.out.println(sorted);
	}
	
	
}