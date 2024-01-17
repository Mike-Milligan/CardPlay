package org.mikemilligan.BlackJack;

import org.mikemilligan.Card;
import org.mikemilligan.Colors;
import org.mikemilligan.Deck;
import org.mikemilligan.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class BlackJack {
    private static Deck deck;
    private static List<Player> players;
    private static Player dealer;
    private static int minBet;
    private static int maxBet;
    private static boolean shouldContinue;

    public static void main(String[] args) {
        // Greeting
        // Set Up
        setUp();
        // Start Play
        play();
        // Goodbye
    }

    /**
     * Sets up the betting limits, players and deck.
     */
    private static void setUp() {
        setUpLimits();
        setUpPlayers();
        setUpDeck();
    }

    /**
     * Sets up betting limits by prompting the user for minimum and maximum
     * bet amounts.
     * Ensures that the maximum bet is not lower than the minimum bet.
     * Also, checks that both minimum and maximum bet amounts are greater
     * than zero.
     */
    private static void setUpLimits() {
        System.out.println("Set up betting limits");
        // Prompt user for minimum bet amount
        minBet = Utilities.getIntUserInput("Enter the minimum bet amount: ");
        // Prompt user for maximum bet amount
        maxBet = Utilities.getIntUserInput("Enter the maximum bet amount: ");

        // Prompt again if max < min
        if (maxBet < minBet) {
            Utilities.errorMessage("Maximum bet amount may not be lower than the minimum bet amount");
            setUpLimits();
        }

        // Prompt again if either are lower than or equal to zero
        if (minBet <= 0 || maxBet <= 0) {
            Utilities.errorMessage("Bet amounts may not be lower than 1");
            setUpLimits();
        }

        // Display bet amounts to the user
        Utilities.successMessage("Minimum bet: " + minBet + "\nMaximum bet: " + maxBet);
    }

    /**
     * Sets up players by prompting the user to enter the number of players
     * and their names.
     * Creates Player objects with the entered names and adds them to the
     * players list.
     * Displays a success message with the names of the added players.
     */
    private static void setUpPlayers() {
        dealer = new Player("DEALER");

        players = new ArrayList<>();
        // Used to concatenate player names
        StringJoiner playerNames = new StringJoiner(", ");

        System.out.println("Set up players");

        // Prompt user for number of players
        int numPlayers = Utilities.getIntUserInput("Enter the number of players: ");

        // For each player, prompt for their name, and add them to the players list
        for (int i = 0; i < numPlayers; i++) {
            String name = Utilities.getUserInput("Enter name for Player " + (i + 1));
            players.add(new Player(name));
            playerNames.add(players.get(i).getName());
        }

        // Display the names of the added players to the user
        Utilities.successMessage("Players: " + playerNames);
    }

    /**
     * Sets up the deck by setting card visibility, shuffling and selecting
     * the card style.
     */
    private static void setUpDeck() {
        deck = new Deck(false);
        deck.shuffle();
        deck.setStyle(Card.Style.COMPACT);
    }

    private static void play() {
        shouldContinue = true;
        int i = 0;
        System.out.println("Game Starting");

        while (shouldContinue) {
            // Place Bets
            System.out.println("Place your bets\n" + Colors.PURPLE
                    + "Minimum bet: " + minBet
                    + "\nMaximum bet: " + maxBet + Colors.RESET);
            placeBets();
            // Initial Deal
            System.out.println("Dealing");
            deal();
            // Handle Naturals
            boolean dealerNatural = handleNaturals();
            if (dealerNatural) {
                settlement();

                for (Player player : players) {
                    player.newHand();
                }
                dealer.newHand();

                continue;
            }
            // Each Player turn
            System.out.println("Player's turn");
            playersTurns();
            // Dealer turn
            System.out.println("Dealer's turn");
            dealersTurn();
            // Settlement
            System.out.println("Settlement");
            settlement();
            // Play again? / Buy back in?
            i++;
            if (i == 5) {
                shouldContinue = false;
            }
            setUpDeck();
            for (Player player : players) {
                player.newHand();
            }
            dealer.newHand();
        }
    }

    private static void placeBets() {
        for (Player player : players) {
            playerBet(player);
        }

        for (Player player : players) {
            Utilities.successMessage(player.getName() + ", Bet: "
                    + player.getCurrentBet() + ", Balance: " + player.getBalance());
        }
    }

    private static void playerBet(Player player) {
        String name = player.getName();
        int balance = player.getBalance();

        if (balance < minBet) {
            player.setCurrentBet(balance);
            System.out.println(Colors.CYAN + name + " is all in with " + balance + Colors.RESET);
            return;
        }

        int bet = Utilities.getIntUserInput(name + ", enter your bet (Balance: " + balance + ")");

        if (bet < minBet || bet > maxBet) {
            Utilities.errorMessage("Your bet must be between " + minBet
                    + " and " + maxBet);
            playerBet(player);
        }

        if (balance < bet) {
            Utilities.errorMessage("Your bet must be lower or equal to your balance: "
                    + player.getBalance());
            playerBet(player);
        }

        player.setCurrentBet(bet);
    }

    private static void deal() {
        dealToAllPlayers();
        dealToDealer();

        dealToAllPlayers();
        dealToDealer();
        for (Player player : players) {
            Utilities.successMessage(player.getName() + ": " + player.getHand().toString());
        }
        Utilities.successMessage(dealer.getName() + ": " + dealer.getHand().toString());
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

            Utilities.infoMessage(player.getName() + " drew " + drawnCard);
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

        Utilities.infoMessage(dealer.getName() + " drew " + drawnCard);
    }

    private static boolean handleNaturals() {
        boolean dealerNatural = dealer.getHand().isNatural();

        if (dealerNatural) {
            Utilities.infoMessage("The dealer has BlackJack!");
        }

        for (Player player : players) {
            if (player.getHand().isNatural()) {
                player.setSkipTurn(true);
                int payoutRate = dealerNatural ? 1 : 3;
                int payout = player.claimPayout(payoutRate);

                Utilities.infoMessage(player.getName() + " has BlackJack! They have won: " + payout);
            }
        }

        return dealerNatural;
    }

    private static void playersTurns() {
        for (Player player : players) {
            String name = player.getName();
            if (player.getSkipTurn()) {
                Utilities.infoMessage("Skipping " + player.getName() + "'s turn");
                continue;
            }
            Utilities.infoMessage(name + "'s turn");
            playersTurn(player);
        }
    }

    private static void playersTurn(Player player) {
        Utilities.infoMessage(player.getHand().toString() + "\nTotal: " + player.getHand().calculateTotal());
        String name = player.getName();

        boolean canHit = true;

        while (canHit) {
            String action = Utilities.getUserInput("Would you like to hit or stand?");

            switch (action) {
                case "hit":
                case "h":
                    Card card = deck.draw();
                    card.setVisibility(true);

                    boolean bust = player.hit(card);
                    int total = player.getHand().calculateTotal();

                    Utilities.infoMessage(name + " drew " + card + ", total: " + total);

                    if (bust) {
                        Utilities.failureMessage(name + " has bust with a total of " + total);
                        canHit = false;
                    }
                    break;
                case "stand":
                case "s":
                    Utilities.successMessage(name + " stands with a total of " + player.getHand().calculateTotal());
                    canHit = false;
            }
        }

    }

    private static void dealersTurn() {

    }

    private static void settlement() {

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
}
