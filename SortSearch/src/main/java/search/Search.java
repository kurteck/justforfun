package search;

public class Search {
	
	public static int findNumber(int[] a, int toFind) {
		if (a == null) {
			return -1;
		}
		
		return findNumber(a, toFind, 0, a.length-1);
	}
	
	
	// 	Sorted, In Range 
	// 	YES		YES			Search This  Half
	// 	YES		NO			Search Other Half
	// 	NO		YES			Search Both - Unsorted Input
	//  NO		NO			Search Both - Not Enough Info
	public static int findNumber(int[] a, int toFind, int start, int end) {
		
		if (a == null || start < 0 || end > a.length-1 || start > end) {
			return -1;
		}
		
		int len = end-start;
		int mid = start + (len >>> 1);
		if (a[mid] == toFind) {
			return mid;
		}
		
		boolean isSortedL = a[start] <= a[mid];
		boolean isSortedR = a[mid] <= a[end];
		boolean isInHalfL = isSortedL && a[start] <= toFind && toFind <= a[mid];
		boolean isInHalfR = isSortedR && a[mid] <= toFind && toFind <= a[end];
//		System.out.println("Start: " + a[start] + " Mid: " + a[mid] + " End: " + a[end] + "  " + isSortedL + "  " + isSortedR + "  " + isInHalfL + "  " + isInHalfR);
		if (isInHalfL) {
			return findNumber(a, toFind, start, mid-1);
		}
		else if (isInHalfR) {
			return findNumber(a, toFind, mid+1, end);
		}
		else if (isSortedL && isSortedR) {
			int left = findNumber(a, toFind, start, mid-1);
			if (left >= 0) {
				return left;
			}
			else {
				return findNumber(a, toFind, mid+1, end);
			}
		}
		else if (isSortedL) {
			return findNumber(a, toFind, mid+1, end);
		}
		else if (isSortedR) {
			return findNumber(a, toFind, start, mid-1);
		}
		// Array Input is not sorted!
		else {
			int left = findNumber(a, toFind, start, mid-1);
			if (left >= 0) {
				return left;
			}
			else {
				return findNumber(a, toFind, mid+1, end);
			}
		}
	}


	public static void main(String[] args) {
		
		int[] a1 = {1, 2, 3, 4, 5, 6, 7};
		int[] a2 = {4, 5, 6, 7, 1, 2, 3};
		int[] a3 = {2, 2, 2, 2, 5, 1, 2};
		int[] a4 = {2, 2, 2, 2, 4, 1, 2};
		int[] a5 = {7, 7, 7, 8, 1, 5, 6};
		int[] a6 = {3, 5, 1, 2, 2, 2, 2};
		int[] a7 = {7, 1, 5, 7, 7, 7, 7};
		int[] a8 = {2, 2, 3, 3, 5, 1, 2};
		int[] a9 = {6, 6, 7, 7, 1, 4, 5};
		int[] a0 = {6, 4, 5, 3, 2, 9, 1};
				
		System.out.println("1: " + findNumber(a1, 5));
		System.out.println("2: " + findNumber(a2, 5));
		System.out.println("3: " + findNumber(a3, 5));
		System.out.println("4: " + findNumber(a4, 5));
		System.out.println("5: " + findNumber(a5, 5));
		System.out.println("6: " + findNumber(a6, 5));
		System.out.println("7: " + findNumber(a7, 5));
		System.out.println("8: " + findNumber(a8, 5));
		System.out.println("9: " + findNumber(a9, 5));
		System.out.println("0: " + findNumber(a0, 5));
		
		
	}

}