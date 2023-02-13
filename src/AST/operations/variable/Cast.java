package AST.operations.variable;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.components.Locality;
import AST.components.Variable;
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
        if(getChild(1) instanceof Variable var) {
            if (variables.hasVariable(var.getName()))
                setChild(1, variables.getVariable(var.getName()));
            else {
                //throw new Error("Variable used without assignment:" + var.getName());
                var.setType(new InferredType());
                variables.putVariable(var.getName(), var);
            }
        }
        else
            getChild(1).unifyVariables(variables);


        if(getChild(0) instanceof Variable var) {
            if (variables.hasVariable(var.getName())) {
                var = variables.getVariable(var.getName());
                setChild(0, var);

                //TODO user-defined casting
                if(!var.getType().typeEquals(getType()))
                    throw new Error("Can not convert " + var + " to " + getType());
            }
            else {
                //throw new Error("Variable used without assignment:" + var.getName());
                var.setType(getType());
                variables.putVariable(var.getName(), var);
            }
        }
        else {
            //TODO user-defined casting
            getChild(0).setType(getType());
            getChild(0).unifyVariables(variables);
        }
    }

    public BasicType interpret() {
        return getChild(0).interpret();
    }
}
