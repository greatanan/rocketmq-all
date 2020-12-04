
package org.apache.rocketmq.logappender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class log4j2Test extends AbstractTestCase {

    @Before
    public void init() {
        Configurator.initialize("log4j2", "src/test/resources/log4j2-example.xml");
    }

    @Test
    public void testLog4j2() throws InterruptedException, MQClientException {
        clear();
        Logger logger = LogManager.getLogger("test");
        for (int i = 0; i < 10; i++) {
            logger.info("log4j2 log message " + i);
        }
        int received = consumeMessages(10, "log4j2", 10);
        Assert.assertTrue(received > 5);
    }
}
