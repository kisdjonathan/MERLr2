package AST.operations.variable;

import baseTypes.InferredType;
import baseTypes.Tuple;
import data.Usage;
import AST.abstractNode.SyntaxNode;
import AST.Function;

//Call stores information of a function call
//TODO L for now, Call is expected to return the values of ret, but in the future, allow Call to write directly to ret
//TODO L allow calls on non-functions
//TODO complete
public class Call extends operations.BuiltinOperation {
    //func should be a function when passed in (ie, not an identifier)
    public Call(SyntaxNode func, SyntaxNode args) {
        setOrigin(func);
        setVector(Tuple.asTuple(args));
        setDeclaredType(new InferredType());
    }

    public Call(SyntaxNode func, SyntaxNode args, SyntaxNode type) {
        setOrigin(func);
        setVector(Tuple.asTuple(args));
        setDeclaredType(type);
    }

    public Call(Function func, SyntaxNode args) {
        this((SyntaxNode)func, args);
        setDeclaredType(func.getDeclaredType());
    }

    public Usage getUsage() {
        return Usage.CALL;
    }
    public String getName() {
        return "call";
    }
}
