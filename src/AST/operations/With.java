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

    public With clone() {
        return new With( getChild(0).clone(), getChild(1).clone());
    }

    public BasicType getType() {
        return getChild(0).getType();
    }

    public BasicType interpret() {
        BasicType ret = getChild(0).interpret();
        getChild(1).interpret();
        return ret;
    }
}
