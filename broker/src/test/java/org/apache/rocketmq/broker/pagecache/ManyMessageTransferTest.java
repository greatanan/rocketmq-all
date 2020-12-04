

package org.apache.rocketmq.broker.pagecache;

import java.nio.ByteBuffer;
import org.apache.rocketmq.store.GetMessageResult;
import org.junit.Assert;
import org.junit.Test;

public class ManyMessageTransferTest {

    @Test
    public void ManyMessageTransferBuilderTest(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        byteBuffer.putInt(20);
        GetMessageResult getMessageResult = new GetMessageResult();
        ManyMessageTransfer manyMessageTransfer = new ManyMessageTransfer(byteBuffer,getMessageResult);
    }

    @Test
    public void ManyMessageTransferPosTest(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        byteBuffer.putInt(20);
        GetMessageResult getMessageResult = new GetMessageResult();
        ManyMessageTransfer manyMessageTransfer = new ManyMessageTransfer(byteBuffer,getMessageResult);
        Assert.assertEquals(manyMessageTransfer.position(),4);
    }

    @Test
    public void ManyMessageTransferCountTest(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        byteBuffer.putInt(20);
        GetMessageResult getMessageResult = new GetMessageResult();
        ManyMessageTransfer manyMessageTransfer = new ManyMessageTransfer(byteBuffer,getMessageResult);

        Assert.assertEquals(manyMessageTransfer.count(),20);

    }

    @Test
    public void ManyMessageTransferCloseTest(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        byteBuffer.putInt(20);
        GetMessageResult getMessageResult = new GetMessageResult();
        ManyMessageTransfer manyMessageTransfer = new ManyMessageTransfer(byteBuffer,getMessageResult);
        manyMessageTransfer.close();
        manyMessageTransfer.deallocate();
    }
}
