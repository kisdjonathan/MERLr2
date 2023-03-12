package AST.abstractNode;

import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.components.Locality;
import AST.components.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class SyntaxNode {
    private SyntaxNode parent;
    public SyntaxNode getParent() {
        return parent;
    }
    public void setParent(SyntaxNode parent) {
        this.parent = parent;
    }

    private List<SyntaxNode> children = new ArrayList<>();
    public void setChildren(List<SyntaxNode> values) {
        children = values;
    }
    public List<SyntaxNode> getChildren() {
        return children;
    }
    public void addChild(SyntaxNode child) {
        child.setParent(this);
        children.add(child);
    }
    public void addChild(int index, SyntaxNode child) {
        child.setParent(this);
        children.add(index, child);
    }
    public void addChild(String descrip, SyntaxNode child) {
        addChild(child);
    }
    public SyntaxNode getChild(int index) {
        return children.get(index);
    }
    public void setChild(int index, SyntaxNode child) {
        child.setParent(this);
        children.set(index, child);
    }
    public SyntaxNode removeChild(int index) {
        SyntaxNode ret = children.remove(index);
        ret.setParent(null);
        return ret;
    }
    public int size() {
        return children.size();
    }

    /**
     * implemented in syntaxNodes that store children
     * stops at children locals and calls their unifyVariables function
     * @param variables manages the locally stored variables and other accessible variables
     */
    public void unifyVariables(Locality variables){
        for(int i = 0; i < size(); ++i) {
            if(getChild(i) instanceof Variable var) {
                if (variables.hasVariable(var.getName()))
                    setChild(i, variables.getVariable(var.getName()));
                else {
                    //throw new Error("Variable used without assignment:" + var.getName());
                    var.setType(new InferredType());
                    variables.putVariable(var.getName(), var);
                }
            }
            else
                getChild(i).unifyVariables(variables);
        }
    }

    public boolean isVariable() {
        return false;
    }
    public Variable asVariable() {
        throw new Error(this + " is not a variable");
    }

    public abstract BasicType getType();

    public void setType(BasicType type) {
        throw new Error("can not set type for " + this);
    }
    public boolean assertType(BasicType type) {
        return getType().typeEquals(type);
    }

    public SyntaxNode clone() {
        SyntaxNode ret = emptyClone();
        for (SyntaxNode child : getChildren()) {
            ret.addChild(child.clone());
        }
        ret.init(this);
        return ret;
    }
    public void init(SyntaxNode original){}
    public abstract SyntaxNode emptyClone();
    public final SyntaxNode with(Consumer<SyntaxNode> action) {
        action.accept(this);
        return this;
    }

    public abstract BasicType interpret();


    private int line;
    public void setLine(int line) {
        this.line = line;
    }
    public String errorString() {
        return "Error at line " + line + ": " + this;
    }
    public String errorString(String message) {
        return "Error at line " + line + ": " + message;
    }
}