package org.mikemilligan;



public class CardPlay {
	static Deck deck = new Deck();

	static String welcome = """
 $$$$$$\\                            $$\\      $$$$$$$\\  $$\\                    \s
$$  __$$\\                           $$ |     $$  __$$\\ $$ |                   \s
$$ /  \\__| $$$$$$\\   $$$$$$\\   $$$$$$$ |.-.  $$ |  $$ |$$ | $$$$$$\\  $$\\   $$\\\s
$$ |       \\____$$\\ $$  __$$\\ $$  __$$ ((5)) $$$$$$$  |$$ | \\____$$\\ $$ |  $$ |
$$ |       $$$$$$$ |$$ |  \\__|$$ /  $$ |'-.-.$$  ____/ $$ | $$$$$$$ |$$ |  $$ |
$$ |  $$\\ $$  __$$ |$$ |      $$ |  $$ | ((1))$ |      $$ |$$  __$$ |$$ |  $$ |
\\$$$$$$  |\\$$$$$$$ |$$ |      \\$$$$$$$ |  '-'$$ |      $$ |\\$$$$$$$ |\\$$$$$$$ |
 \\______/  \\_______|\\__|       \\_______|     \\__|      \\__| \\_______| \\____$$ |
                                                                     $$\\   $$ |
                                                                     \\$$$$$$  |
                                                                      \\______/\s""";

	/**
	 * The main entry point of the program that handles user interactions.
	 *
	 * @param args The command-line arguments (not used in this program).
	 */
	public static void main(String[] args) {
		String userInput;
		boolean shouldContinue = true;

		System.out.println(Colors.RED + welcome + Colors.RESET);
		// Continuously prompt the user for input until the user quits.
		while (shouldContinue) {
			// Get the users input
			userInput = Utilities.getUserInput("Options: show, shuffle, draw [num], quickdraw [num] style [option], help, quit");
			// Handle the users input
			shouldContinue = handleInput(userInput);
			//Print a newline for readability
			System.out.println();
		}
		System.out.println(Colors.GREEN + "Goodbye! Thank you for playing Cardplay!" + Colors.RESET);
	}

	/**
	 * Handles the user's input by parsing the command and executing it.
	 *
	 * @param userInput The user's input as a String.
	 * @return True if the program should continue, False if the user wants to quit.
	 */
	private static boolean handleInput(String userInput) {
		// Get the command
		String command = userInput.split(" ")[0];
		// Execute it
		switch (command) {
			// Shuffle the deck
			case "shuffle" -> deck.shuffle();
			// Draw cards once
			case "draw" -> drawCards(userInput);
			// Draw cards repeatedly
			case "quickdraw" -> quickDraw(userInput);
			// Display the deck
			case "show" -> deck.show();
			// Change the card style
			case "style" -> selectStyle(userInput);
			// Show command info
			case "help" -> showHelp();
			// Exit the program
			case "quit" -> { return false; }

			// Display an invalid command message
			default -> Utilities.errorMessage("Invalid Command");
		}
		// Continue the program
		return true;
	}

	/**
	 * Allows the user to select a style for displaying the deck of cards.
	 *
	 * @param userInput The user's input as a String, which may include the desired style.
	 */
	private static void selectStyle(String userInput) {
		String style;
		String[] command = userInput.split(" ");

		// Check if the user entered "style" without specifying a style.
		if (command.length == 1) {
			String prompt = "Options: normal, pretty, or compact";
			style = Utilities.getUserInput(prompt);
		} else { // User entered "style pretty" or similar.
			style = command[1];
		}
		// Determine the selected style and set it for the deck.
		switch (style) {
			case "normal" -> deck.setStyle(Card.Style.NORMAL);
			case "pretty" -> deck.setStyle(Card.Style.PRETTY);
			case "compact" -> deck.setStyle(Card.Style.COMPACT);
			//User wants to go back to the previous menu.
			case "back" -> { return; }
			//Invalid style, prompt the user again.
			default -> {
				Utilities.errorMessage("Invalid Style");
				selectStyle(style);
				return;
			}
		}
		// Display a success message with the selected style.
		Utilities.successMessage("Style set to: " + style.toUpperCase());
	}

	/**
	 * Repeatedly draws a specified number of cards from the deck and displays them.
	 *
	 * @param userInput The users input as a string, which may contain the number
	 *                  of cards to draw.
	 */
	private static void quickDraw(String userInput) {
		boolean cardsRemaining = true;
		while (cardsRemaining) {
			cardsRemaining = drawCards(userInput);
			System.out.println();
		}
	}

	/**
	 * Draws a specified number of cards from the deck and displays them.
	 * @param userInput The users input as a string, which may contain the number
	 * 	 *                  of cards to draw.
	 * @return True if there are undrawn cards remaining, otherwise false.
	 */
	private static boolean drawCards(String userInput) {
		String[] command = userInput.split(" ");
		int numCards;
		boolean cardsRemaining = true;
		if (command.length == 1) {
			cardsRemaining = deck.draw(1);
			return cardsRemaining;
		}
		if (Utilities.isInt(command[1])) {
			numCards = Integer.parseInt(command[1]);
			cardsRemaining = deck.draw(numCards);
		} else {
			Utilities.errorMessage("Argument must be an integer");
		}
		return cardsRemaining;
	}

	/**
	 * Displays information about the commands and their arguments.
	 */
	private static void showHelp() {
		String commandPrefix = "  - ";
		String argumentPrefix = "  --> ";
		System.out.println(Colors.GREEN + "Available Commands:");
		System.out.println(commandPrefix + "show: Displays the drawn and undrawn cards in the deck.");
		System.out.println(commandPrefix + "shuffle: Shuffles the deck, randomizing the order of cards.");
		System.out.println(commandPrefix + "draw: Draws 1 or more cards and displays the state of the deck.");
		System.out.println(argumentPrefix + "numCards: Optional, specify a number of cards to draw, leave blank to draw 1.");
		System.out.println(commandPrefix + "quickdraw: Draws 1 or more cards until there are no cards left to draw and displays the state of the deck after each draw.");
		System.out.println(argumentPrefix + "numCards: Optional, specify a number of cards to draw each time, leave blank to draw 1.");
		System.out.println(commandPrefix + "style: Sets the style of the cards.");
		System.out.println(argumentPrefix + "options: Optional, [normal, pretty, or compact]");
		System.out.println(argumentPrefix + "normal: 'ACE of SPADES', 'SEVEN of HEARTS'");
		System.out.println(argumentPrefix + "pretty: 'Ace of Spades', 'Seven of Hearts'");
		System.out.println(argumentPrefix + "compact: 'A of ♠', '7 of ♥'");
		System.out.println(commandPrefix + "help: Displays the available commands and their arguments");
		System.out.println(commandPrefix + "quit: Exits the program" + Colors.RESET);
	}
}
