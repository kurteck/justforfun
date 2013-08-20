package search;

public class FindString {
	
	public static int search(String[] a, String toFind) {

		if (a == null || toFind == null) {
			return -1;
		}
		return search(a, toFind, 0, a.length-1);
	}
	
	private static int search(String[] a, String toFind, int start, int end) {
		
		if (a == null || toFind==null || start < 0 || end > a.length-1 || start > end) {
			return -1;
		}
		
		int mid  = (start >>> 1) + (end >>> 1) + (start & end & 0x1);
		if (a[mid].equals(toFind)) {
			return mid;
		}

		int left = mid-1;
		while (left >= start && a[left].equals("")) left--;
		if ((left < start) || (a[left].compareTo(toFind) < 0)) {
			return search(a, toFind, mid+1, end);
		}
		else {
			return search(a, toFind, start, left);
		}
	}


	public static void main(String[] args) {
		
		String[] a1 = {"at", "", "", "", "bat", "", "", "cat", "", "", "fat", "", "hat", "mat", "", "", "", "pat", "", "rat", "", "sat", "", "", "", "", "", "tat", "vat"};

		System.out.println("a1: " + search(a1, "at"));
		
		
	}

}