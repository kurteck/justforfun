package cards;

public enum Suit {

	CLUB("Club", "C"),
	SPADE("Spade", "S"),
	HEART("Heart", "H"),
	DIAMOND("Diamond", "D");

	String name;
	String symbol;
	
	private Suit(String name, String symbol) {
		this.name   = name;
		this.symbol = symbol;
	}

	public String toString() {
		return name;
	}
	
}