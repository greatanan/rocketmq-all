
package org.apache.rocketmq.logappender;

import org.apache.log4j.xml.DOMConfigurator;

public class Log4jXmlTest extends Log4jTest {

    @Override
    public void init() {
        DOMConfigurator.configure("src/test/resources/log4j-example.xml");
    }

    @Override
    public String getType() {
        return "xml";
    }
}
