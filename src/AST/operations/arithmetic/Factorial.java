package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Int;
import AST.operations.UnaryOperator;

import java.util.stream.IntStream;

public class Factorial extends UnaryOperator {
    public Factorial(){}
    public Factorial(SyntaxNode value) {
        addChild(value);
    }

    static {
        setEvaluation(new Int(), new Int(), x -> {
            if (x.getValue() < 0){
                throw new Error("Invalid integer argument: integer cannot be negative");
            }
            return new Int(IntStream.rangeClosed(1, Math.max(1, x.getValue())).reduce((a, b) -> a * b).getAsInt());
        });
    }

    public String getName() {
        return "factorial";
    }

    public Factorial clone() {
        return new Factorial(getChild(0).clone());
    }

}
