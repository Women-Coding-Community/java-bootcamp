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
        System.out.println("Enter 3 names: ");
        String[] names = new String[3];

        // Read exactly 3 names
        for (int count = 0; count < 3; count++) {
            names[count] = scanner.nextLine();
        }

        // Print the names in reverse order
        for (int i = 2; i >= 0; i--) { // Start from the last name
            String[] nameParts = names[i].split(" ");
            for (int j = nameParts.length - 1; j >= 0; j--) { // Print each part in reverse
                System.out.println(nameParts[j]);
            }
        }

        scanner.close(); // Close the scanner
    }
}