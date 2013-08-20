package datagen;
import java.util.ArrayList;

public class DataGenerator {
	
	// Random, Sorted

	// Uniqueness
	// Spread
	// Repeating
	// Shape (quadratic, linear, logarithmic, exponential) 

//  b. Ordering   (ascending, descending, mostly ascending, mostly descending, random, patterned)
//  c. Uniqueness (no duplicates, some duplicates, many duplicates, all duplicates)
//  d. Data Size  ("large", "small")
//  e. Deviation  (clustered, spread out, min, max, mean)
	
	
	/**
	 * @param num
	 * @param min
	 * @param max
	 * @return
	 */
	public static int[] getRandomData(int num, int min, int max) { 
		
		long range = (long)max - (long)min + 1;
		int[] data = new int[num];
		for (int i=0; i < num; i++) {
			data[i] = (int)((Math.random() * range) + min);
		}

		return data;
	}
	
	
	/**
	 * @param num		The number of elements to generate
	 * @param start		The initial starting value
	 * @param jitter	How much movement up or down from the expected ordered value 
	 * @param ascending	True if the data generally increases; false if decreases
	 * @return
	 */
	public static int[] getPartiallySortedData(int num, int start, int jitter, boolean ascending) {
		
		int[] data = new int[num];
		int sign = ascending == true ? 1 : -1;
		for (int i=0; i < num; i++) {
			int jitterSign = (int)(Math.random() * 2) == 0 ? -1 : 1;
			data[i] = start + (sign * i) + (jitterSign * (int)(Math.random() * (jitter+1)));
		}

		return data;
	}
	

	/**
	 * Returns a strictly increasing or decreasing set of data
	 * 
	 * For example: 0,1,2,3,4,5,6,7,8,9
	 * 
	 * 
	 * @param num         The number of elements to generate
	 * @param start	      The initial starting value
	 * @param uniqueness  From 0-100%. 100% = All unique.  0% = All Duplicates
	 * @param ascending   True if the data strictly increases; false if decreases
	 */
	public static int[] getOrderedData(int num, int start, int uniqueness, boolean ascending) {

		int sign = ascending == true ? 1 : -1;
		int numUnique = (int)((long)uniqueness * num / 100);
		
		int[] data = new int[num];
		for (int i=0; i < numUnique; i++) {
			data[i] = start + (sign * i);
		}

		for (int i=numUnique; i < num; i++) {
			data[i] = start + (sign * numUnique);
		}

		return data;
	}
	

	/**
	 * Returns a strictly increasing set of data that repeats some number of times.
	 * 
	 * For example: 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4
	 * This wave has amplitude=5, start=0, repeats=4, and ascending=true
	 *
	 * @param amplitude	The number of elements in the base pattern.  Total = amplitude * repeats
	 * @param start		The initial starting value
	 * @param repeats	The number of times to repeat the pattern
	 * @param ascending	True if the data strictly increases; false if decreases
	 */
	public static int[] getSawtoothWave(int num, int start, int repeats, boolean ascending) {

		int data[] = getOrderedData(num, start, 100, ascending);
		data = repeat(data, repeats);

		return data;
	}
	
	
	/**
	 * Returns data that strictly increases to a certain "height" and then strictly decreases
	 * back to its original starting value.
	 * 
	 * For example: 1, 2, 3, 4, 3, 2, 1, 2, 3, 4, 3, 2, 1, 2, 3, 4, 3, 2
	 * This wave has amplitude=4, start=1, repeats=3
	 * 
	 * @param amplitude	The number of elements on either side of the peak.  Must be > 1
	 * @param start		The initial starting value
	 * @param repeats	The number of times the wave is repeated
	 */
	public static int[] getTriangleWave(int amplitude, int start, int repeats) {

		int wavelength = (amplitude << 1) - 2;
		int num = wavelength * repeats; // + 1
		int data[] = new int[num];

		data[0] = start;
		for (int i=1; i < num; i++) {
			int inc = ((i-1) % wavelength) < (wavelength / 2) ? 1 : -1;
			data[i] = data[i-1] + inc;
		}
		
		return data;
	}
	
	
	/**
	 * Returns data that strictly flips between two values with some distance 
	 * between them.  
	 * 
	 * For example: 8, 8, 8, 8, 8, 2, 2, 2, 2, 2, 8, 8, 8, 8, 8, 2, 2, 2, 2, 2
	 * This wave has amplitude=3, wavelength=10, start=5, and repeats=2
	 * 
	 * @param amplitude		The height of each peak and trough
	 * @param wavelength	The distance between each successive peak (must be even)
	 * @param start			Shifts the wave up or down the y axis by this amount
	 * @param repeats		The number of times the wave is repeated
	 */
	public static int[] getSquareWave(int amplitude, int wavelength, int start, int repeats) {
		
		wavelength = wavelength + (wavelength % 2); // ensure wavelength is even
		int num    = wavelength * repeats;
		int data[] = new int[num];
		for (int i = 0; i < num; i++) {
			int sign = (i % wavelength) < (wavelength / 2) ? 1 : -1;
			data[i]  = start + (sign * amplitude);
		}
		
		return data;
	}
	
	
	/**
	 * @param amplitude		The height of each peak and trough
	 * @param wavelength	The distance between each successive peak (from 3-360)
	 * @param start			Shifts the wave up or down the y axis by this amount
	 * @param repeats		The number of times the wave is repeated
	 */
	public static int[] getSineWave(int amplitude, int wavelength, int start, int repeats) {

		int num = wavelength * repeats;
		int data[] = new int[num];

		int inc = 360 / wavelength;
		for (int i=0; i < num; i++) {
			double rads = (Math.PI/180) * (i % wavelength) * inc;
			data[i] = (int)(Math.sin(rads) * amplitude) + start;
		}

		return data;
	}

	
	/**
	 * Can we move in favor of triangle wave?
	 * @param num
	 * @param pctAscend
	 * @return
	 */
	public static int[] getSemiSortedInts(int num, int pctAscend) { 

		int numAscnd = pctAscend * (num-1) / 100;
		int[] data = new int[num];
		for (int i=0; i <= numAscnd; i++) {
			data[i] = i;
		}
		for (int i=numAscnd+1; i < num; i++) {
			data[i] = data[i-1]-1;
		}

		return data;
	}	
	
	

	/*** Helper functions to build more complex input data patterns ***/
	
	/**
	 * Reverses the given input.  
	 * Note the original array is modified - a copy is not returned.
	 * 
	 * @param input
	 */
	public static void reverse(int[] input) {

		int length = input.length;
		int iters  = length >> 1;
		for (int i=0; i < iters; i++) {
			input[i] ^= input[length-i-1];
			input[length-i-1] ^= input[i];
			input[i] ^= input[length-i-1];
		}
	}

	
	/**
	 * 
	 * @param pattern
	 * @param repeats
	 * @return
	 */
	public static int[] repeat(int[] pattern, int repeats) {

		int length = pattern.length;
		int[] data = new int[length*repeats];
		for (int i=0; i < length*repeats; i++) {
			data[i] = pattern[i % length];
		}
		
		return data;
	}

	
	/**
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static int[] join(int[] array1, int[] array2) {
		
		int[] joined = new int[array1.length + array2.length];
		System.arraycopy(array1, 0, joined, 0, array1.length);
		System.arraycopy(array2, 0, joined, array1.length, array2.length);

		return joined;
	}

	
	public static void shuffle(int[] data) {
		
		int numElems = data.length;
		for (int i=numElems-1; i > 0; i--) {
			int random = (int)(Math.random() * (i+1));
			swap(data, i, random);
		}
	}

	
	public static void swap(int[] data, int i, int j) {
		int temp = data[i];
		data[i]  = data[j];
		data[j]  = temp;
	}
	
	
	/**
	 * @param data
	 * @return
	 */
	public static int[] copy(int[] data) {
		return data.clone(); // performs better than System.arraycopy and Arrays.copyOf !
 	}

	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static ArrayList<Integer> toArrayList(int[] data) {

		ArrayList<Integer> list = new ArrayList<Integer>(data.length);
		for (int i=0; i < data.length; i++) {
			list.add(new Integer(data[i]));
		}
		
		return list;
	}
	
	
	/*** Helper functions to see and validate data ***/ 
	public static void println(int[] data) {
		
		System.out.print("[");
		for (int i=0; i < data.length; i++) {
			System.out.print(data[i] + (i < data.length-1 ? ", " : ""));
		}
		System.out.println("]");
	}

	public static void printlnFirst(int[] data, int firstN) {

		System.out.print("[");
		for (int i=0; firstN > 0; i++, firstN--) {
			System.out.print(data[i] + (firstN-1 > 0 ? ", " : ""));
		}
		System.out.println("]");
	}

	public static void printlnLast(int[] data, int lastN) {

		System.out.print("[");
		for (int i=data.length-lastN; lastN > 0; i++, lastN--) {
			System.out.print(data[i] + (lastN-1 > 0 ? ", " : ""));
		}
		System.out.println("]");
	}
	
	public static void println(Object[] elements, int start, int end) {
		
		System.out.print("[");
		for (int i=start; i <= end; i++) {
			System.out.print(elements[i] + (i < end ? ", " : ""));
		}
		System.out.println("]");
	}


	
	
	public static ArrayList<String> getUnsortedStrings(int num) {

		int strLen = (int)(Math.log(num) / Math.log(26)) + 1;
		ArrayList<String> data = new ArrayList<String>(num);
		for (int i=0; i < num; i++) {
			String aString = makeRandomString(strLen);
			data.add(aString);
		}
		
		return data;
	}
	
	
	private static String makeRandomString(int numChars) {
		StringBuffer sb = new StringBuffer(numChars);
		for (int i=0; i < numChars; i++) {
			char c = (char)((int)(Math.random() * 26) + (int)'a');
			sb.append(c);
		}
		
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
//		int[] random   = getRandomData(4, 0, 9);
//		int[] ordered  = getOrderedData(20, 10, 40, false);
//		int[] square   = getSquareWave(3, 10, 5, 2);
//		int[] triangle = getTriangleWave(5, 1, 3);
//		int[] sawtooth = getSawtoothWave(8, 1, 4, true);
//		int[] sine     = getSineWave(100, 24, 0, 3);
		int[] partial  = getPartiallySortedData(16, 0, 1, true);
		println(partial);
	}
}