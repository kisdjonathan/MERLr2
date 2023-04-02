package AST.abstractNode;

import interpreter.Value;
import type.Situation;
import type.Tuple;
import type.Type;

import java.util.List;
import java.util.function.Function;

public abstract class Operator extends SyntaxNode{
    protected static class Evaluation {
        public Situation situation;
        public Function<Value, Value> interpret;
        public Function<Type, Type> compile;

        public Evaluation(Situation situation, Function<Value, Value> interpret, Function<Type, Type> compile) {
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
        for(Evaluation eval : getEvaluationList()) {
            if(Type.typeEquals(situ, eval.situation))
                return eval;
        }
        throw new Error(errorString("unable to find a matching operator for evaluation"));
    }



    public Value interpret() {
        Evaluation app = getEvaluation();
        return app.interpret.apply(new Tuple(getChildren()).interpret());
    }
}
