
package org.apache.rocketmq.tools.command.topic;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.rocketmq.srvutil.ServerUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateTopicSubCommandTest {
    @Test
    public void testExecute() {
        UpdateTopicSubCommand cmd = new UpdateTopicSubCommand();
        Options options = ServerUtil.buildCommandlineOptions(new Options());
        String[] subargs = new String[] {
            "-b 127.0.0.1:10911",
            "-t unit-test",
            "-r 8",
            "-w 8",
            "-p 6",
            "-o false",
            "-u false",
            "-s false"};
        final CommandLine commandLine =
            ServerUtil.parseCmdLine("mqadmin " + cmd.commandName(), subargs, cmd.buildCommandlineOptions(options), new PosixParser());
        assertThat(commandLine.getOptionValue('b').trim()).isEqualTo("127.0.0.1:10911");
        assertThat(commandLine.getOptionValue('r').trim()).isEqualTo("8");
        assertThat(commandLine.getOptionValue('w').trim()).isEqualTo("8");
        assertThat(commandLine.getOptionValue('t').trim()).isEqualTo("unit-test");
        assertThat(commandLine.getOptionValue('p').trim()).isEqualTo("6");
        assertThat(commandLine.getOptionValue('o').trim()).isEqualTo("false");
        assertThat(commandLine.getOptionValue('u').trim()).isEqualTo("false");
        assertThat(commandLine.getOptionValue('s').trim()).isEqualTo("false");
    }
}