package AST.operations.variable;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.components.Locality;
import AST.variables.Variable;
import AST.operations.Operator;

/**
 * Cast represents cases of conversions where from != to and from->to is not overridden
 */
public class Cast extends Operator {
    public Cast(){}
    public Cast(SyntaxNode from, SyntaxNode to) {
        addChild(from); addChild(to);
    }

    public String getName() {
        return "cast";
    }

    public Cast clone() {
        return new Cast(getChild(0).clone(), getChild(1).clone());
    }

    public BasicType getType() {
        return getChild(1).getType();
    }
    public void setType(BasicType type) {
        if(size() == 1)
            addChild(type);
        else if(!getChild(1).getType().typeEquals(type))
            throw new Error("Can not set type of cast statement " + this);
    }

    public void unifyVariables(Locality variables) {
        getChild(1).unifyVariables(variables);

        getChild(0).setType(getType());
        getChild(0).unifyVariables(variables);
    }

    public BasicType interpret() {
        return getChild(0).interpret();
    }
}
