

package org.apache.rocketmq.common.protocol.header;

import java.util.List;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class GetConsumerListByGroupResponseBody extends RemotingSerializable {
    private List<String> consumerIdList;

    public List<String> getConsumerIdList() {
        return consumerIdList;
    }

    public void setConsumerIdList(List<String> consumerIdList) {
        this.consumerIdList = consumerIdList;
    }
}
