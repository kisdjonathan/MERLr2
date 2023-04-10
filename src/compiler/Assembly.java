package compiler;

import compiler.commands.AssemblyCommand;

import java.util.ArrayList;
import java.util.List;

public class Assembly {
    private final List<AssemblyCommand> commands = new ArrayList<>();
    private final List<LabeledBlock> blocks = new ArrayList<>();

    public void addCommand(AssemblyCommand cmd) {
        commands.add(cmd);
    }
    //TODO
}
