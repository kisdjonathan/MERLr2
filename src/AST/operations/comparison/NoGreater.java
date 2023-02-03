package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Bool;
import AST.baseTypes.Numerical;

public class NoGreater extends ComparisonOperator{
    public NoGreater(){}
    public NoGreater(SyntaxNode a, SyntaxNode b) {
        addChild(a);
        addChild(b);
    }

    public SyntaxNode clone() {
        return new NoGreater(getChild(0),getChild(1));
    }

    public String getName() {
        return "not greater";
    }

    protected Bool interpretInts(Numerical first, Numerical second) {
        return new Bool(first.asInt() <= second.asInt());
    }
    protected Bool interpretFloats(Numerical first, Numerical second) {
        return new Bool(first.asDouble() <= second.asDouble());
    }
    protected Bool interpretOthers() {
        return null;
    }
}
