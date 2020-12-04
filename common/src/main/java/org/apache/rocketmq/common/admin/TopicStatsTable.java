
package org.apache.rocketmq.common.admin;

import java.util.HashMap;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class TopicStatsTable extends RemotingSerializable {
    private HashMap<MessageQueue, TopicOffset> offsetTable = new HashMap<MessageQueue, TopicOffset>();

    public HashMap<MessageQueue, TopicOffset> getOffsetTable() {
        return offsetTable;
    }

    public void setOffsetTable(HashMap<MessageQueue, TopicOffset> offsetTable) {
        this.offsetTable = offsetTable;
    }
}
