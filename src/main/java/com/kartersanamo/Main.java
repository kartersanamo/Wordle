package com.kartersanamo;

import com.kartersanamo.enums.GameOptions;

import java.util.Scanner;

public class Main {

    // Scanner & random that can be accessed anywhere
    public static Scanner scanner = new Scanner(System.in);

    static void main() {

        boolean valid = false;
        GameOptions gameOption = null;
        while (!valid) {
            System.out.println("Welcome to a game of Wordle!");
            System.out.println("1. Play a game");
            System.out.println("2. Play a game (with Wordle Bot suggestions)");
            System.out.println("3. Let Wordle Bot play a game");
            System.out.println("4. Exit");
            System.out.print("Please select an option: ");

            String option = scanner.next();

            gameOption = option.equals("2") ? GameOptions.BOT_SUGGESTIONS : null;
            if (gameOption == null ) { gameOption = option.equals("3") ? GameOptions.PLAY_BOT_GAME : null; }
            switch (option) {
                case "1":
                    valid = true;
                    System.out.println("Game started!");
                    System.out.println();
                    break;
                case "2":
                    valid = true;
                    System.out.println("Game started with Wordle Bot suggestions!");
                    System.out.println();
                    break;
                case "3":
                    valid = true;
                    System.out.println("Wordle Bot is playing a game!");
                    System.out.println();
                    break;
                case "4":
                    valid = true;
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option\n");
            }
        }
        WordleGame game = new WordleGame(gameOption);
        game.start();
    }
}
