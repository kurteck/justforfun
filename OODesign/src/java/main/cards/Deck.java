package cards;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * A deck is a collection of all cards required to play a game.
 * 
 * This class maintains the state of cards of the deck and provides
 * methods to modify the deck.  Cards in the deck could natually be 
 * stored in Stack since most card games use the notion of picking 
 * the next (top) card from the deck.  However, to accommodate 
 * card games where cards could be picked from anywhere in the deck
 * (e.g "Go Fish"), the cards are stored in an Array.  This affords
 * us the ability to choose a random card from anywhere in the deck
 * in O(1) time.  To avoid the penalty of removing cards from the
 * deck (elements from an array), we use a separate array that 
 * flags whether or not a card is available.  This slightly increases 
 * our memory footprint at the benefit of performance.
 */
public abstract class Deck extends ArrayList<Card> {
	
	private static final long serialVersionUID = -5299905478088774970L;
	private static int MAX_SHUFFLES = 10;

	private BitSet availCards;
	
	
	/**
	 * All subclasses must return an ordered list of cards that defined this deck.
	 * 
	 * @return
	 */
	protected abstract List<Card> getNewDeck();

	
	protected Deck(int numCards) {
		
		super(numCards);
		availCards = new BitSet(numCards);
		init();
	}
	
	public int getNumCards() { 
		return size();
	}
	
	public Card getTopCard() {
		return getCard(0);
	}

	public Card getRandomCard() {
		return getCard((int)(Math.random() * size()));
	}
	
	public Card getCard(int i) {
		availCards.set(i, false);
		return get(i);
	}
	
	public Card peekTopCard() { 
		return get(0);
	}
	
	
	/**
	 * Randomizes the order of all cards in the deck.
	 */
	public void shuffle() {
		int numShuffles = (int)(Math.random() * MAX_SHUFFLES);
		for (int i=0; i < numShuffles; i++) {
			
			int numSwaps = (int)(Math.random() * size() * 2);
			for (int j=0; j < numSwaps; j++) {
				
				int card1 = (int)(Math.random() * size());
				int card2 = (int)(Math.random() * size());
				
				Card temp = get(card1);
				set(card1, get(card2));
				set(card2, temp);
				
				boolean card1State = availCards.get(card1);
				availCards.set(card1, availCards.get(card2));
				availCards.set(card2, card1State);
			}
		}
	}
	

	/**
	 * Loads all cards into this deck and makes them available
	 */
	private void init() {

		for (Card c : getNewDeck()) {
			this.add(c);
		}
		
		reset();
	}
	

	/**
	 * Makes all cards available for use
	 */
	public void reset() {
		availCards.set(0, availCards.size() - 1, true);
	}
	

	public void printDeck() {
		
		for (int i=0; i < size(); i++) {
			System.out.println(get(i));
		}
	}
}