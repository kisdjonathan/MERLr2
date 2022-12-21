package AST.operations.arithmetic;

import AST.baseTypes.BasicType;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;
import interpreter.Context;
import interpreter.Value;

import java.util.Arrays;

public abstract class ArithmeticInfix extends Operator {
    private static SyntaxNode largerType(SyntaxNode a, SyntaxNode b) {
        //BasicType t1 = a.getBaseType(), t2 = b.getBaseType();
        //return t1.getByteSize().compareTo(t2.getByteSize()) > 0 ? a : b;
        return null;
    }

    public ArithmeticInfix() {}
    public ArithmeticInfix(SyntaxNode a, SyntaxNode b) {
        setChildren(Arrays.asList(a, b));
    }

    public SyntaxNode getDeclaredType() {
        return largerType(getChild(0), getChild(1));
    }

    @Override
    public BasicType getType() {
        return null;
    }

    @Override
    public Value interpret(Context context) {
        Value first = getChild(0).interpret(context);
        Value second = getChild(1).interpret(context);
        if (first.getValue() instanceof Int && second.getValue().getBaseType() instanceof Int) {
            return interpretInts(context);
        } else if (first.getValue().getBaseType() instanceof Float || second.getValue().getBaseType() instanceof Float) {
            return interpretFloats(context);
        } else {
            //TODO
            return null;
        }
    }

    protected abstract Value interpretFloats(Context context);

    protected abstract Value interpretInts(Context context);


}
