package AST.components;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;

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

    public String getName() {
        return name;
    }

    public BasicType getType() {
        return type;
    }
    public void setType(BasicType t) {
        type = t;
    }


    private boolean constant = false;
    public boolean isConstant() {
        return constant;
    }
    public void setConstant(boolean constant) {
        this.constant = constant;
    }


    public Variable clone() {
        return new Variable(name, type.clone());
    }

    public Variable asVariable() {
        return this;
    }

    public BasicType interpret() {
        return type.clone();
    }

    public String toString() {
        return type + " " + name;
    }
}
