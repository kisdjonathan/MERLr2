package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.*;
import AST.baseTypes.Float;
import AST.operations.BinaryOperator;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Multiply extends BinaryOperator {
    public Multiply(){}
    public Multiply(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("mul");
        setEvaluation("mul", new Int(), new Int(), new Int(), (x, y) -> new Int(x.asInt() * y.asInt()));
        setEvaluation("mul", new Float(), new Int(), new Float(), (x, y) -> new Float(x.asDouble() * y.asDouble()));
        setEvaluation("mul", new Int(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() * y.asDouble()));
        setEvaluation("mul", new Float(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() * y.asDouble()));
        setEvaluation("mul", new Str(), new Int(), new Str(), (x, y) -> {
            String s = x.getValue();
            if (y.getValue() < 0) {
                s = new StringBuilder(s).reverse().toString();
            }
            String finalS = s;
            return new Str(IntStream.range(0, Math.abs(y.getValue())).mapToObj(i -> finalS).collect(Collectors.joining()));
        });
        setEvaluation("mul", new Int(), new Str(), new Str(), (x, y) -> {
            String s = y.getValue();
            if (x.getValue() < 0) {
                s = new StringBuilder(s).reverse().toString();
            }
            String finalS = s;
            return new Str(IntStream.range(0, Math.abs(x.getValue())).mapToObj(i -> finalS).collect(Collectors.joining()));
        });
    }

    public String getName() {
        return "mul";
    }

    public Multiply clone() {
        return new Multiply(getChild(0).clone(), getChild(1).clone());
    }

}
