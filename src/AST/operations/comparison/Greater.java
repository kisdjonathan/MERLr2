package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Bool;
import AST.baseTypes.Numerical;

public class Greater extends ComparisonOperator{
    public Greater(){}
    public Greater(SyntaxNode a, SyntaxNode b) {
        addChild(a);
        addChild(b);
    }

    public SyntaxNode clone() {
        return new Greater(getChild(0),getChild(1));
    }

    public String getName() {
        return "greater";
    }

    protected Bool interpretInts(Numerical first, Numerical second) {
        return new Bool(first.asInt() > second.asInt());
    }
    protected Bool interpretFloats(Numerical first, Numerical second) {
        return new Bool(first.asDouble() > second.asDouble());
    }
    protected Bool interpretOthers() {
        return null;
    }
}
