package AST.abstractNode;

import interpreter.Value;
import type.Type;
import AST.variable.Variable;

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
     * gets all variables to reference the same entry
     * @param variables manages the locally stored variables and other accessible variables
     */
    public void unifyVariables(Locality variables){
        for(int i = 0; i < size(); ++i)
            getChild(i).unifyVariables(variables);
    }

    /**
     * determines the types of all nodes
     */
    public void unifyTypes() {
        for(int i = 0; i < size(); ++i)
            getChild(i).unifyTypes();
    }

    public boolean isVariable() {
        return false;
    }
    public Variable asVariable() {
        throw new Error(this + " is not a variable");
    }

    public abstract Type getType();


    public void setType(Type type) {
        throw new Error("can not set type for " + this);
    }
    public boolean assertType(Type type) {
        if(getType().isInferred()) {
            setType(type);
            return true;
        }
        else
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

    public abstract Value interpret();


    /**
     * error handling
     */
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