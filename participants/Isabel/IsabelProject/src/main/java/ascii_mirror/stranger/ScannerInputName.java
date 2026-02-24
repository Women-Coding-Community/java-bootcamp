package ascii_mirror.stranger;


import java.util.Scanner;

public class ScannerInputName  {
    /**
     *  Sample Input 1:
     *  Jane Kate - name1 with space
     *  John  - name2
     *  Mary - name3
     *
     *  Sample Output 1:
     *  Mary  - name3
     *  John - name2
     *  Kate - name1
     *  Jane - name1
     *
     *
     *  Sample Input 2:
     *  Joseph - name1
     *  Piotr Eugene - name2 with space
     *  Jack - name3
     *
     *  Sample Output 2:
     *  Jack - name3
     *  Eugene - name2
     *  Piotr - name2
     *  Joseph - name1
     */

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 3 names::");
        // Read the first name part (single token)
        String name1Part1 = scanner.next();

        // Read the rest of the name1 (including spaces)
        String name1Part2 = scanner.nextLine();

        // Combine them to form the full name1
        String name1 = name1Part1 + name1Part2;

        // Read the second name (which can include spaces)
        String name2 = scanner.nextLine();

        // Read the third name
        String name3 = scanner.nextLine();

        // Print the third name
        System.out.println(name3);

        // Split name2 into parts and print them in reverse
        String[] nameParts2 = name2.split(" ");
        for (int i = nameParts2.length - 1; i >= 0; i--) {
            System.out.println(nameParts2[i]);
        }

        // Split name1 into parts and print them in reverse
        String[] nameParts1 = name1.split(" ");
        for (int i = nameParts1.length - 1; i >= 0; i--) {
            System.out.println(nameParts1[i]);
        }

        scanner.close(); // Close the scanner
    }
}