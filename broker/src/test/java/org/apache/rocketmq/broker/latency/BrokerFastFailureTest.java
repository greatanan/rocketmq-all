
package org.apache.rocketmq.broker.latency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.rocketmq.remoting.netty.RequestTask;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BrokerFastFailureTest {
    @Test
    public void testCleanExpiredRequestInQueue() throws Exception {
        BrokerFastFailure brokerFastFailure = new BrokerFastFailure(null);

        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        brokerFastFailure.cleanExpiredRequestInQueue(queue, 1);
        assertThat(queue.size()).isZero();

        //Normal Runnable
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        queue.add(runnable);

        assertThat(queue.size()).isEqualTo(1);
        brokerFastFailure.cleanExpiredRequestInQueue(queue, 1);
        assertThat(queue.size()).isEqualTo(1);

        queue.clear();

        //With expired request
        RequestTask expiredRequest = new RequestTask(runnable, null, null);
        queue.add(new FutureTaskExt<>(expiredRequest, null));
        TimeUnit.MILLISECONDS.sleep(100);

        RequestTask requestTask = new RequestTask(runnable, null, null);
        queue.add(new FutureTaskExt<>(requestTask, null));

        assertThat(queue.size()).isEqualTo(2);
        brokerFastFailure.cleanExpiredRequestInQueue(queue, 100);
        assertThat(queue.size()).isEqualTo(1);
        assertThat(((FutureTaskExt) queue.peek()).getRunnable()).isEqualTo(requestTask);
    }

}