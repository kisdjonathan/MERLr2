package AST.operations.variable;

import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.baseTypes.Tuple;
import AST.components.Function;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;

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
        this((SyntaxNode)func, args);
        setType(func.getType().getRets());
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

    public BasicType interpret() {
        return null;    //TODO
    }
}
