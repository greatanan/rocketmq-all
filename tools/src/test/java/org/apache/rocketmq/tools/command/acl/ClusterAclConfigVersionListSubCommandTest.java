
package org.apache.rocketmq.tools.command.acl;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.rocketmq.srvutil.ServerUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClusterAclConfigVersionListSubCommandTest {

    @Test
    public void testExecute() {
        ClusterAclConfigVersionListSubCommand cmd = new ClusterAclConfigVersionListSubCommand();
        Options options = ServerUtil.buildCommandlineOptions(new Options());
        String[] subargs = new String[] {"-c default-cluster"};
        final CommandLine commandLine =
            ServerUtil.parseCmdLine("mqadmin " + cmd.commandName(), subargs, cmd.buildCommandlineOptions(options), new PosixParser());
        assertThat(commandLine.getOptionValue('c').trim()).isEqualTo("default-cluster");
    }
}
