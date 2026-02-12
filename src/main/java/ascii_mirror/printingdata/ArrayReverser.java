package ascii_mirror.printingdata;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ArrayReverser  {
    private static final Logger logger = LoggerFactory.getLogger(ArrayReverser.class);

    public static void main(String[] args) {


        int num[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        StringBuilder reversedOutput = new StringBuilder();

        for (int i = num.length - 1; i >= 0; i--) {
            reversedOutput.append(num[i]);
            if (i > 0) {
                reversedOutput.append(",");
            }
        }

        logger.info("Reversed Array: {}", reversedOutput.toString());
    }
}
