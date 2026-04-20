package com.wcc.bootcamp.java.mentorship.dto;

import jakarta.validation.constraints.*;

/**
 * Form object for mentee registration.
 */
public class MenteeRegistrationForm {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Pattern(regexp = "^[\\p{L}\\p{M}\\s.'-]+$", message = "Name contains invalid characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    @NotBlank(message = "At least one learning goal is required")
    @Size(max = 1000, message = "Learning goals must not exceed 1000 characters")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\s,._#+-]+$", message = "Learning goals contain invalid characters")
    private String learningGoals;

    @NotBlank(message = "Experience level is required")
    private String experienceLevel = "beginner";

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLearningGoals() {
        return learningGoals;
    }

    public void setLearningGoals(String learningGoals) {
        this.learningGoals = learningGoals;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
}
