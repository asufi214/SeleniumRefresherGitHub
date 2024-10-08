package testng;

import org.apache.logging.log4j.LogManager;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import utlities.ReadConfigFiles;


public class TestNgMavenExampleTest {

    private static final Logger LOGGER = LogManager.getLogger(TestNgMavenExampleTest.class);


    public void run(){
        //System.out.println("This is TestNG-Maven Example");
        LOGGER.debug("This is debug message");
        LOGGER.info("This is info message");
        LOGGER.warn("This is a warning");
        LOGGER.error("This is an error");
        LOGGER.fatal("This is dangerous");
    }

    @Test
    public void testPropertyFile() {
        String value = ReadConfigFiles.getPropertyValues("DbUser");
        LOGGER.info(value);
    }
}
