package sort;

import java.util.Arrays;
import java.util.List;

import datagen.DataGenerator;

public class MergeSort2 extends Sort implements SortAlgorithm {

	public static int INSERTSORT_THRESHOLD = 0;
	public static int RADIXSORT_THRESHOLD  = 64;

	
	@SuppressWarnings("unchecked")
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {

		if (elements == null) {
			return null;
		}

		Object[] oArray = elements.toArray();
 		sort(oArray);
 		
 		return (List<T>)Arrays.asList(oArray);
	}
	
	

	public static void sort(Object[] elements) {
		
		if (elements == null) {
			return;
		}
		
 		sort(elements, 0, elements.length-1);
	}		
	
	
	private static void sort(Object[] elements, int start, int end) {
		
		int length = end-start+1;
		if (length <= 1 || start >= end) {
			return;
		}
		if (length <= INSERTSORT_THRESHOLD) {
			InsertionSort.sort(elements, start, end);
			return;
		}
		
		int mid = (start+end+1) >>> 1;
		sort(elements, start, mid-1); 	// sort left half
		sort(elements, mid, end);		// sort right half

		merge(elements, start, mid-1, mid, end);
	}
	
	


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void merge(Object[] elements, int list1s, int list1e, int list2s, int list2e) {
		
		int length = list2e-list1s+1;
		if (length <= 1) {
			return;
		}
		
		// Stop if we are already in order
		Comparable c1e = (Comparable)elements[list1e];
		Comparable c2s = (Comparable)elements[list2s];
		if (c1e.compareTo(c2s) <= 0) {
			return;
		}

		// swap lists if they are in reverse order
		Comparable c2e = (Comparable)elements[list2e];
		Comparable c1s = (Comparable)elements[list1s];
		if (c2e.compareTo(c1s) <= 0 ) {
			int mid = length >>> 1;
			for (int i=0; i < mid; i++) {
				swap(elements, list1s+i, list2s+i);
			}
			return;
		}
		
		// else merge the two
		Object[] temp = new Object[length];
		int insIdx = 0;
		int list1i = list1s;
		int list2i = list2s;
		while (list1i <= list1e && list2i <= list2e) { 
			Comparable c1 = (Comparable)elements[list1i];
			Comparable c2 = (Comparable)elements[list2i];
			if (c1.compareTo(c2) <= 0) {
				temp[insIdx++] = c1;
				list1i++;
			}
			else {  
				temp[insIdx++] = c2;
				list2i++;
			}
		}
		
		while (list1i <= list1e) {
			temp[insIdx++] = elements[list1i++];
		}

		while (list2i <= list2e) {
			temp[insIdx++] = elements[list2i++];
		}
		
		for (int i=0; i < length; i++) {
			elements[list1s+i] = temp[i];
		}
	}
	
	
	private static void mergeInPlace() {
//		System.out.print("Merging list1: ");
//		printArray(elements, list1s, list1e);
//		System.out.print("  and list2:  ");
//		printArray(elements, list2s, list2e);
//		System.out.print("  Result is:  ");
//		
//		printArray(temp, 0, length-1); System.out.println();
		
		//		System.out.println("Hi3");
//		Object[] temp = new Object[length];
//		int insIdx = list1s;
//		int list1i = list1s;
//		int list2i = list2s;
//		while (insIdx < list2e && list2i <= list2e) {
//
//			Comparable c1 = (Comparable)elements[list1i];
//			Comparable c2 = (Comparable)elements[list2i];
//			System.out.print("c1: " + c1 + "   c2: " + c2 + "   ");
//			printArray(elements, list1s, list2e); System.out.println();
//			if (c2.compareTo(c1) <= 0) {
//				swap(elements, insIdx, list2i);
//				if (list1i == insIdx) {
//					list1i=list2i; // Keep index pointing to list1's element
//				}
//			}
//			// c1 >= c1
//			else {  
//				if (list1i <= insIdx) {
//					list1i++;
//				}
//				// (list1i > insIdx)
//				else { 
//					swap(elements, insIdx, list1i);
//				}
//			}
//			insIdx++;
//		}
//		System.out.println("List1i: " +list1i + "  List2i: " + list2i + "  InsIdx: " +insIdx + " list2e: " +list2e);
//		
//		insIdx--;
//		while (list1i < list2e) {
//			swap(elements, insIdx, list2e);
//			list1i++;
//		}
//		printArray(elements, list1s, list2e); System.out.println();
	}

	
	public String getName() { 
		return "MergeSort2";
	}
	

	
	private static void printInfo(Object[] elements, int start, int end, int mid, int length) {
		System.out.println("Start: " + start + "  End: " + end + "   Mid: " + mid + "  Length: " +length);
		DataGenerator.println(elements, 0, end);
		System.out.println();
	}

	public static void main(String[] args) {
		
		List<Integer> data = DataGenerator.toArrayList(DataGenerator.getRandomData(32, 0, 100));
		MergeSort2 ms = new MergeSort2();
		System.out.println(data);
		List<Integer> sorted = ms.sort(data);
		System.out.println(sorted);
		System.out.println(isSorted(sorted));

	
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