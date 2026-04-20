package com.wcc.bootcamp.java.mentorship.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Mentor model class.
 */
@DisplayName("Mentor")
class MentorTest {

    private Mentor mentor;

    @BeforeEach
    void setUp() {
        mentor = new Mentor("Alice Johnson", "alice@example.com", 
                Arrays.asList("java", "spring boot", "sql"));
    }

    @Nested
    @DisplayName("Constructor")
    class ConstructorTests {

        @Test
        @DisplayName("should generate unique ID")
        void shouldGenerateUniqueId() {
            Mentor mentor2 = new Mentor("Bob", "bob@example.com", List.of("python"));
            
            assertNotNull(mentor.getId());
            assertNotNull(mentor2.getId());
            assertNotEquals(mentor.getId(), mentor2.getId());
        }

        @Test
        @DisplayName("should set default max mentees to 3")
        void shouldSetDefaultMaxMentees() {
            assertEquals(3, mentor.getMaxMentees());
        }

        @Test
        @DisplayName("should allow custom max mentees")
        void shouldAllowCustomMaxMentees() {
            Mentor customMentor = new Mentor("Carol", "carol@example.com", 
                    List.of("java"), 5);
            
            assertEquals(5, customMentor.getMaxMentees());
        }

        @Test
        @DisplayName("should initialize current mentee count to 0")
        void shouldInitializeCurrentMenteeCountToZero() {
            assertEquals(0, mentor.getCurrentMenteeCount());
        }
    }

    @Nested
    @DisplayName("Mentee Capacity")
    class MenteeCapacityTests {

        @Test
        @DisplayName("should accept more mentees when under capacity")
        void shouldAcceptMoreMenteesWhenUnderCapacity() {
            assertTrue(mentor.canAcceptMoreMentees());
        }

        @Test
        @DisplayName("should not accept more mentees when at capacity")
        void shouldNotAcceptMoreMenteesWhenAtCapacity() {
            mentor.incrementMenteeCount();
            mentor.incrementMenteeCount();
            mentor.incrementMenteeCount();
            
            assertFalse(mentor.canAcceptMoreMentees());
        }

        @Test
        @DisplayName("should increment mentee count")
        void shouldIncrementMenteeCount() {
            mentor.incrementMenteeCount();
            
            assertEquals(1, mentor.getCurrentMenteeCount());
        }

        @Test
        @DisplayName("should decrement mentee count")
        void shouldDecrementMenteeCount() {
            mentor.incrementMenteeCount();
            mentor.incrementMenteeCount();
            mentor.decrementMenteeCount();
            
            assertEquals(1, mentor.getCurrentMenteeCount());
        }

        @Test
        @DisplayName("should not decrement below zero")
        void shouldNotDecrementBelowZero() {
            mentor.decrementMenteeCount();
            
            assertEquals(0, mentor.getCurrentMenteeCount());
        }
    }

    @Nested
    @DisplayName("Expertise Matching")
    class ExpertiseMatchingTests {

        @Test
        @DisplayName("should match exact expertise")
        void shouldMatchExactExpertise() {
            assertTrue(mentor.hasExpertise("java"));
        }

        @Test
        @DisplayName("should match expertise case-insensitively")
        void shouldMatchExpertiseCaseInsensitively() {
            assertTrue(mentor.hasExpertise("JAVA"));
            assertTrue(mentor.hasExpertise("Spring Boot"));
        }

        @Test
        @DisplayName("should match partial expertise")
        void shouldMatchPartialExpertise() {
            assertTrue(mentor.hasExpertise("spring"));
        }

        @Test
        @DisplayName("should not match unrelated skill")
        void shouldNotMatchUnrelatedSkill() {
            assertFalse(mentor.hasExpertise("python"));
        }
    }

    @Nested
    @DisplayName("Equality")
    class EqualityTests {

        @Test
        @DisplayName("should be equal to itself")
        void shouldBeEqualToItself() {
            assertEquals(mentor, mentor);
        }

        @Test
        @DisplayName("should not be equal to different mentor")
        void shouldNotBeEqualToDifferentMentor() {
            Mentor other = new Mentor("Bob", "bob@example.com", List.of("java"));
            
            assertNotEquals(mentor, other);
        }

        @Test
        @DisplayName("should have consistent hashCode")
        void shouldHaveConsistentHashCode() {
            int hash1 = mentor.hashCode();
            int hash2 = mentor.hashCode();
            
            assertEquals(hash1, hash2);
        }
    }
}
