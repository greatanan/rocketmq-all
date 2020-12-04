

package org.apache.rocketmq.common.message;

import org.apache.rocketmq.common.UtilAll;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageClientIDSetterTest {

    @Test
    public void testGetIPStrFromID() {
        byte[] ip = UtilAll.getIP();
        String ipStr = (4 == ip.length) ? UtilAll.ipToIPv4Str(ip) : UtilAll.ipToIPv6Str(ip);

        String uniqID = MessageClientIDSetter.createUniqID();
        String ipStrFromID = MessageClientIDSetter.getIPStrFromID(uniqID);

        assertThat(ipStr).isEqualTo(ipStrFromID);
    }

    @Test
    public void testGetPidFromID() {
        int pid = UtilAll.getPid();

        String uniqID = MessageClientIDSetter.createUniqID();
        int pidFromID = MessageClientIDSetter.getPidFromID(uniqID);

        assertThat(pid).isEqualTo(pidFromID);
    }
}
