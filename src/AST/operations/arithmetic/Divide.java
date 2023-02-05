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

public class Divide extends BinaryOperator {
    public Divide(){}
    public Divide(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("div");
        setEvaluation("div", new Int(), new Int(), new Int(), (x, y) -> new Int(x.asInt() / y.asInt()));
        setEvaluation("div", new Float(), new Int(), new Float(), (x, y) -> new Float(x.asDouble() / y.asDouble()));
        setEvaluation("div", new Int(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() / y.asDouble()));
        setEvaluation("div", new Float(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() / y.asDouble()));
    }

    public String getName() {
        return "div";
    }

    public Divide clone() {
        return new Divide(getChild(0).clone(), getChild(1).clone());
    }

}
