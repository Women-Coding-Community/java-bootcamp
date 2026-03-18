package com.wcc.bootcamp.java.mentorship.service;

import com.wcc.bootcamp.java.mentorship.model.Match;
import com.wcc.bootcamp.java.mentorship.model.Mentee;
import com.wcc.bootcamp.java.mentorship.model.Mentor;
import com.wcc.bootcamp.java.mentorship.repository.MatchRepository;
import com.wcc.bootcamp.java.mentorship.repository.MenteeRepository;
import com.wcc.bootcamp.java.mentorship.repository.MentorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Spring-managed service for mentorship matching operations.
 * Uses JPA repositories for data persistence.
 */
@Service
@Transactional
public class MentorshipService {
    private final MentorRepository mentorRepository;
    private final MenteeRepository menteeRepository;
    private final MatchRepository matchRepository;
    private final EmailService emailService;

    public MentorshipService(MentorRepository mentorRepository, 
                            MenteeRepository menteeRepository, 
                            MatchRepository matchRepository,
                            EmailService emailService) {
        this.mentorRepository = mentorRepository;
        this.menteeRepository = menteeRepository;
        this.matchRepository = matchRepository;
        this.emailService = emailService;
    }

    // ==================== Mentor Operations ====================

    public Mentor registerMentor(String name, String email, List<String> expertiseAreas, int maxMentees) {
        List<String> normalizedExpertise = expertiseAreas.stream()
                .map(String::toLowerCase)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        Mentor mentor = new Mentor(name, email, normalizedExpertise, maxMentees);
        return mentorRepository.save(mentor);
    }

    @Transactional(readOnly = true)
    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Mentor> findMentorById(String id) {
        return mentorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Mentor> findMentorByName(String name) {
        return mentorRepository.findByNameIgnoreCase(name);
    }

    public void deleteMentor(String id) {
        mentorRepository.findById(id).ifPresent(mentor -> {
            // Delete all matches involving this mentor first
            matchRepository.deleteByMentor(mentor);
            mentorRepository.delete(mentor);
        });
    }

    // ==================== Mentee Operations ====================

    public Mentee registerMentee(String name, String email, List<String> learningGoals, String experienceLevel) {
        List<String> normalizedGoals = learningGoals.stream()
                .map(String::toLowerCase)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        Mentee mentee = new Mentee(name, email, normalizedGoals, experienceLevel);
        return menteeRepository.save(mentee);
    }

    @Transactional(readOnly = true)
    public List<Mentee> getAllMentees() {
        return menteeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Mentee> findMenteeById(String id) {
        return menteeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Mentee> findMenteeByName(String name) {
        return menteeRepository.findByNameIgnoreCase(name);
    }

    public void deleteMentee(String id) {
        menteeRepository.findById(id).ifPresent(mentee -> {
            // Delete all matches involving this mentee first
            matchRepository.deleteByMentee(mentee);
            menteeRepository.delete(mentee);
        });
    }

    // ==================== Matching Operations ====================

    @Transactional(readOnly = true)
    public List<Match> findMatchesForMentee(String menteeId) {
        Optional<Mentee> menteeOpt = findMenteeById(menteeId);
        if (menteeOpt.isEmpty()) {
            return Collections.emptyList();
        }

        Mentee mentee = menteeOpt.get();
        List<Match> potentialMatches = new ArrayList<>();

        for (Mentor mentor : mentorRepository.findAll()) {
            if (!mentor.canAcceptMoreMentees()) {
                continue;
            }

            MatchResult result = calculateMatchScore(mentor, mentee);

            if (result.score > 0) {
                Match match = new Match(mentor, mentee, result.matchedSkills, result.score);
                potentialMatches.add(match);
            }
        }

        potentialMatches.sort((m1, m2) -> Double.compare(m2.getMatchScore(), m1.getMatchScore()));
        return potentialMatches;
    }

    @Transactional(readOnly = true)
    public List<Match> findMatchesForMentor(String mentorId) {
        Optional<Mentor> mentorOpt = findMentorById(mentorId);
        if (mentorOpt.isEmpty()) {
            return Collections.emptyList();
        }

        Mentor mentor = mentorOpt.get();
        if (!mentor.canAcceptMoreMentees()) {
            return Collections.emptyList();
        }

        List<Match> potentialMatches = new ArrayList<>();

        for (Mentee mentee : menteeRepository.findAll()) {
            if (mentee.isMatched()) {
                continue;
            }

            MatchResult result = calculateMatchScore(mentor, mentee);

            if (result.score > 0) {
                Match match = new Match(mentor, mentee, result.matchedSkills, result.score);
                potentialMatches.add(match);
            }
        }

        potentialMatches.sort((m1, m2) -> Double.compare(m2.getMatchScore(), m1.getMatchScore()));
        return potentialMatches;
    }

    @Transactional(readOnly = true)
    public List<Match> findAllPotentialMatches() {
        List<Match> allMatches = new ArrayList<>();

        for (Mentee mentee : menteeRepository.findByIsMatchedFalse()) {
            allMatches.addAll(findMatchesForMentee(mentee.getId()));
        }

        // Remove duplicates and sort by score
        return allMatches.stream()
                .distinct()
                .sorted((m1, m2) -> Double.compare(m2.getMatchScore(), m1.getMatchScore()))
                .collect(Collectors.toList());
    }

    private MatchResult calculateMatchScore(Mentor mentor, Mentee mentee) {
        List<String> matchedSkills = new ArrayList<>();
        List<String> mentorExpertise = mentor.getExpertiseAreas();
        List<String> menteeGoals = mentee.getLearningGoals();

        for (String goal : menteeGoals) {
            for (String expertise : mentorExpertise) {
                if (isSkillMatch(expertise, goal)) {
                    if (!matchedSkills.contains(goal)) {
                        matchedSkills.add(goal);
                    }
                }
            }
        }

        double score = menteeGoals.isEmpty() ? 0 :
                (double) matchedSkills.size() / menteeGoals.size();

        return new MatchResult(score, matchedSkills);
    }

    private boolean isSkillMatch(String skill1, String skill2) {
        String s1 = skill1.toLowerCase().trim();
        String s2 = skill2.toLowerCase().trim();

        if (s1.equals(s2)) {
            return true;
        }

        if (s1.contains(s2) || s2.contains(s1)) {
            return true;
        }

        Set<String> words1 = new HashSet<>(Arrays.asList(s1.split("\\s+")));
        Set<String> words2 = new HashSet<>(Arrays.asList(s2.split("\\s+")));

        for (String word : words1) {
            if (word.length() > 2 && words2.contains(word)) {
                return true;
            }
        }

        return false;
    }

    public Match createMatch(String mentorId, String menteeId) {
        Optional<Mentor> mentorOpt = findMentorById(mentorId);
        Optional<Mentee> menteeOpt = findMenteeById(menteeId);

        if (mentorOpt.isEmpty() || menteeOpt.isEmpty()) {
            throw new IllegalArgumentException("Mentor or Mentee not found");
        }

        Mentor mentor = mentorOpt.get();
        Mentee mentee = menteeOpt.get();

        MatchResult result = calculateMatchScore(mentor, mentee);
        Match match = new Match(mentor, mentee, result.matchedSkills, result.score);
        match.activate();
        
        // Save updated mentor and mentee counts
        mentorRepository.save(mentor);
        menteeRepository.save(mentee);
        
        Match savedMatch = matchRepository.save(match);
        
        // Send email notifications to both mentor and mentee
        emailService.sendMatchNotification(savedMatch);
        
        return savedMatch;
    }

    @Transactional(readOnly = true)
    public List<Match> getActiveMatches() {
        return matchRepository.findByStatus(Match.MatchStatus.ACTIVE);
    }

    @Transactional(readOnly = true)
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public void cancelMatch(String matchId) {
        matchRepository.findById(matchId).ifPresent(match -> {
            match.cancel();
            // Save updated mentor and mentee counts
            mentorRepository.save(match.getMentor());
            menteeRepository.save(match.getMentee());
            matchRepository.save(match);
        });
    }

    // Helper class for match calculation
    private static class MatchResult {
        final double score;
        final List<String> matchedSkills;

        MatchResult(double score, List<String> matchedSkills) {
            this.score = score;
            this.matchedSkills = matchedSkills;
        }
    }

    // ==================== Statistics ====================

    @Transactional(readOnly = true)
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        List<Mentor> allMentors = mentorRepository.findAll();
        List<Mentee> allMentees = menteeRepository.findAll();
        
        stats.put("totalMentors", allMentors.size());
        stats.put("totalMentees", allMentees.size());
        stats.put("activeMatches", getActiveMatches().size());
        stats.put("availableMentors", allMentors.stream().filter(Mentor::canAcceptMoreMentees).count());
        stats.put("unmatchedMentees", allMentees.stream().filter(m -> !m.isMatched()).count());
        return stats;
    }
}
