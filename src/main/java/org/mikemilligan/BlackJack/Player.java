package org.mikemilligan.BlackJack;

import org.mikemilligan.Card;

public class Player {
    private final String name;
    private Hand hand;
    private int balance;
    private final int defaultStartingBalance = 500;
    private int currentBet;
    private boolean skipTurn;

    int latestPayout;


    public Player(String name) {
        this.name = name.toUpperCase();
        hand = new Hand();
        balance = defaultStartingBalance;
        currentBet = 0;
        skipTurn = false;
        latestPayout = 0;
    }


    public String getName() {
        return name;
    }


    public int getCurrentBet() {
        return currentBet;
    }


    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
        this.balance -= currentBet;
    }


    public int getBalance() {
        return this.balance;
    }


    public Hand getHand() {
        return this.hand;
    }

    public void newHand() {
        this.hand = new Hand();
    }


    public void setSkipTurn(boolean skipTurn) {
        this.skipTurn = skipTurn;
    }


    public boolean getSkipTurn() {
        return this.skipTurn;
    }


    public boolean isBust() {
        return hand.isBust();
    }


    public boolean hit(Card card) {
        hand.addCard(card);

        return isBust();
    }


    public int claimPayout(int payoutRate) {
        // Natural = 3x
        // Higher = 2x
        // Equal = 1x
        int payout = currentBet * payoutRate;
        latestPayout = payout;
        balance += payout;

        return payout;
    }
}
