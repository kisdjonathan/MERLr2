package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.*;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Int;
import AST.operations.Operator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public abstract class BitwiseOperator extends Operator {

    // list of evaluations
    protected Map<Tuple, BiFunction<BasicType, BasicType, BasicType>> evaluationMap;
    protected Map<Tuple, BasicType> returnMap;

    public BitwiseOperator() {}
    public BitwiseOperator(SyntaxNode a, SyntaxNode b) {
        addChild(a);
        addChild(b);
        evaluationMap = new HashMap<>();
        returnMap = new HashMap<>();
        setEvaluations();
    }

    protected abstract void setEvaluations();

    protected <T extends BasicType, U extends BasicType, R extends BasicType> void setEvaluation(T first, U second, R ret, BiFunction<T, U, R> bf) {
        evaluationMap.put(new Tuple(first, second), (x, y) -> bf.apply((T) x, (U) y));
    }

    //TODO
    public BasicType getType() {
        BasicType first = getChild(0).getType();
        BasicType second = size() > 1 ?  getChild(1).getType() : null;
        Tuple args = new Tuple(first, second);
        if (returnMap.containsKey(args)) {
            return returnMap.get(args);
        } else {
            throw new Error("Unsupported arguments for " + getName() + " operator: \n\tfirst: " + first + "\n\tsecond:" + second);
        }
    }

    public BasicType interpret() {
        BasicType first = getChild(0).interpret();
        BasicType second = size() > 1 ?  getChild(1).interpret() : null;
        Tuple args = new Tuple(first, second);
        BasicType outType = getType();
        if (outType != null) {
            return evaluationMap.get(args).apply(first, second);
        } else {
            //TODO
            throw new Error("invalid types for " + getName());
        }
    }

    protected abstract Bool interpretBools(Bool first, Bool second);

    protected abstract Int interpretBytes(Int first, Int second);

}
