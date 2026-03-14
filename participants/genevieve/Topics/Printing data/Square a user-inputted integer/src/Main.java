import java.util.Scanner; // essential import for user input

public class Main {
    public static void main(String[] args) {
        // Create a new scanner for user input
        Scanner sc = new Scanner(System.in);

        // Taking integer input from the user
        int number = sc.nextInt();

        // Calculating the square of the input
        int squared_number = number * number;

        // TODO: Print the result out

        // Important to close the scanner!
        sc.close();
    }
}