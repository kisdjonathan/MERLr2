package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Bool;
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
        return null;
    }

    @Override
    public BasicType interpret() {
        return null;
    }
}
