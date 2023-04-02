package AST.variable;

import java.util.HashMap;
import java.util.Map;

public class Layer implements Locality {
    private Locality parent = null;
    private final Map<String, VariableEntry> variables = new HashMap<>();

    public Layer(){}
    public Layer(Locality parent) {
        this.parent = parent;
    }

    public Map<String, VariableEntry> getVariables() {
        return variables;
    }

    public boolean hasVariable(String name) {
        return variables.containsKey(name) || parent != null && parent.hasVariable(name);
    }

    public VariableEntry getVariable(String name) {
        return (parent == null || variables.containsKey(name)) ? variables.get(name) : parent.getVariable(name);
    }
}