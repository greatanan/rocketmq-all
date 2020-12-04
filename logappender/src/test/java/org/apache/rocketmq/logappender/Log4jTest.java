
package org.apache.rocketmq.logappender;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class Log4jTest extends AbstractTestCase {

    @Before
    public abstract void init();

    public abstract String getType();

    @Test
    public void testLog4j() {
        clear();
        Logger logger = Logger.getLogger("testLogger");
        for (int i = 0; i < 10; i++) {
            logger.info("log4j " + this.getType() + " simple test message " + i);
        }
        int received = consumeMessages(10, "log4j", 10);
        Assert.assertTrue(received > 5);
    }

}
