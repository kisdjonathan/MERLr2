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
import type.numerical.Int;
import type.numerical.Numerical;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class BinaryOperator extends Operator{
    protected static Function<MultiValue, Value> from(BiFunction<Value,Value,Value>biFunction) {
        return (MultiValue mv) -> biFunction.apply(mv.getComponent(0), mv.getComponent(1));
    }

}
