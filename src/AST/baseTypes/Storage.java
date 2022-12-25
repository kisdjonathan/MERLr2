package AST.baseTypes;

import AST.abstractNode.SyntaxNode;

import java.util.List;

//TODO define storage (ie does it only store values of the same type...)
public abstract class Storage extends BasicType {
    public String getName() {
        return "storage";
    }

    public List<SyntaxNode> getFields() {
        return null;    //TODO
    }

    public SyntaxNode getField(String name) {
        return null;    //TODO
    }
}
