package f1.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            new F1DataUI().run(args);
        } catch (Exception e) {
            logger.error("Caught Exception ", e);
        }
    }
}
