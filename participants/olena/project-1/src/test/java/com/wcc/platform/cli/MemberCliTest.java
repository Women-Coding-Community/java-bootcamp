package com.wcc.platform.cli;

import com.wcc.platform.model.Member;
import com.wcc.platform.model.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MemberCliTest {

    private MemberRepository repository;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        repository = mock(MemberRepository.class);
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
    }

    private MemberCli cliWithInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        return new MemberCli(repository);
    }

    // --- choice "0" ---

    @Test
    void shouldExitImmediatelyWhenChoiceIsZero() throws IOException {
        //Arrange
        MemberCli cli = cliWithInput("0\n");

        //Act
        cli.start();

        //Assert
        verifyNoInteractions(repository);
    }

    // --- choice "2" view members ---

    @Test
    void shouldCallFindAllWhenViewMembersChosen() throws IOException {
        //Arrange
        when(repository.findAll()).thenReturn(List.of());
        MemberCli cli = cliWithInput("2\n0\n");

        //Act
        cli.start();

        //Assert
        verify(repository).findAll();
    }

    // --- choice "1" add member ---

    @Test
    void shouldCallRepositoryAddWhenMemberIsAdded() throws IOException {
        //Arrange
        MemberCli cli = cliWithInput("1\nAlice\nalice@example.com\nLondon\nJava\n0\n");

        //Act
        cli.start();

        //Assert
        verify(repository).add(argThat(m -> m.getName().equals("Alice")
                && m.getEmail().equals("alice@example.com")
                && m.getLocation().equals("London")));
    }

    @Test
    void shouldCallSaveToCsvAfterAddingMember() throws IOException {
        //Arrange
        MemberCli cli = cliWithInput("1\nAlice\nalice@example.com\nLondon\nJava\n0\n");

        //Act
        cli.start();

        //Assert
        verify(repository).saveToCsv("members.csv");
    }

    @Test
    void shouldRetryEmailPromptUntilValidEmailEnteredOnAdd() throws IOException {
        //Arrange — first email is invalid, second is valid
        MemberCli cli = cliWithInput("1\nAlice\nbademail\nalice@example.com\nLondon\nJava\n0\n");

        //Act
        cli.start();

        //Assert
        verify(repository).add(argThat(m -> m.getEmail().equals("alice@example.com")));
    }

    // --- choice "3" update member ---

    @Test
    void shouldCallRepositoryUpdateMemberWithCorrectArguments() throws IOException {
        //Arrange
        MemberCli cli = cliWithInput("3\nalice@example.com\nAlice Updated\nnewalice@example.com\nBerlin\nPython\n0\n");

        //Act
        cli.start();

        //Assert
        verify(repository).updateMember("alice@example.com", "Alice Updated", "newalice@example.com", "Berlin", "Python");
    }

    @Test
    void shouldCallSaveToCsvAfterUpdatingMember() throws IOException {
        //Arrange
        MemberCli cli = cliWithInput("3\nalice@example.com\nAlice Updated\nnewalice@example.com\nBerlin\nPython\n0\n");

        //Act
        cli.start();

        //Assert
        verify(repository).saveToCsv("members.csv");
    }

    // --- choice "4" delete member ---

    @Test
    void shouldCallDeleteByEmailWhenMemberIsDeleted() throws IOException {
        //Arrange
        MemberCli cli = cliWithInput("4\nalice@example.com\n0\n");

        //Act
        cli.start();

        //Assert
        verify(repository).deleteByEmail("alice@example.com");
    }

    @Test
    void shouldCallSaveToCsvAfterDeletingMember() throws IOException {
        //Arrange
        MemberCli cli = cliWithInput("4\nalice@example.com\n0\n");

        //Act
        cli.start();

        //Assert
        verify(repository).saveToCsv("members.csv");
    }

    // --- choice "5" search by location ---

    @Test
    void shouldCallFindByLocationWithEnteredLocation() throws IOException {
        //Arrange
        when(repository.findByLocation("London")).thenReturn(List.of());
        MemberCli cli = cliWithInput("5\nLondon\n0\n");

        //Act
        cli.start();

        //Assert
        verify(repository).findByLocation("London");
    }

    // --- choice "6" search by skill ---

    @Test
    void shouldCallFindBySkillWithEnteredSkill() throws IOException {
        //Arrange
        when(repository.findBySkill("Java")).thenReturn(List.of());
        MemberCli cli = cliWithInput("6\nJava\n0\n");

        //Act
        cli.start();

        //Assert
        verify(repository).findBySkill("Java");
    }

    // --- choice "7" sort by name ---

    @Test
    void shouldCallSortByNameWhenChoice7IsSelected() throws IOException {
        //Arrange
        when(repository.sortByName()).thenReturn(List.of());
        MemberCli cli = cliWithInput("7\n0\n");

        //Act
        cli.start();

        //Assert
        verify(repository).sortByName();
    }

    // --- choice "8" sort by join date ---

    @Test
    void shouldCallSortByJoinDateWhenChoice8IsSelected() throws IOException {
        //Arrange
        when(repository.sortByJoinDate()).thenReturn(List.of());
        MemberCli cli = cliWithInput("8\n0\n");

        //Act
        cli.start();

        //Assert
        verify(repository).sortByJoinDate();
    }

    // --- invalid choice ---

    @Test
    void shouldNotCallRepositoryWhenChoiceIsInvalid() throws IOException {
        //Arrange
        MemberCli cli = cliWithInput("X\n0\n");

        //Act
        cli.start();

        //Assert
        verifyNoInteractions(repository);
    }
}
