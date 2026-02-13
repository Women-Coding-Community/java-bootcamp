package ascii_mirror.stranger;

import java.util.Scanner;


public class ScannerInput  {

    /**
     * Test input:
     * Java
     * Programming
     * Language
     * <p>
     * Correct output:
     * Language
     * Programming
     * Java
     */

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Java:");
        String java = scanner.nextLine();

        System.out.println("Enter Programming:");
        String programming = scanner.nextLine();

        System.out.println("Enter Language:");
        String language = scanner.nextLine();
        System.out.println("Correct output::");
        System.out.println(language + "\n" + programming + "\n" + java + "\n");
        // scanner.close();

        System.out.println("=============================");
        System.out.println("Enter your name:: ");
        String name = scanner.nextLine();
        System.out.println("Enter your age:: ");
        String age = scanner.nextLine();
        //Hello, I am John! I am 22 years old.
        System.out.println("Hello, I am " + name + "! I am " + age + " years old.");
    }

    }
