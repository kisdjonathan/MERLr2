package AST.variable;

import java.util.HashMap;
import java.util.Map;

/**
 * used when needing to replace a single variable
 */
public class SingleVariableTarget implements Locality {
    private final Map<String, VariableEntry> variables = new HashMap<>();

    public SingleVariableTarget(String name, VariableEntry target) {
        this.variables.put(name, target);
    }
    public SingleVariableTarget() {}

    public Map<String, VariableEntry> getVariables() {
        return variables;
    }
}