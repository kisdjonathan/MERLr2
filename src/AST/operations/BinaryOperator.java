package AST.operations;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.*;
import util.Pair;

import java.util.*;
import java.util.function.BiFunction;

public abstract class BinaryOperator extends Operator{

    protected static Map<String,List<Map.Entry<Tuple, Pair<BasicType, BiFunction<BasicType, BasicType, BasicType>>>>> evaluationList = new HashMap<>();

    public BinaryOperator(){}

    public BinaryOperator(SyntaxNode a, SyntaxNode b) {
        addChild(a);
        addChild(b);
    }

    protected static void addEvaluationOperation(String op) {
        evaluationList.put(op, new ArrayList<>());
    }
    protected static <T extends BasicType, U extends BasicType, R extends BasicType> void setEvaluation(String op, T first, U second, R ret, BiFunction<T, U, R> bf) {
        evaluationList.get(op).add(new AbstractMap.SimpleEntry<>(new Tuple(first, second), new Pair<>(ret, (x, y) -> bf.apply((T) x, (U) y))));
    }

    private Pair<BasicType, BiFunction<BasicType, BasicType, BasicType>> getEvaluation() {
        BasicType first = getChild(0).getType();
        BasicType second = getChild(1).getType();
        Tuple args = new Tuple(first, second);
        Optional<Map.Entry<Tuple, Pair<BasicType, BiFunction<BasicType, BasicType, BasicType>>>> evaluation = evaluationList.get(getName()).stream().filter(e -> args.typeEquals(e.getKey())).findFirst();
        if (evaluation.isPresent()) {
            return evaluation.get().getValue();
        } else {
            throw new Error("Unsupported arguments for " + getName() + " operator: \n\tfirst: " + first + "\n\tsecond:" + second);
        }
    }

    @Override
    public BasicType getType() {
        return getEvaluation().getFirst();
    }

    @Override
    public BasicType interpret() {
        BasicType first = getChild(0).interpret();
        BasicType second = getChild(1).interpret();
        return getEvaluation().getSecond().apply(first, second);
    }

}
