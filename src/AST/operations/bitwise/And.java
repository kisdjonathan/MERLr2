package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.*;
import AST.baseTypes.Float;
import AST.baseTypes.Str;
import AST.operations.BinaryOperator;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class And extends BinaryOperator {
    public And() {}
    public And(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("and");
        setEvaluation("and", new Bool(), new Bool(), new Bool(), (x, y) -> new Bool(x.getValue() && y.getValue()));
        setEvaluation("and", new Int(), new Int(), new Int(), (x, y) -> new Int(x.getValue() & y.getValue()));
    }

    public And clone() {
        return new And(getChild(0).clone(), getChild(1).clone());
    }

    public String getName() {
        return "and";
    }
}
