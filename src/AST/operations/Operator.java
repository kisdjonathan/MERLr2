package AST.operations;

import AST.abstractNode.SyntaxNode;

import java.util.ArrayList;
import java.util.List;

public abstract class Operator extends SyntaxNode {
    private List<String> operators = new ArrayList<>();

    public void addChild(String op, SyntaxNode val) {
        super.addChild(op, val);
        operators.add(op);
    }
    public String getOperator(int index) {
        return operators.get(index);
    }
}
