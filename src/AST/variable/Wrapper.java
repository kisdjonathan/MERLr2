package AST.variable;

import java.util.HashMap;
import java.util.Map;

public class Wrapper implements Locality {
    private final Map<String, VariableEntry> variables = new HashMap<>();

    public Wrapper(Map<String, VariableEntry> vars) {
        this.variables.putAll(vars);
    }
    public Wrapper() {}

    public Map<String, VariableEntry> getVariables() {
        return variables;
    }
}
