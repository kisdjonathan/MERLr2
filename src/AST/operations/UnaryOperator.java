package AST.operations;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Tuple;
import util.Pair;
import util.Trio;

import java.util.*;
import java.util.function.Function;

public abstract class UnaryOperator extends Operator{
    public static BasicType interpretEvaluate(String op, BasicType first) {
        //TODO first should be SyntaxNode?
        Optional<Map.Entry<BasicType, Trio<BasicType, Function<SyntaxNode, BasicType>, Boolean>>> evaluation = evaluationList.get(op).stream().filter(e -> first.typeEquals(e.getKey())).findFirst();
        if (evaluation.isPresent()) {
            return evaluation.get().getValue().getSecond().apply(first);
        } else {
            throw new Error("Unsupported arguments for " + op + " operator: \n\tfirst: " + first);
        }
    }

    protected static Map<String, List<Map.Entry<BasicType, Trio<BasicType, Function<SyntaxNode, BasicType>, Boolean>>>> evaluationList = new HashMap<>();

    public UnaryOperator(){}

    public UnaryOperator(SyntaxNode a) {
        addChild(a);
    }

    public static void addEvaluationOperation(String op) {
        evaluationList.put(op, new ArrayList<>());
    }
    public static <T extends BasicType, R extends BasicType> void setEvaluation(String op, T t, R r, Function<T, R> f) {
        evaluationList.get(op).add(new AbstractMap.SimpleEntry<>(t, new Trio<>(r, (x)->f.apply((T) x), false)));
    }

    public static <R extends BasicType> void setRawEvaluation(String op, SyntaxNode i, R r, Function<SyntaxNode, R> f) {
        evaluationList.get(op).add(new AbstractMap.SimpleEntry<>((BasicType) i, new Trio<>(r, (Function<SyntaxNode, BasicType>) f, true)));
    }

    private Trio<BasicType, Function<SyntaxNode, BasicType>, Boolean> getEvaluation() {
        BasicType first = getChild(0).getType();
        Optional<Map.Entry<BasicType, Trio<BasicType, Function<SyntaxNode, BasicType>, Boolean>>> evaluation = evaluationList.get(getName()).stream().filter(e -> first.typeEquals(e.getKey())).findFirst();
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
        Trio<BasicType, Function<SyntaxNode, BasicType>, Boolean> evaluation = getEvaluation();
        if (evaluation.getThird()) {
            return getEvaluation().getSecond().apply(getChild(0));
        }
        BasicType first = getChild(0).interpret();
        return getEvaluation().getSecond().apply(first);
    }
}
