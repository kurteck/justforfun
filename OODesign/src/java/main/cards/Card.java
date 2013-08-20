package cards;
/**
 * This class is a base class that contains attributes common to most 
 * card games.  A Card may be extended in order to provide behavior 
 * that is specific to specialized types of card games (e.g. Magic, Settlers, 
 * Poker).
 *
 */
public class Card {
	
	String name;
	String desc;
	String img;
	
	public Card(String name) {
		this(name, null, null);
	}

	public Card(String name, String desc, String img) {
		
		this.name = name;
		this.desc = desc;
		this.img  = img;
	}
}