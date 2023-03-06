package AST.operations.variable;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.baseTypes.CustomType;
import AST.components.Locality;
import AST.variables.Variable;
import AST.operations.Operator;
import AST.variables.VariableEntry;

public class Construct extends Operator {
    public Construct(SyntaxNode variable, SyntaxNode body) {
        addChild(variable);
        addChild(body);
    }

    public void unifyVariables(Locality variables) {
        getChild(0).unifyVariables(variables);
        Variable var = getChild(0).asVariable();

        if(var.getType() instanceof InferredType) {
            Locality localLayer = new Locality.Layer(variables);

            SyntaxNode val = getChild(1);
            val.unifyVariables(localLayer); //TODO fields with name that are also defined outside of body

            var.setType(new CustomType(localLayer.getVariables()));
        }
        else {
            //extending the struct
            Locality localLayer = new Locality.Layer(variables);
            localLayer.getVariables().putAll(var.getType().getFields());

            SyntaxNode val = getChild(1);
            val.unifyVariables(localLayer); //TODO fields with name that are also defined outside of body

            var.getType().getFields().putAll(localLayer.getVariables());
        }
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
        getChild(1).interpret();
        return getChild(0).interpret();
    }

    public Construct clone() {
        return new Construct(getChild(0).clone(), getChild(1).clone());
    }
}
