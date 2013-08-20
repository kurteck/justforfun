package datastructures;

public class BitSet {
	
	private byte[] bytes;
	
	/**
	 * Must be evenly divisible by 8
	 */
	public BitSet(int numBits) {
		bytes = new byte[numBits/8];
	}

	
	public boolean get(int pos) {
		
		int byteNum = pos / 8;
		int bitNum  = pos % 8;
		byte value  = (byte)(bytes[byteNum] & (1 << bitNum));
		
		return value == 0 ? false : true;
	}
	
	
	public void set(int pos) {
		
		int byteNum = pos / 8;
		int bitNum  = pos % 8;
		
		bytes[byteNum] |= (1 << bitNum);
	}
	
	
	public static void main(String[] args) {
		
		int[] dups = new int[32000];
		int[] ints = new int[32000];
		for (int i=0; i < 1000; i++) {
			ints[i] = (int)(Math.random() * 32000) + 1; // 1-32000
			dups[ints[i]]++;
		}
		
		BitSet bits = new BitSet(32000);
		for (int i=0; i < 1000; i++) {
			if (bits.get(ints[i] - 1)) {
				System.out.println("Duplicate[" + ints[i] + "]");
			}
			bits.set(ints[i] - 1);
		}
		
		for (int i=0; i < dups.length; i++) {
			if (dups[i] > 1) {
				System.out.println(i + " is duplicated " + dups[i] + " times.");
			}
		}
	}
}