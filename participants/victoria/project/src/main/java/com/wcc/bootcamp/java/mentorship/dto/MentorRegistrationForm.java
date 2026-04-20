package com.wcc.bootcamp.java.mentorship.dto;

import jakarta.validation.constraints.*;

/**
 * Form object for mentor registration.
 */
public class MentorRegistrationForm {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Pattern(regexp = "^[\\p{L}\\p{M}\\s.'-]+$", message = "Name contains invalid characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    @NotBlank(message = "At least one skill/expertise area is required")
    @Size(max = 1000, message = "Skills must not exceed 1000 characters")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\s,._#+-]+$", message = "Skills contain invalid characters")
    private String skills;

    @Min(value = 1, message = "Must accept at least 1 mentee")
    @Max(value = 10, message = "Cannot accept more than 10 mentees")
    private int maxMentees = 3;

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

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public int getMaxMentees() {
        return maxMentees;
    }

    public void setMaxMentees(int maxMentees) {
        this.maxMentees = maxMentees;
    }
}
