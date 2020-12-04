

package org.apache.rocketmq.client.impl.consumer;

import java.util.List;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DefaultMQPushConsumerImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkConfigTest() throws MQClientException {

        //test type
        thrown.expect(MQClientException.class);

        //test message
        thrown.expectMessage("consumeThreadMin (10) is larger than consumeThreadMax (9)");

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test_consumer_group");

        consumer.setConsumeThreadMin(10);
        consumer.setConsumeThreadMax(9);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                ConsumeConcurrentlyContext context) {
                System.out.println(" Receive New Messages: " + msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        DefaultMQPushConsumerImpl defaultMQPushConsumerImpl = new DefaultMQPushConsumerImpl(consumer, null);
        defaultMQPushConsumerImpl.start();
    }
}
