package ascii_mirror.printingdata;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LifeStageBaseonAge implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LifeStageBaseonAge.class);

//Your task is to write a complete Java program that takes in a single line with an integer between 0-100 (inclusive) as input. This integer represents a person's age. The program should then print a message that tells the person which life stage they are in based on the following conditions:
//If the person's age is less than 12 (inclusive), print 'Child'.
//If the age is between 13 and 17 (both inclusive), print 'Teenager'.
//If the age is between 18 and 59 (both inclusive), print 'Adult'.
//Lastly, if the person's age is 60 (inclusive) or above, print 'Senior Citizen'.
    //Contrains:
    //inputs 12, output: Child
    //inputs 13, output: Teenager
    @Override
    public void run(String... args) {
        int age = 13; // You can change this value to test with different ages; 13
        if (age <= 12) {
            logger.info("Child");
        } else if (age >= 13 && age <= 17) {
            logger.info("Teenager");
        } else if (age >= 18 && age <= 59) {
            logger.info("Adult");
        } else if (age >= 60) {
            logger.info("Senior Citizen");
        } else {
            logger.warn("Invalid age input: {}", age);
            //System.out.println("invalid age");
        }
    }
}
