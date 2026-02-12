package ascii_mirror.stranger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Scanner;
@Order(4)
@Component
public class ScannerOrder implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Welcome to our restaurant!");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name:: ");
        String name = scanner.nextLine();
        System.out.print("Enter your order:: ");
        String order = scanner.nextLine();

        System.out.println("Thank you, " + name + "!");
        System.out.println("Your order is:: " + order);
        System.out.println("=============================");


        /** Sample Input 1:
        //Hello
        //Java
        //Future programmer

        //Sample Output 1:
        //Hello
        //Java
        //Future
        //programmer */
        // Read three lines. The third line can contain multiple words.
        String hello = scanner.nextLine(); // Input 1
        String java = scanner.nextLine();   // Input 2
        String futureProgrammer = scanner.nextLine(); // Input 3 (can have multiple words)

        // Output the results
        System.out.println(hello);
        System.out.println(java);

        // Split the third line into individual words and print each on a new line
        String[] words = futureProgrammer.split(" ");
        for (String word : words) {
            System.out.println(word);
        }
        scanner.close();
    }
}