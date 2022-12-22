package AST.components;

import AST.abstractNode.SyntaxNode;

import java.util.HashMap;
import java.util.Map;

public abstract class Locality extends SyntaxNode {
    private final Map<String, Variable> variables = new HashMap<>();

    public Variable getVariable(String name) {
        return variables.get(name);
    }

    /**
     * this is where variables and functions are added from the locality's children to its variable map
     */
    public void unifyVariables() {
        unifyVariables(variables);
    }
    public void unifyVariables(Map<String, Variable> variables) {
        Map<String, Variable> currentVariables = new HashMap<>(variables);
        currentVariables.putAll(variables);
        super.unifyVariables(currentVariables);
    }
}
