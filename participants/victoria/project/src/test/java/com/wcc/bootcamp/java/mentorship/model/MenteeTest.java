package com.wcc.bootcamp.java.mentorship.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Mentee model class.
 */
@DisplayName("Mentee")
class MenteeTest {

    private Mentee mentee;

    @BeforeEach
    void setUp() {
        mentee = new Mentee("Bob Smith", "bob@example.com", 
                Arrays.asList("java", "web development", "databases"));
    }

    @Nested
    @DisplayName("Constructor")
    class ConstructorTests {

        @Test
        @DisplayName("should generate unique ID")
        void shouldGenerateUniqueId() {
            Mentee mentee2 = new Mentee("Alice", "alice@example.com", List.of("python"));
            
            assertNotNull(mentee.getId());
            assertNotNull(mentee2.getId());
            assertNotEquals(mentee.getId(), mentee2.getId());
        }

        @Test
        @DisplayName("should set default experience level to beginner")
        void shouldSetDefaultExperienceLevel() {
            assertEquals("beginner", mentee.getExperienceLevel());
        }

        @Test
        @DisplayName("should allow custom experience level")
        void shouldAllowCustomExperienceLevel() {
            Mentee advancedMentee = new Mentee("Carol", "carol@example.com", 
                    List.of("java"), "intermediate");
            
            assertEquals("intermediate", advancedMentee.getExperienceLevel());
        }

        @Test
        @DisplayName("should initialize as not matched")
        void shouldInitializeAsNotMatched() {
            assertFalse(mentee.isMatched());
        }
    }

    @Nested
    @DisplayName("Match Status")
    class MatchStatusTests {

        @Test
        @DisplayName("should update matched status")
        void shouldUpdateMatchedStatus() {
            mentee.setMatched(true);
            
            assertTrue(mentee.isMatched());
        }

        @Test
        @DisplayName("should toggle matched status")
        void shouldToggleMatchedStatus() {
            mentee.setMatched(true);
            mentee.setMatched(false);
            
            assertFalse(mentee.isMatched());
        }
    }

    @Nested
    @DisplayName("Learning Goals")
    class LearningGoalsTests {

        @Test
        @DisplayName("should return learning goals")
        void shouldReturnLearningGoals() {
            List<String> goals = mentee.getLearningGoals();
            
            assertEquals(3, goals.size());
            assertTrue(goals.contains("java"));
        }

        @Test
        @DisplayName("should want to learn matching skill")
        void shouldWantToLearnMatchingSkill() {
            assertTrue(mentee.wantsToLearn("java"));
        }

        @Test
        @DisplayName("should match learning goals case-insensitively")
        void shouldMatchLearningGoalsCaseInsensitively() {
            assertTrue(mentee.wantsToLearn("JAVA"));
            assertTrue(mentee.wantsToLearn("Web Development"));
        }

        @Test
        @DisplayName("should match partial learning goal")
        void shouldMatchPartialLearningGoal() {
            assertTrue(mentee.wantsToLearn("web"));
        }

        @Test
        @DisplayName("should not match unrelated skill")
        void shouldNotMatchUnrelatedSkill() {
            assertFalse(mentee.wantsToLearn("machine learning"));
        }

        @Test
        @DisplayName("should add learning goal")
        void shouldAddLearningGoal() {
            mentee.addLearningGoal("python");
            
            assertTrue(mentee.getLearningGoals().contains("python"));
        }

        @Test
        @DisplayName("should not add duplicate learning goal")
        void shouldNotAddDuplicateLearningGoal() {
            int initialSize = mentee.getLearningGoals().size();
            mentee.addLearningGoal("java");
            
            assertEquals(initialSize, mentee.getLearningGoals().size());
        }
    }

    @Nested
    @DisplayName("Equality")
    class EqualityTests {

        @Test
        @DisplayName("should be equal to itself")
        void shouldBeEqualToItself() {
            assertEquals(mentee, mentee);
        }

        @Test
        @DisplayName("should not be equal to different mentee")
        void shouldNotBeEqualToDifferentMentee() {
            Mentee other = new Mentee("Alice", "alice@example.com", List.of("java"));
            
            assertNotEquals(mentee, other);
        }
    }
}
