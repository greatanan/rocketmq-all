

package org.apache.rocketmq.client.producer;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.rocketmq.common.message.Message;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestResponseFutureTest {

    @Test
    public void testExecuteRequestCallback() throws Exception {
        final AtomicInteger cc = new AtomicInteger(0);
        RequestResponseFuture future = new RequestResponseFuture(UUID.randomUUID().toString(), 3 * 1000L, new RequestCallback() {
            @Override public void onSuccess(Message message) {
                cc.incrementAndGet();
            }

            @Override public void onException(Throwable e) {
            }
        });
        future.setSendRequestOk(true);
        future.executeRequestCallback();
        assertThat(cc.get()).isEqualTo(1);
    }

}
