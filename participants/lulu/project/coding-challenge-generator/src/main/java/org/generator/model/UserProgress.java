package org.generator.model;

import java.util.HashSet;
import java.util.Set;

public class UserProgress {
  private final Set<String> completedChallenges = new HashSet<>();
  private int score = 0;

  public void completeChallenge(String title) {
    if (!completedChallenges.contains(title)) {
      completedChallenges.add(title);
      score += 10;
    }
  }

  public int getScore() {
    return score;
  }
}
