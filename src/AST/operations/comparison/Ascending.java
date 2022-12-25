package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Bool;
import AST.baseTypes.Numerical;
import AST.operations.bitwise.Not;

public class Ascending extends ComparisonOperator {
    public Ascending() {}
    public Ascending(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public Ascending clone() {
        Ascending ret = new Ascending();
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        ret.setOperators(getOperators());
        return ret;
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
