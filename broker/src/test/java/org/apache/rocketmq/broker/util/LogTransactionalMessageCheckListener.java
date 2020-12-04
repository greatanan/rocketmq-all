
package org.apache.rocketmq.broker.util;

import org.apache.rocketmq.broker.transaction.AbstractTransactionalMessageCheckListener;
import org.apache.rocketmq.common.message.MessageExt;

public class LogTransactionalMessageCheckListener extends AbstractTransactionalMessageCheckListener {

    @Override
    public void resolveDiscardMsg(MessageExt msgExt) {

    }
}
