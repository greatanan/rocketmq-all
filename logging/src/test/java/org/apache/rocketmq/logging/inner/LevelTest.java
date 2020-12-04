

package org.apache.rocketmq.logging.inner;

import org.junit.Assert;
import org.junit.Test;

public class LevelTest {

    @Test
    public void levelTest() {
        Level info = Level.toLevel("info");
        Level error = Level.toLevel(3);
        Assert.assertTrue(error != null && info != null);
    }

    @Test
    public void loggerLevel(){
        Level level = Logger.getRootLogger().getLevel();
        Assert.assertTrue(level!=null);
    }
}
