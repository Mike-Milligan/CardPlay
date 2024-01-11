package org.mikemilligan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a deck of playing cards.
 */
public class Deck {
	private List<Card> cards;
	private boolean areCardsVisible;
	
	/**
	 * Constructs a new deck of cards and initializes it with the NORMAL style.
	 */
	public Deck() {
		areCardsVisible = true;
		build();
	}

	public Deck(boolean areCardsVisible) {
		this.areCardsVisible = areCardsVisible;
		build();
	}
	
	/**
	 * Retrieves the list of cards in the deck.
	 *
	 * @return The list of cards.
	 */
	@SuppressWarnings("unused")
	public List<Card> getCards() {
		return cards;
	}
	
	/**
	 * Sets the display style for the deck.
	 *
	 * @param style The style to set.
	 */
	public void setStyle(Card.Style style) {
		for (Card card : cards) {
			card.setStyle(style);
		}
	}

	/**
	 * Checks whether cards will be visible to the user (face-up) or hidden
	 * (face-down).
	 * @return True if the cards are visible, False otherwise.
	 */
	public boolean getAreCardsVisible() {
		return areCardsVisible;
	}
	
	/**
	 * 	Build the deck with all possible cards.
	 */
	private void build() {
		cards = new ArrayList<>();
		for (Card.Suit suit : Card.Suit.values()) {
			for (Card.Value value: Card.Value.values()) {
				Card card = new Card(value, suit);

				card.setVisibility(areCardsVisible);

				cards.add(card);
			}
		}
	}
	
	/**
	 * Shuffles the deck, randomizing the order of cards.
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	/**
	 * Draws a specified number of cards from the deck and displays them.
	 *
	 * @param numDraws The number of cards to draw.
	 * @return True if the cards are successfully drawn and displayed, False if all cards have been drawn.
	 */
	public boolean draw(int numDraws) {
		for (int i = 0; i < numDraws; i++) {
			
			Card drawnCard = null;
			// Iterate through the cards in the deck to find an undrawn card.
			for (Card card : cards) {
				if (card.isDrawn()) {
					continue; //If already drawn, find a new one
				}
				card.draw();
				drawnCard = card;
				break; // Draw card and stop looking
			}
			// If no undrawn cards are found, display a message and return False.
			if (drawnCard == null) {
				System.out.println("All cards have been drawn!");
				show();
				return false;
			}

			System.out.println(Colors.GREEN + drawnCard + Colors.RESET);
		}
//		show();
		return true;
	}
	
	/**
	 * Displays the deck of cards based on the selected display style.
	 */
	public void show() {
		List<Card> drawnCards = cards.stream()
				.filter(Card::isDrawn)
				.collect(Collectors.toList());
		System.out.println("Drawn Cards: " + drawnCards);
		
		List<Card> undrawnCards = cards.stream()
				.filter(card -> !card.isDrawn())
				.collect(Collectors.toList());
		System.out.println("Undrawn Cards: " + undrawnCards);
	}
}