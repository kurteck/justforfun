package prime;

/**
 * Decomposes a given number to it's prime factors
 */
public class NumberToPrimeFactors {
	
	public static void printPrimeFactors(int num) {
		
		PrimeGenerator pg = new PrimeGenerator();
		boolean[] primes = pg.generate(num);
		
		if (primes[num]) {
			System.out.println(num);
			return;
		}
		
		int i = 2;
		while (num > 0 && i < primes.length) {
			
			if (primes[i]) {
				
				int exp = 0;
				while (num % i == 0) {
					exp++;
					num = num / i;
				}

				if (exp > 0) {
					System.out.print(i + "^" + exp + " + ");
				}

			}
			
			i++;
		}
	}
	
	
	private static int power(int base, int exp) {

		int result = 1;
		for (int i=0; i < exp; i++) {
			result = result * base; 
		}
		
		return result;
	}
	
	
	private static int pow(int base, int exp)
	{
	    int result = 1;
	    while (exp > 0)
	    {
	        if ((exp & 0x1) == 1) {
	            result = result * base;
	        }
	        
	        exp = exp >>> 1;
	        base = base * base;
	    }

	    return result;
	}
	
	
	public static void main(String[] args) {
	
		System.out.print("Prime Factors of 24 are: "); NumberToPrimeFactors.printPrimeFactors(24); System.out.println();
		System.out.print("Prime Factors of 15 are: "); NumberToPrimeFactors.printPrimeFactors(15); System.out.println();
		
	}
}