
package org.apache.rocketmq.client.consumer;

import java.util.Set;
import org.apache.rocketmq.common.message.MessageQueue;

/**
 * A MessageQueueListener is implemented by the application and may be specified when a message queue changed
 */
public interface MessageQueueListener {
    /**
     * @param topic message topic
     * @param mqAll all queues in this message topic
     * @param mqDivided collection of queues,assigned to the current consumer
     */
    void messageQueueChanged(final String topic, final Set<MessageQueue> mqAll,
        final Set<MessageQueue> mqDivided);
}
