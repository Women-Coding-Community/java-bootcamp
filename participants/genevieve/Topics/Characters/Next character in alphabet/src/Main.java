import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char inputChar = scanner.next().charAt(0);
        char outputChar = inputChar == 'z' ? 'a' : (char)(inputChar + 1);
        System.out.println(outputChar);
    }
}