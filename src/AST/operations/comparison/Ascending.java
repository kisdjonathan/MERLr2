package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Bool;
import AST.baseTypes.Numerical;

public class Ascending extends ComparisonOperator {
    public Ascending() {}
    public Ascending(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    //TODO
    @Override
    protected Bool interpretNumerical(Numerical first, Numerical second) {
        return null;
    }

    @Override
    protected Bool interpretOthers() {
        return null;
    }

    public String getName() {
        return "greater";
    }
}
