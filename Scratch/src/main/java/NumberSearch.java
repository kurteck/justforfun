import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class NumberSearch {
	
	public static String TEST_FILE_1   = "c:/temp/allints.txt";
	public static final int MAX_INT    = 65536;  // 2^16
	public static final int NUM_CHUNKS = 4096;   // 2^12
	public static final int INTS_PER_CHUNK = MAX_INT / NUM_CHUNKS;

	
	public NumberSearch() {
		;
	}
	
	
	/**
	 * Each chunk totals to [(n)(n-1)]/2 (start at base 0).
	 * The difference between each chunk's total is 4096^2
	 * since each chunk holds 4096 numbers.
	 * 
	 * So,
	 * chunk #0 = (4096*4095)/2         = 8386560
	 * chunk #1 = chunk #1 + 1*(4096^2) = 25163776
	 * chunk #2 = chunk #1 + 2*(4096^2) = 41940992
	 * chunk #9 = chunk #1 + 9*(4096^2) = 159381504
	 * chunk #4095                      = 68711086080
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static int findMissingNumber()
	throws FileNotFoundException, IOException {	

		long[] chunk  = new long[NUM_CHUNKS];

		int chunki    = 0;
		int oldChunki = 0;
		Scanner scanner = new Scanner(new FileReader(TEST_FILE_1));
		while (scanner.hasNextInt()) {

			int anInt = scanner.nextInt();
			chunki = (anInt-1) / INTS_PER_CHUNK;
			chunk[chunki] += anInt;

			// see if this last chunk has the missing number
			if (chunki != oldChunki || !scanner.hasNextInt()) {
				long expectedTotal = oldChunki*(INTS_PER_CHUNK)*(INTS_PER_CHUNK)+chunk[0];
//				System.out.println("Chunk: " + oldChunki + " Expected: " + expectedTotal + " Actual: " + chunk[oldChunki]);
				int difference = (int)(expectedTotal - chunk[oldChunki]);
				if (difference > 0) {
					scanner.close();
					return difference;
				}
				else {
					oldChunki = chunki;
				}
			}
		}

		scanner.close();
		return -1;
	}
	
	
	

	public static int findMissingNumber2()
	throws FileNotFoundException, IOException {	

		int expectedNum = 1;
		Scanner scanner = new Scanner(new FileReader(TEST_FILE_1));
		while (scanner.hasNextInt()) {
			if (scanner.nextInt() != expectedNum) {
				scanner.close();
				return expectedNum;
			}
			expectedNum++;
		}

		scanner.close();
		return expectedNum;
	}
	
	
	public static void generateFile() {
		
		FileWriter fw = null;
		try {
			fw = new FileWriter(TEST_FILE_1);
			int missing = (int)(Math.random() * MAX_INT) + 1;
			for (int i=1; i <= MAX_INT; i++) {
				if (i != missing) {
					fw.write(i + " ");
				}
			}
			System.out.println("Set missing number to: " + missing);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (fw != null) { try { fw.close(); } catch (Exception e2) {;} }
		}
	}

	
	
	public static int[] getDigitCount(int num) {
		
		int[] count = new int[10];
		
		count[0] = 1;
		for (int i=1; i <= num; i++) {
			int anInt = i;
			while (anInt > 0) {
				count[anInt % 10]++;
				anInt = anInt / 10;
			}
		}
		
		for (int i=0; i < 10; i++) {
			System.out.println(i + " " + count[i]);
		}
		return count;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			NumberSearch.generateFile();  // Missing # is 9706624

			long start1  = System.currentTimeMillis();
			int missing1 = NumberSearch.findMissingNumber();
			long end1    = System.currentTimeMillis();
			System.out.println("Found missing number: " + missing1 + " elapsed " + (end1-start1));

			long start2  = System.currentTimeMillis();
			int missing2 = NumberSearch.findMissingNumber2();
			long end2    = System.currentTimeMillis();
			System.out.println("Found missing number: " + missing2 + " elapsed " + (end2-start2));
			
//			getDigitCount(99997);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}