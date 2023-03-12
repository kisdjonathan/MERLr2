package AST.operator.binary.variable;

import AST.abstractNode.SyntaxNode;

public class Field extends SyntaxNode {
    public static String symbol = "::";

    public Field() {}
    public Field(SyntaxNode context, SyntaxNode body) {
        addChild(context);
        addChild(body);
    }

}
