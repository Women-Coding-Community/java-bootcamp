package org.generator.model;

public class Challenge {
  private String title;
  private String description;
  private String difficulty;
  private String hint;

  // Jackson requires a no-args constructor
  public Challenge() {}

  public Challenge(String title, String description, String difficulty, String hint) {
    this.title = title;
    this.description = description;
    this.difficulty = difficulty;
    this.hint = hint;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(String difficulty) {
    this.difficulty = difficulty;
  }

  public String getHint() {
    return hint;
  }

  public void setHint(String hint) {
    this.hint = hint;
  }
}
