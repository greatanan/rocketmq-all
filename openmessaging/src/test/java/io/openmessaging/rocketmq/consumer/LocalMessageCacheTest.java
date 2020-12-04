
package io.openmessaging.rocketmq.consumer;

import io.openmessaging.rocketmq.config.ClientConfig;
import io.openmessaging.rocketmq.domain.ConsumeRequest;
import io.openmessaging.rocketmq.domain.NonStandardKeys;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocalMessageCacheTest {
    private LocalMessageCache localMessageCache;
    @Mock
    private DefaultMQPullConsumer rocketmqPullConsume;
    @Mock
    private ConsumeRequest consumeRequest;

    @Before
    public void init() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRmqPullMessageBatchNums(512);
        clientConfig.setRmqPullMessageCacheCapacity(1024);
        localMessageCache = new LocalMessageCache(rocketmqPullConsume, clientConfig);
    }

    @Test
    public void testNextPullBatchNums() throws Exception {
        assertThat(localMessageCache.nextPullBatchNums()).isEqualTo(512);
        for (int i = 0; i < 513; i++) {
            localMessageCache.submitConsumeRequest(consumeRequest);
        }
        assertThat(localMessageCache.nextPullBatchNums()).isEqualTo(511);
    }

    @Test
    public void testNextPullOffset() throws Exception {
        MessageQueue messageQueue = new MessageQueue();
        when(rocketmqPullConsume.fetchConsumeOffset(any(MessageQueue.class), anyBoolean()))
            .thenReturn(123L);
        assertThat(localMessageCache.nextPullOffset(new MessageQueue())).isEqualTo(123L);
    }

    @Test
    public void testUpdatePullOffset() throws Exception {
        MessageQueue messageQueue = new MessageQueue();
        localMessageCache.updatePullOffset(messageQueue, 124L);
        assertThat(localMessageCache.nextPullOffset(messageQueue)).isEqualTo(124L);
    }

    @Test
    public void testSubmitConsumeRequest() throws Exception {
        byte[] body = new byte[] {'1', '2', '3'};
        MessageExt consumedMsg = new MessageExt();
        consumedMsg.setMsgId("NewMsgId");
        consumedMsg.setBody(body);
        consumedMsg.putUserProperty(NonStandardKeys.MESSAGE_DESTINATION, "TOPIC");
        consumedMsg.setTopic("HELLO_QUEUE");

        when(consumeRequest.getMessageExt()).thenReturn(consumedMsg);
        localMessageCache.submitConsumeRequest(consumeRequest);
        assertThat(localMessageCache.poll()).isEqualTo(consumedMsg);
    }
}