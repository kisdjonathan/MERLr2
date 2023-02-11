package AST.components;

import AST.abstractNode.SyntaxNode;

import java.util.HashMap;
import java.util.Map;

public interface Locality {
    Map<String, Variable> getVariables();

    default Variable getVariable(String name) {
        return getVariables().get(name);
    }
    default void putVariable(String name, Variable var) {
        getVariables().put(name, var);
    }

    default Map<String, Variable> getVariableClones() {
        Map<String, Variable> ret = new HashMap<>();
        for(String varName : getVariables().keySet()) {
            ret.put(varName, getVariables().get(varName).clone());
        }
        return ret;
    }

//    protected final Map<String, Variable> variables = new HashMap<>();
//
//    public Variable getVariable(String name) {
//        return variables.get(name);
//    }
//    public void putVariable(String name, Variable var) {
//        variables.put(name, var);
//    }

    /**
     * this is where variables and functions are added from the locality's children to its variable map
     */
//    public void unifyVariables(Map<String, Variable> variables) {
//        this.variables.putAll(variables);
//        super.unifyVariables(this.variables);
//    }
//
//    public Map<String, Variable> getVariableClones() {
//        Map<String, Variable> ret = new HashMap<>();
//        for(String varName : variables.keySet()) {
//            ret.put(varName, variables.get(varName).clone());
//        }
//        return ret;
//    }
}
