
package org.apache.rocketmq.client.latency;

import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LatencyFaultToleranceImplTest {
    private LatencyFaultTolerance<String> latencyFaultTolerance;
    private String brokerName = "BrokerA";
    private String anotherBrokerName = "BrokerB";

    @Before
    public void init() {
        latencyFaultTolerance = new LatencyFaultToleranceImpl();
    }

    @Test
    public void testUpdateFaultItem() throws Exception {
        latencyFaultTolerance.updateFaultItem(brokerName, 3000, 3000);
        assertThat(latencyFaultTolerance.isAvailable(brokerName)).isFalse();
        assertThat(latencyFaultTolerance.isAvailable(anotherBrokerName)).isTrue();
    }

    @Test
    public void testIsAvailable() throws Exception {
        latencyFaultTolerance.updateFaultItem(brokerName, 3000, 50);
        assertThat(latencyFaultTolerance.isAvailable(brokerName)).isFalse();

        TimeUnit.MILLISECONDS.sleep(70);
        assertThat(latencyFaultTolerance.isAvailable(brokerName)).isTrue();
    }

    @Test
    public void testRemove() throws Exception {
        latencyFaultTolerance.updateFaultItem(brokerName, 3000, 3000);
        assertThat(latencyFaultTolerance.isAvailable(brokerName)).isFalse();
        latencyFaultTolerance.remove(brokerName);
        assertThat(latencyFaultTolerance.isAvailable(brokerName)).isTrue();
    }

    @Test
    public void testPickOneAtLeast() throws Exception {
        latencyFaultTolerance.updateFaultItem(brokerName, 1000, 3000);
        assertThat(latencyFaultTolerance.pickOneAtLeast()).isEqualTo(brokerName);

        latencyFaultTolerance.updateFaultItem(anotherBrokerName, 1001, 3000);
        assertThat(latencyFaultTolerance.pickOneAtLeast()).isEqualTo(brokerName);
    }
}