package AST.variables;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.components.Locality;

public class Variable extends SyntaxNode {
    private String name;
    private VariableEntry body = new VariableEntry();

    public Variable(String name) {
        this.name = name;
    }
    public Variable(String name, BasicType type) {
        this.name = name;
        body.setType(type);
    }
    public Variable(String name, VariableEntry body) {
        this.name = name;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public BasicType getType() {
        return body.getType();
    }
    public void setType(BasicType type) {
        body.setType(type);
    }

    public VariableEntry getEntry(){
        return body;
    }
    public void setEntry(VariableEntry body){
        this.body = body;
    }


    private boolean constant = false;
    public boolean isConstant() {
        return constant;
    }
    public void setConstant(boolean constant) {
        this.constant = constant;
    }



    public void unifyVariables(Locality variables) {
        if(!variables.hasVariable(getName())) {
            variables.putVariable(getName(), getEntry());
        }
        VariableEntry existing = variables.getVariable(getName());
        setEntry(existing);
    }
    public Variable clone() {
        return new Variable(name, body.clone());
    }

    public boolean isVariable() {
        return true;
    }
    public Variable asVariable() {
        return this;
    }

    public BasicType interpret() {
        return body.getValue().clone();
    }

    public String toString() {
        return body + " " + name;
    }
}
