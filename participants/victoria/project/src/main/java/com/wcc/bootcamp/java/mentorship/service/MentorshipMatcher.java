package com.wcc.bootcamp.java.mentorship.service;

import com.wcc.bootcamp.java.mentorship.model.Match;
import com.wcc.bootcamp.java.mentorship.model.Mentee;
import com.wcc.bootcamp.java.mentorship.model.Mentor;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing mentorship matching operations.
 * Handles registration, matching, and persistence of mentor-mentee relationships.
 */
public class MentorshipMatcher {
    private final List<Mentor> mentors;
    private final List<Mentee> mentees;
    private final List<Match> matches;
    private static final String MATCHES_FILE = "matches.txt";

    public MentorshipMatcher() {
        this.mentors = new ArrayList<>();
        this.mentees = new ArrayList<>();
        this.matches = new ArrayList<>();
    }

    // ==================== Registration Methods ====================

    /**
     * Registers a new mentor with the system.
     */
    public Mentor registerMentor(String name, String email, List<String> expertiseAreas) {
        // Normalize expertise areas to lowercase
        List<String> normalizedExpertise = expertiseAreas.stream()
                .map(String::toLowerCase)
                .map(String::trim)
                .collect(Collectors.toList());
        
        Mentor mentor = new Mentor(name, email, normalizedExpertise);
        mentors.add(mentor);
        System.out.println("✓ Mentor registered: " + mentor.getName());
        return mentor;
    }

    /**
     * Registers a new mentor with max mentees limit.
     */
    public Mentor registerMentor(String name, String email, List<String> expertiseAreas, int maxMentees) {
        List<String> normalizedExpertise = expertiseAreas.stream()
                .map(String::toLowerCase)
                .map(String::trim)
                .collect(Collectors.toList());
        
        Mentor mentor = new Mentor(name, email, normalizedExpertise, maxMentees);
        mentors.add(mentor);
        System.out.println("✓ Mentor registered: " + mentor.getName());
        return mentor;
    }

    /**
     * Registers a new mentee with the system.
     */
    public Mentee registerMentee(String name, String email, List<String> learningGoals) {
        // Normalize learning goals to lowercase
        List<String> normalizedGoals = learningGoals.stream()
                .map(String::toLowerCase)
                .map(String::trim)
                .collect(Collectors.toList());
        
        Mentee mentee = new Mentee(name, email, normalizedGoals);
        mentees.add(mentee);
        System.out.println("✓ Mentee registered: " + mentee.getName());
        return mentee;
    }

    /**
     * Registers a new mentee with experience level.
     */
    public Mentee registerMentee(String name, String email, List<String> learningGoals, String experienceLevel) {
        List<String> normalizedGoals = learningGoals.stream()
                .map(String::toLowerCase)
                .map(String::trim)
                .collect(Collectors.toList());
        
        Mentee mentee = new Mentee(name, email, normalizedGoals, experienceLevel);
        mentees.add(mentee);
        System.out.println("✓ Mentee registered: " + mentee.getName());
        return mentee;
    }

    // ==================== Matching Methods ====================

    /**
     * Finds all potential matches for a specific mentee.
     * Returns matches sorted by score (highest first).
     */
    public List<Match> findMatchesForMentee(Mentee mentee) {
        List<Match> potentialMatches = new ArrayList<>();

        for (Mentor mentor : mentors) {
            if (!mentor.canAcceptMoreMentees()) {
                continue; // Skip mentors who are at capacity
            }

            MatchResult result = calculateMatchScore(mentor, mentee);
            
            if (result.score > 0) {
                Match match = new Match(mentor, mentee, result.matchedSkills, result.score);
                potentialMatches.add(match);
            }
        }

        // Sort by score descending
        potentialMatches.sort((m1, m2) -> Double.compare(m2.getMatchScore(), m1.getMatchScore()));
        
        return potentialMatches;
    }

    /**
     * Finds all potential matches for a specific mentor.
     * Returns matches sorted by score (highest first).
     */
    public List<Match> findMatchesForMentor(Mentor mentor) {
        List<Match> potentialMatches = new ArrayList<>();

        if (!mentor.canAcceptMoreMentees()) {
            return potentialMatches; // Return empty if at capacity
        }

        for (Mentee mentee : mentees) {
            if (mentee.isMatched()) {
                continue; // Skip already matched mentees
            }

            MatchResult result = calculateMatchScore(mentor, mentee);
            
            if (result.score > 0) {
                Match match = new Match(mentor, mentee, result.matchedSkills, result.score);
                potentialMatches.add(match);
            }
        }

        // Sort by score descending
        potentialMatches.sort((m1, m2) -> Double.compare(m2.getMatchScore(), m1.getMatchScore()));
        
        return potentialMatches;
    }

    /**
     * Calculates match score between a mentor and mentee using multiple criteria.
     * Score is based on:
     * - Keyword matching between expertise and learning goals
     * - Number of matching skills
     * - Partial string matching for related terms
     */
    private MatchResult calculateMatchScore(Mentor mentor, Mentee mentee) {
        List<String> matchedSkills = new ArrayList<>();
        List<String> mentorExpertise = mentor.getExpertiseAreas();
        List<String> menteeGoals = mentee.getLearningGoals();

        // Find matching skills
        for (String goal : menteeGoals) {
            for (String expertise : mentorExpertise) {
                if (isSkillMatch(expertise, goal)) {
                    // Add the original goal (what mentee wants to learn)
                    if (!matchedSkills.contains(goal)) {
                        matchedSkills.add(goal);
                    }
                }
            }
        }

        // Calculate score as percentage of mentee goals that can be fulfilled
        double score = menteeGoals.isEmpty() ? 0 : 
                (double) matchedSkills.size() / menteeGoals.size();

        return new MatchResult(score, matchedSkills);
    }

    /**
     * Checks if two skills match using various matching strategies:
     * - Exact match
     * - Contains match (one string contains the other)
     * - Common words match
     */
    private boolean isSkillMatch(String skill1, String skill2) {
        String s1 = skill1.toLowerCase().trim();
        String s2 = skill2.toLowerCase().trim();

        // Exact match
        if (s1.equals(s2)) {
            return true;
        }

        // Contains match
        if (s1.contains(s2) || s2.contains(s1)) {
            return true;
        }

        // Word-level matching
        Set<String> words1 = new HashSet<>(Arrays.asList(s1.split("\\s+")));
        Set<String> words2 = new HashSet<>(Arrays.asList(s2.split("\\s+")));
        
        // Check for common significant words (length > 2)
        for (String word : words1) {
            if (word.length() > 2 && words2.contains(word)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Creates and activates a match between mentor and mentee.
     */
    public Match createMatch(Mentor mentor, Mentee mentee) {
        MatchResult result = calculateMatchScore(mentor, mentee);
        Match match = new Match(mentor, mentee, result.matchedSkills, result.score);
        match.activate();
        matches.add(match);
        System.out.println("✓ Match created: " + mentor.getName() + " <-> " + mentee.getName());
        return match;
    }

    /**
     * Activates a pending match.
     */
    public void activateMatch(Match match) {
        if (match.getStatus() == Match.MatchStatus.PENDING) {
            match.activate();
            if (!matches.contains(match)) {
                matches.add(match);
            }
            System.out.println("✓ Match activated: " + match);
        }
    }

    // ==================== Unmatch/Rematch Methods ====================

    /**
     * Cancels an existing match (unmatch).
     */
    public void unmatch(Match match) {
        match.cancel();
        System.out.println("✓ Match cancelled: " + match.getMentor().getName() + " <-> " + match.getMentee().getName());
    }

    /**
     * Rematches a mentee with a new mentor.
     */
    public Match rematch(Mentee mentee, Mentor newMentor) {
        // Find and cancel existing active match for this mentee
        Optional<Match> existingMatch = matches.stream()
                .filter(m -> m.getMentee().equals(mentee) && m.getStatus() == Match.MatchStatus.ACTIVE)
                .findFirst();

        existingMatch.ifPresent(this::unmatch);

        // Create new match
        return createMatch(newMentor, mentee);
    }

    // ==================== Display Methods ====================

    /**
     * Displays all potential matches for all unmatched mentees.
     */
    public void displayAllPotentialMatches() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           POTENTIAL MATCHES");
        System.out.println("=".repeat(60));

        for (Mentee mentee : mentees) {
            if (mentee.isMatched()) {
                continue;
            }

            List<Match> potentialMatches = findMatchesForMentee(mentee);
            
            System.out.println("\n► Mentee: " + mentee.getName());
            System.out.println("  Learning Goals: " + mentee.getLearningGoals());
            
            if (potentialMatches.isEmpty()) {
                System.out.println("  ✗ No matching mentors found");
            } else {
                System.out.println("  Potential Mentors:");
                for (int i = 0; i < potentialMatches.size(); i++) {
                    Match match = potentialMatches.get(i);
                    System.out.printf("    %d. %s (Score: %.0f%%) - Skills: %s%n",
                            i + 1,
                            match.getMentor().getName(),
                            match.getMatchScore() * 100,
                            match.getMatchedSkills());
                }
            }
        }
        System.out.println("\n" + "=".repeat(60));
    }

    /**
     * Displays all active matches.
     */
    public void displayActiveMatches() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           ACTIVE MATCHES");
        System.out.println("=".repeat(60));

        List<Match> activeMatches = matches.stream()
                .filter(m -> m.getStatus() == Match.MatchStatus.ACTIVE)
                .collect(Collectors.toList());

        if (activeMatches.isEmpty()) {
            System.out.println("No active matches found.");
        } else {
            for (Match match : activeMatches) {
                System.out.println("\n► " + match);
                System.out.println("  Match Date: " + match.getMatchDate());
            }
        }
        System.out.println("\n" + "=".repeat(60));
    }

    /**
     * Displays all registered mentors.
     */
    public void displayAllMentors() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           REGISTERED MENTORS");
        System.out.println("=".repeat(60));

        if (mentors.isEmpty()) {
            System.out.println("No mentors registered.");
        } else {
            for (int i = 0; i < mentors.size(); i++) {
                Mentor mentor = mentors.get(i);
                System.out.printf("%d. %s%n", i + 1, mentor);
            }
        }
        System.out.println("=".repeat(60));
    }

    /**
     * Displays all registered mentees.
     */
    public void displayAllMentees() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           REGISTERED MENTEES");
        System.out.println("=".repeat(60));

        if (mentees.isEmpty()) {
            System.out.println("No mentees registered.");
        } else {
            for (int i = 0; i < mentees.size(); i++) {
                Mentee mentee = mentees.get(i);
                System.out.printf("%d. %s%n", i + 1, mentee);
            }
        }
        System.out.println("=".repeat(60));
    }

    // ==================== File Persistence Methods ====================

    /**
     * Saves all matches to a file.
     */
    public void saveMatchesToFile() {
        saveMatchesToFile(MATCHES_FILE);
    }

    /**
     * Saves all matches to a specified file.
     */
    public void saveMatchesToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("# Mentorship Matches Export");
            writer.println("# Format: ID|MentorID|MentorName|MenteeID|MenteeName|Score|Skills|Status");
            writer.println("# Generated: " + java.time.LocalDateTime.now());
            writer.println();

            for (Match match : matches) {
                writer.println(match.toFileFormat());
            }

            System.out.println("✓ Matches saved to: " + filename);
        } catch (IOException e) {
            System.err.println("✗ Error saving matches: " + e.getMessage());
        }
    }

    /**
     * Exports a detailed report of all matches.
     */
    public void exportDetailedReport(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("╔══════════════════════════════════════════════════════════════╗");
            writer.println("║           MENTORSHIP MATCHER - DETAILED REPORT               ║");
            writer.println("╚══════════════════════════════════════════════════════════════╝");
            writer.println();
            writer.println("Generated: " + java.time.LocalDateTime.now());
            writer.println();

            // Summary statistics
            writer.println("── SUMMARY ─────────────────────────────────────────────────────");
            writer.printf("Total Mentors: %d%n", mentors.size());
            writer.printf("Total Mentees: %d%n", mentees.size());
            writer.printf("Total Matches: %d%n", matches.size());
            writer.printf("Active Matches: %d%n", 
                    matches.stream().filter(m -> m.getStatus() == Match.MatchStatus.ACTIVE).count());
            writer.println();

            // Mentors section
            writer.println("── MENTORS ─────────────────────────────────────────────────────");
            for (Mentor mentor : mentors) {
                writer.println("  • " + mentor);
            }
            writer.println();

            // Mentees section
            writer.println("── MENTEES ─────────────────────────────────────────────────────");
            for (Mentee mentee : mentees) {
                writer.println("  • " + mentee);
            }
            writer.println();

            // Matches section
            writer.println("── MATCHES ─────────────────────────────────────────────────────");
            for (Match match : matches) {
                writer.println("  • " + match);
                writer.println("    Date: " + match.getMatchDate());
                writer.println();
            }

            System.out.println("✓ Detailed report exported to: " + filename);
        } catch (IOException e) {
            System.err.println("✗ Error exporting report: " + e.getMessage());
        }
    }

    // ==================== Getter Methods ====================

    public List<Mentor> getMentors() {
        return new ArrayList<>(mentors);
    }

    public List<Mentee> getMentees() {
        return new ArrayList<>(mentees);
    }

    public List<Match> getMatches() {
        return new ArrayList<>(matches);
    }

    public Optional<Mentor> findMentorByName(String name) {
        return mentors.stream()
                .filter(m -> m.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public Optional<Mentee> findMenteeByName(String name) {
        return mentees.stream()
                .filter(m -> m.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    // Helper class for match calculation results
    private static class MatchResult {
        final double score;
        final List<String> matchedSkills;

        MatchResult(double score, List<String> matchedSkills) {
            this.score = score;
            this.matchedSkills = matchedSkills;
        }
    }
}
