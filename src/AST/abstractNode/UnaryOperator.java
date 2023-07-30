package AST.abstractNode;

import interpreter.MultiValue;
import interpreter.Value;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class UnaryOperator extends Operator {
    protected static Function<MultiValue, Value> from(Function<Value,Value> function) {
        return (MultiValue mv) -> function.apply(mv.getComponent(0));
    }
}
