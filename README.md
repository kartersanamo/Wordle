# Wordle

A console-based Wordle game written in Java. Guess the hidden 5-letter word in up to 6 tries, with optional AI-powered suggestions and an automated Wordle Bot mode.

## Features

- **Classic Wordle gameplay** — Guess the secret word; letters are colored green (correct position), yellow (in word, wrong position), or gray (not in word).
- **Wordle Bot suggestions** — Play with real-time suggestions: the bot narrows the word list using your feedback and shows up to 10 valid guesses.
- **Watch the Bot play** — Let the Wordle Bot play automatically, choosing from the remaining valid words each round.
- **Custom word list** — Uses `src/main/resources/words.txt` (5-letter words, one per line). Falls back to a default word if the file is missing or empty.

## Requirements

- **Java 25** (or compatible JRE)
- **Maven** (for building from source)

## Download (JAR release)

Pre-built JARs are available on the [Releases](https://github.com/kartersanamo/Wordle/releases) page. Download the latest `Wordle-*.jar`, then run:

```bash
java -jar Wordle-1.0-SNAPSHOT.jar
```

Replace with the actual JAR filename from the release. Requires Java 25 (or a compatible JRE) installed.

## Project structure

```
src/main/java/com/kartersanamo/
├── Main.java              # Entry point, main menu
├── WordleGame.java        # Game loop, guess validation, word loading
├── enums/
│   ├── Colors.java        # ANSI color codes for terminal output
│   ├── GameOptions.java   # BOT_SUGGESTIONS, PLAY_BOT_GAME
│   ├── GameState.java     # NOT_STARTED, IN_PROGRESS, WON, LOST
│   └── WordleColor.java   # GREEN, YELLOW, GRAY (feedback)
└── models/
    ├── Board.java         # Stores guesses, prints board (6 rows)
    ├── Guess.java         # Single guess + colorization logic
    └── WordleBot.java     # Filters valid words from feedback
```

## Building and running

From the project root:

```bash
mvn compile
```

Run the `Main` class from your IDE (e.g. Run → Run 'Main'). The game entry point is `Main.main()`. To run from the command line with `java` or `mvn exec:java`, add a `public static void main(String[] args)` in `Main.java` that calls `main()`.

## How to play

1. Run the game and choose an option from the menu:
   - **1** — Play a normal game (you guess, no hints).
   - **2** — Play with Wordle Bot suggestions (you still type guesses; bot shows valid words).
   - **3** — Let the Wordle Bot play automatically.
   - **4** — Exit.

2. Each round you have 6 guesses. Enter a 5-letter word from the word list.

3. After each guess you get feedback:
   - **Green** — Letter is correct and in the right position.
   - **Yellow** — Letter is in the word but in a different position.
   - **Gray** — Letter is not in the word (or you’ve used all occurrences).

4. Win by guessing the word before you run out of guesses.

## Word list

Place a list of 5-letter words in `src/main/resources/words.txt`, one word per line. The game picks a random word from this file. If the file is missing or empty, a default word is used.

## License

Use and modify as you like for learning or personal projects.
