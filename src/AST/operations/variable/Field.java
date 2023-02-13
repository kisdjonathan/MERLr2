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
            Locality fieldLayer = new Locality.Inserted(varType, variables);

            SyntaxNode field = getChild(1);
            if(field instanceof Variable val){
                if(fieldLayer.hasVariable(val.getName())) {
                    val = fieldLayer.getVariable(val.getName());
                    setChild(1, val);
                }
                else
                    fieldLayer.putVariable(val.getName(), val);
            }
            else
                field.unifyVariables(fieldLayer);
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
    public boolean isVariable() {
        return true;
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
    public void setType(BasicType type) {
        getChild(1).setType(type);
    }

    public BasicType interpret() {
        return getChild(1).interpret();
    }
}
