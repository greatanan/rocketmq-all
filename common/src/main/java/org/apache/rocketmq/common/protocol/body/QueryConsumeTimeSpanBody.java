

package org.apache.rocketmq.common.protocol.body;

import java.util.ArrayList;
import java.util.List;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class QueryConsumeTimeSpanBody extends RemotingSerializable {
    List<QueueTimeSpan> consumeTimeSpanSet = new ArrayList<QueueTimeSpan>();

    public List<QueueTimeSpan> getConsumeTimeSpanSet() {
        return consumeTimeSpanSet;
    }

    public void setConsumeTimeSpanSet(List<QueueTimeSpan> consumeTimeSpanSet) {
        this.consumeTimeSpanSet = consumeTimeSpanSet;
    }
}
