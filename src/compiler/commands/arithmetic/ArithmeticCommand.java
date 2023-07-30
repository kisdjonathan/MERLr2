package compiler.commands.arithmetic;

import compiler.commands.AssemblyCommand;
import compiler.components.AssemblyArgument;
import compiler.components.Register;
import compiler.components.Size;

/**
 * applies an arithmetic operation between arg/register, register/arg
 * or register/register and stores it in the second one provided
 */
public abstract class ArithmeticCommand extends AssemblyCommand {
    //TODO setArgs(Register register, Memory mem)
    public void setArgs(AssemblyArgument arg, Register register) {
        //TODO
    }

    public void setSize(Size size) {
        //TODO
    }
}
