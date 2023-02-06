package AST.operations;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import util.Pair;

import java.util.*;
import java.util.function.Function;

public abstract class UnaryOperator extends Operator{

    protected static Map<String, List<Map.Entry<BasicType, Pair<BasicType, Function<BasicType, BasicType>>>>> evaluationList = new HashMap<>();

    public UnaryOperator(){}

    public UnaryOperator(SyntaxNode a) {
        addChild(a);
    }

    public static void addEvaluationOperation(String op) {
        evaluationList.put(op, new ArrayList<>());
    }
    public static <T extends BasicType, R extends BasicType> void setEvaluation(String op, T t, R r, Function<T, R> f) {
        evaluationList.get(op).add(new AbstractMap.SimpleEntry<>(t, new Pair<>(r, (Function<BasicType, BasicType>) f)));
    }

    private Pair<BasicType, Function<BasicType, BasicType>> getEvaluation() {
        BasicType first = getChild(0).getType();
        Optional<Map.Entry<BasicType, Pair<BasicType, Function<BasicType, BasicType>>>> evaluation = evaluationList.get(getName()).stream().filter(e -> first.typeEquals(e.getKey())).findFirst();
        if (evaluation.isPresent()) {
            return evaluation.get().getValue();
        } else {
            throw new Error("Unsupported arguments for " + getName() + " operator: \n\tfirst: " + first);
        }
    }

    @Override
    public BasicType getType() {
        return getEvaluation().getFirst();
    }

    @Override
    public BasicType interpret() {
        BasicType first = getChild(0).interpret();
        return getEvaluation().getSecond().apply(first);
    }
}
