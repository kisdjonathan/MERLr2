package AST.operations;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.*;
import util.Trio;

import java.util.*;
import java.util.function.BiFunction;

public abstract class BinaryOperator extends Operator{
    public static BasicType interpretEvaluate(String op, BasicType first, BasicType second) {
        Tuple args = new Tuple(first, second);
        Optional<Map.Entry<Tuple, Trio<BasicType, BiFunction<SyntaxNode, SyntaxNode, BasicType>, Boolean>>> evaluation = evaluationList.get(op).stream().filter(e -> args.typeEquals(e.getKey())).findFirst();
        if (evaluation.isPresent()) {
            return evaluation.get().getValue().getSecond().apply(first, second);
        } else {
            throw new Error("Unsupported arguments for " + op + " operator: \n\tfirst: " + first + "\n\tsecond:" + second);
        }
    }

    protected static Map<String,List<Map.Entry<Tuple, Trio<BasicType, BiFunction<SyntaxNode, SyntaxNode, BasicType>, Boolean>>>> evaluationList = new HashMap<>();

    public BinaryOperator(){}

    public BinaryOperator(SyntaxNode a, SyntaxNode b) {
        addChild(a);
        addChild(b);
    }

    protected static void addEvaluationOperation(String op) {
        evaluationList.put(op, new ArrayList<>());
    }
    protected static <T extends BasicType, U extends BasicType, R extends BasicType> void setEvaluation(String op, T first, U second, R ret, BiFunction<T, U, R> bf) {
        evaluationList.get(op).add(new AbstractMap.SimpleEntry<>(new Tuple(first, second), new Trio<>(ret, (x, y) -> bf.apply((T) x, (U) y), false)));
    }

    protected static <R extends BasicType> void setRawEvaluation(String op, SyntaxNode first, SyntaxNode second, R ret, BiFunction<SyntaxNode, SyntaxNode, R> bf) {
        evaluationList.get(op).add(new AbstractMap.SimpleEntry<>(new Tuple(first, second), new Trio<>(ret, bf::apply, true)));
    }

    private Trio<BasicType, BiFunction<SyntaxNode, SyntaxNode, BasicType>, Boolean> getEvaluation() {
        BasicType first = getChild(0).getType();
        BasicType second = getChild(1).getType();
        Tuple args = new Tuple(first, second);
        Optional<Map.Entry<Tuple, Trio<BasicType, BiFunction<SyntaxNode, SyntaxNode, BasicType>, Boolean>>> evaluation = evaluationList.get(getName()).stream().filter(e -> args.typeEquals(e.getKey())).findFirst();
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
        Trio<BasicType, BiFunction<SyntaxNode, SyntaxNode, BasicType>, Boolean> evaluation = getEvaluation();
        if (evaluation.getThird()) {
            return evaluation.getSecond().apply(getChild(0), getChild(1));
        } else {
            BasicType first = getChild(0).interpret();
            BasicType second = getChild(1).interpret();
            return getEvaluation().getSecond().apply(first, second);
        }
    }

}
