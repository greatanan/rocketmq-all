

package org.apache.rocketmq.namesrv.routeinfo;

import org.apache.rocketmq.common.namesrv.NamesrvConfig;
import org.apache.rocketmq.namesrv.NamesrvController;
import org.apache.rocketmq.remoting.netty.NettyServerConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BrokerHousekeepingServiceTest {
    private static BrokerHousekeepingService brokerHousekeepingService;

    @BeforeClass
    public static void setup() {
        NamesrvController namesrvController = new NamesrvController(
            new NamesrvConfig(),
            new NettyServerConfig()
        );
        brokerHousekeepingService = new BrokerHousekeepingService(namesrvController);
    }

    @AfterClass
    public static void terminate() {

    }

    @Test
    public void testOnChannelClose() {
        brokerHousekeepingService.onChannelClose("127.0.0.1:9876", null);
    }

    @Test
    public void testOnChannelException() {
        brokerHousekeepingService.onChannelException("127.0.0.1:9876", null);
    }

    @Test
    public void testOnChannelIdle() {
        brokerHousekeepingService.onChannelException("127.0.0.1:9876", null);
    }

}