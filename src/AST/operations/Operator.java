package AST.operations;

import AST.abstractNode.SyntaxNode;

import java.util.ArrayList;
import java.util.List;

public abstract class Operator extends SyntaxNode {
    public String getOperator(int index) {
        return null;
    }

    public abstract String getName();

    public String toString() {
        return getName() + getChildren();
    }
}

