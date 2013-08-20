package sort;
import java.util.List;

import datastructures.MinHeap;

public class HeapSort implements SortAlgorithm {
	
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		
		if (elements == null) {
			return null;
		}

		MinHeap<T> heap = new MinHeap<T>(elements);
		int numElems = elements.size();
		for (int i=0; i < numElems; i++) {
			elements.set(i, heap.remove());
		}

		return elements;
	}

	public String getName() { 
		return "HeapSort";
	}
}