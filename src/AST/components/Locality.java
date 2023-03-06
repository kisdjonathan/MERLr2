package AST.components;

import AST.abstractNode.SyntaxNode;
import AST.variables.RelativeVariableEntry;
import AST.variables.VariableEntry;

import java.util.HashMap;
import java.util.Map;

public interface Locality {
    class Layer implements Locality {
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
    class StructInsertion implements Locality {
        private Locality parent = null;
        private final SyntaxNode structParent;

        public StructInsertion(SyntaxNode structParent){
            this.structParent = structParent;
        }
        public StructInsertion(Locality parent, SyntaxNode structParent) {
            this.parent = parent;
            this.structParent = structParent;
        }

        public Map<String, VariableEntry> getVariables() {
            return structParent.getType().getFields();
        }

        public boolean hasVariable(String name) {
            return structParent.getType().hasField(name) ||
                    parent != null && parent.hasVariable(name);
        }
        public VariableEntry getVariable(String name) {
            return (parent == null || structParent.getType().hasField(name)) ?
                    new RelativeVariableEntry(name, structParent.asVariable().getEntry()) : parent.getVariable(name);
        }
        public void putVariable(String name, VariableEntry var) {
            structParent.getType().putField(name, var);
        }
    }
    class Wrapper implements Locality {
        private final Map<String, VariableEntry> variables = new HashMap<>();

        public Wrapper(Map<String, VariableEntry> vars) {
            this.variables.putAll(vars);
        }
        public Wrapper() {}

        public Map<String, VariableEntry> getVariables() {
            return variables;
        }
    }

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
