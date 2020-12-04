
package org.apache.rocketmq.common.protocol.body;

import java.util.HashSet;
import java.util.Set;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class TopicList extends RemotingSerializable {
    private Set<String> topicList = new HashSet<String>();
    private String brokerAddr;

    public Set<String> getTopicList() {
        return topicList;
    }

    public void setTopicList(Set<String> topicList) {
        this.topicList = topicList;
    }

    public String getBrokerAddr() {
        return brokerAddr;
    }

    public void setBrokerAddr(String brokerAddr) {
        this.brokerAddr = brokerAddr;
    }
}
