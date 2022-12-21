package AST.operations.control;


import AST.abstractNode.SyntaxNode;

public class While extends Control {
    public While(SyntaxNode condition, SyntaxNode body) {
        setBase(condition, body);
    }
    public While(SyntaxNode condition, SyntaxNode body, SyntaxNode parent) {
        setParent(parent);
        setBase(condition, body);
    }
}
