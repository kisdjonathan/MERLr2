package AST.components;

import AST.abstractNode.SyntaxNode;

import java.util.HashMap;
import java.util.Map;

public interface Locality {
    class Layer implements Locality {
        private Locality parent = null;
        private final Map<String, Variable> variables = new HashMap<>();

        public Layer(){}
        public Layer(Locality parent) {
            this.parent = parent;
        }

        public Map<String, Variable> getVariables() {
            return variables;
        }

        public boolean hasVariable(String name) {
            return variables.containsKey(name) || parent != null && parent.hasVariable(name);
        }

        public Variable getVariable(String name) {
            return (parent == null || variables.containsKey(name)) ? variables.get(name) : parent.getVariable(name);
        }
    }
    class StructInsertion implements Locality {
        private Locality parent = null, insertion;
        private SyntaxNode structParent;

        public StructInsertion(Locality insertion, SyntaxNode structParent){
            this.insertion = insertion;
            this.structParent = structParent;
        }
        public StructInsertion(Locality insertion, Locality parent, SyntaxNode structParent) {
            this.parent = parent;
            this.insertion = insertion;
            this.structParent = structParent;
        }

        public Map<String, Variable> getVariables() {
            return insertion.getVariables();
        }

        public boolean hasVariable(String name) {
            return insertion.hasVariable(name) || parent != null && parent.hasVariable(name);
        }
        public Variable getVariable(String name) {
            return (parent == null || insertion.hasVariable(name)) ? insertion.getVariable(name) : parent.getVariable(name);
        }
        public void putVariable(String name, Variable var) {
            Locality.super.putVariable(name, new RelativeVariable(var, structParent));
        }
    }
    class Wrapper implements Locality {
        private final Map<String, Variable> variables = new HashMap<>();

        public Wrapper(Map<String, Variable> vars) {
            this.variables.putAll(vars);
        }
        public Wrapper() {}

        public Map<String, Variable> getVariables() {
            return variables;
        }
    }

    Map<String, Variable> getVariables();

    default boolean hasVariable(String name) {
        return getVariables().containsKey(name);
    }
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
}
