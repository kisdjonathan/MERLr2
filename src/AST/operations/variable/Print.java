package AST.operations.variable;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.components.Locality;
import AST.operations.Operator;

public class Print extends Operator {

    public Print(){}

    public Print(SyntaxNode message) {
        addChild(message);
    }

    @Override
    public BasicType getType() {
        return getChild(0).getType();
    }

    @Override
    public SyntaxNode clone() {
        return new Print(getChild(0).clone());
    }

    @Override
    public void unifyVariables(Locality variables) {
        super.unifyVariables(variables);
    }

    @Override
    public BasicType interpret() {
        BasicType b = getChild(0).interpret();
        System.out.println(b.valueString());
        return b;
    }

    @Override
    public String getName() {
        return "print";
    }
}
