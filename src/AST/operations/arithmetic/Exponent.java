package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;
import interpreter.Value;

public class Exponent extends ArithmeticInfix{
    public Exponent(){}
    public Exponent(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "exp";
    }

    public Value interpretFloats(Value first, Value second) {
        double a = ((Numerical) first.getValue()).asDouble();
        double b = ((Numerical) second.getValue()).asDouble();
        return (a > 0 || (int) b == b) ? new Value(new Float(Math.pow(a, b))) : new Value(Float.ZERO);
        //TODO negative base
    }

    public Value interpretInts(Value first, Value second) {
        Int a = (Int)(first.getValue());
        Int b = (Int)(second.getValue());
        return new Value(new Int((int) Math.pow(a.getValue(), b.getValue())));
    }

}
