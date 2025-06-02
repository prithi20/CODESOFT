package project; // ✅ Add this line if the file is in a folder named 'project'

import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int score = 0;
        int round = 1;
        final int MAX_ATTEMPTS = 7;

        System.out.println("🎉 Welcome to the Number Guessing Game!");

        boolean playAgain = true;

        while (playAgain) {
            System.out.println("\n🎯 Round " + round);
            int secretNumber = random.nextInt(100) + 1;
            int attemptsLeft = MAX_ATTEMPTS;
            boolean guessedCorrectly = false;

            while (attemptsLeft > 0) {
                System.out.print("Enter your guess (1-100), Attempts left: " + attemptsLeft + ": ");
                int guess;

                if (!scanner.hasNextInt()) {
                    System.out.println("❗ Invalid input. Please enter a number.");
                    scanner.next(); // discard invalid input
                    continue;
                }

                guess = scanner.nextInt();

                if (guess < 1 || guess > 100) {
                    System.out.println("❗ Please enter a number between 1 and 100.");
                    continue;
                }

                attemptsLeft--;

                if (guess == secretNumber) {
                    System.out.println("✅ Correct! You guessed the number!");
                    score++;
                    guessedCorrectly = true;
                    break;
                } else if (guess < secretNumber) {
                    System.out.println("⬆️ Too low!");
                } else {
                    System.out.println("⬇️ Too high!");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("💥 You're out of attempts! The number was " + secretNumber + ".");
            }

            System.out.println("🏅 Your current score: " + score);

            System.out.print("🔁 Do you want to play another round? (yes/no): ");
            String response = scanner.next();

            if (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("y")) {
                playAgain = false;
                System.out.println("👋 Thanks for playing!");
            } else {
                round++;
            }
        }

        scanner.close();
    }
}
