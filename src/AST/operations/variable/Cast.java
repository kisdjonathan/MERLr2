package AST.operations.variable;

import AST.abstractNode.SyntaxNode;
import AST.operations.Operator;

/**
 * Cast represents cases of conversions where from != to and from->to is not overridden
 */
public class Cast extends Operator {
    public Cast(){}
    public Cast(SyntaxNode from, SyntaxNode to) {
        setOrigin(from);
        setVector(to);
    }
    public Cast(SyntaxNode from, SyntaxNode to, SyntaxNode parent) {
        this(from, to);
        setParent(parent);
    }

    public String getName() {
        return "cast";
    }

    public BasicType getBaseType() {
        return getVector().getBaseType();
    }
    public boolean typeEquals(SyntaxNode other) {
        return getVector().typeEquals(other);
    }
}
