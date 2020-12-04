

package org.apache.rocketmq.broker;

import java.io.File;
import org.apache.rocketmq.common.BrokerConfig;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.remoting.netty.NettyClientConfig;
import org.apache.rocketmq.remoting.netty.NettyServerConfig;
import org.apache.rocketmq.store.config.MessageStoreConfig;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BrokerControllerTest {

    @Test
    public void testBrokerRestart() throws Exception {
        BrokerController brokerController = new BrokerController(
            new BrokerConfig(),
            new NettyServerConfig(),
            new NettyClientConfig(),
            new MessageStoreConfig());
        assertThat(brokerController.initialize());
        brokerController.start();
        brokerController.shutdown();
    }

    @After
    public void destroy() {
        UtilAll.deleteFile(new File(new MessageStoreConfig().getStorePathRootDir()));
    }
}
