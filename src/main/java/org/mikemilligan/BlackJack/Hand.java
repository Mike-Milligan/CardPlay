package org.mikemilligan.BlackJack;

import org.mikemilligan.Card;
import org.mikemilligan.Card.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hand {
    private List<Card> cards;
    private int total;

    List<Value> numbers = Arrays.asList(
            Value.TWO, Value.THREE, Value.FOUR,
            Value.FIVE, Value.SIX, Value.SEVEN,
            Value.EIGHT, Value.NINE
    );

    List<Value> tenCards = Arrays.asList(
            Value.TEN, Value.JACK, Value.QUEEN, Value.KING
    );

    public Hand() {
        cards = new ArrayList<>();
        total = 0;
    }

    /**
     * Returns a List of Cards representing the Hand.
     * @return cards, the list of cards in the hand
     */
    public List<Card> getCards() {
        return cards;
    }

    //TODO TEST
    /**
     * Adds the given card to your hand.
     *
     * @param card the card to add to your hand.
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    //TODO TEST
    /**
     * Calculates the value of the hand.
     * ACE counts as 11 if it won't bust your hand, else it will
     * count as 1.
     * TWO - TEN count as their number value.
     * JACK, QUEEN, and KING count as 10.
     *
     * @return int total, the total value of the hand
     */
    public int calculateTotal() {
        total = 0;

        for (Card card : cards) {
            Value value = card.getValue();
            // Number cards
            if (numbers.contains(value)) {
                total += value.ordinal() + 1;

            // Face cards
            } else if (tenCards.contains(value)) {
                total += 10;

            // Ace cards
            } else if (value.equals(Value.ACE)) {
                // Count as 11 if able
                if (total + 11 <= 21) {
                    total += 11;

                // Else count as 1
                } else {
                    total += 1;
                }
            }
        }

        return total;
    }

    //TODO TEST
    /**
     * Returns True if the hand has gone Bust.
     * A hand has gone bust if the total is greater than 21
     * @return True if the hand has gone bust, False otherwise.
     */
    public boolean isBust() {
        int total = calculateTotal();
        return total > 21;
    }

    //TODO TEST
    /**
     * Returns true if the hand is a natural blackjack.
     * A natural blackjack consists of 2 cards, one being an ACE,
     * and the other being a 10-card
     * @return True if the hand is a natural blackjack,
     * False otherwise.
     */
    public boolean isNatural() {
        // Natural must have 2 cards
        if (cards.size() != 2) {
            return false;
        }

        return (
                // First card is 10-card
                tenCards.contains(cards.get(0).getValue())
                        // Second card is ACE
                        && Value.ACE.equals(cards.get(1).getValue())
        ) || (  // OR
                // First card is ACE
                Value.ACE.equals(cards.get(0).getValue())
                        // Second card is 10-card
                        && tenCards.contains(cards.get(1).getValue())
        );



    }
}
