package org.generator.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import org.generator.model.Challenge;

public class ChallengeRepository {

  public List<Challenge> loadChallenges() {
    try {
      ObjectMapper mapper = new ObjectMapper();

      InputStream input = getClass().getClassLoader().getResourceAsStream("challenges.json");

      return mapper.readValue(input, new TypeReference<List<Challenge>>() {});
    } catch (Exception e) {
      throw new RuntimeException("Failed to load challenges", e);
    }
  }
}
