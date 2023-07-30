package AST.operator.binary.arithmetic;

import AST.abstractNode.BinaryOperator;
import AST.abstractNode.SyntaxNode;
import compiler.commands.Move;
import compiler.commands.Pop;
import compiler.commands.Push;
import compiler.commands.arithmetic.AddFloat;
import compiler.commands.arithmetic.AddInt;
import compiler.components.Register;
import interpreter.RawValue;
import type.Situation;
import type.Tuple;
import type.Type;
import type.numerical.Float;
import type.numerical.Int;

import java.util.List;

public class Add extends ArithmeticOperator {
    public static final String literal = "add";
    private static final List<Evaluation> evaluationList = initEvaluationList(
            Integer::sum,
            Double::sum,
            AddInt::new,
            AddFloat::new
    );

    public String getName() {
        return literal;
    }
    protected List<Evaluation> getEvaluationList() {
        return evaluationList;
    }

    public SyntaxNode emptyClone() {
        return new Add();
    }
}
