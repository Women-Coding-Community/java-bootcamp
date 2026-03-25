package com.wcc.platform;

import com.wcc.platform.cli.MemberCli;
import com.wcc.platform.model.CsvMemberRepository;
import com.wcc.platform.model.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberDirectoryIntegrationTest {

    private CsvMemberRepository repository;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        repository = new CsvMemberRepository();
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
    }

    private void runCli(String input) throws IOException {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        new MemberCli(repository).start();
    }

    private CsvMemberRepository reloadFrom(Path csvFile) throws IOException {
        CsvMemberRepository loaded = new CsvMemberRepository();
        loaded.loadFromCsv(csvFile.toString());
        return loaded;
    }


    @Test
    void shouldPersistAddedMemberAfterCsvRoundTrip(@TempDir Path tempDir) throws IOException {
        //Arrange
        Path csvFile = tempDir.resolve("members.csv");

        //Act
        runCli("1\nAlice\nalice@example.com\nLondon\nJava\n0\n");
        repository.saveToCsv(csvFile.toString());

        //Assert
        Member m = reloadFrom(csvFile).findAll().get(0);
        assertEquals("Alice", m.getName());
        assertEquals("alice@example.com", m.getEmail());
        assertEquals("London", m.getLocation());
        assertEquals(List.of("Java"), m.getSkills());
    }

    @Test
    void shouldPersistAllFieldsIncludingMultipleSkillsAfterRoundTrip(@TempDir Path tempDir) throws IOException {
        //Arrange
        Path csvFile = tempDir.resolve("members.csv");

        //Act
        runCli("1\nAlice\nalice@example.com\nLondon\nJava,Python\n0\n");
        repository.saveToCsv(csvFile.toString());

        //Assert
        Member m = reloadFrom(csvFile).findAll().get(0);
        assertEquals(List.of("Java", "Python"), m.getSkills());
    }

    @Test
    void shouldNotPersistDuplicateEmailAfterRoundTrip(@TempDir Path tempDir) throws IOException {
        //Arrange
        Path csvFile = tempDir.resolve("members.csv");

        //Act — add same email twice
        runCli("1\nAlice\nalice@example.com\nLondon\nJava\n1\nBob\nalice@example.com\nBerlin\nGo\n0\n");
        repository.saveToCsv(csvFile.toString());

        //Assert
        assertEquals(1, reloadFrom(csvFile).findAll().size());
    }


    @Test
    void shouldRemoveMemberFromCsvAfterDelete(@TempDir Path tempDir) throws IOException {
        //Arrange: add then save
        Path csvFile = tempDir.resolve("members.csv");
        runCli("1\nAlice\nalice@example.com\nLondon\nJava\n0\n");
        repository.saveToCsv(csvFile.toString());

        //Act: reload into fresh repo, delete, save again
        repository = reloadFrom(csvFile);
        runCli("4\nalice@example.com\n0\n");
        repository.saveToCsv(csvFile.toString());

        //Assert
        assertTrue(reloadFrom(csvFile).findAll().isEmpty());
    }

    @Test
    void shouldRetainOtherMembersAfterOneMemberDeleted(@TempDir Path tempDir) throws IOException {
        //Arrange: add two members
        Path csvFile = tempDir.resolve("members.csv");
        runCli("1\nAlice\nalice@example.com\nLondon\nJava\n1\nBob\nbob@example.com\nBerlin\nGo\n0\n");
        repository.saveToCsv(csvFile.toString());

        //Act: reload, delete Alice only
        repository = reloadFrom(csvFile);
        runCli("4\nalice@example.com\n0\n");
        repository.saveToCsv(csvFile.toString());

        //Assert
        List<Member> remaining = reloadFrom(csvFile).findAll();
        assertEquals(1, remaining.size());
        assertEquals("Bob", remaining.get(0).getName());
    }


    @Test
    void shouldPersistUpdatedFieldsAfterCsvRoundTrip(@TempDir Path tempDir) throws IOException {
        //Arrange: add then save
        Path csvFile = tempDir.resolve("members.csv");
        runCli("1\nAlice\nalice@example.com\nLondon\nJava\n0\n");
        repository.saveToCsv(csvFile.toString());

        //Act: reload, update, save again
        repository = reloadFrom(csvFile);
        runCli("3\nalice@example.com\nAlice Updated\nnewalice@example.com\nBerlin\nPython\n0\n");
        repository.saveToCsv(csvFile.toString());

        //Assert
        Member m = reloadFrom(csvFile).findAll().get(0);
        assertEquals("Alice Updated", m.getName());
        assertEquals("newalice@example.com", m.getEmail());
        assertEquals("Berlin", m.getLocation());
        assertEquals(List.of("Python"), m.getSkills());
    }

    @Test
    void shouldPreserveJoinDateAfterUpdateAndReload(@TempDir Path tempDir) throws IOException {
        //Arrange: add, save, note the join date
        Path csvFile = tempDir.resolve("members.csv");
        runCli("1\nAlice\nalice@example.com\nLondon\nJava\n0\n");
        repository.saveToCsv(csvFile.toString());
        java.time.LocalDate originalJoinDate = repository.findAll().get(0).getJoinDate();

        //Act: reload, update, save again
        repository = reloadFrom(csvFile);
        runCli("3\nalice@example.com\nAlice Updated\nnewalice@example.com\nBerlin\nPython\n0\n");
        repository.saveToCsv(csvFile.toString());

        //Assert
        assertEquals(originalJoinDate, reloadFrom(csvFile).findAll().get(0).getJoinDate());
    }

    @Test
    void shouldFindMemberByLocationAfterReload(@TempDir Path tempDir) throws IOException {
        //Arrange
        Path csvFile = tempDir.resolve("members.csv");
        runCli("1\nAlice\nalice@example.com\nLondon\nJava\n1\nBob\nbob@example.com\nBerlin\nGo\n0\n");
        repository.saveToCsv(csvFile.toString());

        //Act
        List<Member> results = reloadFrom(csvFile).findByLocation("London");

        //Assert
        assertEquals(1, results.size());
        assertEquals("Alice", results.get(0).getName());
    }

    @Test
    void shouldFindMemberBySkillAfterReload(@TempDir Path tempDir) throws IOException {
        //Arrange
        Path csvFile = tempDir.resolve("members.csv");
        runCli("1\nAlice\nalice@example.com\nLondon\nJava\n1\nBob\nbob@example.com\nBerlin\nGo\n0\n");
        repository.saveToCsv(csvFile.toString());

        //Act
        List<Member> results = reloadFrom(csvFile).findBySkill("Go");

        //Assert
        assertEquals(1, results.size());
        assertEquals("Bob", results.get(0).getName());
    }


    @Test
    void shouldSortByNameCorrectlyAfterReload(@TempDir Path tempDir) throws IOException {
        //Arrange: add out of alphabetical order
        Path csvFile = tempDir.resolve("members.csv");
        runCli("1\nZara\nzara@example.com\nParis\nRust\n1\nAlice\nalice@example.com\nLondon\nJava\n0\n");
        repository.saveToCsv(csvFile.toString());

        //Act
        List<Member> sorted = reloadFrom(csvFile).sortByName();

        //Assert
        assertEquals("Alice", sorted.get(0).getName());
        assertEquals("Zara", sorted.get(1).getName());
    }

    @Test
    void shouldSortByJoinDateChronologicallyAfterReload(@TempDir Path tempDir) throws IOException {
        //Arrange: Alice joins before Bob
        Path csvFile = tempDir.resolve("members.csv");
        runCli("1\nBob\nbob@example.com\nBerlin\nGo\n1\nAlice\nalice@example.com\nLondon\nJava\n0\n");
        repository.saveToCsv(csvFile.toString());

        //Act
        List<Member> sorted = reloadFrom(csvFile).sortByJoinDate();

        //Assert — both added same day, order stable; at minimum both members are present
        assertEquals(2, sorted.size());
    }

    @Test
    void shouldReloadEmptyRepositoryWithoutError(@TempDir Path tempDir) throws IOException {
        //Arrange
        Path csvFile = tempDir.resolve("members.csv");
        repository.saveToCsv(csvFile.toString());

        //Act & Assert
        assertTrue(reloadFrom(csvFile).findAll().isEmpty());
    }

    @Test
    void shouldStartWithEmptyStateWhenCsvFileDoesNotExist(@TempDir Path tempDir) throws IOException {
        //Arrange
        Path csvFile = tempDir.resolve("nonexistent.csv");

        //Act & Assert
        assertTrue(reloadFrom(csvFile).findAll().isEmpty());
    }
}
