package AST.operations.comparison;

import AST.operations.Operator;
import baseTypes.Bool;
import AST.abstractNode.SyntaxNode;
import operations.BuiltinOperation;

/**
 * represents operator infix that performs comparison
 */
public class ComparisonInfix extends Operator {
    public ComparisonInfix(){}
    public ComparisonInfix(SyntaxNode a, SyntaxNode b) {
        setOrigin(a);
        setVector(b);
    }

    public SyntaxNode getDeclaredType() {
        return new Bool();
    }
}
