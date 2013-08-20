package sort;

import java.util.ArrayList;
import java.util.List;

import datagen.DataGenerator;

public class MergeSort extends Sort implements SortAlgorithm {

	public static int INSERTSORT_THRESHOLD = 15;

	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
 		return sortStatic(elements);
	}
	
	public static <T extends Comparable<? super T>> List<T> sortStatic(List<T> elements) {

		if (elements == null) {
			return null;
		}
		
		int numElements = elements.size();
		if (numElements <= 1) {
			return elements;
		}

		int mid = numElements / 2;
//		System.out.println("elements: " + elements);
		List<T> left  = elements.subList(0, mid);
		List<T> right = elements.subList(mid, numElements);
//		System.out.println("Left: " + left);
//		System.out.println("Right: " + right);
		
		merge(sortStatic(left), sortStatic(right));
		return elements;
	}

	
	private static <T extends Comparable<? super T>> void merge(List<T> list1, List<T> list2) {
		
//		System.out.println("Merging l1: " + list1);
//		System.out.println("Merging l2: " + list2);
		int list1i = 0;
		int list2i = 0;
		int list1s = list1.size();
		int list2s = list2.size();

		if (list1s <= 0 || list2s <=0) {
			return;
		}

		List<T> merged = new ArrayList<T>(list1s + list2s);
		while (list1i < list1s && list2i < list2s) {
			T e1 = list1.get(list1i);
			T e2 = list2.get(list2i);
			if (e2.compareTo(e1) <= 0) {
				merged.add(e2);
				list2i++;
			}
			else {
				merged.add(e1);
				list1i++;
			}
		}
		
		while (list1i < list1s) {
			merged.add(list1.get(list1i++));
		}

		while (list2i < list2s) {
			merged.add(list2.get(list2i++));
		}
		
//		System.out.println("Merged: " + merged);

		// ugh - copy results back into original array
		// need to get in-place merging working!
		for (int i=0; i < list1s; i++) {
			list1.set(i, merged.get(i));
		}
		for (int i=0; i < list2s; i++) {
			list2.set(i, merged.get(list1s+i));
		}
	}
	

	private static <T extends Comparable<? super T>> List<T> merge_v1(List<T> list1, List<T> list2) {
		
		int list1i = 0;
		int list2i = 0;
		int list1s = list1.size();
		int list2s = list2.size();
		List<T> merged = new ArrayList<T>(list1s + list2s);

		while (list1i < list1s && list2i < list2s) {
			T e1 = list1.get(list1i);
			T e2 = list2.get(list2i);
			if (e2.compareTo(e1) <= 0) {
				merged.add(e2);
				list2i++;
			}
			else {
				merged.add(e1);
				list1i++;
			}
		}
		
		while (list1i < list1s) {
			merged.add(list1.get(list1i++));
		}

		while (list2i < list2s) {
			merged.add(list2.get(list2i++));
		}

		
		return merged;
	}

	
	public String getName() { 
		return "MergeSort";
	}
	


	public static void main(String[] args) {
		
		List<Integer> data = DataGenerator.toArrayList(DataGenerator.getRandomData(4, 0, 100));
//		System.out.println(data);
		List<Integer> sorted = MergeSort.sortStatic(data);
//		System.out.println(sorted);
		System.out.println(Sort.isSorted(sorted));

	
////		int[] data2 = DataGenerator.getRandomData(16, 0, 16);
//		int[] data2 = new int[16];
//		data2[5] = 1;
//		data2[6] = 1;
//		data2[7] = 5;
//		data2[8] = 4;
//		data2[9] = 9;
//		data2[10] = 2;
//		data2[11] = 1;
//		data2[12] = 1;
//		DataGenerator.println(data2);
//		RadixSort.sort(data2,6,10);
//		DataGenerator.println(data2);
//		System.out.println(Sort.isSorted(data2));
	
	}
}