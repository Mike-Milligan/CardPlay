package org.mikemilligan.BlackJack;

import org.mikemilligan.BlackJack.GUI.BlackJackGUI;
import org.mikemilligan.BlackJack.GUI.BlackJackGUI.Button;
import org.mikemilligan.Card;
import org.mikemilligan.Deck;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BlackJack {
    private static Deck deck;
    private static List<Player> players;
    private static Player dealer;
    private static int minBet;
    private static int maxBet;
    private static BlackJackGUI gui;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            gui = new BlackJackGUI();
            setUp();
            play();
        });
    }

    /**
     * Sets up the betting limits and players
     */
    private static void setUp() {
        gui.setStage("Set up");
        gui.setCurrentBet(0);

        setUpLimits();
        setUpPlayers();
    }

    /**
     * Sets up betting limits by prompting the user for minimum and maximum
     * bet amounts.
     * Ensures that the maximum bet is not lower than the minimum bet.
     * Also, checks that both minimum and maximum bet amounts are greater
     * than zero.
     */
    private static void setUpLimits() {
        gui.setStage("Betting setup");
        // Prompt user for minimum bet amount
        minBet = gui.promptForIntValue("Enter the minimum bet amount: ");

        // Prompt again if min <= 0
        while (minBet <= 0) {
            gui.showErrorMessage("Minimum bet amount may not be lower than 1");
            minBet = gui.promptForIntValue("Enter the minimum bet amount: ");
        }

        // Prompt user for maximum bet amount
        maxBet = gui.promptForIntValue("Enter the maximum bet amount: ");

        // Prompt again if max < min
        while (maxBet < minBet) {
            gui.showErrorMessage("Maximum bet amount may not be lower than the minimum bet amount (" + minBet + ")");
            maxBet = gui.promptForIntValue("Enter the maximum bet amount: ");
        }

        // Display bet amounts to the user
        gui.setMinBet(minBet);
        gui.setMaxBet(maxBet);
        gui.addToHistory("Min bet: " + minBet);
        gui.addToHistory("Max bet: " + maxBet);
    }

    /**
     * Sets up players by prompting the user to enter the number of players
     * and their names.
     * Creates Player objects with the entered names and adds them to the
     * players list.
     * Displays a success message with the names of the added players.
     */
    private static void setUpPlayers() {
        gui.setStage("Player setup");

        dealer = new Player("DEALER", -1);

        players = new ArrayList<>();
        // Prompt user for number of hands
        int numPlayers = gui.promptForIntValue("Enter the number of players: ");

        while (numPlayers < 1) {
            gui.showErrorMessage("Number of players may not be lower than 1");
            numPlayers = gui.promptForIntValue("Enter the number of players: ");
        }

        // For each player, prompt for their name, and add them to the players list
        for (int i = 0; i < numPlayers; i++) {
            String name = gui.promptForStringValue("Enter name for Player " + (i + 1));
            Player player = new Player(name, i);
            players.add(player);
            // add a hand panel
            gui.addPlayerHand(player.getName());
            // Display the name of the added player to the user
            gui.addToHistory(player.getName() + " joined");
        }
    }

    /**
     * Sets up the deck by setting card visibility, shuffling and selecting
     * the card style.
     */
    private static void setUpDeck() {
        deck = new Deck(false);
        deck.shuffle();
        deck.setStyle(Card.Style.COMPACT);
        gui.addToHistory("Deck shuffled");
    }

    private static void play() {
        boolean shouldContinue = true;

        while (shouldContinue) {
            gui.setStage("\nNew Round");
            setUpDeck();
            // Place Bets
            placeBets();
            // Initial Deal
            deal();
            // Handle Naturals
            boolean dealerNatural = handleNaturals();
            if (dealerNatural) {
                settlement();
                shouldContinue = playAgain();
                resetHands();
                continue;
            }
            // Each Player turn
            playersTurns();
            // Dealer turn
            dealersTurn();
            // Settlement
            settlement();
            // Play again? / Buy back in?
            shouldContinue = playAgain();
            resetHands();
        }
    }

    private static void placeBets() {
        gui.setStage("Placing bets");
        for (Player player : players) {
            playerBet(player);

            gui.updatePlayerBet(player.id, player.getCurrentBet());
            gui.updatePlayerBalance(player.id, player.getBalance());
            gui.addToHistory(player.getName() + " bet " + player.getCurrentBet());
        }
    }

    private static void playerBet(Player player) {
        String name = player.getName();
        int balance = player.getBalance();

        if (balance < minBet) {
            player.setCurrentBet(balance);
            gui.showInfoMessage(name + " is all in with " + player.getCurrentBet());

            return;
        }

        int bet = gui.promptForIntValue(name + " enter your bet");

        if (bet < minBet || bet > maxBet) {
            gui.showErrorMessage("Your bet must be between " + minBet + " and " + maxBet);

            playerBet(player);
            return;
        }

        if (balance < bet) {
            gui.showErrorMessage("You bet can not be higher than your balance of " + player.getBalance());
            playerBet(player);
            return;
        }

        player.setCurrentBet(bet);
    }

    private static void deal() {
        gui.setStage("\nDealing");

        dealToAllPlayers();
        dealToDealer();

        dealToAllPlayers();
        dealToDealer();
    }

    private static void dealToAllPlayers() {
        for (Player player : players) {
            Card drawnCard = deck.draw();

            if (drawnCard == null) {
                shuffleDiscards();
                drawnCard = deck.draw();
            }

            drawnCard.setVisibility(true);
            player.hit(drawnCard);

            gui.updateHand(player.id, player.getHand());
            gui.updateTotal(player.id, player.getHand().isNatural() ? "BlackJack" : player.getHand().calculateTotal());
            gui.addToHistory(player.getName() + " drew " + drawnCard.compact());

            gui.refresh();
        }
    }

    private static void dealToDealer() {
        Card drawnCard = deck.draw();

        if (drawnCard == null) {
            shuffleDiscards();
            drawnCard = deck.draw();
        }

        if (dealer.getHand().size() != 1) {
            drawnCard.setVisibility(true);
        }

        dealer.hit(drawnCard);

        gui.updateHand(dealer.id, dealer.getHand());
        gui.updateTotal(dealer.id, dealer.getHand().isNatural() ? "BlackJack" : "Unknown");
        gui.addToHistory(dealer.getName() + " drew " + drawnCard.compact());
    }

    private static boolean handleNaturals() {
        boolean dealerNatural = dealer.getHand().isNatural();

        if (dealerNatural) {
            gui.showInfoMessage("The dealer has BlackJack!");
        }

        for (Player player : players) {
            if (player.getHand().isNatural()) {
                int payoutRate = dealerNatural ? 1 : 3;
                int payout = player.claimPayout(payoutRate);

                gui.updatePlayerBalance(player.id, player.getBalance());
                gui.showInfoMessage(player.getName() + " has BlackJack! They have won " + payout);
            }
        }

        return dealerNatural;
    }

    private static void playersTurns() {
        for (Player player : players) {
            String name = player.getName();
            gui.setStage("\n" + name + "'s turn");
            // Handle Natural BlackJacks
            if (player.getHand().isNatural()) {
                gui.showInfoMessage(name + " has a natural BlackJack");
                gui.addToHistory(name + " has BlackJack");
                continue;
            }

            while (player.canHit()) {
                playersTurn(player);
            }

        }
    }

    private static void playersTurn(Player player) {
        Button button = gui.promptForOption();
        switch (button) {
            case HIT -> hit(player);
            case STAND -> stand(player);
        }
    }

    private static void hit(Player player) {
        String name = player.getName();
        Card card = deck.draw();

        if (card == null) {
            shuffleDiscards();
            card = deck.draw();
        }
        card.setVisibility(true);

        boolean bust = player.hit(card);
        int total = player.getHand().calculateTotal();

        gui.addToHistory(name + " drew " + card);

        gui.updateHand(player.id, player.getHand());
        gui.updateTotal(player.id, player.getHand().calculateTotal());

        if (bust) {
            gui.showErrorMessage(name + " has bust with a total of " + total);
            gui.addToHistory(name + " has bust");
            player.setCanHit(false);
        }
    }

    private static void stand(Player player) {
        String name = player.getName();
        gui.addToHistory(name + " stands");
        player.setCanHit(false);
    }

    private static void dealersTurn() {
        gui.setStage("\nDealer's turn");
        String name = dealer.getName();

        dealer.getHand().getCards().get(1).setVisibility(true);
        gui.updateHand(dealer.id, dealer.getHand());
        gui.updateTotal(dealer.id, dealer.getHand().calculateTotal());

        while (dealer.getHand().calculateTotal() < 17) {
            Card card = deck.draw();
            card.setVisibility(true);

            boolean bust = dealer.hit(card);
            int total = dealer.getHand().calculateTotal();

            gui.updateHand(dealer.id, dealer.getHand());
            gui.updateTotal(dealer.id, dealer.getHand().calculateTotal());
            gui.addToHistory(name + " drew " + card);

            if (bust) {
                gui.showInfoMessage(name + " has bust with a total of " + total);
                gui.addToHistory(name + " has bust");
                return;
            }
        }
        gui.addToHistory(name + " stands");
    }

    private static void settlement() {
        gui.setStage("\nSettlement");
        int dealerTotal = dealer.getHand().calculateTotal();
        for (Player player : players) {
            String name = player.getName();
            int playerTotal = player.getHand().calculateTotal();

            if (player.getHand().isNatural()) {
                gui.addToHistory(name + " has BlackJack!");
                gui.addToHistory(name + " has won " + player.latestPayout);
                continue;
            }

            if (player.isBust()) {
                gui.addToHistory(name + " has bust");
                gui.addToHistory(name + " has won nothing");
                continue;
            }

            if (dealer.isBust()) {
                int payout = player.claimPayout(2);
                gui.addToHistory(dealer.getName() + " has bust");
                gui.addToHistory(name + " has won " + payout);
                gui.updatePlayerBalance(player.id, player.getBalance());
                continue;
            }

            if (playerTotal > dealerTotal) {
                int payout = player.claimPayout(2);
                gui.addToHistory(name + " beat " + dealer.getName() + "!");
                gui.addToHistory(name + " has won " + payout);
                gui.updatePlayerBalance(player.id, player.getBalance());
            } else if (playerTotal == dealerTotal) {
                int payout = player.claimPayout(1);
                gui.addToHistory(name + " is in a stand-off!");
                gui.addToHistory(name + " keeps their bet of " + payout);
                gui.updatePlayerBalance(player.id, player.getBalance());
            } else {
                gui.addToHistory(name + " lost to " + dealer.getName());
                gui.addToHistory(name + " has won nothing");
            }

        }

    }

    private static void resetHands() {
        for (Player player : players) {
            player.newHand();
            player.setCanHit(true);
            gui.updateHand(player.id, player.getHand());
            gui.updateTotal(player.id, player.getHand().calculateTotal());
        }
        dealer.newHand();
        gui.updateHand(dealer.id, dealer.getHand());
        gui.updateTotal(dealer.id, dealer.getHand().calculateTotal());
    }

    private static void shuffleDiscards() {
        // Reset deck
        deck = new Deck(false);
        deck.setStyle(Card.Style.COMPACT);
        // Redraw all cards currently in players hands
        for (Player player : players) {
            for (Card card : player.getHand().getCards()) {
                deck.draw(card);
            }
        }
        // Redraw all cards currently in dealers hand
        for (Card card : dealer.getHand().getCards()) {
            deck.draw(card);
        }
        deck.shuffle();
    }

    private static boolean playAgain() {
        int playAgain = gui.promptForYesNo("Would you like to play again?");

        if (playAgain == JOptionPane.YES_OPTION) {
            newPlayers();
            return true;
        }
        return false;
    }

    private static void newPlayers() {
        int samePlayers = gui.promptForYesNo("Play with the same players?");

        if (samePlayers == JOptionPane.YES_OPTION) {
            return;
        }

        setUpPlayers();
    }
}
