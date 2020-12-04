
package org.apache.rocketmq.tools.command.topic;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.rocketmq.srvutil.ServerUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateOrderConfCommandTest {
    @Test
    public void testExecute() {
        UpdateOrderConfCommand cmd = new UpdateOrderConfCommand();
        Options options = ServerUtil.buildCommandlineOptions(new Options());
        String[] subargs = new String[] {"-t unit-test", "-v default-broker:8", "-m post"};
        final CommandLine commandLine =
            ServerUtil.parseCmdLine("mqadmin " + cmd.commandName(), subargs, cmd.buildCommandlineOptions(options), new PosixParser());
        assertThat(commandLine.getOptionValue('t').trim()).isEqualTo("unit-test");
        assertThat(commandLine.getOptionValue('v').trim()).isEqualTo("default-broker:8");
        assertThat(commandLine.getOptionValue('m').trim()).isEqualTo("post");
    }
}