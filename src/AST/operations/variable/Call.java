package AST.operations.variable;

import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.baseTypes.Tuple;
import AST.baseTypes.Function;
import AST.components.Signature;
import AST.components.Variable;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;
import AST.operations.bitwise.Not;

import java.util.Map;

//Call stores information of a function call
//TODO L for now, Call is expected to return the values of ret, but in the future, allow Call to write directly to ret
//TODO L allow calls on non-functions
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
        return new Call(getChild(0).clone(), getChild(1).clone());
    }

    public Function createFunction(SyntaxNode body) {
        Function ret = new Function();
        BasicType texplicit = getChild(0).getType();
        ret.setRets(texplicit == null ? body.getType() : texplicit);
        ret.setArgs(getChild(1));
        ret.addChild(body.clone());
        return ret;
    }

    @Override
    public Signature asVariable() {
        Variable var = getChild(0).asVariable();
        return new Signature(var.getName());
    }

    public void unifyVariables(Map<String, Variable> variables) {
        super.unifyVariables(variables);
        if(getChild(0) instanceof Variable var) {
            if (!(var instanceof Signature))
                variables.put(var.getName(), new Signature(var.getName()));
            Signature signature = (Signature) variables.get(var.getName());
            setChild(0, signature);
            if(!signature.hasOverload(Tuple.asTuple(getChild(1)), Tuple.asTuple(getType())))
                signature.addOverload(new Function(Tuple.asTuple(getChild(1)), Tuple.asTuple(getType())));
        }
    }

    public BasicType interpret() {
        Function f = ((Signature)getChild(0)).getOverload((Tuple)getChild(1), Tuple.asTuple(getType()));
        return f.interpretExecute((Tuple)getChild(1));
    }
}
