package AST.operations.variable;

import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.baseTypes.Structure;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;
import AST.components.*;

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
            Locality fieldLayer = new Locality.StructInsertion(varType, variables, var);

            SyntaxNode field = getChild(1);
            if(field instanceof Variable val){
                if(fieldLayer.hasVariable(val.getName())) {
                    //getChild(1).setType(fieldLayer.getVariable(val.getName()).getType());
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
        Structure struct = (Structure)getChild(0).getType();
        return struct.getVariable(getChild(1).asVariable().getName());
    }
    public boolean isVariable() {
        return getChild(1).isVariable();
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
//        Structure struct = (Structure)getChild(0).interpret();
//        Variable var = struct.getVariable(getChild(1).asVariable().getName());
//        return var.interpret();
        return getChild(1).interpret();
    }
}
