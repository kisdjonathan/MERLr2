package AST.operations;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;

public class With extends Operator {
    public With(){}
    public With(SyntaxNode val, SyntaxNode add){
        addChild(val); addChild(add);
    }

    public String getName() {
        return "with";
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
