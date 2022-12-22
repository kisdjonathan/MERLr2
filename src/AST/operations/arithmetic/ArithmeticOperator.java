package AST.operations.arithmetic;

import AST.baseTypes.BasicType;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;

import java.util.Arrays;

public abstract class ArithmeticOperator extends Operator {
    private static SyntaxNode largerType(SyntaxNode a, SyntaxNode b) {
        //BasicType t1 = a.getBaseType(), t2 = b.getBaseType();
        //return t1.getByteSize().compareTo(t2.getByteSize()) > 0 ? a : b;
        return null;
    }

    public ArithmeticOperator() {}
    public ArithmeticOperator(SyntaxNode a, SyntaxNode b) {
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
    public BasicType interpret() {
        BasicType first = getChild(0).interpret();
        BasicType second = size() > 1 ? getChild(1).interpret() : null;
        if (first instanceof Int a && second instanceof Int b) {
            return interpretInts(a, b);
        } else if (first instanceof Float || second instanceof Float) {
            return interpretFloats((Numerical)first, (Numerical)second);
        } else {
            //TODO
            return null;
        }
    }

    protected abstract BasicType interpretFloats(Numerical first, Numerical second);

    protected abstract BasicType interpretInts(Numerical first, Numerical second);


}
