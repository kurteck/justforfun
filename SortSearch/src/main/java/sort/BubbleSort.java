package sort;
import java.util.List;

import datagen.DataGenerator;

public class BubbleSort extends Sort implements SortAlgorithm {
	
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		
		if (elements == null) {
			return elements; 
		}
		
		Object[] oArray = toArray(elements);
		sort(oArray);
		copyInto(oArray, elements);

		return elements;
	}
	

	/**
	 * @param elements
	 */
	public static void sort(Object[] elements) {
		sort(elements, 0, elements.length-1);
	}

	
	@SuppressWarnings ({"unchecked", "rawtypes"})
	public static void sort2(Object[] elements, int start, int end) {

		for (int i=start; i < end; i++) {
			
			boolean stop = true;
			for (int j=start; j < end; j++) {

				Comparable a = (Comparable)elements[j];
				Comparable b = (Comparable)elements[j+1];
				int cmp = b.compareTo(a);
				if (cmp < 0) {
					elements[j]   = b;
					elements[j+1] = a;
					stop = false;
				}
			}
			
			if (stop) {
				return;
			}
		}
	}	
	
	public String getName() { 
		return "BubbleSort";
	}

	
	@SuppressWarnings ({"unchecked", "rawtypes"})
	public static void sort(Object[] elements, int start, int end) {

		for (int i=start; i < end; i++) {
			
			boolean stop = true;
			for (int j=i+1; j <= end; j++) {

				Comparable a = (Comparable)elements[i];
				Comparable b = (Comparable)elements[j];
				int cmp = b.compareTo(a);
				if (cmp < 0) {
					elements[i] = b;
					elements[j] = a;
					stop = false;
				}
			}
			
			if (stop) {
				return;
			}
		}
	}	
	
	public static void main(String[] args) {
		
		List<Integer> data = DataGenerator.toArrayList(DataGenerator.getRandomData(16, 0, 100));
		System.out.println(data);
		BubbleSort bs = new BubbleSort();
		bs.sort(data);
		System.out.println(data);
		System.out.println(isSorted(data));
	}
}