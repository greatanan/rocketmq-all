

package org.apache.rocketmq.test.tls;

import org.apache.rocketmq.test.base.BaseConf;
import org.apache.rocketmq.test.client.rmq.RMQNormalConsumer;
import org.apache.rocketmq.test.client.rmq.RMQNormalProducer;
import org.apache.rocketmq.test.listener.rmq.concurrent.RMQNormalListener;
import org.apache.rocketmq.test.util.MQWait;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TLS_Mix_IT extends BaseConf {

    private RMQNormalProducer producer;
    private RMQNormalConsumer consumer;

    private String topic;

    @Before
    public void setUp() {
        topic = initTopic();

        // send message without TLS
        producer = getProducer(nsAddr, topic);

        // Receive message via TLS
        consumer = getConsumer(nsAddr, topic, "*", new RMQNormalListener(), true);
    }

    @After
    public void tearDown() {
        shutdown();
    }

    @Test
    public void testSendAndReceiveMessageOverTLS() {
        int numberOfMessagesToSend = 16;
        producer.send(numberOfMessagesToSend);

        boolean consumedAll = MQWait.waitConsumeAll(consumeTime, producer.getAllMsgBody(), consumer.getListener());
        Assertions.assertThat(consumedAll).isEqualTo(true);
    }

}
