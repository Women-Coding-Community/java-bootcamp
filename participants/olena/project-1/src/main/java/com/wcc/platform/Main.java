package com.wcc.platform;


import com.wcc.platform.model.Member;
import com.wcc.platform.model.MemberRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final MemberRepository repository = new MemberRepository();


    public static void main(String[] args) {


        System.out.println("Enter name: ");
        String name = scanner.nextLine();

        System.out.println("Enter email: ");
        String email = scanner.nextLine();

        Member member = new Member(
                name,
                email,
                "Unknown",
                List.of(),
                LocalDate.now()
        );
        repository.add(member);
        System.out.println("Member added.");
    }
}

