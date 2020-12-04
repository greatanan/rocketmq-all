
package org.apache.rocketmq.logappender;

import org.apache.rocketmq.client.producer.DefaultMQProducer;

import org.apache.rocketmq.common.message.*;
import org.apache.rocketmq.logappender.common.ProducerInstance;
import org.junit.Before;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Basic test rocketmq broker and name server init
 */
public class AbstractTestCase {

    private static CopyOnWriteArrayList<Message> messages = new CopyOnWriteArrayList<>();

    @Before
    public void mockLoggerAppender() throws Exception {
        DefaultMQProducer defaultMQProducer = spy(new DefaultMQProducer("loggerAppender"));
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Message message = (Message) invocationOnMock.getArgument(0);
                messages.add(message);
                return null;
            }
        }).when(defaultMQProducer).sendOneway(any(Message.class));
        ProducerInstance spy = mock(ProducerInstance.class);
        Field instance = ProducerInstance.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(ProducerInstance.class, spy);
        doReturn(defaultMQProducer).when(spy).getInstance(anyString(), anyString());
    }

    public void clear() {

    }

    protected int consumeMessages(int count, final String key, int timeout) {
        final AtomicInteger cc = new AtomicInteger(0);
        for (Message message : messages) {
            String body = new String(message.getBody());
            if (body.contains(key)) {
                cc.incrementAndGet();
            }
        }
        return cc.get();
    }
}
