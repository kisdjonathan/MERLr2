package AST.operations.variable;

import AST.baseTypes.BasicType;
import AST.baseTypes.Function;
import AST.baseTypes.InferredType;
import AST.components.Locality;
import AST.components.Signature;
import AST.components.Variable;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;

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
                Signature sig = call.asVariable();
                if(!variables.hasVariable(sig.getName()) || !(variables.getVariable(sig.getName()) instanceof Signature))
                    variables.putVariable(sig.getName(), sig);
                else
                    sig = (Signature) variables.getVariable(sig.getName());

                Function f = call.createFunctionHeader(getChild(size() - 1));
                sig.addOverload(f);

                f.addChild(getChild(size() - 1));
                f.unifyVariables(variables);
                functionDeclaration = f;
            }
            else {
                Variable var = getChild(i).asVariable();
                if(variables.getVariable(var.getName()).getType() instanceof InferredType)
                    var = variables.getVariable(var.getName());
                else if(variables.hasVariable(var.getName()))
                    throw new Error("attempting to perform constant assignment " + getChild(size() - 1) + " on defined " + variables.getVariable(var.getName()));
                SyntaxNode val = getChild(size() - 1);
                val.unifyVariables(variables);
                var.setType(val.getType().clone());
                var.setConstant(true);
                variables.putVariable(var.getName(), var);
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
                getChild(i).setType(value);
        }
        return value;
    }
}
