package AST.operations;

import AST.abstractNode.SyntaxNode;

public class Without extends With {
    public Without(){}
    public Without(SyntaxNode add, SyntaxNode val){
        addChild(val); addChild(add);
    }

}
