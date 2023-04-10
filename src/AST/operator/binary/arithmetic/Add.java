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

public class Add extends BinaryOperator {
    public static final String literal = "add";
    private static final List<Evaluation> evaluationList = List.of(
            new Evaluation(new Situation(new Tuple(new Int(), new Int()), new Int()),
                    from((a,b)->new RawValue(new Int(((Int)a.getValue()).getValue() + ((Int)b.getValue()).getValue()))),
                    partialCompileArithmetic(new AddInt(), false, false)
            ),
            new Evaluation(new Situation(new Tuple(new Int(), new Int()), new Float()),
                    from((a,b)->new RawValue(new Float(((Int)a.getValue()).getValue() + ((Int)b.getValue()).getValue()))),
                    partialCompileArithmetic(new AddFloat(), true, true)
            ),
            new Evaluation(new Situation(new Tuple(new Int(), new Float()), new Float()),
                    from((a,b)->new RawValue(new Float(((Int)a.getValue()).getValue() + ((Float)b.getValue()).getValue()))),
                    partialCompileArithmetic(new AddFloat(), true, false)
            ),
            new Evaluation(new Situation(new Tuple(new Float(), new Int()), new Float()),
                    from((a,b)->new RawValue(new Float(((Float)a.getValue()).getValue() + ((Int)b.getValue()).getValue()))),
                    partialCompileArithmetic(new AddFloat(), false, true)
            ),
            new Evaluation(new Situation(new Tuple(new Float(), new Float()), new Float()),
                    from((a,b)->new RawValue(new Float(((Float)a.getValue()).getValue() + ((Float)b.getValue()).getValue()))),
                    partialCompileArithmetic(new AddFloat(), false, false)
            )
    );

    public String getName() {
        return literal;
    }

    protected List<Evaluation> getEvaluationList() {
        return evaluationList;
    }

    public Type getType() {
        return null;
    }

    public SyntaxNode emptyClone() {
        return new Add();
    }
}
