

package org.apache.rocketmq.common.protocol.body;

import java.util.HashSet;
import java.util.Set;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class LockBatchResponseBody extends RemotingSerializable {

    private Set<MessageQueue> lockOKMQSet = new HashSet<MessageQueue>();

    public Set<MessageQueue> getLockOKMQSet() {
        return lockOKMQSet;
    }

    public void setLockOKMQSet(Set<MessageQueue> lockOKMQSet) {
        this.lockOKMQSet = lockOKMQSet;
    }

}
