
package org.apache.rocketmq.tools.command.acl;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.rocketmq.srvutil.ServerUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateGlobalWhiteAddrSubCommandTest {

    @Test
    public void testExecute() {
        UpdateGlobalWhiteAddrSubCommand cmd = new UpdateGlobalWhiteAddrSubCommand();
        Options options = ServerUtil.buildCommandlineOptions(new Options());
        String[] subargs = new String[] {"-g 10.10.103.*,192.168.0.*", "-c default-cluster"};
        final CommandLine commandLine =
            ServerUtil.parseCmdLine("mqadmin " + cmd.commandName(), subargs, cmd.buildCommandlineOptions(options), new PosixParser());
        assertThat(commandLine.getOptionValue('g').trim()).isEqualTo("10.10.103.*,192.168.0.*");
        assertThat(commandLine.getOptionValue('c').trim()).isEqualTo("default-cluster");
    }
}
