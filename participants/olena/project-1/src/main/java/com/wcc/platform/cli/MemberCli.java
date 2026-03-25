package com.wcc.platform.cli;

import com.wcc.platform.model.Member;
import com.wcc.platform.model.MemberRepository;
import com.wcc.platform.service.MemberDataOperations;
import com.wcc.platform.service.MemberSearchService;
import com.wcc.platform.service.MemberSortService;
import com.wcc.platform.validation.EmailValidator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MemberCli {
    private final Scanner scanner;
    private final MemberSearchService searchService;
    private final MemberSortService sortService;
    private final MemberDataOperations dataOperations;
    private final MemberPrinter printer;

    public MemberCli(MemberRepository repository) {
        this.scanner = new Scanner(System.in);
        this.searchService = new MemberSearchService(repository);
        this.sortService = new MemberSortService(repository);
        this.dataOperations = new MemberDataOperations(repository);
        this.printer = new MemberPrinter();
    }

    public void start() throws IOException {
        boolean running = true;
        while(running) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addMember();
                    break;
                case "2":
                    viewMembers();
                    break;
                case "3":
                    updateMember();
                    break;
                case "4":
                    deleteMember();
                    break;


                case "5":
                    searchByLocation();
                    break;

                case "6":
                    searchBySkill();
                    break;

                case "7":
                    sortByName();
                    break;

                case "8":
                    sortByJoinDate();
                    break;


                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("===== Member Directory =====");
        System.out.println("1. Add Member");
        System.out.println("2. View Members");
        System.out.println("3. Update Member");
        System.out.println("4. Delete Member");
        System.out.println("5. Search by Location");
        System.out.println("6. Search by Skill");
        System.out.println("7. Sort by Name");
        System.out.println("8. Sort by Join Date");
        System.out.println("0. Exit");
        System.out.println("Choose option: ");
    }

    private void addMember() throws IOException {

        System.out.println("Enter name:");
        String name = scanner.nextLine();

        String email = EmailValidator.promptValidEmail(scanner);

        System.out.println("Enter location:");
        String location = scanner.nextLine();

        System.out.println("Enter skills (comma separated):");
        String skillsInput = scanner.nextLine();

        List<String> skills = List.of(skillsInput.split(","));

        Member member = new Member(
                name,
                email,
                location,
                skills,
                LocalDate.now()
        );

        dataOperations.addMember(member);

        System.out.println("Member added.");
    }

    private void viewMembers() {

        printer.printHeader();
        printer.printMembers(dataOperations.findAll());
    }

    private void updateMember() throws IOException {

        System.out.println("Enter email of member to update:");
        String email = scanner.nextLine();

        System.out.println("Enter new name:");
        String newName = scanner.nextLine();

        System.out.println("Enter new email:");
        String newEmail = EmailValidator.promptValidEmail(scanner);

        System.out.println("Enter new location:");
        String newLocation = scanner.nextLine();

        System.out.println("Enter new skills:");
        String newSkills = scanner.nextLine();

        dataOperations.updateMember(email, newName, newEmail, newLocation, newSkills);

        System.out.println("Member updated.");
    }


    private void deleteMember() throws IOException {

        System.out.println("Enter email to delete:");

        String email = scanner.nextLine();

        dataOperations.deleteMember(email);

        System.out.println("Member deleted.");
    }

    private void searchByLocation() {

        System.out.println("Enter location:");
        String location = scanner.nextLine();

        List<Member> results = searchService.findByLocation(location);

        printer.printHeader();
        printer.printMembers(results);
    }

    private void searchBySkill() {

        System.out.println("Enter skill:");
        String skill = scanner.nextLine();

        List<Member> results = searchService.findBySkill(skill);

        printer.printHeader();
        printer.printMembers(results);
    }

    private void sortByName() {

        List<Member> results = sortService.sortByName();

        printer.printHeader();
        printer.printMembers(results);
    }

    private void sortByJoinDate() {

        List<Member> results = sortService.sortByJoinDate();

        printer.printHeader();
        printer.printMembers(results);
    }


}
