package AST.operations.variable;

import AST.baseTypes.BasicType;
import AST.components.Function;
import AST.components.Variable;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;

public class Assign extends Operator {
    public Assign(){}
    public Assign(SyntaxNode dest, SyntaxNode value){
        addChild(dest); addChild(value);
    }

    public String getName() {
        return "assign";
    }

    public BasicType getType() {
        return getChild(size() - 1).getType();
    }

    public BasicType interpret() {
        BasicType value = getChild(size() - 1).interpret();
        for(int i = 0; i < size()-1; ++i)
            getChild(i).setType(value);
        return value;
    }
}
