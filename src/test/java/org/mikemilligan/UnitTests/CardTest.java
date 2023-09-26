package org.mikemilligan.UnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mikemilligan.Card;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
	private Card card;
	
	@BeforeEach
	public void setUp() {
		card = new Card(Card.Value.ACE, Card.Suit.HEARTS);
	}
	
	@Test
	public void testGetValue() {
		assertEquals(Card.Value.ACE, card.getValue());
	}
	
	@Test
	public void testGetSuit() {
		assertEquals(Card.Suit.HEARTS, card.getSuit());
	}
	
	@Test
	public void testSetStyle() {
		card.setStyle(Card.Style.PRETTY);
		assertEquals(Card.Style.PRETTY, card.getStyle());
	}
	
	@Test
	public void testIsDrawnInitiallyFalse() {
		assertFalse(card.isDrawn());
	}
	
	@Test
	public void testDraw() {
		card.draw();
		assertTrue(card.isDrawn());
	}
	
	@Test
	public void testToStringNormalStyle() {
		card.setStyle(Card.Style.NORMAL);
		assertEquals("ACE of HEARTS", card.toString());
	}
	
	@Test
	public void testToStringPrettyStyle() {
		card.setStyle(Card.Style.PRETTY);
		assertEquals("Ace of Hearts", card.toString());
	}
	
	@Test
	public void testToStringCompactStyle() {
		card.setStyle(Card.Style.COMPACT);
		assertEquals("A of ♥", card.toString());
	}
}