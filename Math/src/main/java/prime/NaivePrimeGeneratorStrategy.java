package prime;

public class NaivePrimeGeneratorStrategy implements PrimeGeneratorStrategy {
	
	/**
	 * Returns an boolean array with primes marked true and composite values marked false.
	 * 
	 * @param max
	 * @return
	 */
	public boolean[] generate(int max) {

		int length = max+1;
		boolean[] primes = initPrimes(length);

		for (int aNum=2; aNum < length; aNum++) {
		
			int largestFactor = (int)Math.sqrt(new Double(aNum)) + 1; //aNum
			for (int j=2; j < largestFactor; j++) {
				
				if ((aNum % j) == 0) {
					primes[aNum] = false;
					break;
				}
			}
		}
			
		return primes;
	}
	
	
	/**
	 * 
	 * @param length
	 */
	private boolean[] initPrimes(int length) {
		
		boolean[] primes = new boolean[length];
		for (int i=1; i < length; i++) {
			primes[i] = true;
		}
		primes[0] = false;
		
		return primes;
	}

}

