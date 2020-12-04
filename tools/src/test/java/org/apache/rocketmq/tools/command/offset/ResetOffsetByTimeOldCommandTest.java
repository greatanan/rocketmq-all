
package org.apache.rocketmq.tools.command.offset;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.rocketmq.srvutil.ServerUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResetOffsetByTimeOldCommandTest {
    @Test
    public void testExecute() {
        ResetOffsetByTimeOldCommand cmd = new ResetOffsetByTimeOldCommand();
        Options options = ServerUtil.buildCommandlineOptions(new Options());
        String[] subargs = new String[] {"-g default-group", "-t unit-test", "-s 1412131213231", "-f false"};
        final CommandLine commandLine =
            ServerUtil.parseCmdLine("mqadmin " + cmd.commandName(), subargs, cmd.buildCommandlineOptions(options), new PosixParser());
        assertThat(commandLine.getOptionValue('g').trim()).isEqualTo("default-group");
        assertThat(commandLine.getOptionValue('t').trim()).isEqualTo("unit-test");
        assertThat(commandLine.getOptionValue('s').trim()).isEqualTo("1412131213231");
    }
}