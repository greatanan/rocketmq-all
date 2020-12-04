

package org.apache.rocketmq.example.simple;

import java.util.TreeMap;
import org.apache.rocketmq.common.message.MessageExt;

public class CachedQueue {
    private final TreeMap<Long, MessageExt> msgCachedTable = new TreeMap<Long, MessageExt>();

    public TreeMap<Long, MessageExt> getMsgCachedTable() {
        return msgCachedTable;
    }
}
