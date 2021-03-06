
package org.apache.rocketmq.namesrv.kvconfig;

import java.util.HashMap;
import org.apache.rocketmq.common.namesrv.NamesrvUtil;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KVConfigSerializeWrapperTest {
    private KVConfigSerializeWrapper kvConfigSerializeWrapper;

    @Before
    public void setup() throws Exception {
        kvConfigSerializeWrapper = new KVConfigSerializeWrapper();
    }

    @Test
    public void testEncodeAndDecode() {
        HashMap<String, HashMap<String, String>> result = new HashMap<>();
        HashMap<String, String> kvs = new HashMap<>();
        kvs.put("broker-name", "default-broker");
        kvs.put("topic-name", "default-topic");
        kvs.put("cid", "default-consumer-name");
        result.put(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG, kvs);
        kvConfigSerializeWrapper.setConfigTable(result);
        byte[] serializeByte = KVConfigSerializeWrapper.encode(kvConfigSerializeWrapper);
        assertThat(serializeByte).isNotNull();

        KVConfigSerializeWrapper deserializeObject = KVConfigSerializeWrapper.decode(serializeByte, KVConfigSerializeWrapper.class);
        assertThat(deserializeObject.getConfigTable()).containsKey(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG);
        assertThat(deserializeObject.getConfigTable().get(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG).get("broker-name")).isEqualTo("default-broker");
        assertThat(deserializeObject.getConfigTable().get(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG).get("topic-name")).isEqualTo("default-topic");
        assertThat(deserializeObject.getConfigTable().get(NamesrvUtil.NAMESPACE_ORDER_TOPIC_CONFIG).get("cid")).isEqualTo("default-consumer-name");
    }

}