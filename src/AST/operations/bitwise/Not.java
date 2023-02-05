package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Bool;
import AST.baseTypes.Int;
import AST.baseTypes.Tuple;
import AST.operations.UnaryOperator;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class Not extends UnaryOperator {
    public Not(){}

    static  {
        addEvaluationOperation("not");
        setEvaluation("not", new Bool(), new Bool(), (x) -> new Bool(!x.getValue()));
        setEvaluation("not", new Int(), new Int(), (x) -> new Int(~x.getValue()));
    }

    public Not(SyntaxNode value) {
        addChild(value);
    }

    public Not clone() {
        return new Not(getChild(0).clone());
    }

    public String getName() {
        return "not";
    }
}
