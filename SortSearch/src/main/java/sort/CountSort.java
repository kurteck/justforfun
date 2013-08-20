package sort;

import java.util.List;

import datagen.DataGenerator;

public class CountSort extends Sort implements SortAlgorithm {
	
	
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {

		if (elements == null) {
			return elements;
		}

		sort(elements, 0, elements.size()-1);
		return elements;
	}
	
	/**
	 * @param elements
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> void sort(List<T> elements, int start, int end) {
		
		if (elements == null) {
			return;
		}
		
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i=start; i <= end; i++) {
			int anInt = ((Integer)elements.get(i)).intValue();
			if (anInt > max) { 
				max = anInt;
			}
			if (anInt < min) {
				min = anInt;
			}
		}

		int size = max - min + 1;
		int[] count = new int[size];
		for (int i=start; i <= end; i++) {
			int countVal = ((Integer)elements.get(i)).intValue();
			count[countVal - min]++;
		}

		int setIdx = start;
		for (int i=0; i < size; i++) {
			for (int j=0; j < count[i]; j++) {
				elements.set(setIdx++, (T)new Integer(i+min));
			}
		}
	}
	
	
	/**
	 * @param elements
	 * @param start
	 * @param end
	 */
	public static void sort(int[] elements, int start, int end) {
		
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i=start; i <= end; i++) {
			if (elements[i] < min) {
				min = elements[i];
			}
			if (elements[i] > max) {
				max = elements[i];
			}
		}
		
		
		int size = max - min + 1;
		int count[] = new int[size];
		for (int i=start; i <= end; i++) {
			count[elements[i] - min]++;
		}

		int elemIdx = start;
		for (int i=0; i < size; i++) {
			for (int j=0; j < count[i]; j++) {
				elements[elemIdx++] = i+min;
			}
		}
	}
	

	public String getName() { 
		return "CountSort";
	}

	public static void main(String[] args) {

		List<Integer> data = DataGenerator.toArrayList(DataGenerator.getRandomData(16, 0, 100));
		System.out.println(data);
		CountSort cs = new CountSort();
		cs.sort(data);
		System.out.println(data);
		System.out.println(isSorted(data));
	}
	
}