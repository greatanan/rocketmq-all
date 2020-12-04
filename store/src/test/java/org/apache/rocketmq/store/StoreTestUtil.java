
package org.apache.rocketmq.store;

import org.apache.rocketmq.logging.InternalLogger;
import org.apache.rocketmq.logging.InternalLoggerFactory;
import org.apache.rocketmq.store.index.IndexFile;
import org.apache.rocketmq.store.index.IndexService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


public class StoreTestUtil {

    private static final InternalLogger log = InternalLoggerFactory.getLogger(StoreTestUtil.class);

    public static boolean isCommitLogAvailable(DefaultMessageStore store) {
        try {

            Field serviceField = store.getClass().getDeclaredField("reputMessageService");
            serviceField.setAccessible(true);
            DefaultMessageStore.ReputMessageService reputService =
                    (DefaultMessageStore.ReputMessageService) serviceField.get(store);

            Method method = DefaultMessageStore.ReputMessageService.class.getDeclaredMethod("isCommitLogAvailable");
            method.setAccessible(true);
            return (boolean) method.invoke(reputService);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static void flushConsumeQueue(DefaultMessageStore store) throws Exception {
        Field field = store.getClass().getDeclaredField("flushConsumeQueueService");
        field.setAccessible(true);
        DefaultMessageStore.FlushConsumeQueueService flushService = (DefaultMessageStore.FlushConsumeQueueService) field.get(store);

        final int RETRY_TIMES_OVER = 3;
        Method method = DefaultMessageStore.FlushConsumeQueueService.class.getDeclaredMethod("doFlush", int.class);
        method.setAccessible(true);
        method.invoke(flushService, RETRY_TIMES_OVER);
    }


    public static void waitCommitLogReput(DefaultMessageStore store) {
        for (int i = 0; i < 500 && isCommitLogAvailable(store); i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }

        if (isCommitLogAvailable(store)) {
            log.warn("isCommitLogAvailable expected false ,but true");
        }
    }


    public static void flushConsumeIndex(DefaultMessageStore store) throws NoSuchFieldException, Exception {
        Field field = store.getClass().getDeclaredField("indexService");
        field.setAccessible(true);
        IndexService indexService = (IndexService) field.get(store);

        Field field2 = indexService.getClass().getDeclaredField("indexFileList");
        field2.setAccessible(true);
        ArrayList<IndexFile> indexFileList = (ArrayList<IndexFile>) field2.get(indexService);

        for (IndexFile f : indexFileList) {
            indexService.flush(f);
        }
    }
}
