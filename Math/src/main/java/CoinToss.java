import java.text.DecimalFormat;


public class CoinToss {
	
	int maxStreakLen   = 0;
	int maxStreakFlips = 0;
	
	
	public CoinToss(int numFlips, int runLength, int iters) {
		
//		double expectedValue = (numFlips - runLength + 1) * Math.pow(pHeads, (double)(runLength-1));
//		double observedValue = runExperiment(numFlips, runLength, pHeads, iters);
//		System.out.println("Expected observations of streaks of length " + runLength + " in " + numFlips + " flips is " + expectedValue);
//		System.out.println("Observed " + observedValue + " streaks in " + iters  + " iterations.");
//		System.out.print("MaxStreakLen is " + maxStreakLen + " -> "); printlnBits(maxStreakFlips); 

		int[] results = runExperiment(numFlips, runLength, iters);
		printResults(results, numFlips, runLength, iters);
	}
	
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		CoinToss coinToss = new CoinToss(3, 2, 10000000);
		long stop = System.currentTimeMillis();
		System.out.println();
		System.out.println("Runtime: " + (stop-start) + "ms");
	}

	
	
	private int[] runExperiment(int numFlips, int runLength, int iters) {

		int[] results = new int[33];
		
		for (int i=0; i < iters; i++) {

			int coinFlips  = flipCoins(numFlips);
			int[] runCount = countRunLengths(coinFlips, numFlips);
			for (int runLen = 0; runLen <= 32; runLen++) {
				results[runLen] += runCount[runLen];
			}
		}
		
		return results;
	}

	
	private void printResults(int[] results, int numFlips, int runLength, int iters) {

		double expectedValue = (numFlips - runLength + 1) * Math.pow(0.5, (double)(runLength-1));
		double observedValue = (double)results[runLength] / (double)iters;
		System.out.println("Expected observations of streaks of length " + runLength + " in " + numFlips + " flips is " + expectedValue);
		System.out.println("Observed " + observedValue + " streaks in " + iters  + " iterations.");
		System.out.print("MaxStreakLen is " + maxStreakLen + " -> "); printlnBits(maxStreakFlips); 
		System.out.println();

		System.out.println("------ Full Results ------");
		DecimalFormat df = new DecimalFormat("#00.000000");
		for (int runLen = 0; runLen <= 32; runLen++) {
			double percent = ((double)(results[runLen]) / (double)results[0] * 100);
			double observs = ((double)(results[runLen] / (double)iters));
			System.out.printf("%-2s  %s%%  %8s  %s\n", runLen, df.format(percent), results[runLen], df.format(observs));
		}
		System.out.println("----------------------");
		System.out.println("Total Streaks " + results[0]);
	}
	
	
	
	private int flipCoins(int numFlips) {
		
		if (numFlips < 0 || numFlips > 32) {
			return 0;
		}
		
		int result = 0;
		for (int i=0; i < numFlips; i++) {
			if (Math.random() >= 0.5) {
				result |= 1 << i;
			}
		}
		
		return result;
	}
	
	
	/**
	 * Counts number of "runLength" streaks in the given coinFlips
	 * All counts are returned in an array base 1 to represent
	 * streaks of 1 to 32.
	 * 
	 * @param coinFlips
	 * @param numFlips
	 * @param runLength
	 * @return
	 */
	private int[] countRunLengths(int coinFlips, int numFlips) {

		int[] runCount = new int[33];		// streaks of 1 - 32

		int streak = 1;
		int match  = coinFlips & 0x1;
		for (int bitI = 1; bitI < numFlips; bitI++) {

			int bitAtI = (coinFlips >> bitI) & 0x1;
			if (bitAtI == match) {
				streak++;
			}
			// Add number of "runLength" groups in "streak" bits
			else {
				for (int runLen = 1; runLen <= streak; runLen++) {
					runCount[runLen] += streak - runLen + 1;
					runCount[0] += runLen;
				}

				if (streak > maxStreakLen) {
					maxStreakLen   = streak;
					maxStreakFlips = coinFlips;
				}
				streak = 1;
				match  = bitAtI;
			}
		}

		// Add in last streak
		for (int runLen = 1; runLen <= streak; runLen++) {
			runCount[runLen] += streak - runLen + 1;
			runCount[0] += runLen;
		}


		if (streak > maxStreakLen) {
			maxStreakLen = streak;
			maxStreakFlips = coinFlips;
		}
		
		return runCount;
	}
	
	
	private double runSingleExperiment(int numFlips, int runLength, int iters) {

		double result = 0;
		for (int i=0; i < iters; i++) {

			int coinFlips = flipCoins(numFlips);
			int runCount  = countRunLength(coinFlips, numFlips, runLength);
			result = result + runCount;
		}

		return result / iters;
	}

	/**
	 * Counts number of "runLength" streaks in the given coinFlips
	 * 
	 * @param coinFlips
	 * @param numFlips
	 * @param runLength
	 * @return
	 */
	private int countRunLength(int coinFlips, int numFlips, int runLength) {
		
		int result = 0;
		int streak = 1;
		int match  = coinFlips & 0x1;
		for (int bitI = 1; bitI < numFlips; bitI++) {

			int bitAtI = (coinFlips >> bitI) & 0x1;
			if (bitAtI == match) {
				streak++;
			}
			// Add number of "runLength" groups in "streak" bits to the total number
			else {
				if (streak >= runLength) {
					result += (streak - runLength + 1);
				}
				if (streak > maxStreakLen) {
					maxStreakLen   = streak;
					maxStreakFlips = coinFlips;
				}
				streak = 1;
				match  = bitAtI;
			}
		}

		// Add in last streak
		if (streak >= runLength) {
			result += (streak - runLength + 1);
		}
		if (streak > maxStreakLen) {
			maxStreakLen = streak;
			maxStreakFlips = coinFlips;
		}
		
		return result;
	}
	
	

	
	
	
	private void printBits(int bits) {

		for (int i=31; i >= 0; i--) {
			System.out.print((bits >> i) & 0x1);
		}
	}
	
	private void printlnBits(int bits) {

		printBits(bits);
		System.out.println();
	}
}