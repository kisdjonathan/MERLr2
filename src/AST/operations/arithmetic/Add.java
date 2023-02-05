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

public class Add extends BinaryOperator {
    public Add(){}
    public Add(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("add");
        setEvaluation("add", new Int(), new Int(), new Int(), (x, y) -> new Int(x.asInt() + y.asInt()));
        setEvaluation("add", new Float(), new Int(), new Float(), (x, y) -> new Float(x.asDouble() + y.asDouble()));
        setEvaluation("add", new Int(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() + y.asDouble()));
        setEvaluation("add", new Float(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() + y.asDouble()));
        setEvaluation("add", new Str(), new Str(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setEvaluation("add", new Str(), new Int(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setEvaluation("add", new Int(), new Str(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setEvaluation("add", new Str(), new Float(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setEvaluation("add", new Float(), new Str(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
    }

    public String getName() {
        return "add";
    }

    public Add clone() {
        return new Add(getChild(0).clone(), getChild(1).clone());
    }
}
