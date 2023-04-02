package AST.variable;

import java.util.HashMap;
import java.util.Map;

public interface Locality {
    Map<String, VariableEntry> getVariables();

    default boolean hasVariable(String name) {
        return getVariables().containsKey(name);
    }
    default VariableEntry getVariable(String name) {
        return getVariables().get(name);
    }
    default void putVariable(String name, VariableEntry var) {
        getVariables().put(name, var);
    }

    default Map<String, VariableEntry> getVariableClones() {
        Map<String, VariableEntry> ret = new HashMap<>();
        for(String varName : getVariables().keySet()) {
            ret.put(varName, getVariables().get(varName).clone());
        }
        return ret;
    }
}
