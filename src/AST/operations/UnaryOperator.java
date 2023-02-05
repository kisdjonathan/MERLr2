package AST.operations;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import util.Pair;

import java.util.*;
import java.util.function.Function;

public abstract class UnaryOperator extends Operator{

    protected static List<Map.Entry<BasicType, Pair<BasicType, Function<BasicType, BasicType>>>> evaluationList = new ArrayList<>();;

    public UnaryOperator(){}

    public UnaryOperator(SyntaxNode a) {
        addChild(a);
    }

    public static <T extends BasicType, R extends BasicType> void setEvaluation(T t, R r, Function<T, R> f) {
        evaluationList.add(new AbstractMap.SimpleEntry<>(t, new Pair<>(r, (Function<BasicType, BasicType>) f)));
    }

    private Pair<BasicType, Function<BasicType, BasicType>> getEvaluation() {
        BasicType first = getChild(0).getType();
        Optional<Map.Entry<BasicType, Pair<BasicType, Function<BasicType, BasicType>>>> evaluation = evaluationList.stream().filter(e -> first.typeEquals(e.getKey())).findFirst();
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
