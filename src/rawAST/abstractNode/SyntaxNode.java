package rawAST.abstractNode;

import completeAST.abstractNode.FunctionalNode;

public abstract class SyntaxNode {
    public abstract String getUse();
    public abstract String getName();
    public abstract int getSize();

    private SyntaxNode parent = null;
    public void setParent(SyntaxNode parent) {
        this.parent = parent;
    }
    public SyntaxNode getParent() {
        return parent;
    }

    public boolean isFunctionCall() {
        return false;
    }
    public boolean isFunctionHeader(){
        return false;
    }
    public boolean isFunctionSignature() {
        return false;
    }

    public boolean isUndefinedFunction(FunctionalNode parent) {
        return false;
    }
    public boolean isUndefinedVariable(FunctionalNode parent) {
        return false;
    }

    public abstract FunctionalNode evaluated();
}
