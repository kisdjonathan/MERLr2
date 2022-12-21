package AST.operations;

import AST.abstractNode.SyntaxNode;

import java.util.ArrayList;
import java.util.List;

public abstract class Operator extends SyntaxNode {
    private List<SyntaxNode> operands = new ArrayList<>();

    public void addChild(String op, SyntaxNode val) {
        operands.add(val);
    }
    public SyntaxNode getChild(int index) {
        return operands.get(index);
    }
}
