
package io.openmessaging.rocketmq.promise;

import io.openmessaging.Future;
import io.openmessaging.FutureListener;
import io.openmessaging.Promise;
import io.openmessaging.exception.OMSRuntimeException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.failBecauseExceptionWasNotThrown;

public class DefaultPromiseTest {
    private Promise<String> promise;

    @Before
    public void init() {
        promise = new DefaultPromise<>();
    }

    @Test
    public void testIsCancelled() throws Exception {
        assertThat(promise.isCancelled()).isEqualTo(false);
    }

    @Test
    public void testIsDone() throws Exception {
        assertThat(promise.isDone()).isEqualTo(false);
        promise.set("Done");
        assertThat(promise.isDone()).isEqualTo(true);
    }

    @Test
    public void testGet() throws Exception {
        promise.set("Done");
        assertThat(promise.get()).isEqualTo("Done");
    }

    @Test
    public void testGet_WithTimeout() throws Exception {
        try {
            promise.get(100);
            failBecauseExceptionWasNotThrown(OMSRuntimeException.class);
        } catch (OMSRuntimeException e) {
            assertThat(e).hasMessageContaining("Get request result is timeout or interrupted");
        }
    }

    @Test
    public void testAddListener() throws Exception {
        promise.addListener(new FutureListener<String>() {
            @Override
            public void operationComplete(Future<String> future) {
                assertThat(promise.get()).isEqualTo("Done");

            }
        });
        promise.set("Done");
    }

    @Test
    public void testAddListener_ListenerAfterSet() throws Exception {
        promise.set("Done");
        promise.addListener(new FutureListener<String>() {
            @Override
            public void operationComplete(Future<String> future) {
                assertThat(future.get()).isEqualTo("Done");
            }
        });
    }

    @Test
    public void testAddListener_WithException_ListenerAfterSet() throws Exception {
        final Throwable exception = new OMSRuntimeException("-1", "Test Error");
        promise.setFailure(exception);
        promise.addListener(new FutureListener<String>() {
            @Override
            public void operationComplete(Future<String> future) {
                assertThat(promise.getThrowable()).isEqualTo(exception);
            }
        });
    }

    @Test
    public void testAddListener_WithException() throws Exception {
        final Throwable exception = new OMSRuntimeException("-1", "Test Error");
        promise.addListener(new FutureListener<String>() {
            @Override
            public void operationComplete(Future<String> future) {
                assertThat(promise.getThrowable()).isEqualTo(exception);
            }
        });
        promise.setFailure(exception);
    }

    @Test
    public void getThrowable() throws Exception {
        assertThat(promise.getThrowable()).isNull();
        Throwable exception = new OMSRuntimeException("-1", "Test Error");
        promise.setFailure(exception);
        assertThat(promise.getThrowable()).isEqualTo(exception);
    }

}