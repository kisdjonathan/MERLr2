package AST.operations.control;

import AST.abstractNode.SyntaxNode;

public class Repeat extends Control {
    public Repeat(SyntaxNode count, SyntaxNode body) {
        setBase(count, body);
    }
}
