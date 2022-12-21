package AST.operations;

import AST.abstractNode.SyntaxNode;

public class With extends Operator {
    public With(){}
    public With(SyntaxNode val, SyntaxNode add){
        addChild(val); addChild(add);
    }

    public String getName() {
        return "with";
    }
}
