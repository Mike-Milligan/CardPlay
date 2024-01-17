package org.mikemilligan.UnitTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mikemilligan.BlackJack.Hand;
import org.mikemilligan.Card;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HandTest {
    private Hand hand;

    @AfterEach
    public void tearDown() {
        hand = null;
    }

    @Test
    public void testNewHand_IsEmpty() {
        hand = new Hand();

        assertTrue(hand.getCards().isEmpty());
    }

    @Test
    public void testNewHand_TotalIsZero() {
        int expectedTotal = 0;

        hand = new Hand();

        assertEquals(expectedTotal, hand.calculateTotal());
    }

    @Test
    public void testAddCard() {
        Card card = new Card(Card.Value.ACE, Card.Suit.CLUBS);
        List<Card> expectedCardList = List.of(card);

        hand = new Hand();
        hand.addCard(card);

        assertEquals(expectedCardList, hand.getCards());
    }

    @Test
    public void testCalculateTotal_Numbers() {
        Card eight = new Card(Card.Value.EIGHT, Card.Suit.CLUBS);
        Card five = new Card(Card.Value.FIVE, Card.Suit.CLUBS);
        int expectedTotal = 8 + 5;

        hand = new Hand();
        hand.addCard(eight);
        hand.addCard(five);

        assertEquals(expectedTotal, hand.calculateTotal());
    }

    @Test
    public void testCalculateTotal_Faces() {
        Card ten = new Card(Card.Value.TEN, Card.Suit.DIAMONDS);
        Card queen = new Card(Card.Value.QUEEN, Card.Suit.DIAMONDS);
        int expectedTotal = 10 + 10;

        hand = new Hand();
        hand.addCard(ten);
        hand.addCard(queen);

        assertEquals(expectedTotal, hand.calculateTotal());
    }

    @Test
    public void testCalculateTotal_AceEleven() {
        Card ace = new Card(Card.Value.ACE, Card.Suit.CLUBS);
        int expectedTotal = 11;

        hand = new Hand();
        hand.addCard(ace);

        assertEquals(expectedTotal, hand.calculateTotal());
    }

    @Test
    public void testCalculateTotal_AceOne() {
        Card ace = new Card(Card.Value.ACE, Card.Suit.HEARTS);
        Card eight = new Card(Card.Value.EIGHT, Card.Suit.HEARTS);
        Card five = new Card(Card.Value.FIVE, Card.Suit.HEARTS);
        int expectedInitialTotal = 11 + 8;
        int expectedFinalTotal = 1 + 8 + 5;

        hand = new Hand();
        hand.addCard(ace);
        hand.addCard(eight);

        assertEquals(expectedInitialTotal, hand.calculateTotal());

        hand.addCard(five);

        assertEquals(expectedFinalTotal, hand.calculateTotal());
    }

    @Test
    public void testIsBust_True() {
        Card ten = new Card(Card.Value.TEN, Card.Suit.HEARTS);
        Card jack = new Card(Card.Value.JACK, Card.Suit.HEARTS);
        Card queen = new Card(Card.Value.QUEEN, Card.Suit.HEARTS);

        hand = new Hand();
        hand.addCard(ten);
        hand.addCard(jack);
        hand.addCard(queen);

        assertTrue(hand.calculateTotal() > 21);
        assertTrue(hand.isBust());
    }

    @Test
    public void testIsBust_False() {
        Card six = new Card(Card.Value.SIX, Card.Suit.DIAMONDS);
        Card seven = new Card(Card.Value.SEVEN, Card.Suit.DIAMONDS);

        hand = new Hand();
        hand.addCard(six);
        hand.addCard(seven);

        assertTrue(hand.calculateTotal() <= 21);
        assertFalse(hand.isBust());
    }

    @Test
    public void testIsNatural_TrueAceFirst() {
        Card ace = new Card(Card.Value.ACE, Card.Suit.CLUBS);
        Card ten = new Card(Card.Value.TEN, Card.Suit.CLUBS);
        Card jack = new Card(Card.Value.JACK, Card.Suit.CLUBS);
        Card queen = new Card(Card.Value.QUEEN, Card.Suit.CLUBS);
        Card king = new Card(Card.Value.KING, Card.Suit.CLUBS);
        List<Card> tenCards = List.of(ten, jack, queen, king);

        for (Card tenCard : tenCards) {
            hand = new Hand();
            hand.addCard(ace);
            hand.addCard(tenCard);

            assertTrue(hand.isNatural());
        }
    }

    @Test
    public void testIsNatural_TrueAceSecond() {
        Card ace = new Card(Card.Value.ACE, Card.Suit.CLUBS);
        Card ten = new Card(Card.Value.TEN, Card.Suit.CLUBS);
        Card jack = new Card(Card.Value.JACK, Card.Suit.CLUBS);
        Card queen = new Card(Card.Value.QUEEN, Card.Suit.CLUBS);
        Card king = new Card(Card.Value.KING, Card.Suit.CLUBS);
        List<Card> tenCards = List.of(ten, jack, queen, king);

        for (Card tenCard : tenCards) {
            hand = new Hand();
            hand.addCard(tenCard);
            hand.addCard(ace);

            assertTrue(hand.isNatural());
        }
    }

    @Test
    public void testIsNatural_FalseOneCard() {
        Card ace = new Card(Card.Value.ACE, Card.Suit.DIAMONDS);

        hand = new Hand();
        hand.addCard(ace);

        assertFalse(hand.isNatural());
    }

    @Test
    public void testIsNatural_FalseThreeCards() {
        Card ace = new Card(Card.Value.ACE, Card.Suit.DIAMONDS);
        Card ten = new Card(Card.Value.TEN, Card.Suit.DIAMONDS);
        Card three = new Card(Card.Value.THREE, Card.Suit.DIAMONDS);

        hand = new Hand();
        hand.addCard(ace);
        hand.addCard(ten);
        hand.addCard(three);

        assertFalse(hand.isNatural());
    }

    @Test
    public void testIsNatural_FalseNoAce() {
        Card ten = new Card(Card.Value.TEN, Card.Suit.CLUBS);
        Card four = new Card(Card.Value.FOUR, Card.Suit.CLUBS);

        hand = new Hand();
        hand.addCard(ten);
        hand.addCard(four);

        assertFalse(hand.isNatural());
    }

    @Test
    public void testIsNatural_FalseNoTenCard() {
        Card ace = new Card(Card.Value.ACE, Card.Suit.CLUBS);
        Card four = new Card(Card.Value.FOUR, Card.Suit.CLUBS);

        hand = new Hand();
        hand.addCard(ace);
        hand.addCard(four);

        assertFalse(hand.isNatural());
    }
}
