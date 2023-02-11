package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Int;
import AST.baseTypes.numerical.Numerical;
import AST.operations.Operator;

/**
 * represents operator infix that performs comparison
 */
public abstract class ComparisonOperator extends Operator {
    public ComparisonOperator(){}
    public ComparisonOperator(SyntaxNode a, SyntaxNode b) {
        addChild(a); addChild(b);
    }

    public SyntaxNode getDeclaredType() {
        return new Bool();
    }

    public BasicType getType() {
        return new Bool();
    }

    @Override
    public BasicType interpret() {
        BasicType first = getChild(0).interpret();
        BasicType second = size() > 1 ? getChild(1).interpret() : null;

        if (first instanceof Int && second instanceof Int) {
            return interpretInts((Numerical)first, (Numerical)second);
        } else if (first instanceof Numerical && second instanceof Numerical) {
            return interpretFloats((Numerical)first, (Numerical)second);
        } else {
            //TODO
            return null;
        }
    }

    protected abstract Bool interpretInts(Numerical first, Numerical second);
    protected abstract Bool interpretFloats(Numerical first, Numerical second);
}
