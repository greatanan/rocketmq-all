
package org.apache.rocketmq.client.impl.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.body.CMResult;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ConsumeMessageOrderlyServiceTest {
    private String consumerGroup;
    private String topic = "FooBar";
    private String brokerName = "BrokerA";
    private DefaultMQPushConsumer pushConsumer;

    @Before
    public void init() throws Exception {
        consumerGroup = "FooBarGroup" + System.currentTimeMillis();
        pushConsumer = new DefaultMQPushConsumer(consumerGroup);
    }

    @Test
    public void testConsumeMessageDirectly_WithNoException() {
        Map<ConsumeOrderlyStatus, CMResult> map = new HashMap();
        map.put(ConsumeOrderlyStatus.SUCCESS, CMResult.CR_SUCCESS);
        map.put(ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT, CMResult.CR_LATER);
        map.put(ConsumeOrderlyStatus.COMMIT, CMResult.CR_COMMIT);
        map.put(ConsumeOrderlyStatus.ROLLBACK, CMResult.CR_ROLLBACK);
        map.put(null, CMResult.CR_RETURN_NULL);

        for (ConsumeOrderlyStatus consumeOrderlyStatus : map.keySet()) {
            final ConsumeOrderlyStatus status = consumeOrderlyStatus;
            MessageListenerOrderly listenerOrderly = new MessageListenerOrderly() {
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                    return status;
                }
            };

            ConsumeMessageOrderlyService consumeMessageOrderlyService = new ConsumeMessageOrderlyService(pushConsumer.getDefaultMQPushConsumerImpl(), listenerOrderly);
            MessageExt msg = new MessageExt();
            msg.setTopic(topic);
            assertTrue(consumeMessageOrderlyService.consumeMessageDirectly(msg, brokerName).getConsumeResult().equals(map.get(consumeOrderlyStatus)));
        }

    }

    @Test
    public void testConsumeMessageDirectly_WithException() {
        MessageListenerOrderly listenerOrderly = new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                throw new RuntimeException();
            }
        };

        ConsumeMessageOrderlyService consumeMessageOrderlyService = new ConsumeMessageOrderlyService(pushConsumer.getDefaultMQPushConsumerImpl(), listenerOrderly);
        MessageExt msg = new MessageExt();
        msg.setTopic(topic);
        assertTrue(consumeMessageOrderlyService.consumeMessageDirectly(msg, brokerName).getConsumeResult().equals(CMResult.CR_THROW_EXCEPTION));
    }

}
