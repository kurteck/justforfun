package sort.v2;

import java.util.ArrayList;
import java.util.List;

import sort.InsertionSort;
import sort.Sort;
import sort.SortAlgorithm;

public class QuickSortR extends Sort implements SortAlgorithm {
	
	public static int INSERTSORT_THRESHOLD = 15;

	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		
		if (elements == null) {
			return null;
		}
		
		int numElems = elements.size();
		if (numElems <= INSERTSORT_THRESHOLD) {
			InsertionSort.sort(elements, 0, numElems-1);
			return elements;
		}
		
		int pIdx = (int)(Math.random() * numElems);
		T pivot  = elements.remove(pIdx);
		List<T> smaller = new ArrayList<T>();
		List<T> bigger  = new ArrayList<T>();
		for (int i=0; i < numElems-1; i++) {
			T anElem = elements.get(i);
			if (anElem.compareTo(pivot) < 0) {
				smaller.add(anElem);
			}
			else {
				bigger.add(anElem);
			}
		}
		
		List<T> sorted = new ArrayList<T>(numElems);
		sorted.addAll(sort(smaller));
		sorted.add(pivot);
		sorted.addAll(sort(bigger));
		
		return sorted;
	}
	
	public String getName() { 
		return "QuickSortR";
	}
	
	
}