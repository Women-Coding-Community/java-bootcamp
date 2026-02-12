package ascii_mirror.stranger;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Order(1)
@Component
public class Stranger implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Stranger.class);

    @Override
    public void run(String... args) {


            logger.info("                    _______ ");
            logger.info("                   < hello >");
            logger.info("                    ------- ");
            logger.info("            ^__^   /        ");
            logger.info("    _______/(oo)  /         ");
            logger.info("/\\/(      /(__)            ");
            logger.info("   | w----||                ");
            logger.info("   ||     ||                ");
        System.out.println();
    }
}
