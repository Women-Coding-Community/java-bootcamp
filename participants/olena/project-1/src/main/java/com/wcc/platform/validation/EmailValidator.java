package com.wcc.platform.validation;

import java.util.Scanner;
import java.util.regex.Pattern;

public class EmailValidator {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static boolean isValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static String promptValidEmail(Scanner scanner) {

        while (true) {
            System.out.println("Enter email: ");
            String email = scanner.nextLine();

            if (isValid(email)) {
                return email;
            }

            System.out.println("Invalid email format. Please enter a valid email.");
        }
    }
}