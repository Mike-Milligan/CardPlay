package org.mikemilligan.BlackJack;

import org.mikemilligan.Card;

public class Player {
    int id;
    private final String name;
    private Hand hand;
    private int balance;
    private int currentBet;
    int latestPayout;
    private boolean canHit;


    public Player(String name, int id) {
        this.id = id;
        this.name = name.toUpperCase();
        hand = new Hand();
        balance = 500;
        currentBet = 0;
        latestPayout = 0;
        canHit = true;
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

    public boolean isBust() {
        return hand.isBust();
    }

    public boolean canHit() {
        return canHit;
    }

    public void setCanHit(boolean canHit) {
        this.canHit = canHit;
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
