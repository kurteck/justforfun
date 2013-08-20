package sort;

import java.util.ArrayList;
import java.util.List;

import datagen.DataGenerator;

public class RadixSort3 extends Sort implements SortAlgorithm {
	
	
	/**
	 * For MSB:
	 * 1. Sort elements by their most significant piece of data first 
	 *    (i.e for numbers the left most digit, for words, the left 
	 *    most character).
	 *    
	 * 2. Continue sorting elements by their next most significant 
	 *    piece of data (i.e. for numbers the digit next to the left 
	 *    most) until no there are no more pieces.
	 *    
	 * 3. Finally, concatenate the results together.
	 *    
	 * For example, given the list of numbers: 801, 20, 402, 6, 17, 25
	 * 1. First sort by hundreds
	 * Bucket 000: 020, 006, 017, 025
	 * Bucket 400: 402
	 * Bucket 800: 801
	 * 
	 * 2. Then by tens:
	 * Bucket 00: 006 
	 * Bucket 10: 017 
	 * Bucket 20: 025, 020
	 * 
	 * 3. Then by ones:
	 * Bucket 0: 020
	 * Bucket 5: 025
	 * 
	 * @param elements
	 * @return
	 */
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		sortStatic(elements);
//		sortInPlace(elements);
		return elements;
	}
	

	/**
	 * LSB algorithm to sort Integer numbers.
	 * 
	 * Note this solution works using just two buckets (base2)
	 * so that all Integers, regardless of base can be sorted.
	 * 
	 * @param elements
	 * @return
	 */
	@SuppressWarnings ("unchecked")
	public static <T extends Comparable<? super T>> void sortStatic(List<T> elements) {

		if (elements == null) {
			return;
		}
		
		int numElems = elements.size();
		if (numElems <= 1) {
			return;
		}

		
		int[] bucket0 = new int[numElems];
		for (int i=0; i < numElems; i++) {
			bucket0[i] = ((Integer)elements.get(i)).intValue();
		}
		
		// For each increasing significant bit position
		int lowestOnesBitPos  = getLowestOnesBitPos(bucket0);
		int highestOnesBitPos = getHighestOnesBitPos(bucket0); 
		int k = highestOnesBitPos - lowestOnesBitPos;
		for (int bitPos=lowestOnesBitPos; bitPos <= highestOnesBitPos; bitPos++) {
			
			int bucket0i  = 0;
			int bucket1i  = 0;
			int[] bucket1 = new int[numElems];

			for (int i=0; i < numElems; i++) {
				int anInt = bucket0[i];
				byte aBit = (byte)((anInt >>> bitPos) & 0x1);
				if (aBit == 0) {
					bucket0[bucket0i++] = anInt;
				}
				else {
					bucket1[bucket1i++] = anInt;
				}
			}
			
			// continue sorting with the next most significant bit
			for (int i=0; i < bucket1i; i++) {
				bucket0[bucket0i++] = bucket1[i];
			}
		}

		// Set sorted elements back into original array
		for (int i=0; i < numElems; i++) {
			elements.set(i, (T)(new Integer(bucket0[i])));
		}
	}
	
	

	/**
	 * LSB algorithm to sort Integer numbers.
	 * 
	 * Note this solution works using just two buckets (base2)
	 * so that all Integers, regardless of base can be sorted.
	 * 
	 * @param elements
	 * @return
	 */
	@SuppressWarnings ("unchecked")
	public static <T extends Comparable<? super T>> void sortInPlace(List<T> elements) {

		if (elements == null) {
			return;
		}
		
		int numElems = elements.size();
		if (numElems <= 1) {
			return;
		}

		
		int[] bucket0 = new int[numElems];
		for (int i=0; i < numElems; i++) {
			bucket0[i] = ((Integer)elements.get(i)).intValue();
		}
		
		// For each increasing significant bit position
		int lowestOnesBitPos  = getLowestOnesBitPos(bucket0);
		int highestOnesBitPos = getHighestOnesBitPos(bucket0); 
		for (int bitPos=lowestOnesBitPos; bitPos <= highestOnesBitPos; bitPos++) {
			
			int bucket0i = 0;
			int bucket1i = numElems-1;
			while (bucket0i < bucket1i) {
				int anInt = bucket0[bucket0i];
				byte aBit = (byte)((anInt >>> bitPos) & 0x1);
				if (aBit == 0) {
					bucket0[bucket0i++] = anInt;
				}
				else {
					bucket0[bucket0i] = bucket0[bucket1i];
					bucket0[bucket1i--] = anInt;
				}
				System.out.print("Pass: " + bitPos + " ");
				DataGenerator.println(bucket0);
			}
			
//			System.out.print("Pass: " + bitPos + " ");
//			DataGenerator.println(bucket0);
		}

		// Set sorted elements back into original array
		for (int i=0; i < numElems; i++) {
			elements.set(i, (T)(new Integer(bucket0[i])));
		}
	}
	
	
	/**
	 * Returns the position of the lowest ones bit in our data
	 * Note this is not necessarily the lowest base10 value.
	 * Consider: 12 (1100) and 13 (1101).  Although 12 is
	 * less than 13, the lowest ones bit position is 0 which 
	 * is set by 13.
	 * 
	 * @param data
	 * @return
	 */
	private static int getLowestOnesBitPos(int[] data) {
		
		int lowestOnesBitVal = Integer.MAX_VALUE;
		int numElems = data.length;

		// optimization to stop early if we find a 1 at bit0 since can't go any lower
		for (int i=0; i < numElems; i++) {

			if (data[i] == 0) {
				continue;
			}

			int lowOneBitVal = data[i] & -data[i];  //~(data[i] - 1) & data[i];
			if (lowOneBitVal == 1) {
				return 0;
			}
			else if (lowOneBitVal < lowestOnesBitVal) {
				lowestOnesBitVal = lowOneBitVal;
			}
		}
		
		return (int)(Math.log(lowestOnesBitVal) / Math.log(2));
	}
	
	
	
	/**
	 * Returns the position of the highest ones bit in the
	 * given data array.
	 * 
	 * 
	 * 
	 * @param data
	 * @return
	 */
	private static int getHighestOnesBitPos(int[] data) {
		
		int bit30 = 0x40000000;
		
		int max = 0;
		int numElems = data.length;
		for (int i=0; i < numElems; i++) {
			int anInt = data[i];
			
			// stop if bit30 is set since it quantifies the largest positive ints
			if ((anInt & bit30) > 0) {
				return 31;
			}
			else if (anInt > max) {
				max = anInt;
			}
		}
		
		// Get high one bit value
		max |= (max >>  1);
		max |= (max >>  2);
		max |= (max >>  4);
		max |= (max >>  8);
		max |= (max >> 16);
		max = max - (max >>> 1);

		return (int)(Math.log(max) / Math.log(2));
	}
	

	
	
	public static void sort(int[] elements) {
		sort(elements, 0, elements.length-1);
	}

	public static void sort(int[] elements, int start, int end) {
		
		int numElems  = end-start+1;
		int[] bucket0 = new int[numElems];
		for (int i=0; i < numElems; i++) {
			bucket0[i] = elements[i+start];
		}
		
		// For each increasing significant bit position
		int lowestOnesBitPos  = getLowestOnesBitPos(bucket0);
		int highestOnesBitPos = getHighestOnesBitPos(bucket0); 
		for (int bitPos=lowestOnesBitPos; bitPos <= highestOnesBitPos; bitPos++) {
			
			int bucket0i  = 0;
			int bucket1i  = 0;
			int[] bucket1 = new int[numElems];

			for (int i=0; i < numElems; i++) {
				int anInt = bucket0[i];
				byte aBit = (byte)((anInt >>> bitPos) & 0x1);
				if (aBit == 0) {
					bucket0[bucket0i++] = anInt;
				}
				else {
					bucket1[bucket1i++] = anInt;
				}
			}
			
			// continue sorting with the next most significant bit
			for (int i=0; i < bucket1i; i++) {
				bucket0[bucket0i++] = bucket1[i];
			}
		}

		// Set sorted elements back into original array
		for (int i=0; i < numElems; i++) {
			elements[i+start] = bucket0[i];
		}
	}
	
	
	
	public String getName() { 
		return "RadixSort";
	}

	
	
	public static void main(String[] args) {
		
		List<Integer> data = DataGenerator.toArrayList(DataGenerator.getOrderedData(3, 2, 100, false));
		System.out.println(data);
		RadixSort.sortInPlace(data);
		System.out.println(data);
		System.out.println(isSorted(data));

	
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
//		System.out.println(isSorted(data2));
//	
//		System.out.println("Test 3");
//		int[] data3 = new int[2];
//		data3[0] = 5;
//		data3[1] = 1;
//		DataGenerator.println(data3);
//		RadixSort.sort(data3, 0, data3.length-1);
//		DataGenerator.println(data3);
//		System.out.println(isSorted(data3));
//		System.out.println();
//	
//		System.out.println("Test 4");
//		List<Integer> data4 = new ArrayList<Integer>();
//		data4.add(new Integer(5));
//		data4.add(new Integer(1));
//		System.out.println(data4);
//		RadixSort rs = new RadixSort();
//		rs.sort(data4);
//		System.out.println(data4);
//		System.out.println(isSorted(data4));
	}
	
}