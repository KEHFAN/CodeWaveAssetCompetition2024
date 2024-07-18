package command;

import com.netease.lowcode.extension.command.CmdConst;
import com.netease.lowcode.extension.command.Command;
import org.junit.jupiter.api.Test;

public class CommandTest {

    @Test
    public void execTest() {
        Command.exec(CmdConst.PWD);
    }
}
