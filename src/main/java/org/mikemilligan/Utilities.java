package org.mikemilligan;

import java.util.Scanner;

public class Utilities {
    static Scanner scanner = new Scanner(System.in);

    /**
     * Retrieves user input after displaying a prompt message.
     *
     * @param prompt The message to display as a prompt for user input.
     * @return The user's input as a trimmed and lowercase String.
     */
    public static String getUserInput(String prompt) {
        String userInput = "";
        // Prompt the user until a non-empty and non-blank input is provided.
        while (userInput.isBlank() || userInput.isEmpty()) {
            // Display the prompt message in cyan.
            System.out.println(Colors.CYAN + prompt + Colors.RESET);
            // Display the input symbol in purple.
            System.out.print(Colors.PURPLE + ">> " + Colors.RESET);
            userInput = scanner.nextLine().trim().toLowerCase();
        }
        return userInput;
    }

    /**
     * Gets integer user input by prompting the user with the given prompt.
     * Validates that the input is a valid integer; if not, displays an
     * error message and prompts again.
     *
     * @param prompt The message prompt to display to the user.
     * @return The validated integer input from the user.
     */
    public static int getIntUserInput(String prompt) {
        String userInput = getUserInput(prompt);

        // Display an error message and prompt again
        if (!isInt(userInput)) {
            errorMessage("Input must be a valid integer");
            return getIntUserInput(prompt);
        }

        return Integer.parseInt(userInput);
    }

    /**
     * Returns true if the given string contains an int value
     * @param string The string to evaluate
     * @return True if the string is only comprised of digits, otherwise false.
     */
    public static boolean isInt(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Displays an error message in red to alert the user about an error or
     * invalid input.
     *
     * @param message The message to be displayed as an error message.
     */
    public static void errorMessage(String message) {
        System.out.println(Colors.RED + "[ERROR] " + message + Colors.RESET);
    }

    /**
     * Displays a success message in green.
     *
     * @param message The message to be displayed as a success message.
     */
    public static void successMessage(String message) {
        System.out.println(Colors.GREEN + message + Colors.RESET);
    }

    public static void failureMessage(String message) {
        System.out.println(Colors.RED + message + Colors.RESET);
    }

    public static void infoMessage(String message) {
        System.out.println(Colors.PURPLE + message + Colors.RESET);
    }
}
