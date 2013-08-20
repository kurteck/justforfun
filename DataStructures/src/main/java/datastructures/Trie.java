package datastructures;
import java.util.Deque;
import java.util.LinkedList;

public class Trie {
	
	public static void main(String[] args) {
		int num = 11;
		printBits(num);
		System.out.println();
		
		int numMask = (~0 << 2);
		printBits(numMask);
		System.out.println();
		
//		int test = num & (~0 << 2);
		int test = num & numMask;
		printBits(test);	
		System.out.println();
		
		int mask1 = 0x7FFFFFFF;
		printBits(mask1);
		System.out.println();

		int mask2 = 0x80000000;
		printBits(mask2);
		System.out.println();
	}

	public static void printBits(int num) {
		
		for (int i=32; i >= 0; i--) {
			System.out.print(getBit(num, i));
		}
	}

	
	public static int getBit(int num, int bit) {

		if (bit < 0 || bit > 32) {
			return -1;
		}
		
		int bitVal = num & (1 << bit); // return 8,4,2,1 etc...
		return (bitVal != 0) ? 1 : 0; // note we can't shift back by num bits (>> bit) due to negative #'s
	}
	
//	
//	private char chr;
//	private Deque<Trie> edges;
//	
//	public Trie(char chr) {
//		this.chr = chr;
//		this.edges = new LinkedList<Trie>();
//	}
//
//	
//	public void insert(String s) {
//		
//		for (int i=0; i < s.length(); i++) {
//			char aChar = s.charAt(i);
//			edges.
//		}
//		s.
//	}
	
}