package AST.operations.variable;

import AST.baseTypes.Int;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;

public class Cardinal extends Operator {
    public Cardinal(){}
    public Cardinal(SyntaxNode value) {
        addChild(value);
    }

    public String getName() {
        return "cardinal";
    }

    public SyntaxNode getDeclaredType() {
        return new Int();
    }
}
