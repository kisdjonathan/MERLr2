package AST.operations.variable;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.baseTypes.Structure;
import AST.components.Locality;
import AST.components.Variable;
import AST.operations.Operator;

import java.util.HashMap;
import java.util.Map;

public class Construct extends Operator {
    public Construct(SyntaxNode variable, SyntaxNode body) {
        addChild(variable);
        addChild(body);
    }

    public void unifyVariables(Locality variables) {
        Variable var = getChild(0).asVariable();
        if(!variables.hasVariable(var.getName()))
            variables.putVariable(var.getName(), var);
        else if(variables.getVariable(var.getName()).getType() instanceof InferredType)
            var = variables.getVariable(var.getName());
        else if(variables.getVariable(var.getName()).getType() instanceof Structure existingStruct) {
            existingStruct.extendBody(getChild(1), variables);
            return;
        }
        else
            throw new Error("attempting to restructure non-structured variable " + var);

        Locality localLayer = new Locality.Layer(variables);

        SyntaxNode val = getChild(1);
        val.unifyVariables(localLayer); //TODO fields with name also defined outside of body

        var.setType(new Structure(localLayer.getVariables()));
        variables.putVariable(var.getName(), var);
    }

    public String getName() {
        return "construct";
    }

    public Variable asVariable() {
        return getChild(0).asVariable();
    }
    public BasicType getType() {
        return getChild(0).getType();
    }
    public BasicType interpret() {
        return getChild(0).interpret();
    }

    public Construct clone() {
        return new Construct(getChild(0).clone(), getChild(1).clone());
    }
}
