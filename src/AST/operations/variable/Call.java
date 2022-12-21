package AST.operations.variable;

import AST.baseTypes.InferredType;
import AST.baseTypes.Tuple;
import AST.components.Function;
import AST.operations.Operator;
import baseTypes.InferredType;
import baseTypes.Tuple;
import data.Usage;
import AST.abstractNode.SyntaxNode;
import AST.Function;

//Call stores information of a function call
//TODO L for now, Call is expected to return the values of ret, but in the future, allow Call to write directly to ret
//TODO L allow calls on non-functions
//TODO complete
public class Call extends Operator {
    //func should be a function when passed in (ie, not an identifier)
    public Call(SyntaxNode func, SyntaxNode args) {
        addChild(func);
        addChild(Tuple.asTuple(args));
        setDeclaredType(new InferredType());
    }

    public Call(SyntaxNode func, SyntaxNode args, SyntaxNode type) {
        addChild(func);
        addChild(Tuple.asTuple(args));
        setDeclaredType(type);
    }

    public Call(Function func, SyntaxNode args) {
        this((SyntaxNode)func, args);
        setDeclaredType(func.getDeclaredType());
    }

    public String  getUsage() {
        return "Call";
    }
    public String getName() {
        return "call";
    }
}
