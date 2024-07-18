package command;

import com.netease.lowcode.extension.command.CmdChains;
import com.netease.lowcode.extension.command.CmdConst;
import com.netease.lowcode.extension.command.Command;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class CommandTest {

    @Test
    public void execTest() {
        Command.exec(CmdChains.CAT_NATIVE_LOG_LAST(2));
    }

    @Test
    public void execSingleTest() {
        Command.exec(Arrays.asList(CmdConst.CD("~/logs"),CmdConst.PWD));
    }
}
