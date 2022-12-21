package AST.operations.variable;

import baseTypes.Signature;
import baseTypes.Tuple;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;
import AST.Function;
import AST.Variable;

public class Assign extends Operator {
    public Assign(){}
    public Assign(SyntaxNode dest, SyntaxNode value){
        setOrigin(dest);
        setVector(value);
    }

    public void evaluate() {
        super.evaluate();
        Variable dest = getVariable(getOrigin().getName());
        SyntaxNode source = getVector();
        if(dest.getDeclaredType().equals("inferred"))
            dest.setDeclaredType(source);
        else if(!dest.getDeclaredType().equals(source)) {
            Function conv = getFunction("convert", new Signature(Tuple.asTuple(source.getDeclaredType()), Tuple.asTuple(dest.getDeclaredType())));
            if(conv != null)
                setVector(new Call(conv, source));
            else if(source.typeConvertsTo(dest))
                setVector(new Cast(source, dest));
            else
                throw new Error("No conversion exists from " + source + " to " + dest);
        }
    }

    public String getName() {
        return "assign";
    }
}
