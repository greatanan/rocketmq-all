
package org.apache.rocketmq.remoting.netty;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class NettyRemotingClientTest {
    private NettyRemotingClient remotingClient = new NettyRemotingClient(new NettyClientConfig());

    @Test
    public void testSetCallbackExecutor() throws NoSuchFieldException, IllegalAccessException {        
        ExecutorService customized = Executors.newCachedThreadPool();
        remotingClient.setCallbackExecutor(customized);

        assertThat(remotingClient.getCallbackExecutor()).isEqualTo(customized);
    }
}
