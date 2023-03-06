package AST.operations.variable;

import AST.baseTypes.BasicType;
import AST.baseTypes.Function;
import AST.baseTypes.InferredType;
import AST.components.Locality;
import AST.variables.Signature;
import AST.variables.Variable;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;
import AST.variables.VariableEntry;

public class Declare extends Operator {
    private Function functionDeclaration = null;

    public Declare(){}
    public Declare(SyntaxNode dest, SyntaxNode value){
        addChild(dest); addChild(value);
    }

    public String getName() {
        return "declare";
    }

    public Declare clone() {
        Declare ret = new Declare();
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        return ret;
    }

    public BasicType getType() {
        return getChild(size() - 1).getType();
    }

    public void unifyVariables(Locality variables) {
        for(int i = size() - 2; i >= 0; --i) {
            if (getChild(i) instanceof Call call) {
                VariableEntry sig = call.asVariable().getEntry();

                Function f = call.createFunctionHeader(getChild(size() - 1));
                sig.addOverload(f);

                f.addChild(getChild(size() - 1));
                f.unifyVariables(variables);
                functionDeclaration = f;
            }
            else {
                Variable key = getChild(i).asVariable();
                VariableEntry var = key.getEntry();
                if(var.isConstant())
                    throw new Error("attempting to perform assignment on constant " + key);
                SyntaxNode val = getChild(size() - 1);
                val.unifyVariables(variables);

                var.setType(val.getType().clone());
                var.setConstant(true);
            }
        }
    }

    public Variable asVariable() {
        return getChild(0).asVariable();
    }

    public BasicType interpret() {
        if(functionDeclaration != null)
            return functionDeclaration;
        BasicType value = getChild(size() - 1).interpret();
        for(int i = 0; i < size()-1; ++i) {
            if (!getChild(i).asVariable().isConstant())
                throw new Error("attempting to assign variable " + getChild(i) + " to a constant value");
            else if(!getChild(i).getType().equals(value))
                throw new Error("attempting to assign constant " + getChild(i) + " to a new value " + value);
            else
                ((Variable)getChild(i)).getEntry().setValue(value);
        }
        return value;
    }
}
