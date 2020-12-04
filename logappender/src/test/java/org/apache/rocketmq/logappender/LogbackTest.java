
package org.apache.rocketmq.logappender;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import java.io.File;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest extends AbstractTestCase {

    @Before
    public void init() throws JoranException {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();
        configurator.doConfigure(new File("src/test/resources/logback-example.xml"));
        StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
    }

    @Test
    public void testLogback() {
        clear();
        Logger logger = LoggerFactory.getLogger("testLogger");
        for (int i = 0; i < 10; i++) {
            logger.info("logback test message " + i);
        }
        int received = consumeMessages(10, "logback", 10);
        Assert.assertTrue(received >= 5);
    }
}
