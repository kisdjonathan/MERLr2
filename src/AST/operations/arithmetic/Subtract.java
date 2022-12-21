package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;
import interpreter.Value;
import AST.baseTypes.Float;

public class Subtract extends ArithmeticInfix {
    public Subtract(){}
    public Subtract(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    @Override
    protected Value interpretFloats(Value first, Value second) {
        double a = ((Float) first.getValue()).asDouble();
        double b = ((Float) second.getValue()).asDouble();
        return new Value(new Float(a - b));
    }

    public Value interpretInts(Value first, Value second) {
        Int a = (Int)(first.getValue());
        Int b = (Int)(second.getValue());
        return new Value(new Int(a.getValue() - b.getValue()));
    }

    public String getName() {
        return "sub";
    }
}
