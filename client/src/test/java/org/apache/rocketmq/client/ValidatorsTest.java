

package org.apache.rocketmq.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.topic.TopicValidator;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.failBecauseExceptionWasNotThrown;
import static org.junit.Assert.fail;

public class ValidatorsTest {

    @Test
    public void testCheckTopic_Success() throws MQClientException {
        Validators.checkTopic("Hello");
        Validators.checkTopic("%RETRY%Hello");
        Validators.checkTopic("_%RETRY%Hello");
        Validators.checkTopic("-%RETRY%Hello");
        Validators.checkTopic("223-%RETRY%Hello");
    }

    @Test
    public void testCheckTopic_HasIllegalCharacters() {
        String illegalTopic = "TOPIC&*^";
        try {
            Validators.checkTopic(illegalTopic);
            failBecauseExceptionWasNotThrown(MQClientException.class);
        } catch (MQClientException e) {
            assertThat(e).hasMessageStartingWith(String.format("The specified topic[%s] contains illegal characters, allowing only %s", illegalTopic, Validators.VALID_PATTERN_STR));
        }
    }

    @Test
    public void testCheckTopic_BlankTopic() {
        String blankTopic = "";
        try {
            Validators.checkTopic(blankTopic);
            failBecauseExceptionWasNotThrown(MQClientException.class);
        } catch (MQClientException e) {
            assertThat(e).hasMessageStartingWith("The specified topic is blank");
        }
    }

    @Test
    public void testCheckTopic_TooLongTopic() {
        String tooLongTopic = StringUtils.rightPad("TooLongTopic", Validators.TOPIC_MAX_LENGTH + 1, "_");
        assertThat(tooLongTopic.length()).isGreaterThan(Validators.TOPIC_MAX_LENGTH);
        try {
            Validators.checkTopic(tooLongTopic);
            failBecauseExceptionWasNotThrown(MQClientException.class);
        } catch (MQClientException e) {
            assertThat(e).hasMessageStartingWith("The specified topic is longer than topic max length");
        }
    }

    @Test
    public void testIsSystemTopic() {
        for (String topic : TopicValidator.getSystemTopicSet()) {
            try {
                Validators.isSystemTopic(topic);
                fail("excepted MQClientException for system topic");
            } catch (MQClientException e) {
                assertThat(e.getResponseCode()).isEqualTo(-1);
                assertThat(e.getErrorMessage()).isEqualTo(String.format("The topic[%s] is conflict with system topic.", topic));
            }
        }
    }

    @Test
    public void testIsNotAllowedSendTopic() {
        for (String topic : TopicValidator.getNotAllowedSendTopicSet()) {
            try {
                Validators.isNotAllowedSendTopic(topic);
                fail("excepted MQClientException for blacklist topic");
            } catch (MQClientException e) {
                assertThat(e.getResponseCode()).isEqualTo(-1);
                assertThat(e.getErrorMessage()).isEqualTo(String.format("Sending message to topic[%s] is forbidden.", topic));
            }
        }
    }
}
