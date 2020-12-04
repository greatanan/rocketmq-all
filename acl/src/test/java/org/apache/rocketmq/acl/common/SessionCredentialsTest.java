
package org.apache.rocketmq.acl.common;

import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

public class SessionCredentialsTest {

    @Test
    public void equalsTest(){
        SessionCredentials sessionCredentials=new SessionCredentials("RocketMQ","12345678");
        sessionCredentials.setSecurityToken("abcd");
        SessionCredentials other=new SessionCredentials("RocketMQ","12345678","abcd");
        Assert.assertTrue(sessionCredentials.equals(other));
    }

    @Test
    public void updateContentTest(){
        SessionCredentials sessionCredentials=new SessionCredentials();
        Properties properties=new Properties();
        properties.setProperty(SessionCredentials.ACCESS_KEY,"RocketMQ");
        properties.setProperty(SessionCredentials.SECRET_KEY,"12345678");
        properties.setProperty(SessionCredentials.SECURITY_TOKEN,"abcd");
        sessionCredentials.updateContent(properties);
    }

    @Test
    public void SessionCredentialHashCodeTest(){
        SessionCredentials sessionCredentials=new SessionCredentials();
        Properties properties=new Properties();
        properties.setProperty(SessionCredentials.ACCESS_KEY,"RocketMQ");
        properties.setProperty(SessionCredentials.SECRET_KEY,"12345678");
        properties.setProperty(SessionCredentials.SECURITY_TOKEN,"abcd");
        sessionCredentials.updateContent(properties);
        Assert.assertEquals(sessionCredentials.hashCode(),353652211);
    }

    @Test
    public void SessionCredentialEqualsTest(){
        SessionCredentials sessionCredential1 =new SessionCredentials();
        Properties properties1=new Properties();
        properties1.setProperty(SessionCredentials.ACCESS_KEY,"RocketMQ");
        properties1.setProperty(SessionCredentials.SECRET_KEY,"12345678");
        properties1.setProperty(SessionCredentials.SECURITY_TOKEN,"abcd");
        sessionCredential1.updateContent(properties1);

        SessionCredentials sessionCredential2 =new SessionCredentials();
        Properties properties2=new Properties();
        properties2.setProperty(SessionCredentials.ACCESS_KEY,"RocketMQ");
        properties2.setProperty(SessionCredentials.SECRET_KEY,"12345678");
        properties2.setProperty(SessionCredentials.SECURITY_TOKEN,"abcd");
        sessionCredential2.updateContent(properties2);

        Assert.assertTrue(sessionCredential2.equals(sessionCredential1));
        sessionCredential2.setSecretKey("1234567899");
        sessionCredential2.setSignature("1234567899");
        Assert.assertFalse(sessionCredential2.equals(sessionCredential1));
    }

    @Test
    public void SessionCredentialToStringTest(){
        SessionCredentials sessionCredential1 =new SessionCredentials();
        Properties properties1=new Properties();
        properties1.setProperty(SessionCredentials.ACCESS_KEY,"RocketMQ");
        properties1.setProperty(SessionCredentials.SECRET_KEY,"12345678");
        properties1.setProperty(SessionCredentials.SECURITY_TOKEN,"abcd");
        sessionCredential1.updateContent(properties1);

        Assert.assertEquals(sessionCredential1.toString(),
            "SessionCredentials [accessKey=RocketMQ, secretKey=12345678, signature=null, SecurityToken=abcd]");
    }


}
