package AST.abstractNode;

import compiler.Assembly;
import interpreter.MultiValue;
import interpreter.ReturnValue;
import interpreter.Value;
import type.Situation;
import type.Tuple;
import type.Type;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class Operator extends SyntaxNode{
    protected static class Evaluation {
        public Situation situation;
        public Function<MultiValue, Value> interpret;
        public BiConsumer<Tuple, Assembly> compile;

        public Evaluation(Situation situation, Function<MultiValue, Value> interpret, BiConsumer<Tuple, Assembly> compile) {
            this.situation = situation;
            this.interpret = interpret;
            this.compile = compile;
        }
    }

    /**
     * returns the name of the operator that is referenced for operator overloading
     */
    public abstract String getName();

    /**
     * returns a list of applications of the operator in the order of top to bottom
     */
    protected abstract List<Evaluation> getEvaluationList();
    protected Evaluation getEvaluation() {
        Situation situ = new Situation(new Tuple(getChildren()), getType());
        Evaluation nextBest = null;
        for(Evaluation eval : getEvaluationList()) {
            if(Type.typeEquals(situ, eval.situation))
                return eval;
            else if(eval.situation.typeEquals(situ))
                nextBest = eval;
        }
        if(nextBest == null)
            throw new Error(errorString("unable to find a matching operator for evaluation"));
        else
            return nextBest;
    }

    public Value interpret() {
        Value args = new Tuple(getChildren()).interpret();
        if(args instanceof ReturnValue ret)
            return ret;
        Evaluation app = getEvaluation();
        return app.interpret.apply((MultiValue) args);
    }

    public void compile(Assembly body) {
        getEvaluation().compile.accept(new Tuple(getChildren()), body);
    }
}
