package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Numerical;

public class NoLesser extends ComparisonOperator{
    public NoLesser(){}
    public NoLesser(SyntaxNode a, SyntaxNode b) {
        addChild(a);
        addChild(b);
    }

    public SyntaxNode clone() {
        return new NoLesser(getChild(0),getChild(1));
    }

    public String getName() {
        return "not lesser";
    }

    protected Bool interpretInts(Numerical first, Numerical second) {
        return new Bool(first.asInt() >= second.asInt());
    }
    protected Bool interpretFloats(Numerical first, Numerical second) {
        return new Bool(first.asDouble() >= second.asDouble());
    }
    protected Bool interpretOthers() {
        return null;
    }
}
