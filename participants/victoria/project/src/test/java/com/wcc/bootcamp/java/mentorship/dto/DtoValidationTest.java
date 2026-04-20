package com.wcc.bootcamp.java.mentorship.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DTO validation rules.
 * Ensures input sanitization and security constraints are enforced.
 */
@DisplayName("DTO Validation")
class DtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("MentorRegistrationForm")
    class MentorRegistrationFormTests {

        private MentorRegistrationForm createValidForm() {
            MentorRegistrationForm form = new MentorRegistrationForm();
            form.setName("Alice Johnson");
            form.setEmail("alice@example.com");
            form.setSkills("java, spring boot");
            form.setMaxMentees(3);
            return form;
        }

        @Test
        @DisplayName("should accept valid form")
        void shouldAcceptValidForm() {
            MentorRegistrationForm form = createValidForm();
            
            Set<ConstraintViolation<MentorRegistrationForm>> violations = validator.validate(form);
            
            assertTrue(violations.isEmpty());
        }

        @Test
        @DisplayName("should reject empty name")
        void shouldRejectEmptyName() {
            MentorRegistrationForm form = createValidForm();
            form.setName("");
            
            Set<ConstraintViolation<MentorRegistrationForm>> violations = validator.validate(form);
            
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("should reject name that is too short")
        void shouldRejectNameTooShort() {
            MentorRegistrationForm form = createValidForm();
            form.setName("A");
            
            Set<ConstraintViolation<MentorRegistrationForm>> violations = validator.validate(form);
            
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("should reject invalid email")
        void shouldRejectInvalidEmail() {
            MentorRegistrationForm form = createValidForm();
            form.setEmail("not-an-email");
            
            Set<ConstraintViolation<MentorRegistrationForm>> violations = validator.validate(form);
            
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("should reject empty skills")
        void shouldRejectEmptySkills() {
            MentorRegistrationForm form = createValidForm();
            form.setSkills("");
            
            Set<ConstraintViolation<MentorRegistrationForm>> violations = validator.validate(form);
            
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("should reject maxMentees below minimum")
        void shouldRejectMaxMenteesBelowMinimum() {
            MentorRegistrationForm form = createValidForm();
            form.setMaxMentees(0);
            
            Set<ConstraintViolation<MentorRegistrationForm>> violations = validator.validate(form);
            
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("should reject maxMentees above maximum")
        void shouldRejectMaxMenteesAboveMaximum() {
            MentorRegistrationForm form = createValidForm();
            form.setMaxMentees(11);
            
            Set<ConstraintViolation<MentorRegistrationForm>> violations = validator.validate(form);
            
            assertFalse(violations.isEmpty());
        }

        @ParameterizedTest
        @DisplayName("should accept valid names")
        @ValueSource(strings = {"Alice", "Mary-Jane", "O'Brien", "José García", "李明"})
        void shouldAcceptValidNames(String name) {
            MentorRegistrationForm form = createValidForm();
            form.setName(name);
            
            Set<ConstraintViolation<MentorRegistrationForm>> violations = validator.validate(form);
            
            assertTrue(violations.isEmpty(), "Name '" + name + "' should be valid");
        }
    }

    @Nested
    @DisplayName("MenteeRegistrationForm")
    class MenteeRegistrationFormTests {

        private MenteeRegistrationForm createValidForm() {
            MenteeRegistrationForm form = new MenteeRegistrationForm();
            form.setName("Bob Smith");
            form.setEmail("bob@example.com");
            form.setLearningGoals("java, spring boot");
            form.setExperienceLevel("beginner");
            return form;
        }

        @Test
        @DisplayName("should accept valid form")
        void shouldAcceptValidForm() {
            MenteeRegistrationForm form = createValidForm();
            
            Set<ConstraintViolation<MenteeRegistrationForm>> violations = validator.validate(form);
            
            assertTrue(violations.isEmpty());
        }

        @Test
        @DisplayName("should reject empty name")
        void shouldRejectEmptyName() {
            MenteeRegistrationForm form = createValidForm();
            form.setName("");
            
            Set<ConstraintViolation<MenteeRegistrationForm>> violations = validator.validate(form);
            
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("should reject invalid email")
        void shouldRejectInvalidEmail() {
            MenteeRegistrationForm form = createValidForm();
            form.setEmail("invalid");
            
            Set<ConstraintViolation<MenteeRegistrationForm>> violations = validator.validate(form);
            
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("should reject empty learning goals")
        void shouldRejectEmptyLearningGoals() {
            MenteeRegistrationForm form = createValidForm();
            form.setLearningGoals("");
            
            Set<ConstraintViolation<MenteeRegistrationForm>> violations = validator.validate(form);
            
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("should reject empty experience level")
        void shouldRejectEmptyExperienceLevel() {
            MenteeRegistrationForm form = createValidForm();
            form.setExperienceLevel("");
            
            Set<ConstraintViolation<MenteeRegistrationForm>> violations = validator.validate(form);
            
            assertFalse(violations.isEmpty());
        }

        @ParameterizedTest
        @DisplayName("should accept valid learning goals")
        @ValueSource(strings = {"java", "C++", "C#", "spring-boot", "machine_learning"})
        void shouldAcceptValidLearningGoals(String goals) {
            MenteeRegistrationForm form = createValidForm();
            form.setLearningGoals(goals);
            
            Set<ConstraintViolation<MenteeRegistrationForm>> violations = validator.validate(form);
            
            assertTrue(violations.isEmpty(), "Learning goal '" + goals + "' should be valid");
        }
    }
}
