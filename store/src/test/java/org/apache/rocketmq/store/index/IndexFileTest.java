

/**
 * $Id: IndexFileTest.java 1831 2013-05-16 01:39:51Z vintagewang@apache.org $
 */
package org.apache.rocketmq.store.index;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.rocketmq.common.UtilAll;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IndexFileTest {
    private final int HASH_SLOT_NUM = 100;
    private final int INDEX_NUM = 400;

    @Test
    public void testPutKey() throws Exception {
        IndexFile indexFile = new IndexFile("100", HASH_SLOT_NUM, INDEX_NUM, 0, 0);
        for (long i = 0; i < (INDEX_NUM - 1); i++) {
            boolean putResult = indexFile.putKey(Long.toString(i), i, System.currentTimeMillis());
            assertThat(putResult).isTrue();
        }

        // put over index file capacity.
        boolean putResult = indexFile.putKey(Long.toString(400), 400, System.currentTimeMillis());
        assertThat(putResult).isFalse();
        indexFile.destroy(0);
        File file = new File("100");
        UtilAll.deleteFile(file);
    }

    @Test
    public void testSelectPhyOffset() throws Exception {
        IndexFile indexFile = new IndexFile("200", HASH_SLOT_NUM, INDEX_NUM, 0, 0);

        for (long i = 0; i < (INDEX_NUM - 1); i++) {
            boolean putResult = indexFile.putKey(Long.toString(i), i, System.currentTimeMillis());
            assertThat(putResult).isTrue();
        }

        // put over index file capacity.
        boolean putResult = indexFile.putKey(Long.toString(400), 400, System.currentTimeMillis());
        assertThat(putResult).isFalse();

        final List<Long> phyOffsets = new ArrayList<Long>();
        indexFile.selectPhyOffset(phyOffsets, "60", 10, 0, Long.MAX_VALUE, true);
        assertThat(phyOffsets).isNotEmpty();
        assertThat(phyOffsets.size()).isEqualTo(1);
        indexFile.destroy(0);
        File file = new File("200");
        UtilAll.deleteFile(file);
    }
}
