package AST.operations.control;

import AST.abstractNode.SyntaxNode;

public class If extends Control {
    public If(SyntaxNode condition, SyntaxNode body) {
        setBase(condition, body);
    }
}
