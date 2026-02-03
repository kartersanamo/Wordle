package com.kartersanamo.models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<Guess> guesses;
    private final int maxGuesses = 6;

    public Board() {
        guesses = new ArrayList<>();
    }

    public boolean addGuess(Guess guess) {
        this.guesses.add(guess);
        if (this.guesses.size() < maxGuesses) {
            return true;
        } else {
            System.out.println("No guesses remaining!");
            return false;
        }
    }

    public List<Guess> getGuessesList() {
        return this.guesses;
    }

    public List<String> getGuesses() {
        List<String> guessesList = new ArrayList<>();
        for (Guess guess : guesses) {
            if (guess != null) {
                guessesList.add(guess.getWord());
            }
        }
        return guessesList;
    }

    public void printBoard() {
        // Print all existing guesses
        for (Guess guess : guesses) {
            System.out.println(guess);
        }

        // Print empty slots for remaining guesses
        int remainingGuesses = maxGuesses - guesses.size();
        for (int i = 0; i < remainingGuesses; i++) {
            System.out.println("_____");
        }
        System.out.println();
    }
}
