package prime;

public class PrimeGenerator {
	
	private PrimeGeneratorStrategy algo;
	private long genTime;
	private boolean[] primes;
	
	public PrimeGenerator() {
		this.algo = new SieveOfEratosthenes();
	}

	public PrimeGenerator(PrimeGeneratorStrategy algo) {
		this.algo = algo;
	}

	
	public boolean[] generate(int max) {
		genTime = System.currentTimeMillis();
		primes = algo.generate(max);
		genTime = System.currentTimeMillis() - genTime;
		
		return primes;
	}
	
	
	public void printPrimes() {
		
		int printed = 0;
		int length  = primes.length;
		for (int i=0; i < length; i++) {

			if (primes[i]) {
				System.out.print(i + " ");
				if (++printed % 20 == 0) {
					System.out.println();
				}
			}
		}
	}
	
	/**
	 * Returns 0 if there are no more primes after startIndex
	 * 
	 * @param primes
	 * @param lastPrime
	 * @return
	 */
	public int getNextPrime(int startIndex) {
		
		int index  = startIndex;
		int length = primes.length;
		while (++index < length) {
			if (primes[index]) {
				return index;
			}
		}
		
		return 0;
	}

	
	/**
	 * Returns the number of milliseconds it took to generate the primes
	 * 
	 * @return
	 */
	public long getGenTime() {
		return genTime;
	}
	
	
	public static void main(String[] args) {
		
		PrimeGenerator pg1 = new PrimeGenerator(new SieveOfEratosthenes());
		pg1.generate(1000);
		System.out.println("PG1 generated primes in " + pg1.getGenTime() + " milliseconds.");
		pg1.printPrimes();
		System.out.println();
		System.out.println();
		
		PrimeGenerator pg2 = new PrimeGenerator(new NaivePrimeGeneratorStrategy());
		pg2.generate(1000000);
		System.out.println("PG2 generated primes in " + pg2.getGenTime() + " milliseconds.");
//		pg2.printPrimes();
		System.out.println();
	}
}