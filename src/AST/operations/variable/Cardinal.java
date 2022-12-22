package AST.operations.variable;

import AST.baseTypes.BasicType;
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

    public BasicType getType() {
        return new Int();
    }

    //TODO
    public BasicType interpret() {
        return null;
    }
}
