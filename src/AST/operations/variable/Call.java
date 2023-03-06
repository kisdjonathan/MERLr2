package AST.operations.variable;

import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.baseTypes.Tuple;
import AST.baseTypes.Function;
import AST.components.Locality;
import AST.variables.Signature;
import AST.variables.Variable;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;
import AST.variables.VariableEntry;

//Call stores information of a function call
//TODO L for now, Call is expected to return the values of ret, but in the future, allow Call to write directly to ret
//TODO complete
public class Call extends Operator {
    private BasicType type;

    //func should be a function when passed in (ie, not an identifier)
    public Call(SyntaxNode func, SyntaxNode args) {
        addChild(func);
        addChild(Tuple.asTuple(args));
        setType(new InferredType());
    }

    public Call(SyntaxNode func, SyntaxNode args, BasicType type) {
        addChild(func);
        addChild(Tuple.asTuple(args));
        setType(type);
    }

    public Call(Function func, SyntaxNode args) {
        this((SyntaxNode)func, Tuple.asTuple(args));
        setType(func.getRets().getType());
    }

    public void setType(BasicType type) {
        this.type = type;
    }

    public BasicType getType() {
        return type;
    }

    public String getUse() {
        return "Call";
    }
    public String getName() {
        return "call";
    }

    public Call clone() {
        Call ret = new Call(getChild(0).clone(), getChild(1).clone(), type);
        return ret;
    }

    protected BasicType bodyType(SyntaxNode body) {
        return body.getType();  //TODO check for returns
    }

    public Function createFunctionHeader(SyntaxNode body) {
        Function ret = new Function();
        BasicType texplicit = getChild(0).getType();
        ret.setRets(texplicit == null ? bodyType(body) : texplicit);
        ret.setArgs(getChild(1));
        return ret;
    }

    public Variable asVariable() {
        return getChild(0).asVariable();
    }

    public void unifyVariables(Locality variables) {
        super.unifyVariables(variables);

        SyntaxNode callee = getChild(0);
        if(callee instanceof Variable var) {
            VariableEntry signature = variables.getVariable(var.getName());
            if (!signature.hasOverload(Tuple.asTuple(getChild(1)), Tuple.asTuple(getType())))
                signature.addOverload(new Function(Tuple.asTuple(getChild(1)), Tuple.asTuple(getType())));
//            else
//                throw new Error("Conflicting overloads for " + callee + " passing " + new Function(Tuple.asTuple(getChild(1)), Tuple.asTuple(getType())));
        }
        else
            callee.setType(new Function(Tuple.asTuple(getChild(1)), Tuple.asTuple(getType())));
    }

    public BasicType interpret() {
        SyntaxNode callee = getChild(0);
        Function f = null;
        if(callee.isVariable()){
            VariableEntry var = callee.asVariable().getEntry();
            if(var.hasOverload()) {
                if (var.hasOverload((Tuple) getChild(1), Tuple.asTuple(getType())))
                    f = var.getOverload((Tuple) getChild(1), Tuple.asTuple(getType()));
                else
                    throw new Error("Attempting to call a function with signature: " +
                            getChild(1) + Tuple.asTuple(getType()) +
                            " but the function only has overloads defined for " +
                            var.getOverloads());
            }
        }

        if(f == null)
            f = (Function)getChild(0).interpret();
        return f.clone().interpretExecute(Tuple.asTuple(getChild(1).interpret()));
    }
}
