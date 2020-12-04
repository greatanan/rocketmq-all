
package org.apache.rocketmq.remoting.protocol;

import java.util.HashMap;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RocketMQSerializableTest {
    @Test
    public void testRocketMQProtocolEncodeAndDecode_WithoutRemarkWithoutExtFields() {
        System.setProperty(RemotingCommand.REMOTING_VERSION_KEY, "2333");

        //org.apache.rocketmq.common.protocol.RequestCode.REGISTER_BROKER
        int code = 103;
        RemotingCommand cmd = RemotingCommand.createRequestCommand(code, new SampleCommandCustomHeader());
        cmd.setSerializeTypeCurrentRPC(SerializeType.ROCKETMQ);

        byte[] result = RocketMQSerializable.rocketMQProtocolEncode(cmd);
        int opaque = cmd.getOpaque();

        assertThat(result).hasSize(21);
        assertThat(parseToShort(result, 0)).isEqualTo((short) code); //code
        assertThat(result[2]).isEqualTo(LanguageCode.JAVA.getCode()); //language
        assertThat(parseToShort(result, 3)).isEqualTo((short) 2333); //version
        assertThat(parseToInt(result, 9)).isEqualTo(0); //flag
        assertThat(parseToInt(result, 13)).isEqualTo(0); //empty remark
        assertThat(parseToInt(result, 17)).isEqualTo(0); //empty extFields

        RemotingCommand decodedCommand = RocketMQSerializable.rocketMQProtocolDecode(result);

        assertThat(decodedCommand.getCode()).isEqualTo(code);
        assertThat(decodedCommand.getLanguage()).isEqualTo(LanguageCode.JAVA);
        assertThat(decodedCommand.getVersion()).isEqualTo(2333);
        assertThat(decodedCommand.getOpaque()).isEqualTo(opaque);
        assertThat(decodedCommand.getFlag()).isEqualTo(0);
        assertThat(decodedCommand.getRemark()).isNull();
        assertThat(decodedCommand.getExtFields()).isNull();
    }

    @Test
    public void testRocketMQProtocolEncodeAndDecode_WithRemarkWithoutExtFields() {
        System.setProperty(RemotingCommand.REMOTING_VERSION_KEY, "2333");

        //org.apache.rocketmq.common.protocol.RequestCode.REGISTER_BROKER
        int code = 103;
        RemotingCommand cmd = RemotingCommand.createRequestCommand(code,
            new SampleCommandCustomHeader());
        cmd.setSerializeTypeCurrentRPC(SerializeType.ROCKETMQ);
        cmd.setRemark("Sample Remark");

        byte[] result = RocketMQSerializable.rocketMQProtocolEncode(cmd);
        int opaque = cmd.getOpaque();

        assertThat(result).hasSize(34);
        assertThat(parseToShort(result, 0)).isEqualTo((short) code); //code
        assertThat(result[2]).isEqualTo(LanguageCode.JAVA.getCode()); //language
        assertThat(parseToShort(result, 3)).isEqualTo((short) 2333); //version
        assertThat(parseToInt(result, 9)).isEqualTo(0); //flag
        assertThat(parseToInt(result, 13)).isEqualTo(13); //remark length

        byte[] remarkArray = new byte[13];
        System.arraycopy(result, 17, remarkArray, 0, 13);
        assertThat(new String(remarkArray)).isEqualTo("Sample Remark");

        assertThat(parseToInt(result, 30)).isEqualTo(0); //empty extFields

        RemotingCommand decodedCommand = RocketMQSerializable.rocketMQProtocolDecode(result);

        assertThat(decodedCommand.getCode()).isEqualTo(code);
        assertThat(decodedCommand.getLanguage()).isEqualTo(LanguageCode.JAVA);
        assertThat(decodedCommand.getVersion()).isEqualTo(2333);
        assertThat(decodedCommand.getOpaque()).isEqualTo(opaque);
        assertThat(decodedCommand.getFlag()).isEqualTo(0);
        assertThat(decodedCommand.getRemark()).contains("Sample Remark");
        assertThat(decodedCommand.getExtFields()).isNull();
    }

    @Test
    public void testRocketMQProtocolEncodeAndDecode_WithoutRemarkWithExtFields() {
        System.setProperty(RemotingCommand.REMOTING_VERSION_KEY, "2333");

        //org.apache.rocketmq.common.protocol.RequestCode.REGISTER_BROKER
        int code = 103;
        RemotingCommand cmd = RemotingCommand.createRequestCommand(code,
            new SampleCommandCustomHeader());
        cmd.setSerializeTypeCurrentRPC(SerializeType.ROCKETMQ);
        cmd.addExtField("key", "value");

        byte[] result = RocketMQSerializable.rocketMQProtocolEncode(cmd);
        int opaque = cmd.getOpaque();

        assertThat(result).hasSize(35);
        assertThat(parseToShort(result, 0)).isEqualTo((short) code); //code
        assertThat(result[2]).isEqualTo(LanguageCode.JAVA.getCode()); //language
        assertThat(parseToShort(result, 3)).isEqualTo((short) 2333); //version
        assertThat(parseToInt(result, 9)).isEqualTo(0); //flag
        assertThat(parseToInt(result, 13)).isEqualTo(0); //empty remark
        assertThat(parseToInt(result, 17)).isEqualTo(14); //extFields length

        byte[] extFieldsArray = new byte[14];
        System.arraycopy(result, 21, extFieldsArray, 0, 14);
        HashMap<String, String> extFields = RocketMQSerializable.mapDeserialize(extFieldsArray);
        assertThat(extFields).contains(new HashMap.SimpleEntry("key", "value"));

        RemotingCommand decodedCommand = RocketMQSerializable.rocketMQProtocolDecode(result);

        assertThat(decodedCommand.getCode()).isEqualTo(code);
        assertThat(decodedCommand.getLanguage()).isEqualTo(LanguageCode.JAVA);
        assertThat(decodedCommand.getVersion()).isEqualTo(2333);
        assertThat(decodedCommand.getOpaque()).isEqualTo(opaque);
        assertThat(decodedCommand.getFlag()).isEqualTo(0);
        assertThat(decodedCommand.getRemark()).isNull();
        assertThat(decodedCommand.getExtFields()).contains(new HashMap.SimpleEntry("key", "value"));
    }

    @Test
    public void testIsBlank_NotBlank() {
        assertThat(RocketMQSerializable.isBlank("bar")).isFalse();
        assertThat(RocketMQSerializable.isBlank("  A  ")).isFalse();
    }

    @Test
    public void testIsBlank_Blank() {
        assertThat(RocketMQSerializable.isBlank(null)).isTrue();
        assertThat(RocketMQSerializable.isBlank("")).isTrue();
        assertThat(RocketMQSerializable.isBlank("  ")).isTrue();
    }

    private short parseToShort(byte[] array, int index) {
        return (short) (array[index] * 256 + array[++index]);
    }

    private int parseToInt(byte[] array, int index) {
        return array[index] * 16777216 + array[++index] * 65536 + array[++index] * 256
            + array[++index];
    }
}