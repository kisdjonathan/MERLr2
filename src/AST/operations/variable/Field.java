package AST.operations.variable;

import AST.baseTypes.BasicType;
import AST.baseTypes.Function;
import AST.baseTypes.InferredType;
import AST.baseTypes.Structure;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;
import AST.components.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//AST.Contextualization simply makes the fields of a variable available to its children
public class Field extends Operator {
    public Field() {}
    public Field(SyntaxNode context, SyntaxNode body) {
        addChild(context);
        addChild(body);
    }

    public void unifyVariables(Locality variables) {
        SyntaxNode potentialVar = getChild(0);
        if(potentialVar instanceof Variable var) {
            if(variables.hasVariable(var.getName())) {
                var = variables.getVariable(var.getName());
                setChild(0, var);
            }
            else
                variables.putVariable(var.getName(), var);

            if(var.getType() instanceof InferredType)
                var.setType(new Structure());
            else if(!(var.getType() instanceof Structure))
                throw new Error("Attempting to access field from non-structure " + var);

            Structure varType = (Structure)var.getType();

            SyntaxNode field = getChild(size() - 1);
            if(field instanceof Variable val){
                if(varType.hasVariable(val.getName())) {
                    val = varType.getVariable(val.getName());
                    setChild(size() - 1, val);
                }
                else
                    varType.putVariable(val.getName(), val);
            }
            else
                field.unifyVariables(varType);
        }
        else {
            //TODO assert field in the type of potentialVar
            //ie could be function
            //ie could be another field
            throw new Error("Field of non-variable is not implemented");
        }
    }

    public Variable asVariable() {
        return getChild(1).asVariable();
    }

    public String getName() {
        return "field";
    }

    public Field clone() {
        return new Field(getChild(0).clone(), getChild(1).clone());
    }

    public BasicType getType() {
        return getChild(1).getType();
    }

    public BasicType interpret() {
        return getChild(1).interpret();
    }
}
