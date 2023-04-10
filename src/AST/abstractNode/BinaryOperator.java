package AST.abstractNode;

import compiler.Assembly;
import compiler.commands.Move;
import compiler.commands.Pop;
import compiler.commands.Push;
import compiler.commands.arithmetic.ArithmeticCommand;
import compiler.commands.arithmetic.ConvertIntToFloat;
import compiler.components.Register;
import interpreter.MultiValue;
import interpreter.Value;
import type.Tuple;
import type.Type;
import type.numerical.Numerical;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class BinaryOperator extends Operator{
    protected static Function<MultiValue, Value> from(BiFunction<Value,Value,Value>biFunction) {
        return (MultiValue mv) -> biFunction.apply(mv.getComponent(0), mv.getComponent(1));
    }



    /**
     * returns the compilation commands for a fundamental arithmetic command on numerical arguments using assemblyCommand as its operator
     * @param assemblyCommand must be a unique, non-reused instance of the command to be used. Will be modified during compilation
     * @return the compilation function
     */
    public static BiConsumer<Tuple, Assembly> partialCompileArithmetic(ArithmeticCommand assemblyCommand, boolean floatFirst, boolean floatSecond) {
        return (inputs, body) -> {
            Numerical first = (Numerical) inputs.getChild(0), second = (Numerical) inputs.getChild(1);
            int firstSize = first.getByteSize().getValue(), secondSize = second.getByteSize().getValue();
            assemblyCommand.setArgs(Register.SECONDARY, Register.DEFAULT);
            assemblyCommand.setSize(Math.max(firstSize, secondSize));

            first.compile(body);
            if(floatFirst)
                body.addCommand(new ConvertIntToFloat(Register.DEFAULT));
            body.addCommand(new Push(Register.SECONDARY, firstSize));
            body.addCommand(new Move(Register.DEFAULT, Register.SECONDARY, firstSize));
            second.compile(body);
            if(floatSecond)
                body.addCommand(new ConvertIntToFloat(Register.DEFAULT));
            body.addCommand(assemblyCommand);
            body.addCommand(new Pop(Register.SECONDARY, firstSize));
        };
    }
}
