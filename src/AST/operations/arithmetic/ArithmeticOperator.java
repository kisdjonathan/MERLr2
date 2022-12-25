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

    @Override
    public BasicType getType() {
        BasicType first = getChild(0).getType();
        BasicType second = size() > 1 ? getChild(1).getType() : null;
        if (first instanceof Int a && second instanceof Int b) {
            return new Int();
        } else if (first instanceof Float || second instanceof Float) {
            return new Float();
        } else {
            //TODO
            throw new Error("Type mismatch for operator " + getName() + "\nfirst:" + first + "\nsecond:"+second);
        }
    }

    @Override
    public BasicType interpret() {
        BasicType first = getChild(0).interpret();
        BasicType second = size() > 1 ? getChild(1).interpret() : null;
        BasicType outType = getType();

        if (outType instanceof Int) {
            return interpretInts((Numerical)first, (Numerical)second);
        } else if (outType instanceof Float) {
            return interpretFloats((Numerical)first, (Numerical)second);
        } else {
            //TODO
            return null;
        }
    }

    protected abstract Float interpretFloats(Numerical first, Numerical second);

    protected abstract Int interpretInts(Numerical first, Numerical second);


}
