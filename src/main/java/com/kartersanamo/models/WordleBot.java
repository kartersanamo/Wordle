package com.kartersanamo.models;

import com.kartersanamo.WordleGame;
import com.kartersanamo.enums.WordleColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordleBot {

    private final WordleGame game;

    public WordleBot(WordleGame game) {
        this.game = game;
    }

    public List<String> getValidWords() {
        List<String> totalWords = this.game.getTotalWords();
        List<Guess> guessesList = this.game.getBoard().getGuessesList();

        if (guessesList == null || guessesList.isEmpty()) {
            return new ArrayList<>(totalWords);
        }

        List<String> words = new ArrayList<>();
        List<Map<Integer, Map<Character, WordleColor>>> charMap = guessesList.stream()
                .map(Guess::getCharMap)
                .toList();

        // DEBUG: Print what the answer is and if it passes
        // String answer = WordleGame.getWord();
        // System.out.println("DEBUG: Checking if answer '" + answer + "' is valid: " + isValidWord(answer, charMap));

        // For each potential word, check if it matches all the clues
        for (String word : totalWords) {
            if (isValidWord(word, charMap)) {
                words.add(word);
            }
        }

        int MAX_SUGGESTIONS = 10;
        return words.size() <= MAX_SUGGESTIONS ? words : words.subList(0, MAX_SUGGESTIONS);
    }

    private boolean isValidWord(String word, List<Map<Integer, Map<Character, WordleColor>>> charMap) {
        // Check each guess's constraints
        for (Map<Integer, Map<Character, WordleColor>> guess : charMap) {
            // First, count how many of each character are green/yellow in THIS guess
            Map<Character, Long> requiredCounts = getCharacterLongMap(guess);

            for (Map.Entry<Integer, Map<Character, WordleColor>> entry : guess.entrySet()) {
                int position = entry.getKey();
                Map<Character, WordleColor> charColor = entry.getValue();

                for (Map.Entry<Character, WordleColor> colorEntry : charColor.entrySet()) {
                    char ch = colorEntry.getKey();
                    WordleColor color = colorEntry.getValue();

                    // GREEN: character must be at this exact position
                    if (color == WordleColor.GREEN) {
                        if (word.charAt(position) != ch) {
                            return false;
                        }
                    }
                    // YELLOW: character must be in word but NOT at this position
                    else if (color == WordleColor.YELLOW) {
                        if (!word.contains(String.valueOf(ch)) || word.charAt(position) == ch) {
                            return false;
                        }
                    }
                    // GRAY: character count must match exactly what's required (if any green/yellow exist)
                    else if (color == WordleColor.GRAY) {
                        if (requiredCounts.containsKey(ch)) {
                            // This character appears as green/yellow elsewhere in THIS guess
                            long required = requiredCounts.get(ch);
                            long actual = word.chars().filter(c -> c == ch).count();
                            if (actual != required) {
                                return false;
                            }
                        } else {
                            // Character doesn't appear as green/yellow, so shouldn't be in word at all
                            if (word.contains(String.valueOf(ch))) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    private static Map<Character, Long> getCharacterLongMap(Map<Integer, Map<Character, WordleColor>> guess) {
        Map<Character, Long> requiredCounts = new HashMap<>();
        for (Map.Entry<Integer, Map<Character, WordleColor>> entry : guess.entrySet()) {
            for (Map.Entry<Character, WordleColor> colorEntry : entry.getValue().entrySet()) {
                char ch = colorEntry.getKey();
                WordleColor color = colorEntry.getValue();
                if (color == WordleColor.GREEN || color == WordleColor.YELLOW) {
                    requiredCounts.put(ch, requiredCounts.getOrDefault(ch, 0L) + 1);
                }
            }
        }
        return requiredCounts;
    }
}
