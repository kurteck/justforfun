package sort;

import java.util.List;

public class BogoSort extends Sort implements SortAlgorithm {
	
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		
		int numElems = elements.size();
		while (!isSorted(elements)) {
			int rand1 = (int)(Math.random() * numElems);
			int rand2 = (int)(Math.random() * numElems);
			T elem1 = elements.get(rand1);
			T elem2 = elements.get(rand2);
			elements.set(rand1, elem2);
			elements.set(rand2, elem1);
		}

		return elements;
	}
	
	
	public String getName() { 
		return "BogoSort";
	}
}