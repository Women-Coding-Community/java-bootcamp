package org.generator.service;

import java.util.List;
import java.util.Random;
import org.generator.model.Challenge;

public class ChallengeGenerator {
  private final Random random = new Random();

  public Challenge getRandomChallenge(List<Challenge> challenges) {
    if (challenges == null || challenges.isEmpty()) {
      return null;
    }
    return challenges.get(random.nextInt(challenges.size()));
  }
}
