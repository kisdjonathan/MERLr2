package compiler;

import compiler.commands.AssemblyCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>Passing conventions for a single value:</h2>
 *  if a value has a variable size, it will be loaded with Register.DEFAULT holding the memory, and Register.Secondary holding the size <br/>
 *  if it is an int/float/byte/bool, it will be loaded in Register.DEFAULT <br/>
 *  otherwise it will be loaded with Register.DEFAULT holding the starting memory <br/>
 *
 * <h2>Passing conventions for several values:</h2>
 *  if all values are of fixed size, it will be loaded with Register.DEFAULT holding the starting memory <br/>
 *  otherwise it will be loaded with Register.DEFAULT holding the starting memory, and Register.Second holding the end of the memory,
 *      and the following memory will store the starting memories of each value <br/>
 *
 * <h2>Storing conventions for several values:</h2>
 *  All fixed sized values will be stored in a fixed memory block <br/>
 *  All variable sized values will have a fixed memory block storing the positions of each value <br/>
 */
public class Assembly {
    private final List<AssemblyCommand> commands = new ArrayList<>();
    private final List<LabeledBlock> blocks = new ArrayList<>();

    public void addCommand(AssemblyCommand cmd) {
        commands.add(cmd);
    }
    //TODO
}
