package ascii_mirror.stranger;

import java.util.Scanner;

public class Oranges_and_Apples {

    public static void main(String[] args) {
        //Sample Input 1:
        //5
        //3
        //Sample Output 1:
        //8

        //Sample Input 2:
        //10
        //7
        //Sample Output 2:
        //17
        // Use a Scanner to read user input
        Scanner scanner = new Scanner(System.in);
        // Read the number of apples from the user
        int apples = scanner.nextInt();
        int oranges = scanner.nextInt();

        // Calculate the total number of fruits and print the result
        int totalFruits = apples + oranges;
        System.out.println(totalFruits);

    }
}