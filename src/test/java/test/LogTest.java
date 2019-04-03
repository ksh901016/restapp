package test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogTest {

    private static final Logger LOG = LoggerFactory.getLogger(LogTest.class);
    @Test
    public void logTest(){
        String id = "corn";

        LOG.trace("id : {}", id);
        LOG.debug("id : {}", id);
        LOG.info("id : {}", id);
        LOG.warn("id : {}", id);
        LOG.error("id : {}", id);
    }
}
