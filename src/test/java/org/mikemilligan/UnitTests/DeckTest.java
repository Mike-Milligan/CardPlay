package org.mikemilligan.UnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mikemilligan.Card;
import org.mikemilligan.Deck;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
	private Deck deck;
	
	@BeforeEach
	public void setUp() {
		deck = new Deck();
	}
	
	@Test
	public void testSetStyle() {
		deck.setStyle(Card.Style.PRETTY);
		for (Card card : deck.getCards()) {
			assertEquals(Card.Style.PRETTY, card.getStyle());
		}
	}
	
	@Test
	public void testShuffle() {
		List<Card> originalOrder = new ArrayList<>(deck.getCards());
		deck.shuffle();
		List<Card> shuffledOrder = deck.getCards();
		
		// The shuffled deck should not be in the same order as the original
		assertNotEquals(originalOrder, shuffledOrder);
		
		// However, the shuffled deck should contain all the same cards
		assertTrue(shuffledOrder.containsAll(originalOrder));
		assertTrue(originalOrder.containsAll(shuffledOrder));
	}
	
	@Test
	public void testDraw() {
		// Drawing 5 cards from a new deck should return true
		assertTrue(deck.draw(5));
		
		// Drawing more cards than are in the deck return fail to indicate
		// that there are no cards left to draw
		assertFalse(deck.draw(53)); // A deck has 52 cards
	}
}