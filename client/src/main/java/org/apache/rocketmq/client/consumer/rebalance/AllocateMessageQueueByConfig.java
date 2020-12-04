
package org.apache.rocketmq.client.consumer.rebalance;

import java.util.List;
import org.apache.rocketmq.client.consumer.AllocateMessageQueueStrategy;
import org.apache.rocketmq.common.message.MessageQueue;

public class AllocateMessageQueueByConfig implements AllocateMessageQueueStrategy {
    private List<MessageQueue> messageQueueList;

    @Override
    public List<MessageQueue> allocate(String consumerGroup, String currentCID, List<MessageQueue> mqAll,
        List<String> cidAll) {
        return this.messageQueueList;
    }

    @Override
    public String getName() {
        return "CONFIG";
    }

    public List<MessageQueue> getMessageQueueList() {
        return messageQueueList;
    }

    public void setMessageQueueList(List<MessageQueue> messageQueueList) {
        this.messageQueueList = messageQueueList;
    }
}
