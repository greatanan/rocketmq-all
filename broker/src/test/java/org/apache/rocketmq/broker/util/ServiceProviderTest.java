

package org.apache.rocketmq.broker.util;

import org.apache.rocketmq.acl.AccessValidator;
import org.apache.rocketmq.broker.transaction.AbstractTransactionalMessageCheckListener;
import org.apache.rocketmq.broker.transaction.TransactionalMessageService;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class ServiceProviderTest {

    @Test
    public void loadTransactionMsgServiceTest() {
        TransactionalMessageService transactionService = ServiceProvider.loadClass(ServiceProvider.TRANSACTION_SERVICE_ID,
            TransactionalMessageService.class);
        assertThat(transactionService).isNotNull();
    }

    @Test
    public void loadAbstractTransactionListenerTest() {
        AbstractTransactionalMessageCheckListener listener = ServiceProvider.loadClass(ServiceProvider.TRANSACTION_LISTENER_ID,
            AbstractTransactionalMessageCheckListener.class);
        assertThat(listener).isNotNull();
    }
    
    @Test
    public void loadAccessValidatorTest() {
    	 List<AccessValidator> accessValidators = ServiceProvider.load(ServiceProvider.ACL_VALIDATOR_ID, AccessValidator.class);
    	 assertThat(accessValidators).isNotNull();
    }
}
