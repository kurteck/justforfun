package cards;
import java.util.ArrayList;
import java.util.List;

/**
 * A standard deck of 52 playing cards.  Does not include Jokers.
 */
//public class StandardDeck<E extends StandardCard> extends Deck<StandardCard> {
public class StandardDeck extends Deck {

	private static final long serialVersionUID = 5470096193312646662L;
	public static final int NUM_CARDS = 16;
	
	public StandardDeck() {
		super(NUM_CARDS);
	}
	
	/**
	 * Returns all cards in a StandardDeck sorted by value
	 */
	public List<Card> getNewDeck() {
		
		List<Card> deck = new ArrayList<Card>(NUM_CARDS);

		deck.add(StandardCard.TWO_OF_HEARTS);
		deck.add(StandardCard.SIX_OF_HEARTS);
		deck.add(StandardCard.TEN_OF_HEARTS);
		deck.add(StandardCard.ACE_OF_HEARTS);
		deck.add(StandardCard.TWO_OF_DIAMONDS);
		deck.add(StandardCard.SIX_OF_DIAMONDS);
		deck.add(StandardCard.TEN_OF_DIAMONDS);
		deck.add(StandardCard.ACE_OF_DIAMONDS);
		deck.add(StandardCard.TWO_OF_CLUBS);
		deck.add(StandardCard.SIX_OF_CLUBS);
		deck.add(StandardCard.TEN_OF_CLUBS);
		deck.add(StandardCard.ACE_OF_CLUBS);
		deck.add(StandardCard.TWO_OF_SPADES);
		deck.add(StandardCard.SIX_OF_SPADES);
		deck.add(StandardCard.TEN_OF_SPADES);
		deck.add(StandardCard.ACE_OF_SPADES);

		return deck;
	}
	
	public static void main(String[] args) {
		StandardDeck sd = new StandardDeck();
		sd.printDeck();
		sd.shuffle();
		System.out.println();
		System.out.println("After Suffling");
		sd.printDeck();
	}
}