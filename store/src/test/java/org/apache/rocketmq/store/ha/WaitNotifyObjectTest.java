

package org.apache.rocketmq.store.ha;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class WaitNotifyObjectTest {
    @Test
    public void removeFromWaitingThreadTable() throws Exception {
        final WaitNotifyObject waitNotifyObject = new WaitNotifyObject();
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    waitNotifyObject.allWaitForRunning(100);
                    waitNotifyObject.removeFromWaitingThreadTable();
                }
            });
            t.start();
            t.join();
        }
        Assert.assertEquals(0, waitNotifyObject.waitingThreadTable.size());
    }

}
