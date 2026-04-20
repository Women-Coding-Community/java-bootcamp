package org.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.generator.model.Challenge;
import org.generator.repository.ChallengeRepository;
import org.generator.service.ChallengeGenerator;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    ChallengeRepository repo = new ChallengeRepository();
    List<Challenge> challenges = repo.loadChallenges();

    if (challenges == null || challenges.isEmpty()) {
      System.out.println("No challenges available.");
      return;
    }

    ChallengeGenerator generator = new ChallengeGenerator();

    // 1. Choose difficulty
    System.out.println("\nChoose difficulty: [1] Easy, [2] Medium, [3] Hard");
    int difficultyInput = scanner.nextInt();
    scanner.nextLine(); // consume newline

    String difficulty;
    if (difficultyInput == 1) {
      difficulty = "Easy";
    } else if (difficultyInput == 2) {
      difficulty = "Medium";
    } else if (difficultyInput == 3) {
      difficulty = "Hard";
    } else {
      System.out.println("Invalid choice. Defaulting to Easy.");
      difficulty = "Easy";
    }

    // 2. Filter challenges by difficulty
    List<Challenge> filtered = new ArrayList<>();
    for (Challenge c : challenges) {
      if (c.getDifficulty().equalsIgnoreCase(difficulty)) {
        filtered.add(c);
      }
    }

    // 3. Get random challenge
    Challenge challenge = generator.getRandomChallenge(filtered);

    // 4. Ask for hint
    System.out.println("Would you like a hint? [Y/N]");
    String needHint = scanner.nextLine();

    boolean showHint = needHint.equalsIgnoreCase("Y");

    // 5. Display challenge
    System.out.println("\n--- Your Challenge ---");
    System.out.println("Title: " + challenge.getTitle());
    System.out.println("Difficulty: " + challenge.getDifficulty());
    System.out.println("Description: " + challenge.getDescription());

    if (showHint) {
      System.out.println("Hint: " + challenge.getHint());
    }

    scanner.close();
  }
}
