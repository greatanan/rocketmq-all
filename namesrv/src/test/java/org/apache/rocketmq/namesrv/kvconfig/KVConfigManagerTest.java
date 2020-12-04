
package org.apache.rocketmq.namesrv.kvconfig;

import org.apache.rocketmq.common.namesrv.NamesrvUtil;
import org.apache.rocketmq.namesrv.NameServerInstanceTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KVConfigManagerTest extends NameServerInstanceTest {
    private KVConfigManager kvConfigManager;

    @Before
    public void setup() throws Exception {
        kvConfigManager = new KVConfigManager(nameSrvController);
    }

    @Test
    public void testPutKVConfig() {
        kvConfigManager.putKVConfig(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG, "UnitTest", "test");
        byte[] kvConfig = kvConfigManager.getKVListByNamespace(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG);
        assertThat(kvConfig).isNotNull();
        String value = kvConfigManager.getKVConfig(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG, "UnitTest");
        assertThat(value).isEqualTo("test");
    }

    @Test
    public void testDeleteKVConfig() {
        kvConfigManager.deleteKVConfig(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG, "UnitTest");
        byte[] kvConfig = kvConfigManager.getKVListByNamespace(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG);
        assertThat(kvConfig).isNull();
        Assert.assertTrue(kvConfig == null);
        String value = kvConfigManager.getKVConfig(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG, "UnitTest");
        assertThat(value).isNull();
    }
}