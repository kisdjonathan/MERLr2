package AST.operations;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;

public class Without extends Operator {
    public Without(){}
    public Without(SyntaxNode add, SyntaxNode val){
        addChild(val); addChild(add);
    }

    public String getName() {
        return "then";
    }

    public Without clone() {
        return new Without( getChild(0).clone(), getChild(1).clone());
    }

    @Override
    public BasicType getType() {
        return getChild(1).getType();
    }

    @Override
    public BasicType interpret() {
        getChild(0).interpret();
        return getChild(1).interpret();
    }
}
