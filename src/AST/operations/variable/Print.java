package AST.operations.variable;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.operations.Operator;

public class Print extends Operator {

    public Print(SyntaxNode child) {
        addChild(child);
    }

    public Print(){}
    @Override
    public BasicType getType() {
        return getChild(0).getType();
    }

    @Override
    public SyntaxNode clone() {
        return new Print(getChild(0).clone());
    }

    @Override
    public BasicType interpret() {
        System.out.println(getChild(0));
        return getChild(0).interpret();
    }

    @Override
    public String getName() {
        return "print";
    }
}
