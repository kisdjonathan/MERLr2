package AST.baseTypes;

import AST.abstractNode.SyntaxNode;
import AST.components.Locality;
import AST.components.Signature;
import AST.components.Variable;
import AST.operations.variable.Call;

import java.util.HashMap;
import java.util.Map;

//TODO
public class Structure extends BasicType implements Locality {
    private Map<String, Variable> fields = new HashMap<>();

    public Map<String, Variable> getVariables() {
        return fields;
    }

    public Structure(SyntaxNode variable, SyntaxNode body) {
        addChild(variable);
        addChild(body);
    }

    public void unifyVariables(Map<String, Variable> variables) {
        Variable var = getChild(0).asVariable();
        if(!variables.containsKey(var.getName()))
            variables.put(var.getName(), var);
        if(variables.get(var.getName()).getType() instanceof InferredType)
            var = variables.get(var.getName());
        else
            throw new Error("attempting to restructure the existing variable " + variables.get(var.getName()));

        SyntaxNode val = getChild(size() - 1);
        val.unifyVariables(getVariables()); //TODO using local and global variables will conflict (ie a=b where a is the field and b is global) because global is not loaded yet
        val.unifyVariables(variables);  //TODO fields with name also defined outside of body

        var.setType(this);
        variables.put(var.getName(), var);
    }

    public String getName() {
        return "struct";
    }

    public Variable asVariable() {
        return getChild(0).asVariable();
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof Structure sother && getType().typeEquals(sother.getType());
    }
    public Structure clone() {
        return new Structure(getChild(0).clone(), getChild(1).clone());
    }

    public String toString() {
        return fields.toString();
    }
}
