
package org.apache.rocketmq.client.producer.selector;

import java.util.ArrayList;
import java.util.List;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SelectMessageQueueByHashTest {

    private String topic = "FooBar";

    @Test
    public void testSelect() throws Exception {
        SelectMessageQueueByHash selector = new SelectMessageQueueByHash();

        Message message = new Message(topic, new byte[] {});

        List<MessageQueue> messageQueues = new ArrayList<MessageQueue>();
        for (int i = 0; i < 10; i++) {
            MessageQueue messageQueue = new MessageQueue(topic, "DefaultBroker", i);
            messageQueues.add(messageQueue);
        }

        String orderId = "123";
        String anotherOrderId = "234";
        MessageQueue selected = selector.select(messageQueues, message, orderId);
        assertThat(selector.select(messageQueues, message, anotherOrderId)).isNotEqualTo(selected);
    }

}