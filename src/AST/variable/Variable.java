package AST.variable;

import AST.abstractNode.SyntaxNode;

public class Variable extends SyntaxNode {
    private String name;
    private BasicType type = new InferredType();

    public Variable(String name) {
        this.name = name;
    }
    public Variable(String name, BasicType type) {
        this.name = name;
        this.type = type;
    }
}
