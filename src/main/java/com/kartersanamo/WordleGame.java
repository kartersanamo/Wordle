package com.kartersanamo;

import com.kartersanamo.enums.GameOptions;
import com.kartersanamo.enums.GameState;
import com.kartersanamo.models.Board;
import com.kartersanamo.models.Guess;
import com.kartersanamo.models.WordleBot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class WordleGame {

    private Board board;
    private int guesses;
    private GameState state;
    private static String word;
    private final List<String> words;
    private final GameOptions option;
    private WordleBot bot;

    public WordleGame(GameOptions option) {
        this.board = new Board();
        this.guesses = 0;
        this.state = GameState.NOT_STARTED;
        this.words = getWords();
        this.option = option;
        if (option == GameOptions.BOT_SUGGESTIONS) {
            System.out.println("Bot suggestions enabled.");
            this.bot = new WordleBot(this);
        }
        else if (option == GameOptions.PLAY_BOT_GAME) {
            System.out.println("Bot is playing a game!");
            this.bot = new WordleBot(this);
        }
    }

    public void start() {
        this.state = GameState.IN_PROGRESS;
        this.guesses = 0;
        this.board = new Board();
        word = getRandomWord();
        // System.out.println("Debug: The word is: " + word);
        gameLoop();
    }

    private void gameLoop() {
        while (state == GameState.IN_PROGRESS) {
            System.out.println("Wordle Round #" + (guesses + 1) + "!");
            System.out.println("Guesses remaining: " + (6 - guesses));
            board.printBoard();

            // Get, validate, and add guess
            String guessStr = getGuess();
            Guess guess = new Guess(guessStr);

            this.guesses++;  // Increment FIRST

            // Check if they won BEFORE checking if out of guesses
            checkGuess(guess);

            boolean status = board.addGuess(guess);
            if (!status) {
                board.printBoard();
                System.out.println("Game over! The word was " + word + ".");
                System.exit(0);
            }
        }
    }

    private void checkGuess(Guess guess) {
        if (guess.getWord().equals(word)) {
            state = GameState.WON;
            board.addGuess(guess);  // Add this line to add the winning guess
            board.printBoard();
            System.out.println("You won in " + guesses + " guesses!");
            System.exit(0);
        }
    }

    private List<String> getWords() {
        InputStream input = WordleGame.class.getResourceAsStream("/words.txt");

        // If the file doesn't exist or is empty, return the default
        if (input == null) {
            System.out.println(WordleGame.class.getResource("/words.txt"));
            System.out.println("Warning: words.txt not found. Using default words.");
            return List.of("stare");
        }

        List<String> words = new BufferedReader(new InputStreamReader(input))
                .lines()
                .toList();

        return words.isEmpty() ? List.of("stare") : words;
    }

    private String getRandomWord() {
        return this.words.get((int) (Math.random() * this.words.size()));
    }

    private String getGuess() {
        String guessStr = "";

        // Validate guess
        while (guessStr.isEmpty()) {
            List<String> validWords = null;
            if (option == GameOptions.BOT_SUGGESTIONS || option == GameOptions.PLAY_BOT_GAME) {
                validWords = bot.getValidWords();
                if (option == GameOptions.BOT_SUGGESTIONS) {
                    System.out.println("Bot suggestions: " + validWords.toString());
                }
            }
            if (option == GameOptions.PLAY_BOT_GAME) {
                if (validWords != null && !validWords.isEmpty()) {
                    guessStr = validWords.getFirst();
                    System.out.println("Enter your guess: " + guessStr);
                    break;
                } else {
                    // Bot has no valid suggestions - pick a random word
                    guessStr = this.words.get((int) (Math.random() * this.words.size()));
                    System.out.println("Bot has no suggestions. Guessing randomly: " + guessStr);
                    break;
                }
            }
            System.out.print("Enter your guess: ");
            String guessInput = Main.scanner.next();
            guessInput = guessInput.toLowerCase();

            // Ensure the guess is 5 characters long
            if (guessInput.length() != 5) {
                System.out.println("Invalid guess. Please enter a 5-letter word.");
                continue;
            }

            // Ensure they only entered alphabetical characters
            if (!guessInput.matches("[a-zA-Z]+")) {
                System.out.println("Invalid guess. Please enter only alphabetical characters.");
                continue;
            }

            // Ensure the word is a valid guess (is in the list of words)
            if (!this.words.contains(guessInput)) {
                System.out.println("Invalid guess. Please enter a valid word.");
                continue;
            }

            // Ensure we haven't already guessed it
            if (board.getGuesses().contains(guessInput)) {
                System.out.println("Invalid guess. You've already guessed that word.");
                continue;
            }
            System.out.println();
            guessStr = guessInput;
        }
        return guessStr;
    }

    /*
        Getters & Setters
     */
    public static String getWord() {
        return word;
    }

    public List<String> getTotalWords() {
        return this.words;
    }

    public Board getBoard() {
        return this.board;
    }
}
