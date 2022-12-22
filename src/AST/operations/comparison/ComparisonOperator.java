package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Bool;
import AST.baseTypes.Numerical;
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

    //TODO
    @Override
    public BasicType getType() {
        return new Bool();
    }

    @Override
    public BasicType interpret() {
        BasicType first = getChild(0).interpret();
        BasicType second = size() > 1 ?  getChild(1).interpret() : null;
        if (first instanceof Numerical f && second instanceof Numerical s) {
            return interpretNumerical(f, s);
        } else {
            return interpretOthers();
        }
    }

    protected abstract Bool interpretNumerical(Numerical first, Numerical second);

    //TODO
    protected abstract Bool interpretOthers();
}
