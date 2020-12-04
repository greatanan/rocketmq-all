

package org.apache.rocketmq.logging.inner;


import org.apache.rocketmq.logging.InnerLoggerFactory;
import org.junit.Assert;
import org.junit.Test;

public class MessageFormatterTest {

    @Test
    public void formatTest(){
        InnerLoggerFactory.FormattingTuple logging = InnerLoggerFactory.MessageFormatter.format("this is {},and {}", "logging", 6546);
        String message = logging.getMessage();
        Assert.assertTrue(message.contains("logging"));

        InnerLoggerFactory.FormattingTuple format = InnerLoggerFactory.MessageFormatter.format("cause exception {}", 143545, new RuntimeException());
        String message1 = format.getMessage();
        Throwable throwable = format.getThrowable();
        System.out.println(message1);
        Assert.assertTrue(throwable != null);
    }

}
