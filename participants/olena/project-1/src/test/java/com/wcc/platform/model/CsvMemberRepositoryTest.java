package com.wcc.platform.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class  CsvMemberRepositoryTest {

    private CsvMemberRepository repository;

    @BeforeEach
    void setUp() {
        repository = new CsvMemberRepository();
    }

    // --- helpers ---

    private Member member(String name, String email, String location, List<String> skills, LocalDate joinDate) {
        return new Member(name, email, location, skills, joinDate);
    }



    @Test
    void shouldAddMemberWhenEmailIsUnique() {
        //Arrange
        Member member = member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 1));

        //Act
        repository.add(member);

        //Assert
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldNotAddMemberWhenEmailAlreadyExists() {
        //Arrange
        Member first = member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 1));
        Member duplicate = member("Alice Two", "alice@example.com", "Berlin", List.of("Go"), LocalDate.of(2024, 1, 1));

        //Act
        repository.add(first);
        repository.add(duplicate);

        //Assert
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldNotAddMemberWhenEmailMatchesCaseInsensitive() {
        //Arrange
        Member first = member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 1));
        Member duplicate = member("Alice Upper", "ALICE@EXAMPLE.COM", "Paris", List.of("Python"), LocalDate.of(2024, 1, 1));

        //Act
        repository.add(first);
        repository.add(duplicate);

        //Assert
        assertEquals(1, repository.findAll().size());
    }



    @Test
    void shouldReturnEmptyListWhenNoMembersAdded() {
        //Act
        List<Member> result = repository.findAll();

        //Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnAllAddedMembers() {
        //Arrange
        repository.add(member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 1)));
        repository.add(member("Bob", "bob@example.com", "Berlin", List.of("Go"), LocalDate.of(2024, 2, 1)));

        //Act
        List<Member> result = repository.findAll();

        //Assert
        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnCopyOfMemberListNotInternalReference() {
        //Arrange
        repository.add(member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 1)));

        //Act
        List<Member> result = repository.findAll();
        result.clear();

        //Assert
        assertEquals(1, repository.findAll().size());
    }



    @Test
    void shouldDeleteMemberByEmail() {
        //Arrange
        repository.add(member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 1)));

        //Act
        repository.deleteByEmail("alice@example.com");

        //Assert
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void shouldDeleteMemberByEmailCaseInsensitive() {
        //Arrange
        repository.add(member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 1)));

        //Act
        repository.deleteByEmail("ALICE@EXAMPLE.COM");

        //Assert
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void shouldNotChangeListWhenDeletingNonExistentEmail() {
        //Arrange
        repository.add(member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 1)));

        //Act
        repository.deleteByEmail("nobody@example.com");

        //Assert
        assertEquals(1, repository.findAll().size());
    }



    @Test
    void shouldUpdateMemberNameEmailAndLocation() {
        //Arrange
        repository.add(member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 1)));

        //Act
        repository.updateMember("alice@example.com", "Alice Updated", "newalice@example.com", "Berlin", "Python");

        //Assert
        Member updated = repository.findAll().get(0);
        assertEquals("Alice Updated", updated.getName());
        assertEquals("newalice@example.com", updated.getEmail());
        assertEquals("Berlin", updated.getLocation());
    }


    @Test
    void shouldNotModifyListWhenUpdatingNonExistentEmail() {
        //Arrange
        repository.add(member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 1)));

        //Act
        repository.updateMember("nobody@example.com", "X", "x@example.com", "Paris", "Go");

        //Assert
        assertEquals("Alice", repository.findAll().get(0).getName());
    }



    @Test
    void shouldReturnMembersMatchingLocation() {
        //Arrange
        repository.add(member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 1)));
        repository.add(member("Bob", "bob@example.com", "Berlin", List.of("Go"), LocalDate.of(2024, 1, 1)));

        //Act
        List<Member> result = repository.findByLocation("London");

        //Assert
        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void shouldReturnEmptyListWhenNoMembersInLocation() {
        //Arrange
        repository.add(member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 1)));

        //Act
        List<Member> result = repository.findByLocation("Tokyo");

        //Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldMatchLocationCaseInsensitive() {
        //Arrange
        repository.add(member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 1)));

        //Act
        List<Member> result = repository.findByLocation("london");

        //Assert
        assertEquals(1, result.size());
    }



    @Test
    void shouldReturnMembersWithMatchingSkill() {
        //Arrange
        repository.add(member("Alice", "alice@example.com", "London", List.of("Java", "Python"), LocalDate.of(2023, 1, 1)));
        repository.add(member("Bob", "bob@example.com", "Berlin", List.of("Go"), LocalDate.of(2024, 1, 1)));

        //Act
        List<Member> result = repository.findBySkill("Java");

        //Assert
        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void shouldReturnEmptyListWhenNoMembersHaveSkill() {
        //Arrange
        repository.add(member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 1)));

        //Act
        List<Member> result = repository.findBySkill("Rust");

        //Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldMatchSkillCaseInsensitive() {
        //Arrange
        repository.add(member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 1)));

        //Act
        List<Member> result = repository.findBySkill("java");

        //Assert
        assertEquals(1, result.size());
    }



    @Test
    void shouldSortMembersByNameAlphabetically() {
        //Arrange
        repository.add(member("Zara", "zara@example.com", "London", List.of("Java"), LocalDate.of(2023, 3, 1)));
        repository.add(member("Alice", "alice@example.com", "Berlin", List.of("Go"), LocalDate.of(2023, 1, 1)));
        repository.add(member("Bob", "bob@example.com", "Paris", List.of("Python"), LocalDate.of(2023, 2, 1)));

        //Act
        List<Member> sorted = repository.sortByName();

        //Assert
        assertEquals("Alice", sorted.get(0).getName());
        assertEquals("Bob", sorted.get(1).getName());
        assertEquals("Zara", sorted.get(2).getName());
    }

    @Test
    void shouldReturnEmptyListFromSortByNameWhenNoMembers() {
        //Act
        List<Member> sorted = repository.sortByName();

        //Assert
        assertTrue(sorted.isEmpty());
    }



    @Test
    void shouldSortMembersByJoinDateChronologically() {
        //Arrange
        repository.add(member("Charlie", "charlie@example.com", "London", List.of("Java"), LocalDate.of(2024, 6, 1)));
        repository.add(member("Alice", "alice@example.com", "Berlin", List.of("Go"), LocalDate.of(2022, 1, 15)));
        repository.add(member("Bob", "bob@example.com", "Paris", List.of("Python"), LocalDate.of(2023, 3, 10)));

        //Act
        List<Member> sorted = repository.sortByJoinDate();

        //Assert
        assertEquals("Alice", sorted.get(0).getName());
        assertEquals("Bob", sorted.get(1).getName());
        assertEquals("Charlie", sorted.get(2).getName());
    }

    @Test
    void shouldReturnEmptyListFromSortByJoinDateWhenNoMembers() {
        //Act
        List<Member> sorted = repository.sortByJoinDate();

        //Assert
        assertTrue(sorted.isEmpty());
    }



    @Test
    void shouldSaveMemberToCsvAndLoadItBack(@TempDir Path tempDir) throws IOException {
        //Arrange
        Path csvFile = tempDir.resolve("members.csv");
        repository.add(member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 15)));
        repository.saveToCsv(csvFile.toString());

        //Act
        CsvMemberRepository loaded = new CsvMemberRepository();
        loaded.loadFromCsv(csvFile.toString());

        //Assert
        assertEquals(1, loaded.findAll().size());
    }

    @Test
    void shouldPreserveAllFieldsAfterRoundTrip(@TempDir Path tempDir) throws IOException {
        //Arrange
        Path csvFile = tempDir.resolve("members.csv");
        repository.add(member("Alice", "alice@example.com", "London", List.of("Java", "Python"), LocalDate.of(2023, 1, 15)));
        repository.saveToCsv(csvFile.toString());

        //Act
        CsvMemberRepository loaded = new CsvMemberRepository();
        loaded.loadFromCsv(csvFile.toString());
        Member m = loaded.findAll().get(0);

        //Assert
        assertEquals("Alice", m.getName());
        assertEquals("alice@example.com", m.getEmail());
        assertEquals("London", m.getLocation());
        assertEquals(List.of("Java", "Python"), m.getSkills());
        assertEquals(LocalDate.of(2023, 1, 15), m.getJoinDate());
    }

    @Test
    void shouldSaveMultipleMembersAndLoadAllBack(@TempDir Path tempDir) throws IOException {
        //Arrange
        Path csvFile = tempDir.resolve("members.csv");
        repository.add(member("Alice", "alice@example.com", "London", List.of("Java"), LocalDate.of(2023, 1, 1)));
        repository.add(member("Bob", "bob@example.com", "Berlin", List.of("Go"), LocalDate.of(2024, 2, 1)));
        repository.saveToCsv(csvFile.toString());

        //Act
        CsvMemberRepository loaded = new CsvMemberRepository();
        loaded.loadFromCsv(csvFile.toString());

        //Assert
        assertEquals(2, loaded.findAll().size());
    }

    @Test
    void shouldLoadFromNonExistentFileWithoutError() throws IOException {
        //Arrange & Act & Assert — must not throw
        repository.loadFromCsv("/tmp/does_not_exist_wcc.csv");
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void shouldSaveEmptyRepositoryWithHeaderOnly(@TempDir Path tempDir) throws IOException {
        //Arrange
        Path csvFile = tempDir.resolve("members.csv");
        repository.saveToCsv(csvFile.toString());

        //Act
        CsvMemberRepository loaded = new CsvMemberRepository();
        loaded.loadFromCsv(csvFile.toString());

        //Assert
        assertTrue(loaded.findAll().isEmpty());
    }
}
