package prime;

public class SieveOfEratosthenes implements PrimeGeneratorStrategy {
	
	/**
	 * Returns an boolean array with primes marked true and composite values marked false.
	 * 
	 * @param max
	 * @return
	 */
	public boolean[] generate(int max) {

		int length = max+1;
		boolean[] primes = initPrimes(length);
		
		int maxStart = (int)Math.sqrt(new Double(Integer.MAX_VALUE));
		int aPrime   = getNextPrime(primes, 1);
		while (aPrime != 0) {

			for (int i = (aPrime*aPrime); i < length; i += aPrime) {
				primes[i] = false;
			}

			aPrime = getNextPrime(primes, aPrime);
			if (aPrime > maxStart) {
				break;
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
	
	
	/**
	 * Returns 0 if there are no more primes after lastPrime in the given array
	 * 
	 * @param primes
	 * @param lastPrime
	 * @return
	 */
	private int getNextPrime(boolean[] primes, int lastPrime) {
		
		int index  = lastPrime;
		int length = primes.length;
		while (++index < length) {
			if (primes[index]) {
				return index;
			}
		}
		
		return 0;
	}
	
}

