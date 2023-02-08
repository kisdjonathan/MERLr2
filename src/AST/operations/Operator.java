package AST.operations;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Tuple;
import util.Pair;
import util.Trio;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class Operator extends SyntaxNode {
    public String getOperator(int index) {
        return null;
    }

    public Operator(){}

    public Operator(SyntaxNode... nodes) {
        for (SyntaxNode n : nodes) {
            addChild(n);
        }
    }

    protected static void addEvaluationOperation(String op) {
        evaluationList.put(op, new ArrayList<>());
    }

    protected static Map<String,List<Map.Entry<Tuple, Trio<BasicType, Function<Tuple, BasicType>, Boolean>>>> evaluationList = new HashMap<>();

    public static <T extends BasicType, R extends BasicType> void setUnaryEvaluation(String op, T t, R r, Function<T, R> f) {
        evaluationList.get(op).add(new AbstractMap.SimpleEntry<>(new Tuple(t), new Trio<>(r, syntaxNodes -> f.apply((T) syntaxNodes.getChild(0).interpret()), false)));
    }

    protected static <T extends BasicType, U extends BasicType, R extends BasicType> void setBinaryEvaluation(String op, T first, U second, R ret, BiFunction<T, U, R> bf) {
        evaluationList.get(op).add(new AbstractMap.SimpleEntry<>(new Tuple(first, second), new Trio<>(ret, syntaxNodes -> bf.apply((T) syntaxNodes.getChild(0).interpret(), (U) syntaxNodes.getChild(1).interpret()), false)));
    }

    protected static <R extends BasicType> void setRawBinaryEvaluation(String op, SyntaxNode first, SyntaxNode second, R ret, BiFunction<SyntaxNode, SyntaxNode, R> bf) {
        evaluationList.get(op).add(new AbstractMap.SimpleEntry<>(new Tuple(first, second), new Trio<>(ret, syntaxNodes -> bf.apply(syntaxNodes.getChild(0), syntaxNodes.getChild(1)), true)));
    }

    private Trio<BasicType, Function<Tuple, BasicType>, Boolean> getEvaluation() {
        Tuple args = new Tuple(getChildren().stream().map(SyntaxNode::getType).collect(Collectors.toList()));
        Optional<Map.Entry<Tuple, Trio<BasicType, Function<Tuple, BasicType>, Boolean>>> evaluation = evaluationList.get(getName()).stream().filter(e -> args.typeEquals(e.getKey())).findFirst();
        if (evaluation.isPresent()) {
            return evaluation.get().getValue();
        } else {
            throw new Error("Unsupported arguments for " + getName() + " operator: \n\tfirst: " + getChild(0).getType() + (getChildren().size() <= 1 ? "" : "\n\tsecond:" + getChild(1).getType()));
        }
    }

    @Override
    public BasicType getType() {
        return getEvaluation().getFirst();
    }

    @Override
    public BasicType interpret() {
        Trio<BasicType, Function<Tuple, BasicType>, Boolean> evaluation = getEvaluation();
        if (evaluation.getThird()) {
            return evaluation.getSecond().apply(new Tuple(getChildren()));
        } else {
            return getEvaluation().getSecond().apply(new Tuple(getChildren().stream().map(SyntaxNode::interpret).collect(Collectors.toList())));
        }
    }

    public static BasicType interpretEvaluate(String op, Tuple args) {
        Optional<Map.Entry<Tuple, Trio<BasicType, Function<Tuple, BasicType>, Boolean>>> evaluation = evaluationList.get(op).stream().filter(e -> args.typeEquals(e.getKey())).findFirst();
        if (evaluation.isPresent()) {
            return evaluation.get().getValue().getSecond().apply(args);
        } else {
            throw new Error("Unsupported arguments for " + op + " operator: \n\tfirst: " + args.getChild(0).getType() + (args.getChildren().size() <= 1 ? "" : "\n\tsecond:" + args.getChild(1).getType()));
        }
    }

    public abstract String getName();

    public String toString() {
        return getName() + getChildren();
    }
}

