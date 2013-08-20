/**
 * This class loves to add (and who doesn't!)  
 * 
 * All operations in this class are based upon addition alone.  
 * Not the most efficient of ways to get the job done, but it
 * demonstrates that addition can be used as the base for many
 * other mathematical operations.
 *  
 * @author KAE
 */
public class Adder {

	/**
	 * @param a
	 * @param b
	 * @return a + b
	 */
	public static int add(int a, int b) {
		return a+b;
	}
	

	/**
	 * @param a
	 * @return -1 * a
	 */
	public static int negate(int a) {
		
		int result = 0;
		int addval = (a > 0) ? -1 : 1;
		while (a != 0) {
			a = a + addval;
			result = result + addval; 
		}
		
		return result;
	}
	
	
	/**
	 * @param a
	 * @return the positive value of a
	 */
	public static int abs(int a) {

		return (a >= 0) ? a : Adder.negate(a);
	}

	
	/**
	 * @param a
	 * @param b
	 * @return a - b
	 */
	public static int subtract(int a, int b) {
		return a + Adder.negate(b);
	}
	

	/**
	 * @param a
	 * @param b
	 * @return a * b
	 */
	public static int multiply(int a, int b) {

		if (a < 0 && b < 0) {
			a = Adder.negate(a);
			b = Adder.negate(b);
		}
		else if (b < 0) {
			return multiply(b,a);
		}
			
		int result = 0;
		for (int i=0; i < b; i++) {
			result += a;
		}
		
		return result;
	}
	
	
	/**
	 * @param a
	 * @param b
	 * @return a / b (floored)
	 */
	public static int divide(int a, int b) 
	throws DivideByZeroException {

		if (b == 0) {
			throw new DivideByZeroException();
		}
		
		int sign = 1;
		// A and B are both negative;
		if (a < 0 && b < 0) {
			a = Adder.negate(a);
			b = Adder.negate(b);
		}
		// A is negative and B is positive
		else if (a < 0) {
			a = Adder.negate(a);
			sign = -1;
		}
		// A is positive and B is negative
		else if (b < 0) {
			b = Adder.negate(b);
			sign = -1;
		}
		
		// a and b will always be positive at this point
		int result = 0;
		int addval = b;
		while (b <= a) {
			b += addval; // b is always positive
			result++;
		}

		return (sign == 1) ? result : Adder.negate(result);
	}
	
	
	public static void main(String[] args) {
		
		System.out.printf("Negate  5 = %-8s\n", Adder.negate(5));
		System.out.printf("Negate -5 = %-8s\n", Adder.negate(-5));
		System.out.printf("Abs  5 = %-8s\n", Adder.abs(5));
		System.out.printf("Abs -5 = %-8s\n", Adder.abs(-5));
		System.out.printf("Add  5 +  2 = %-8s\n", Adder.add(5, 2));
		System.out.printf("Add  5 + -2 = %-8s\n", Adder.add(5, -2));
		System.out.printf("Add -5 +  2 = %-8s\n", Adder.add(-5, 2));
		System.out.printf("Add -5 + -2 = %-8s\n", Adder.add(-5, -2));
		System.out.printf("Subtract  5 -  2 = %-8s\n", Adder.subtract(5, 2));
		System.out.printf("Subtract  5 - -2 = %-8s\n", Adder.subtract(5, -2));
		System.out.printf("Subtract -5 -  2 = %-8s\n", Adder.subtract(-5, 2));
		System.out.printf("Subtract -5 - -2 = %-8s\n", Adder.subtract(-5, -2));
		System.out.printf("Multiply  5 *  2 = %-8s\n", Adder.multiply(5, 2));
		System.out.printf("Multiply  5 * -2 = %-8s\n", Adder.multiply(5, -2));
		System.out.printf("Multiply -5 *  2 = %-8s\n", Adder.multiply(-5, 2));
		System.out.printf("Multiply -5 * -2 = %-8s\n", Adder.multiply(-5, -2));
		System.out.printf("Multiply  8 *  0 = %-8s\n", Adder.multiply(8, 0));

		try {
			System.out.printf("Divide    15 /  3 = %-8s\n", Adder.divide(15, 3));
			System.out.printf("Divide    15 / -3 = %-8s\n", Adder.divide(15, -3));
			System.out.printf("Divide   -15 /  3 = %-8s\n", Adder.divide(-15, 3));
			System.out.printf("Divide   -15 / -3 = %-8s\n", Adder.divide(-15, -3));
			System.out.printf("Divide    15 /  2 = %-8s\n", Adder.divide(15, 2));
			System.out.printf("Divide    10 /  1 = %-8s\n", Adder.divide(10, 1));
			System.out.printf("Divide    10 / -1 = %-8s\n", Adder.divide(10, -1));
			System.out.printf("Divide   -10 /  1 = %-8s\n", Adder.divide(-10, 1));
			System.out.printf("Divide   -10 / -1 = %-8s\n", Adder.divide(-10, -1));
			System.out.printf("Divide    5 / 5 = %-8s\n", Adder.divide(5, 5));
			System.out.printf("Divide    4 / 5 = %-8s\n", Adder.divide(4, 5));
			System.out.printf("Divide    0 / 5 = %-8s\n", Adder.divide(0, 5));
			System.out.printf("Divide    5 / 0 = %-8s\n", Adder.divide(5, 0));
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}