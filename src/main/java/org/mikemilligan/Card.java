package org.mikemilligan;

/**
 * Represents a playing card with a value and suit.
 */
public class Card {
	
	/**
	 * Enumerates the possible values for a card.
	 */
	public enum Value { ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,  JACK, QUEEN, KING }

	/**
	 * Enumerates the possible suits for a card.
	 */
	public enum Suit { CLUBS, HEARTS, SPADES, DIAMONDS }
	
	/**
	 * Enumerates the available styles for displaying the card.
	 */
	public enum Style { NORMAL, PRETTY, COMPACT }
	
	private final Value value;
	private final Suit suit;
	private Style style;
	private boolean isDrawn;
	
	/**
	 * Constructs a card with the specified value and suit.
	 *
	 * @param value The value of the card.
	 * @param suit  The suit of the card.
	 */
	public Card(Value value, Suit suit) {
		this.value = value;
		this.suit = suit;
		style = Style.NORMAL;
		isDrawn = false;
	}
	
	/**
	 * Returns the value of the card.
	 *
	 * @return The value of the card.
	 */
	@SuppressWarnings("unused")
	public Value getValue() {
		return value;
	}
	
	/**
	 * Returns the suit of the card.
	 *
	 * @return The suit of the card.
	 */
	@SuppressWarnings("unused")
	public Suit getSuit() {
		return suit;
	}
	
	/**
	 * Sets the display style of the card.
	 *
	 * @param style The style to set.
	 */
	public void setStyle(Style style) {
		this.style = style;
	}
	
	/**
	 * Returns the display style of the card.
	 *
	 * @return The cards display style
	 */
	public Style getStyle() {
		return style;
	}
	
	/**
	 * Checks if the card has been drawn from the deck.
	 *
	 * @return True if the card has been drawn, False otherwise.
	 */
	public boolean isDrawn() {
		return isDrawn;
	}
	
	/**
	 * Marks the card as drawn from the deck.
	 */
	public void draw() {
		isDrawn = true;
	}
	
	/**
	 * Returns a string representation of the card.
	 *
	 * @return The card's value and suit in the format "VALUE of SUIT."
	 */
	@Override
	public String toString() {
		return switch (style) {
			case NORMAL -> normal();
			case PRETTY -> pretty();
			case COMPACT -> compact();
		};
	}
	
	/**
	 * Returns a normal string representation of the card.
	 *
	 * @return The card's value and suit in the format "VALUE of SUIT".
	 */
	public String normal() {
		return value.name() + " of " + suit.name();
	}
	
	/**
	 * Returns a prettier string representation of the card.
	 *
	 * @return The card's value and suit in the format "Value of Suit".
	 */
	public String pretty() {
		// Initial capitalization for values (KING => King)
		String valueStr = value.toString().charAt(0)
				+ value.toString().substring(1).toLowerCase();
		
		// Initial Capitalization for suits (CLUBS => Clubs)
		String suitStr = suit.toString().charAt(0)
				+ suit.toString().substring(1).toLowerCase();
		
		return valueStr + " of " + suitStr;
	}
	
	/**
	 * Returns a compact string representation of the card.
	 *
	 * @return The card's value as a digit for numbered cards, initial for face
	 * cards, and Unicode suit icon.
	 */
	public String compact() {
		String valueStr;
		// Turn number values into digits
		if (value.ordinal() >= Value.TWO.ordinal()
				&& value.ordinal() <= Value.TEN.ordinal()) {
			valueStr = String.valueOf(value.ordinal() + 1);
		} else {
		// Initialization for face cards (KING => K)
			valueStr = String.valueOf(value.toString().charAt(0));
		}
		// Unicode for suits (CLUBS => ♣)
		String suitIcon = switch (suit) {
			case CLUBS -> "♣";
			case HEARTS -> "♥";
			case SPADES -> "♠";
			case DIAMONDS -> "♦";
		};
		return valueStr + " of " + suitIcon;
	}
}