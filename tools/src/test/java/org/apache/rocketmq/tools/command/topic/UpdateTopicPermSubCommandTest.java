
package org.apache.rocketmq.tools.command.topic;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.rocketmq.srvutil.ServerUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateTopicPermSubCommandTest {
    @Test
    public void testExecute() {
        UpdateTopicPermSubCommand cmd = new UpdateTopicPermSubCommand();
        Options options = ServerUtil.buildCommandlineOptions(new Options());
        String[] subargs = new String[] {"-b 127.0.0.1:10911", "-c default-cluster", "-t unit-test", "-p 6"};
        final CommandLine commandLine =
            ServerUtil.parseCmdLine("mqadmin " + cmd.commandName(), subargs, cmd.buildCommandlineOptions(options), new PosixParser());
        assertThat(commandLine.getOptionValue('b').trim()).isEqualTo("127.0.0.1:10911");
        assertThat(commandLine.getOptionValue('c').trim()).isEqualTo("default-cluster");
        assertThat(commandLine.getOptionValue('t').trim()).isEqualTo("unit-test");
        assertThat(commandLine.getOptionValue('p').trim()).isEqualTo("6");

    }
}