

/**
 * $Id: StoreCheckpointTest.java 1831 2013-05-16 01:39:51Z vintagewang@apache.org $
 */
package org.apache.rocketmq.store;

import java.io.File;
import java.io.IOException;

import org.apache.rocketmq.common.UtilAll;
import org.junit.After;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StoreCheckpointTest {
    @Test
    public void testWriteAndRead() throws IOException {
        StoreCheckpoint storeCheckpoint = new StoreCheckpoint("target/checkpoint_test/0000");
        long physicMsgTimestamp = 0xAABB;
        long logicsMsgTimestamp = 0xCCDD;
        storeCheckpoint.setPhysicMsgTimestamp(physicMsgTimestamp);
        storeCheckpoint.setLogicsMsgTimestamp(logicsMsgTimestamp);
        storeCheckpoint.flush();

        long diff = physicMsgTimestamp - storeCheckpoint.getMinTimestamp();
        assertThat(diff).isEqualTo(3000);
        storeCheckpoint.shutdown();
        storeCheckpoint = new StoreCheckpoint("target/checkpoint_test/0000");
        assertThat(storeCheckpoint.getPhysicMsgTimestamp()).isEqualTo(physicMsgTimestamp);
        assertThat(storeCheckpoint.getLogicsMsgTimestamp()).isEqualTo(logicsMsgTimestamp);
    }

    @After
    public void destory() {
        File file = new File("target/checkpoint_test");
        UtilAll.deleteFile(file);
    }
}
