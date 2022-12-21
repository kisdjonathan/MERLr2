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


    public Value interpretFloats(Value first, Value second) {
        double a = ((Numerical) first.getValue()).asDouble();
        double b = ((Numerical) second.getValue()).asDouble();
        return new Value(new Float(a + b));
    }

    public Value interpretInts(Value first, Value second) {
        Int a = (Int)(first.getValue());
        Int b = (Int)(second.getValue());
        return new Value(new Int(a.getValue() + b.getValue()));
    }

}
