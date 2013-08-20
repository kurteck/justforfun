package sort;

import java.util.List;

import datagen.DataGenerator;

public class MergeSortArray extends Sort implements SortAlgorithm {
	
	@SuppressWarnings ({"unchecked"})
	public <T extends Comparable<? super T>> List<T> sort(List<T> data) {

		if (data == null || data.size() <=1) {
			return data;
		}
		
		int size = data.size();
		int[] ints = new int[size];
		for (int i=0; i < size; i++) {
			ints[i] = (Integer)data.get(i);
		}

		sort(ints, 0, size-1);
		for (int i=0; i < size; i++) {
			data.set(i, (T)new Integer(ints[i]));
		}
		
		return data;
	}
	
	
	private static void sort(int[] data, int start, int end) {
	
		if (data == null || start < 0 || start > end || end > data.length) {
			return;
		}

		int length = end-start+1;
		if (length <= 1) {
			return;
		}
		
		int mid = (start >>> 1) + (end >>> 1) + (start & end & 0x1);
		sort(data, start, mid); // left
		sort(data, mid+1, end); // right
		merge(data, start, mid, mid+1, end);
	}
	
	
	private static void merge(int[] data, int ls, int le, int rs, int re) {

		int length = re-ls+1;
		int[] temp = new int[length];

		int insertIdx = 0;
		int lIdx = ls;
		int rIdx = rs;
		while (lIdx <= le && rIdx <= re) {
			temp[insertIdx++] = data[lIdx] <= data[rIdx] ? data[lIdx++] : data[rIdx++];
		}
		
		while (lIdx <= le) temp[insertIdx++] = data[lIdx++];
		while (rIdx <= re) temp[insertIdx++] = data[rIdx++];
		
		for (int i=0; i < length; i++) {
			data[i+ls] = temp[i];
		}
	}

	
	public String getName() { 
		return "MergeSort-Array";
	}
	
	public static void main(String[] args) {

		List<Integer> data = DataGenerator.toArrayList(DataGenerator.getOrderedData(30, 21, 100, false));
		System.out.println(data);
		MergeSortArray msa = new MergeSortArray();
		msa.sort(data);
		System.out.println(data);
		System.out.println("isSorted: " + isSorted(data));
	}
	
}