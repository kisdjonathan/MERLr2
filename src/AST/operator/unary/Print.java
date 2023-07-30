package AST.operator.unary;

import AST.abstractNode.PrefixOperator;
import AST.abstractNode.SyntaxNode;
import compiler.Assembly;
import compiler.commands.print.*;
import compiler.components.Register;
import compiler.components.Size;
import interpreter.MultiValue;
import interpreter.Value;
import type.*;
import type.numerical.Int;
import type.numerical.Float;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Print extends PrefixOperator {
    public static final String literal = "print";
    private static final Function<MultiValue, Value> printEvaluation = from((arg)->{
        System.out.println(arg.getValue().valueString());
        return arg;
    });
    private static BiConsumer<Tuple, Assembly> printAssemblyFrom(PrintCommand assemblyCommand, boolean varSize) {
        if(varSize){    //string
            return (inputs, body) -> {
                SyntaxNode child = inputs.getChild(0);
                assemblyCommand.setArgs(Register.DEFAULT);
                assemblyCommand.setSize(new Size(Register.SECONDARY));

                child.compile(body);
                body.addCommand(assemblyCommand);
            };
        }
        else {
            return (inputs, body) -> {
                SyntaxNode child = inputs.getChild(0);
                assemblyCommand.setArgs(Register.DEFAULT);
                assemblyCommand.setSize(new Size(child.getType().getIntByteSize().getValue()));

                child.compile(body);
                body.addCommand(assemblyCommand);
            };
        }
    }
    private static final List<Evaluation> evaluationList = List.of(
            new Evaluation(new Situation(new Int(), new Int()),
                    printEvaluation,
                    printAssemblyFrom(new PrintInt(), false)),
            new Evaluation(new Situation(new Float(), new Float()),
                    printEvaluation,
                    printAssemblyFrom(new PrintFloat(), false)),
            new Evaluation(new Situation(new Str(), new Str()),
                    printEvaluation,
                    printAssemblyFrom(new PrintStr(), true))
    );

    public String getName() {
        return literal;
    }
    public List<Evaluation> getEvaluationList() {
        return evaluationList;
    }

    public SyntaxNode emptyClone() {
        return new Print();
    }
}
