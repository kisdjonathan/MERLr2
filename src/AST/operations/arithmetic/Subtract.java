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

public class Subtract extends BinaryOperator {
    public Subtract(){}
    public Subtract(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("sub");
        setEvaluation("sub", new Int(), new Int(), new Int(), (x, y) -> new Int(x.asInt() - y.asInt()));
        setEvaluation("sub", new Float(), new Int(), new Float(), (x, y) -> new Float(x.asDouble() - y.asDouble()));
        setEvaluation("sub", new Int(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() - y.asDouble()));
        setEvaluation("sub", new Float(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() - y.asDouble()));
    }

    public String getName() {
        return "sub";
    }

    public Subtract clone() {
        return new Subtract(getChild(0).clone(), getChild(1).clone());
    }
}
