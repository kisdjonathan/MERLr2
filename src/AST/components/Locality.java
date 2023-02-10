package AST.components;

import AST.abstractNode.SyntaxNode;

import java.util.HashMap;
import java.util.Map;

public abstract class Locality extends SyntaxNode {
    private final Map<String, Variable> variables = new HashMap<>();

    public Variable getVariable(String name) {
        return variables.get(name);
    }
    public void putVariable(String name, Variable var) {
        variables.put(name, var);
    }

    /**
     * this is where variables and functions are added from the locality's children to its variable map
     */
    public void unifyVariables(Map<String, Variable> variables) {
        Map<String, Variable> temp = new HashMap<>(this.variables);
        this.variables.putAll(variables);
        this.variables.putAll(temp);
        super.unifyVariables(this.variables);
    }

    protected void unifyVariables() {
        super.unifyVariables(variables);
    }
}
