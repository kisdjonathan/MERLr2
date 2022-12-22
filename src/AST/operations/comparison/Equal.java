package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Bool;
import AST.baseTypes.Numerical;

public class Equal extends ComparisonOperator {
    public Equal() {}
    public Equal(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    //TODO
    @Override
    protected Bool interpretNumerical(Numerical first, Numerical second) {
        return new Bool(first.asDouble() == second.asDouble());
    }

    @Override
    protected Bool interpretOthers() {
        return null;
    }

    public String getName() {
        return "equal";
    }
}
