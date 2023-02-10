package AST.baseTypes;

import AST.abstractNode.SyntaxNode;

import java.util.*;

public class InferredType extends BasicType{
    public String getName() {
        return "inferred";
    }

    //TODO

    private Map<String, SyntaxNode> fields = null;
    public SyntaxNode getField(String name) {
        return null;    //TODO
    }

    public InferredType clone() {
        return new InferredType();
    }
    public List<SyntaxNode> getFields() {
        return new ArrayList<>(fields.values());
    }

    public boolean typeEquals(BasicType other) {
        return true;
    }
    public boolean equals(Object other) {
        return other instanceof BasicType;
    }
}
