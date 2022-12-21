package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;
import interpreter.Context;
import interpreter.Value;

public class Add extends ArithmeticInfix {
    public Add(){}
    public Add(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "add";
    }


    public Value interpretFloats(Context context) {
        double a = ((Numerical) getChild(0).interpret(context).getValue()).doubleOf();
        double b = ((Numerical) getChild(1).interpret(context).getValue()).doubleOf();
        return new Value(new Float(a + b));
    }

    public Value interpretInts(Context context) {
        Int a = (Int)(getChild(0).interpret(context).getValue());
        Int b = (Int)(getChild(1).interpret(context).getValue());
        return new Value(new Int(a.getValue() + b.getValue()));
    }

}
