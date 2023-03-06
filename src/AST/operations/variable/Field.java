package AST.operations.variable;

import AST.baseTypes.BasicType;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;
import AST.components.*;
import AST.variables.Variable;

//AST.Contextualization simply makes the fields of a variable available to its children
public class Field extends Operator {
    public Field() {}
    public Field(SyntaxNode context, SyntaxNode body) {
        addChild(context);
        addChild(body);
    }

    public void unifyVariables(Locality variables) {
        SyntaxNode parent = getChild(0);
        parent.unifyVariables(variables);

        Locality fieldLayer = new Locality.StructInsertion(variables, parent);

        SyntaxNode field = getChild(1);
        field.unifyVariables(fieldLayer);
    }

    public Variable asVariable() {
        return getChild(1).asVariable();
    }
    public boolean isVariable() {
        return getChild(1).isVariable();
    }

    public String getName() {
        return "field";
    }

    public Field clone() {
        return new Field(getChild(0).clone(), getChild(1).clone());
    }


    public BasicType getType() {
        return getChild(1).getType();
    }
    public void setType(BasicType type) {
        getChild(1).setType(type);
    }

    public BasicType interpret() {
//        Structure struct = (Structure)getChild(0).interpret();
//        Variable var = struct.getVariable(getChild(1).asVariable().getName());
//        return var.interpret();
        return getChild(1).interpret();
    }
}
