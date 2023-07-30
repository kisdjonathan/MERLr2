package AST.operator.binary.arithmetic;

import AST.abstractNode.BinaryOperator;
import AST.abstractNode.SyntaxNode;
import compiler.Assembly;
import compiler.commands.Move;
import compiler.commands.Pop;
import compiler.commands.Push;
import compiler.commands.arithmetic.ArithmeticCommand;
import compiler.commands.arithmetic.ConvertIntToFloat;
import compiler.components.Register;
import compiler.components.Size;
import interpreter.RawValue;
import type.Situation;
import type.Tuple;
import type.numerical.Float;
import type.numerical.Int;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public abstract class ArithmeticOperator extends BinaryOperator {
    protected static List<Evaluation> initEvaluationList(
            BiFunction<Integer,Integer,Integer> opInt,
            BiFunction<Double,Double,Double> opFloat,
            Supplier<ArithmeticCommand> cmdInt,
            Supplier<ArithmeticCommand> cmdFloat
            ) {
        return List.of(
                new Evaluation(new Situation(new Tuple(new Int(), new Int()), new Int()),
                        from((a,b)->new RawValue(new Int(opInt.apply(((Int)a.getValue()).getValue(), ((Int)b.getValue()).getValue())))),
                        partialCompileArithmetic(cmdInt.get(), false, false)
                ),
                new Evaluation(new Situation(new Tuple(new Int(), new Int()), new Float()),
                        from((a,b)->new RawValue(new Float(opFloat.apply((double) ((Int)a.getValue()).getValue(), (double) ((Int)b.getValue()).getValue())))),
                        partialCompileArithmetic(cmdFloat.get(), true, true)
                ),
                new Evaluation(new Situation(new Tuple(new Int(), new Float()), new Float()),
                        from((a,b)->new RawValue(new Float(opFloat.apply((double) ((Int)a.getValue()).getValue(), ((Float)b.getValue()).getValue())))),
                        partialCompileArithmetic(cmdFloat.get(), true, false)
                ),
                new Evaluation(new Situation(new Tuple(new Float(), new Int()), new Float()),
                        from((a,b)->new RawValue(new Float(opFloat.apply(((Float)a.getValue()).getValue(), (double) ((Int)b.getValue()).getValue())))),
                        partialCompileArithmetic(cmdFloat.get(), false, true)
                ),
                new Evaluation(new Situation(new Tuple(new Float(), new Float()), new Float()),
                        from((a,b)->new RawValue(new Float(opFloat.apply(((Float)a.getValue()).getValue(), ((Float)b.getValue()).getValue())))),
                        partialCompileArithmetic(cmdFloat.get(), false, false)
                )
        );
    }

    /**
     * for handling the operator with optimization
     * (different commands for int-float, float-int etc combinations)
     */
    protected static List<Evaluation> initEvaluationList(
            BiFunction<Integer,Integer,Integer> opInt,
            BiFunction<Integer,Double,Integer> opIntFloat,
            BiFunction<Double,Integer,Integer> opFloatInt,
            BiFunction<Double,Double,Double> opFloat,
            Supplier<ArithmeticCommand> cmdInt,
            Supplier<ArithmeticCommand> cmdIntFloat,
            Supplier<ArithmeticCommand> cmdFloatInt,
            Supplier<ArithmeticCommand> cmdFloat
    ) {
        //TODO
        return null;
    }


    /**
     * returns the compilation commands for a fundamental arithmetic command on numerical arguments using assemblyCommand as its operator
     * @param assemblyCommand must be a unique, non-reused instance of the command to be used. Will be modified during compilation
     * @return the compilation function
     */
    public static BiConsumer<Tuple, Assembly> partialCompileArithmetic(ArithmeticCommand assemblyCommand, boolean floatFirst, boolean floatSecond) {
        return (inputs, body) -> {
            SyntaxNode first = inputs.getChild(0), second = inputs.getChild(1);
            int firstSize = (first.getType().getIntByteSize()).getValue(), secondSize = (second.getType().getIntByteSize()).getValue();
            assemblyCommand.setArgs(Register.SECONDARY, Register.DEFAULT);
            assemblyCommand.setSize(new Size(Math.max(firstSize, secondSize)));

            first.compile(body);
            if(floatFirst)
                body.addCommand(new ConvertIntToFloat(Register.DEFAULT));
            body.addCommand(new Push(Register.SECONDARY, firstSize));
            body.addCommand(new Move(Register.DEFAULT, Register.SECONDARY, firstSize));
            second.compile(body);
            if(floatSecond)
                body.addCommand(new ConvertIntToFloat(Register.DEFAULT));
            body.addCommand(assemblyCommand);
            body.addCommand(new Pop(Register.SECONDARY, firstSize));
        };
    }
}
