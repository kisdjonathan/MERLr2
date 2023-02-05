package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Numerical;

public class Lesser extends ComparisonOperator{
    public Lesser(){}
    public Lesser(SyntaxNode a, SyntaxNode b) {
        addChild(a);
        addChild(b);
    }

    public SyntaxNode clone() {
        return new Lesser(getChild(0),getChild(1));
    }

    public String getName() {
        return "lesser";
    }

    protected Bool interpretInts(Numerical first, Numerical second) {
        return new Bool(first.asInt() < second.asInt());
    }
    protected Bool interpretFloats(Numerical first, Numerical second) {
        return new Bool(first.asDouble() < second.asDouble());
    }
    protected Bool interpretOthers() {
        return null;
    }
}
