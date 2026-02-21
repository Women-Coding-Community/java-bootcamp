package ascii_mirror.printingdata;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LifeStageBaseonAge  {

    private static final Logger logger = LoggerFactory.getLogger(LifeStageBaseonAge.class);

//Your task is to write a complete Java program that takes in a single line with an integer between 0-100 (inclusive) as input. This integer represents a person's age. The program should then print a message that tells the person which life stage they are in based on the following conditions:
//If the person's age is less than 12 (inclusive), print 'Child'.
//If the age is between 13 and 17 (both inclusive), print 'Teenager'.
//If the age is between 18 and 59 (both inclusive), print 'Adult'.
//Lastly, if the person's age is 60 (inclusive) or above, print 'Senior Citizen'.
    //Contrains:
    //inputs 12, output: Child
    //inputs 13, output: Teenager

    public static void main(String[] args) {
        // and you can create tests for that
        logger.info(determineLifeStage(30));
        logger.info(determineLifeStage(10));
        logger.info(determineLifeStage(70));
        logger.info(determineLifeStage(0));
        logger.info(determineLifeStage(99));

        // if it is -1 will be valid and consider as a child ;)
        logger.info(determineLifeStage(-1));

        // if it is 200 will be valid and consider as a Adult ;)
        logger.info(determineLifeStage(200));
    }

    public static String determineLifeStage(int age) {
        if (age <= 12) {
            return "Child";
        } else if (age >= 13 && age <= 17) {
            return "Teenager";
        } else if (age >= 18 && age <= 59) {
            return "Adult";
        } else if (age >= 60) {
            return "Senior Citizen";
        } else {
            logger.warn("Invalid age input: {}", age);
            return "Invalid age";
        }
    }
}
