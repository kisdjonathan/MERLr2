package AST.abstractNode;

import AST.variable.Locality;
import compiler.Assembly;
import interpreter.Value;
import type.InferredType;
import type.Type;
import AST.variable.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class SyntaxNode {
    private List<SyntaxNode> children = new ArrayList<>();
    public void setChildren(List<SyntaxNode> values) {
        children = values;
    }
    public List<SyntaxNode> getChildren() {
        return children;
    }
    public void addChild(SyntaxNode child) {
        children.add(child);
    }
    public void addChild(int index, SyntaxNode child) {
        children.add(index, child);
    }
    public void addChild(String descrip, SyntaxNode child) {
        addChild(child);
    }
    public SyntaxNode getChild(int index) {
        return children.get(index);
    }
    public void setChild(int index, SyntaxNode child) {
        children.set(index, child);
    }
    public SyntaxNode removeChild(int index) {
        return children.remove(index);
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
        throw new Error(errorString(this + " is not a variable"));
    }

    private Type type = new InferredType();
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
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


    /**
     * interpreter
     */
    public abstract Value interpret();

    /**
     * compiler
     */
    public abstract void compile(Assembly body);


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