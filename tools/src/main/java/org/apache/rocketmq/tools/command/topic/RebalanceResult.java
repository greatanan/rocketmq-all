

package org.apache.rocketmq.tools.command.topic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.rocketmq.common.message.MessageQueue;

public class RebalanceResult {
    private Map<String/*ip*/, List<MessageQueue>> result = new HashMap<String, List<MessageQueue>>();

    public Map<String, List<MessageQueue>> getResult() {
        return result;
    }

    public void setResult(final Map<String, List<MessageQueue>> result) {
        this.result = result;
    }
}
