package AST.components;

import AST.abstractNode.SyntaxNode;

public class Variable extends SyntaxNode {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
