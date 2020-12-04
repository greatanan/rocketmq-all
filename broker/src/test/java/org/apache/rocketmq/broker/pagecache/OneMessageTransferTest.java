

package org.apache.rocketmq.broker.pagecache;

import java.nio.ByteBuffer;
import org.apache.rocketmq.store.MappedFile;
import org.apache.rocketmq.store.SelectMappedBufferResult;
import org.junit.Assert;
import org.junit.Test;

public class OneMessageTransferTest {

    @Test
    public void OneMessageTransferTest(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        byteBuffer.putInt(20);
        SelectMappedBufferResult selectMappedBufferResult = new SelectMappedBufferResult(0,byteBuffer,20,new MappedFile());
        OneMessageTransfer manyMessageTransfer = new OneMessageTransfer(byteBuffer,selectMappedBufferResult);
    }

    @Test
    public void OneMessageTransferCountTest(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        byteBuffer.putInt(20);
        SelectMappedBufferResult selectMappedBufferResult = new SelectMappedBufferResult(0,byteBuffer,20,new MappedFile());
        OneMessageTransfer manyMessageTransfer = new OneMessageTransfer(byteBuffer,selectMappedBufferResult);
        Assert.assertEquals(manyMessageTransfer.count(),40);
    }

    @Test
    public void OneMessageTransferPosTest(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        byteBuffer.putInt(20);
        SelectMappedBufferResult selectMappedBufferResult = new SelectMappedBufferResult(0,byteBuffer,20,new MappedFile());
        OneMessageTransfer manyMessageTransfer = new OneMessageTransfer(byteBuffer,selectMappedBufferResult);
        Assert.assertEquals(manyMessageTransfer.position(),8);
    }
}
