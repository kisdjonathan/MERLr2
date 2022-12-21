package AST.operations;

import AST.abstractNode.SyntaxNode;

public class Without extends With {
    public Without(){}

    public Without(SyntaxNode add, SyntaxNode val){
        setOrigin(add);
        setVector(val);
    }

}
