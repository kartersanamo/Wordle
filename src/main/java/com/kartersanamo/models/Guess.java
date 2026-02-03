package com.kartersanamo.models;

import com.kartersanamo.WordleGame;
import com.kartersanamo.enums.Colors;
import com.kartersanamo.enums.WordleColor;

import java.util.HashMap;
import java.util.Map;

public class Guess {

    private final String word;
    private final String colorizedWord;
    private final Map<Integer, Map<Character, WordleColor>> charMap = new HashMap<>();

    public Guess(String inputWord) {
        this.word = inputWord;
        this.colorizedWord = colorize();
        // System.out.println("DEBUG charMap for '" + inputWord + "': " + this.charMap);
    }

    public String toString() {
        return this.colorizedWord + Colors.RESET.code();
    }

    public String colorize() {
        String word = WordleGame.getWord();
        String guess = this.word;
        StringBuilder coloredBuilder = new StringBuilder();

        // First pass: count how many of each letter are green
        Map<Character, Integer> greenCount = new HashMap<>();
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == word.charAt(i)) {
                Map<Character, WordleColor> innerMap = new HashMap<>();
                innerMap.put(guess.charAt(i), WordleColor.GREEN);
                this.charMap.put(i, innerMap);
                greenCount.put(guess.charAt(i), greenCount.getOrDefault(guess.charAt(i), 0) + 1);
            }
        }

        // Second pass: track yellows and grays
        Map<Character, Integer> yellowCount = new HashMap<>();

        for (int i = 0; i < guess.length(); i++) {
            char guessCh = guess.charAt(i);

            // If it's in the correct spot, mark green (already in charMap)
            if (guessCh == word.charAt(i)) {
                coloredBuilder.append(Colors.GREEN.code()).append(guessCh);
            }
            // Check if it's in the word but wrong position
            else if (word.contains(String.valueOf(guessCh))) {
                // Count total occurrences in the word
                long totalInWord = word.chars().filter(c -> c == guessCh).count();
                // Count how many we've already marked (green + yellow)
                int alreadyMarked = greenCount.getOrDefault(guessCh, 0) +
                        yellowCount.getOrDefault(guessCh, 0);

                Map<Character, WordleColor> innerMap = new HashMap<>();
                if (alreadyMarked < totalInWord) {
                    innerMap.put(guess.charAt(i), WordleColor.YELLOW);
                    this.charMap.put(i, innerMap);
                    coloredBuilder.append(Colors.YELLOW.code()).append(guessCh);
                    yellowCount.put(guessCh, yellowCount.getOrDefault(guessCh, 0) + 1);
                } else {
                    innerMap.put(guess.charAt(i), WordleColor.GRAY);
                    this.charMap.put(i, innerMap);
                    coloredBuilder.append(Colors.RESET.code()).append(guessCh);
                }
            } else {
                Map<Character, WordleColor> innerMap = new HashMap<>();
                innerMap.put(guess.charAt(i), WordleColor.GRAY);
                this.charMap.put(i, innerMap);
                coloredBuilder.append(Colors.RESET.code()).append(guessCh);
            }
        }
        return coloredBuilder.toString();
    }

    public String getWord() {
        return this.word;
    }

    public Map<Integer, Map<Character, WordleColor>> getCharMap() {
        return this.charMap;
    }
}
