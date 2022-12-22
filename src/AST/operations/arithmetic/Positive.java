package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;

public class Positive extends ArithmeticOperator {
    public Positive(){}
    public Positive(SyntaxNode value) {
        addChild(value);
    }

    public String getName() {
        return "positive";
    }

    public BasicType interpretFloats(Numerical first, Numerical second) {
        return new Float(first.asDouble());
    }

    public BasicType interpretInts(Numerical first, Numerical second) {
        return new Int(first.asInt());
    }
}
