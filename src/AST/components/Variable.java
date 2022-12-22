package AST.components;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;

public class Variable extends SyntaxNode {
    private String name;
    private BasicType type;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BasicType getType() {
        return type;
    }
    public void setType(BasicType t) {
        type = t;
    }

    public BasicType interpret() {
        return type;
    }

    public String toString() {
        return type + " " + name;
    }
}
