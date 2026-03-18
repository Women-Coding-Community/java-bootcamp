package com.wcc.bootcamp.java.mentorship.service;

import com.wcc.bootcamp.java.mentorship.model.Match;
import com.wcc.bootcamp.java.mentorship.model.Mentee;
import com.wcc.bootcamp.java.mentorship.model.Mentor;
import com.wcc.bootcamp.java.mentorship.repository.MatchRepository;
import com.wcc.bootcamp.java.mentorship.repository.MenteeRepository;
import com.wcc.bootcamp.java.mentorship.repository.MentorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the MentorshipService.
 * Uses Mockito to mock repository dependencies.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("MentorshipService")
class MentorshipServiceTest {

    @Mock
    private MentorRepository mentorRepository;

    @Mock
    private MenteeRepository menteeRepository;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private MentorshipService mentorshipService;

    private Mentor sampleMentor;
    private Mentee sampleMentee;

    @BeforeEach
    void setUp() {
        sampleMentor = new Mentor("Alice Johnson", "alice@example.com", 
                Arrays.asList("java", "spring boot", "sql"), 3);
        sampleMentee = new Mentee("Bob Smith", "bob@example.com", 
                Arrays.asList("java", "web development"), "beginner");
    }

    @Nested
    @DisplayName("Mentor Operations")
    class MentorOperationsTests {

        @Test
        @DisplayName("should register a new mentor")
        void shouldRegisterNewMentor() {
            when(mentorRepository.save(any(Mentor.class))).thenAnswer(i -> i.getArgument(0));

            Mentor result = mentorshipService.registerMentor(
                    "Alice Johnson", "alice@example.com", 
                    Arrays.asList("Java", "Spring Boot"), 3);

            assertNotNull(result);
            assertEquals("Alice Johnson", result.getName());
            verify(mentorRepository).save(any(Mentor.class));
        }

        @Test
        @DisplayName("should normalize expertise areas to lowercase")
        void shouldNormalizeExpertiseAreas() {
            ArgumentCaptor<Mentor> mentorCaptor = ArgumentCaptor.forClass(Mentor.class);
            when(mentorRepository.save(mentorCaptor.capture())).thenAnswer(i -> i.getArgument(0));

            mentorshipService.registerMentor("Alice", "alice@example.com", 
                    Arrays.asList("JAVA", "  Spring Boot  ", "SQL"), 3);

            Mentor capturedMentor = mentorCaptor.getValue();
            assertTrue(capturedMentor.getExpertiseAreas().stream()
                    .allMatch(s -> s.equals(s.toLowerCase().trim())));
        }

        @Test
        @DisplayName("should get all mentors")
        void shouldGetAllMentors() {
            when(mentorRepository.findAll()).thenReturn(List.of(sampleMentor));

            List<Mentor> mentors = mentorshipService.getAllMentors();

            assertEquals(1, mentors.size());
            verify(mentorRepository).findAll();
        }

        @Test
        @DisplayName("should find mentor by ID")
        void shouldFindMentorById() {
            when(mentorRepository.findById(sampleMentor.getId()))
                    .thenReturn(Optional.of(sampleMentor));

            Optional<Mentor> result = mentorshipService.findMentorById(sampleMentor.getId());

            assertTrue(result.isPresent());
            assertEquals(sampleMentor.getName(), result.get().getName());
        }

        @Test
        @DisplayName("should find mentor by name")
        void shouldFindMentorByName() {
            when(mentorRepository.findByNameIgnoreCase("Alice Johnson"))
                    .thenReturn(Optional.of(sampleMentor));

            Optional<Mentor> result = mentorshipService.findMentorByName("Alice Johnson");

            assertTrue(result.isPresent());
        }

        @Test
        @DisplayName("should delete mentor and associated matches")
        void shouldDeleteMentorAndAssociatedMatches() {
            when(mentorRepository.findById(sampleMentor.getId()))
                    .thenReturn(Optional.of(sampleMentor));

            mentorshipService.deleteMentor(sampleMentor.getId());

            verify(matchRepository).deleteByMentor(sampleMentor);
            verify(mentorRepository).delete(sampleMentor);
        }
    }

    @Nested
    @DisplayName("Mentee Operations")
    class MenteeOperationsTests {

        @Test
        @DisplayName("should register a new mentee")
        void shouldRegisterNewMentee() {
            when(menteeRepository.save(any(Mentee.class))).thenAnswer(i -> i.getArgument(0));

            Mentee result = mentorshipService.registerMentee(
                    "Bob Smith", "bob@example.com", 
                    Arrays.asList("Java", "Web Development"), "beginner");

            assertNotNull(result);
            assertEquals("Bob Smith", result.getName());
            verify(menteeRepository).save(any(Mentee.class));
        }

        @Test
        @DisplayName("should normalize learning goals to lowercase")
        void shouldNormalizeLearningGoals() {
            ArgumentCaptor<Mentee> menteeCaptor = ArgumentCaptor.forClass(Mentee.class);
            when(menteeRepository.save(menteeCaptor.capture())).thenAnswer(i -> i.getArgument(0));

            mentorshipService.registerMentee("Bob", "bob@example.com", 
                    Arrays.asList("JAVA", "  Web Dev  "), "beginner");

            Mentee capturedMentee = menteeCaptor.getValue();
            assertTrue(capturedMentee.getLearningGoals().stream()
                    .allMatch(s -> s.equals(s.toLowerCase().trim())));
        }

        @Test
        @DisplayName("should get all mentees")
        void shouldGetAllMentees() {
            when(menteeRepository.findAll()).thenReturn(List.of(sampleMentee));

            List<Mentee> mentees = mentorshipService.getAllMentees();

            assertEquals(1, mentees.size());
            verify(menteeRepository).findAll();
        }

        @Test
        @DisplayName("should delete mentee and associated matches")
        void shouldDeleteMenteeAndAssociatedMatches() {
            when(menteeRepository.findById(sampleMentee.getId()))
                    .thenReturn(Optional.of(sampleMentee));

            mentorshipService.deleteMentee(sampleMentee.getId());

            verify(matchRepository).deleteByMentee(sampleMentee);
            verify(menteeRepository).delete(sampleMentee);
        }
    }

    @Nested
    @DisplayName("Matching Operations")
    class MatchingOperationsTests {

        @Test
        @DisplayName("should find matches for mentee")
        void shouldFindMatchesForMentee() {
            when(menteeRepository.findById(sampleMentee.getId()))
                    .thenReturn(Optional.of(sampleMentee));
            when(mentorRepository.findAll()).thenReturn(List.of(sampleMentor));

            List<Match> matches = mentorshipService.findMatchesForMentee(sampleMentee.getId());

            assertFalse(matches.isEmpty());
            assertTrue(matches.get(0).getMatchScore() > 0);
        }

        @Test
        @DisplayName("should return empty list when mentee not found")
        void shouldReturnEmptyListWhenMenteeNotFound() {
            when(menteeRepository.findById("invalid-id")).thenReturn(Optional.empty());

            List<Match> matches = mentorshipService.findMatchesForMentee("invalid-id");

            assertTrue(matches.isEmpty());
        }

        @Test
        @DisplayName("should not match with mentor at capacity")
        void shouldNotMatchWithMentorAtCapacity() {
            // Fill mentor to capacity
            sampleMentor.incrementMenteeCount();
            sampleMentor.incrementMenteeCount();
            sampleMentor.incrementMenteeCount();

            when(menteeRepository.findById(sampleMentee.getId()))
                    .thenReturn(Optional.of(sampleMentee));
            when(mentorRepository.findAll()).thenReturn(List.of(sampleMentor));

            List<Match> matches = mentorshipService.findMatchesForMentee(sampleMentee.getId());

            assertTrue(matches.isEmpty());
        }

        @Test
        @DisplayName("should create and activate match")
        void shouldCreateAndActivateMatch() {
            when(mentorRepository.findById(sampleMentor.getId()))
                    .thenReturn(Optional.of(sampleMentor));
            when(menteeRepository.findById(sampleMentee.getId()))
                    .thenReturn(Optional.of(sampleMentee));
            when(matchRepository.save(any(Match.class))).thenAnswer(i -> i.getArgument(0));

            Match result = mentorshipService.createMatch(
                    sampleMentor.getId(), sampleMentee.getId());

            assertEquals(Match.MatchStatus.ACTIVE, result.getStatus());
            verify(mentorRepository).save(sampleMentor);
            verify(menteeRepository).save(sampleMentee);
            verify(matchRepository).save(any(Match.class));
        }

        @Test
        @DisplayName("should throw exception when creating match with invalid IDs")
        void shouldThrowExceptionWhenCreatingMatchWithInvalidIds() {
            when(mentorRepository.findById("invalid")).thenReturn(Optional.empty());
            when(menteeRepository.findById("invalid")).thenReturn(Optional.empty());

            assertThrows(IllegalArgumentException.class, 
                    () -> mentorshipService.createMatch("invalid", "invalid"));
        }

        @Test
        @DisplayName("should get active matches only")
        void shouldGetActiveMatchesOnly() {
            Match activeMatch = new Match(sampleMentor, sampleMentee, List.of("java"), 0.5);
            activeMatch.activate();
            
            when(matchRepository.findByStatus(Match.MatchStatus.ACTIVE))
                    .thenReturn(List.of(activeMatch));

            List<Match> activeMatches = mentorshipService.getActiveMatches();

            assertEquals(1, activeMatches.size());
            assertEquals(Match.MatchStatus.ACTIVE, activeMatches.get(0).getStatus());
        }

        @Test
        @DisplayName("should cancel match and update entities")
        void shouldCancelMatchAndUpdateEntities() {
            Match match = new Match(sampleMentor, sampleMentee, List.of("java"), 0.5);
            match.activate();
            
            when(matchRepository.findById(match.getId())).thenReturn(Optional.of(match));

            mentorshipService.cancelMatch(match.getId());

            assertEquals(Match.MatchStatus.CANCELLED, match.getStatus());
            verify(mentorRepository).save(sampleMentor);
            verify(menteeRepository).save(sampleMentee);
            verify(matchRepository).save(match);
        }
    }

    @Nested
    @DisplayName("Statistics")
    class StatisticsTests {

        @Test
        @DisplayName("should calculate statistics correctly")
        void shouldCalculateStatisticsCorrectly() {
            Mentor availableMentor = new Mentor("Carol", "carol@example.com", 
                    List.of("python"), 2);
            Mentee unmatchedMentee = new Mentee("Dave", "dave@example.com", 
                    List.of("python"), "beginner");
            
            Match activeMatch = new Match(sampleMentor, sampleMentee, List.of("java"), 0.5);
            activeMatch.activate();

            when(mentorRepository.findAll()).thenReturn(List.of(sampleMentor, availableMentor));
            when(menteeRepository.findAll()).thenReturn(List.of(sampleMentee, unmatchedMentee));
            when(matchRepository.findByStatus(Match.MatchStatus.ACTIVE))
                    .thenReturn(List.of(activeMatch));

            Map<String, Object> stats = mentorshipService.getStatistics();

            assertEquals(2, stats.get("totalMentors"));
            assertEquals(2, stats.get("totalMentees"));
            assertEquals(1, stats.get("activeMatches"));
        }
    }
}
