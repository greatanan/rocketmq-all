
package org.apache.rocketmq.common.protocol.body;

import java.util.HashMap;
import java.util.Map;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class QueryCorrectionOffsetBody extends RemotingSerializable {
    private Map<Integer, Long> correctionOffsets = new HashMap<Integer, Long>();

    public Map<Integer, Long> getCorrectionOffsets() {
        return correctionOffsets;
    }

    public void setCorrectionOffsets(Map<Integer, Long> correctionOffsets) {
        this.correctionOffsets = correctionOffsets;
    }
}
