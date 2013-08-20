package sort;
import java.util.List;

public class SelectionSort extends Sort implements SortAlgorithm {
	
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		
		if (elements == null) {
			return null;
		}

		int numElems = elements.size();
		for (int i=0; i < numElems-1; i++) {
			
			T min = elements.get(i);
			int minIdx = i;
			for (int j=i+1; j < numElems; j++) {

				T anElem = elements.get(j);
				if (anElem.compareTo(min) < 0) {
					min = anElem;
					minIdx = j;
				}
			}
			
			// Sort by swapping the element at i with the minimum we found in this pass
			if (i != minIdx) {
				elements.set(minIdx, elements.get(i));
				elements.set(i, min);
			}
		}
		
		return elements;
	}
	
	public String getName() { 
		return "SelectionSort";
	}
}