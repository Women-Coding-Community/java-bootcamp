package com.wcc.platform;


import com.wcc.platform.model.Member;
import com.wcc.platform.model.MemberRepository;
import com.wcc.platform.model.CsvMemberRepository;
import com.wcc.platform.model.EmailValidator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class Main  {
    private static final Scanner scanner = new Scanner(System.in);
    private static final MemberRepository repository = new CsvMemberRepository();


    public static void main(String[] args) throws IOException {
        repository.loadFromCsv("members.csv");


        System.out.println("Enter name: ");
        String name = scanner.nextLine();

        String email;
        while(true) {
            System.out.println("Enter email: ");
            email = scanner.nextLine();

            if(EmailValidator.isValid(email)) {
                break;
            }
            System.out.println("Invalid email format. Please enter a valid email.");
        }

        System.out.println("Enter location: ");
        String location = scanner.nextLine();

        Member member = new Member(
                name,
                email,
                location,
                List.of(),
                LocalDate.now()
        );
        repository.add(member);
        repository.saveToCsv("members.csv");
        System.out.println("Member added.");
        viewMembers();
    }
    public static void viewMembers() {

        System.out.printf("%-15s %-25s %-15s%n", "Name", "Email", "Location");
        System.out.println("----------------------------------------------------------");
        repository.findAll().forEach(member -> System.out.printf("%-15s %-25s %-15s%n",
                member.getName(),
                member.getEmail(),
                member.getLocation()
        ));
    }
}

