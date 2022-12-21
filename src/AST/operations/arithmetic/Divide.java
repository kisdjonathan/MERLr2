package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;
import interpreter.Value;

public class Divide extends ArithmeticInfix {
    public Divide(){}
    public Divide(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "div";
    }

    public Value interpretFloats(Value first, Value second) {
        double a = ((Numerical) first.getValue()).asDouble();
        double b = ((Numerical) second.getValue()).asDouble();
        return (b != 0) ? new Value(new Float(a / b)) : new Value(Float.ZERO);
        //TODO div by 0
    }

    public Value interpretInts(Value first, Value second) {
        Int a = (Int)(first.getValue());
        Int b = (Int)(second.getValue());
        return (b.getValue() != 0) ? new Value(new Int(a.getValue() / b.getValue())) : new Value(Int.ZERO);
        //TODO div by 0
    }

}
