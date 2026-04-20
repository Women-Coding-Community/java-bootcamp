package com.wcc.bootcamp.java.mentorship.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Match model class.
 */
@DisplayName("Match")
class MatchTest {

    private Mentor mentor;
    private Mentee mentee;
    private Match match;

    @BeforeEach
    void setUp() {
        mentor = new Mentor("Alice Johnson", "alice@example.com", 
                Arrays.asList("java", "spring boot"));
        mentee = new Mentee("Bob Smith", "bob@example.com", 
                Arrays.asList("java", "web development"));
        match = new Match(mentor, mentee, List.of("java"), 0.5);
    }

    @Nested
    @DisplayName("Constructor")
    class ConstructorTests {

        @Test
        @DisplayName("should generate unique ID")
        void shouldGenerateUniqueId() {
            Match match2 = new Match(mentor, mentee, List.of("java"), 0.75);
            
            assertNotNull(match.getId());
            assertNotNull(match2.getId());
            assertNotEquals(match.getId(), match2.getId());
        }

        @Test
        @DisplayName("should set match date to now")
        void shouldSetMatchDateToNow() {
            LocalDateTime now = LocalDateTime.now();
            
            assertNotNull(match.getMatchDate());
            assertTrue(match.getMatchDate().isBefore(now.plusSeconds(1)));
            assertTrue(match.getMatchDate().isAfter(now.minusSeconds(5)));
        }

        @Test
        @DisplayName("should initialize status as PENDING")
        void shouldInitializeStatusAsPending() {
            assertEquals(Match.MatchStatus.PENDING, match.getStatus());
        }

        @Test
        @DisplayName("should store match score")
        void shouldStoreMatchScore() {
            assertEquals(0.5, match.getMatchScore());
        }

        @Test
        @DisplayName("should store matched skills")
        void shouldStoreMatchedSkills() {
            List<String> skills = match.getMatchedSkills();
            
            assertEquals(1, skills.size());
            assertTrue(skills.contains("java"));
        }
    }

    @Nested
    @DisplayName("Match Lifecycle")
    class MatchLifecycleTests {

        @Test
        @DisplayName("should activate match")
        void shouldActivateMatch() {
            match.activate();
            
            assertEquals(Match.MatchStatus.ACTIVE, match.getStatus());
        }

        @Test
        @DisplayName("should increment mentor count on activation")
        void shouldIncrementMentorCountOnActivation() {
            int initialCount = mentor.getCurrentMenteeCount();
            match.activate();
            
            assertEquals(initialCount + 1, mentor.getCurrentMenteeCount());
        }

        @Test
        @DisplayName("should set mentee as matched on activation")
        void shouldSetMenteeAsMatchedOnActivation() {
            match.activate();
            
            assertTrue(mentee.isMatched());
        }

        @Test
        @DisplayName("should cancel match")
        void shouldCancelMatch() {
            match.activate();
            match.cancel();
            
            assertEquals(Match.MatchStatus.CANCELLED, match.getStatus());
        }

        @Test
        @DisplayName("should decrement mentor count on cancellation")
        void shouldDecrementMentorCountOnCancellation() {
            match.activate();
            int countAfterActivation = mentor.getCurrentMenteeCount();
            match.cancel();
            
            assertEquals(countAfterActivation - 1, mentor.getCurrentMenteeCount());
        }

        @Test
        @DisplayName("should set mentee as unmatched on cancellation")
        void shouldSetMenteeAsUnmatchedOnCancellation() {
            match.activate();
            match.cancel();
            
            assertFalse(mentee.isMatched());
        }

        @Test
        @DisplayName("should complete match")
        void shouldCompleteMatch() {
            match.activate();
            match.complete();
            
            assertEquals(Match.MatchStatus.COMPLETED, match.getStatus());
        }

        @Test
        @DisplayName("should handle cancellation of pending match")
        void shouldHandleCancellationOfPendingMatch() {
            int initialCount = mentor.getCurrentMenteeCount();
            match.cancel();
            
            assertEquals(Match.MatchStatus.CANCELLED, match.getStatus());
            assertEquals(initialCount, mentor.getCurrentMenteeCount());
        }
    }

    @Nested
    @DisplayName("Equality")
    class EqualityTests {

        @Test
        @DisplayName("should be equal to itself")
        void shouldBeEqualToItself() {
            assertEquals(match, match);
        }

        @Test
        @DisplayName("should not be equal to different match")
        void shouldNotBeEqualToDifferentMatch() {
            Match other = new Match(mentor, mentee, List.of("java"), 0.5);
            
            assertNotEquals(match, other);
        }

        @Test
        @DisplayName("should have consistent hashCode")
        void shouldHaveConsistentHashCode() {
            int hash1 = match.hashCode();
            int hash2 = match.hashCode();
            
            assertEquals(hash1, hash2);
        }
    }

    @Nested
    @DisplayName("File Format")
    class FileFormatTests {

        @Test
        @DisplayName("should generate file format string")
        void shouldGenerateFileFormatString() {
            String format = match.toFileFormat();
            
            assertNotNull(format);
            assertTrue(format.contains(mentor.getId()));
            assertTrue(format.contains(mentee.getId()));
            assertTrue(format.contains("java"));
            assertTrue(format.contains("PENDING"));
        }
    }
}
