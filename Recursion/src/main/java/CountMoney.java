
public class CountMoney {
	
	public enum Coin {

		PENNY(1), NICKLE(5), DIME(10), QUARTER(25);
		
		private int cents;
		private Coin(int cents) {
			this.cents = cents;
		}
	}
	
	
	public static int countMoney(int total) {
		
		if (total <= 0) {
			return 0;
		}
		
		return countMoney(total, "", Coin.PENNY, 0);
	}
	
	
	private static int countMoney(int total, String path, Coin min, int current) {
		
		if (current > total) {
			return 0;
		}
		if (current == total) {
			return 1;
		}
		
		int count = 0;
		if (Coin.PENNY.cents >= min.cents) {
			count += countMoney(total, path + "P", Coin.PENNY,  current + Coin.PENNY.cents);
		}
		if (Coin.NICKLE.cents >= min.cents) {
			count += countMoney(total, path + "N", Coin.NICKLE, current + Coin.NICKLE.cents);
		}
		if (Coin.DIME.cents >= min.cents) {
			count += countMoney(total, path + "D", Coin.DIME,   current + Coin.DIME.cents);
		}
		if (Coin.QUARTER.cents >= min.cents) {
			count += countMoney(total, path + "Q", Coin.QUARTER, current + Coin.QUARTER.cents);
		}
		
		return count;
	}

	
	public static void main(String[] args) {

		System.out.println("Number of ways to make   0c is " + countMoney(0));
		System.out.println("Number of ways to make   1c is " + countMoney(1));
		System.out.println("Number of ways to make   5c is " + countMoney(5));
		System.out.println("Number of ways to make  10c is " + countMoney(10));
		System.out.println("Number of ways to make  25c is " + countMoney(25));
		System.out.println("Number of ways to make 100c is " + countMoney(100));
		System.out.println("Number of ways to make 200c is " + countMoney(200));
		System.out.println("Number of ways to make 500c is " + countMoney(500));
	}
}