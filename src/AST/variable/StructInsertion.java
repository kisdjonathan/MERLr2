package AST.variable;

import AST.abstractNode.SyntaxNode;

import java.util.Map;

public class StructInsertion implements Locality {
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
