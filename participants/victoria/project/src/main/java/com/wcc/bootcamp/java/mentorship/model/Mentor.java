package com.wcc.bootcamp.java.mentorship.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a mentor with expertise areas.
 * Mentors can be matched with mentees based on their skills.
 */
@Entity
@Table(name = "mentors")
public class Mentor {
    @Id
    private String id;
    
    private String name;
    private String email;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mentor_expertise", joinColumns = @JoinColumn(name = "mentor_id"))
    @Column(name = "expertise")
    private List<String> expertiseAreas;
    
    private int maxMentees;
    private int currentMenteeCount;

    // Default constructor required by JPA
    public Mentor() {
        this.id = UUID.randomUUID().toString();
        this.expertiseAreas = new ArrayList<>();
        this.maxMentees = 3;
        this.currentMenteeCount = 0;
    }

    public Mentor(String name, String email, List<String> expertiseAreas) {
        this();
        this.name = name;
        this.email = email;
        this.expertiseAreas = new ArrayList<>(expertiseAreas);
    }

    public Mentor(String name, String email, List<String> expertiseAreas, int maxMentees) {
        this(name, email, expertiseAreas);
        this.maxMentees = maxMentees;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

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

    public List<String> getExpertiseAreas() {
        return new ArrayList<>(expertiseAreas);
    }

    public void setExpertiseAreas(List<String> expertiseAreas) {
        this.expertiseAreas = new ArrayList<>(expertiseAreas);
    }

    public void addExpertise(String expertise) {
        if (!expertiseAreas.contains(expertise.toLowerCase())) {
            expertiseAreas.add(expertise.toLowerCase());
        }
    }

    public void removeExpertise(String expertise) {
        expertiseAreas.remove(expertise.toLowerCase());
    }

    public int getMaxMentees() {
        return maxMentees;
    }

    public void setMaxMentees(int maxMentees) {
        this.maxMentees = maxMentees;
    }

    public int getCurrentMenteeCount() {
        return currentMenteeCount;
    }

    public void incrementMenteeCount() {
        this.currentMenteeCount++;
    }

    public void decrementMenteeCount() {
        if (this.currentMenteeCount > 0) {
            this.currentMenteeCount--;
        }
    }

    public boolean canAcceptMoreMentees() {
        return currentMenteeCount < maxMentees;
    }

    /**
     * Checks if the mentor has expertise in a given skill (case-insensitive partial match).
     */
    public boolean hasExpertise(String skill) {
        String lowerSkill = skill.toLowerCase();
        return expertiseAreas.stream()
                .anyMatch(expertise -> expertise.toLowerCase().contains(lowerSkill) 
                        || lowerSkill.contains(expertise.toLowerCase()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mentor mentor = (Mentor) o;
        return Objects.equals(id, mentor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Mentor{name='%s', email='%s', expertise=%s, mentees=%d/%d}",
                name, email, expertiseAreas, currentMenteeCount, maxMentees);
    }
}
