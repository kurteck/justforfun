package cards;
public class StandardCard extends Card {

	// Design decision to not use an enum so that we can 
	// 1) extend default Card behaviors and 
	// 2) Use polymorphism by loading any type of card into a Deck
	public static final StandardCard TWO_OF_HEARTS = new StandardCard("Two", 2, Suit.HEART);
	public static final StandardCard SIX_OF_HEARTS = new StandardCard("Six", 6, Suit.HEART);
	public static final StandardCard TEN_OF_HEARTS = new StandardCard("Ten", 10, Suit.HEART);
	public static final StandardCard ACE_OF_HEARTS = new StandardCard("Ace", 14, Suit.HEART);
	public static final StandardCard TWO_OF_DIAMONDS = new StandardCard("Two", 2, Suit.DIAMOND);
	public static final StandardCard SIX_OF_DIAMONDS = new StandardCard("Six", 6, Suit.DIAMOND);
	public static final StandardCard TEN_OF_DIAMONDS = new StandardCard("Ten", 10, Suit.DIAMOND);
	public static final StandardCard ACE_OF_DIAMONDS = new StandardCard("Ace", 14, Suit.DIAMOND);
	public static final StandardCard TWO_OF_CLUBS = new StandardCard("Two", 2, Suit.CLUB);
	public static final StandardCard SIX_OF_CLUBS = new StandardCard("Six", 6, Suit.CLUB);
	public static final StandardCard TEN_OF_CLUBS = new StandardCard("Ten", 10, Suit.CLUB);
	public static final StandardCard ACE_OF_CLUBS = new StandardCard("Ace", 14, Suit.CLUB);
	public static final StandardCard TWO_OF_SPADES = new StandardCard("Two", 2, Suit.SPADE);
	public static final StandardCard SIX_OF_SPADES = new StandardCard("Six", 6, Suit.SPADE);
	public static final StandardCard TEN_OF_SPADES = new StandardCard("Ten", 10, Suit.SPADE);
	public static final StandardCard ACE_OF_SPADES = new StandardCard("Ace", 14, Suit.SPADE);
	
	int value;
	Suit suit;
	
	private StandardCard(String name, int value, Suit suit) {
		
		super(name);
		this.value = value;
		this.suit = suit;
	}
	
	public String toString() {
		return (name + " of " + suit + "s");
	}
}