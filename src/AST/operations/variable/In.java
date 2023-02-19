package AST.operations.variable;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.operations.Operator;

public class In extends Operator {
    public In(){}

    public In(SyntaxNode value, SyntaxNode iterator){
        addChild(value);
        addChild(iterator);
    }

    public String getName() {
        return "in";
    }

    public In clone() {
        return new In(getChild(0).clone(), getChild(1).clone());
    }

    @Override
    public BasicType getType() {
        return null;
    }

    @Override
    public BasicType interpret() {
        return null;
    }
}
