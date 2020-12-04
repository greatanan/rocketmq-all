
package org.apache.rocketmq.namesrv.routeinfo;

import io.netty.channel.Channel;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.rocketmq.common.TopicConfig;
import org.apache.rocketmq.common.namesrv.RegisterBrokerResult;
import org.apache.rocketmq.common.protocol.body.TopicConfigSerializeWrapper;
import org.apache.rocketmq.common.protocol.route.TopicRouteData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class RouteInfoManagerTest {

    private static RouteInfoManager routeInfoManager;

    @Before
    public void setup() {
        routeInfoManager = new RouteInfoManager();
        testRegisterBroker();
    }

    @After
    public void terminate() {
        routeInfoManager.printAllPeriodically();
        routeInfoManager.unregisterBroker("default-cluster", "127.0.0.1:10911", "default-broker", 1234);
    }

    @Test
    public void testGetAllClusterInfo() {
        byte[] clusterInfo = routeInfoManager.getAllClusterInfo();
        assertThat(clusterInfo).isNotNull();
    }

    @Test
    public void testGetAllTopicList() {
        byte[] topicInfo = routeInfoManager.getAllTopicList();
        Assert.assertTrue(topicInfo != null);
        assertThat(topicInfo).isNotNull();
    }

    @Test
    public void testRegisterBroker() {
        TopicConfigSerializeWrapper topicConfigSerializeWrapper = new TopicConfigSerializeWrapper();
        ConcurrentHashMap<String, TopicConfig> topicConfigConcurrentHashMap = new ConcurrentHashMap<>();
        TopicConfig topicConfig = new TopicConfig();
        topicConfig.setWriteQueueNums(8);
        topicConfig.setTopicName("unit-test");
        topicConfig.setPerm(6);
        topicConfig.setReadQueueNums(8);
        topicConfig.setOrder(false);
        topicConfigConcurrentHashMap.put("unit-test", topicConfig);
        topicConfigSerializeWrapper.setTopicConfigTable(topicConfigConcurrentHashMap);
        Channel channel = mock(Channel.class);
        RegisterBrokerResult registerBrokerResult = routeInfoManager.registerBroker("default-cluster", "127.0.0.1:10911", "default-broker", 1234, "127.0.0.1:1001",
            topicConfigSerializeWrapper, new ArrayList<String>(), channel);
        assertThat(registerBrokerResult).isNotNull();
    }

    @Test
    public void testWipeWritePermOfBrokerByLock() {
        int result = routeInfoManager.wipeWritePermOfBrokerByLock("default-broker");
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void testPickupTopicRouteData() {
        TopicRouteData result = routeInfoManager.pickupTopicRouteData("unit_test");
        assertThat(result).isNull();
    }

    @Test
    public void testGetSystemTopicList() {
        byte[] topicList = routeInfoManager.getSystemTopicList();
        assertThat(topicList).isNotNull();
    }

    @Test
    public void testGetTopicsByCluster() {
        byte[] topicList = routeInfoManager.getTopicsByCluster("default-cluster");
        assertThat(topicList).isNotNull();
    }

    @Test
    public void testGetUnitTopics() {
        byte[] topicList = routeInfoManager.getUnitTopics();
        assertThat(topicList).isNotNull();
    }

    @Test
    public void testGetHasUnitSubTopicList() {
        byte[] topicList = routeInfoManager.getHasUnitSubTopicList();
        assertThat(topicList).isNotNull();
    }

    @Test
    public void testGetHasUnitSubUnUnitTopicList() {
        byte[] topicList = routeInfoManager.getHasUnitSubUnUnitTopicList();
        assertThat(topicList).isNotNull();
    }
}